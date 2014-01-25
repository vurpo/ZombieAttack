package org.sandholm.max.zombieattack;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by max on 1/19/14.
 */
public class Zombie {

    static final float SPEED = 1.5f;

    Vector2 velocity = new Vector2();
    Rectangle bounds = new Rectangle();
    Random random = new Random();
    boolean facingLeft;
    World world;

    int health = 100;

    public Zombie(Vector2 position, boolean facingLeft, World world) {
        this.world = world;
        this.bounds.setPosition(position);
        this.bounds.height = 1f;
        this.bounds.width = 0.5f;
        this.facingLeft = facingLeft;
        velocity.x = (random.nextFloat()-0.5f) + SPEED;
        if (facingLeft) {
            velocity.x = -velocity.x;
        }
    }

    public boolean damage(int amount) {  //returns true if zombie died
        health -= amount;
        if (health <= 0) {
            die();
            return true;
        }
        return false;
    }

    public void die() {
        world.killZombie(this);
    }

    public void update(float delta) {
        Vector2 position = getPosition();
        bounds.setPosition(position.add(velocity.cpy().scl(delta)));
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
