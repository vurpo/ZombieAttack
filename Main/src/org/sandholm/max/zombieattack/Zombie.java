package org.sandholm.max.zombieattack;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by max on 1/19/14.
 */
public class Zombie {

    static final float SPEED = 1.75f;

    float sizeModifier;

    Vector2 velocity = new Vector2();
    Rectangle bounds = new Rectangle();
    Random random = new Random();
    boolean rightOfPlayer;
    float distanceToPlayer;
    World world;

    float health = 1f;

    public Zombie(Vector2 position, boolean facingLeft, World world) {
        this.world = world;
        this.bounds.setPosition(position);
        this.rightOfPlayer = facingLeft;
        sizeModifier = random.nextFloat()-0.3f;
        this.bounds.height = 1f+(-sizeModifier/2);
        this.bounds.width = 0.5f+(-sizeModifier/4);
        velocity.x = (sizeModifier * 3f) + SPEED;
        if (facingLeft) {
            velocity.x = -velocity.x;
        }
    }

    public boolean damage(float amount) {  //returns true if zombie died
        health -= amount+(sizeModifier*0.2f);
        if (health <= 0f) {
            die();
            return true;
        }
        return false;
    }

    public void die() {
        world.zombiesKilled += 1;
        world.killZombie(this);
    }

    public void update(float delta) {
        distanceToPlayer = world.getPlayer().getBounds().getCenter(new Vector2()).x-getBounds().getCenter(new Vector2()).x;
        if (Math.abs(distanceToPlayer) <= 1f) {
            velocity.x = ((sizeModifier * 3f) + SPEED) * distanceToPlayer;
        }
        else if (distanceToPlayer < -1f) {
            velocity.x = ((sizeModifier * 3f) + SPEED) * -1;
        }
        else if (distanceToPlayer > 1f) {
            velocity.x = (sizeModifier * 3f) + SPEED;
        }
        Vector2 position = getPosition();
        bounds.setPosition(position.add(velocity.cpy().scl(delta).scl((health*0.75f)+0.25f)));
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getPosition() {
        return bounds.getPosition(new Vector2());
    }

    public void changeDirection() {
        velocity.x = -velocity.x;
    }
}
