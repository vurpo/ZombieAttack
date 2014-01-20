package org.sandholm.max.zombieattack;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by max on 1/17/14.
 */
public class Player {



    public enum State {
        IDLE, WALKING, JUMPING
    }

    static final float SPEED = 3.0f;
    static final float JUMP_VELOCITY =  1.0f;

    Vector2 velocity = new Vector2();
    Rectangle bounds = new Rectangle();
    State state = State.IDLE;
    boolean facingLeft = true;
    float gunAngle;
    float fireFrequency;

    int health = 100;

    public Player(Vector2 position) {
        this.bounds.setPosition(position);
        this.bounds.height = 1f;
        this.bounds.width = 0.5f;
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
}
