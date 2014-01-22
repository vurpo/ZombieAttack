package org.sandholm.max.zombieattack;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by max on 1/17/14.
 */
public class Player {


    public State getState() {
        return state;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public enum State {
        IDLE, WALKING, JUMPING
    }

    static final float SPEED = 3.0f;
    static final float JUMP_VELOCITY =  1.0f;
    static final float BULLET_VELOCITY = 48f;

    Vector2 velocity = new Vector2();
    Rectangle bounds = new Rectangle();

    World world;

    Vector2 barrelEnd = new Vector2();
    State state = State.IDLE;
    boolean facingLeft = true;
    float gunAngle;
    float fireFrequency;

    int health = 100;
    int bullets = 100;

    public Player(Vector2 position, World world) {
        this.bounds.setPosition(position);
        this.bounds.height = 1f;
        this.bounds.width = 0.5f;
        this.world = world;
    }

    public boolean isInsideWorldBounds(float delta) {
        Vector2 position = new Vector2(getPosition());
        position.add(velocity.cpy().scl(delta));
        if (position.x >= world.getLevelBounds().x
         && position.x+getBounds().width <= world.getLevelBounds().x+world.getLevelBounds().width) {
            return true;
        }
        return false;
    }

    public void update(float delta) {
        Vector2 position = new Vector2(getPosition());
        position.add(velocity.cpy().scl(delta));
        if (!isInsideWorldBounds(delta)){
            velocity.set(0f, 0f);
            return;
        }
        bounds.setPosition(position);

    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getPosition() {
        return bounds.getPosition(new Vector2());
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setState(State newState) {
        this.state = newState;
    }

    public void setFacingLeft(boolean facingLeft) {
        this.facingLeft = facingLeft;
    }

    public float getGunAngle() {
        return gunAngle;
    }

    public void setGunAngle(float gunAngle) {
        this.gunAngle = gunAngle;
    }

    public float getFireFrequency() {
        return fireFrequency;
    }

    public void setFireFrequency(float fireFrequency) {
        this.fireFrequency = fireFrequency;
    }

    public Vector2 getBarrelEnd() {
        return getPosition().cpy().add(new Vector2(0.26f, 0.72f)).add(barrelEnd);
    }

    public void setBarrelEnd(Vector2 barrelEnd) {
        this.barrelEnd = barrelEnd;
    }
}
