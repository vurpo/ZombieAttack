package org.sandholm.max.zombieattack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

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
        IDLE, WALKING
    }

    public boolean jumping = false;

    static final float SPEED = 2.5f;
    static final float JUMP_VELOCITY =  2.0f;
    static final float BULLET_VELOCITY = 48f;

    Vector2 velocity = new Vector2();
    Rectangle bounds = new Rectangle();

    World world;

    Vector2 barrelEnd = new Vector2();
    State state = State.IDLE;
    boolean facingLeft = true;
    float gunAngle;

    private Texture standingTexture;
    private Array<Texture> runningTextures = new Array<Texture>();
    private Texture jumpingTexture;

    public float getHealth() {
        return health;
    }

    float health = 1f;
    int bullets = 100;

    public boolean damage(float amount) {
        if (health - amount >= 0) {
            health -= amount;
            return false; //didn't die
        }
        else {
            health = 0f;
            return true;  //died
        }
    }

    private void loadTextures() {
        standingTexture = new Texture(Gdx.files.internal("guy_1080.png"));
    }

    public Texture getCurrentTexture() {
        return standingTexture;
    }

    public Player(Vector2 position, World world) {
        loadTextures();
        this.bounds.setPosition(position);
        this.bounds.height = 1f;
        this.bounds.width = 0.5f;
        this.world = world;
    }

    public boolean isInsideLevelBounds(float delta) {
        Vector2 position = new Vector2(getPosition());
        position.add(velocity.cpy().scl(delta));
        return position.x >= world.getLevelBounds().x && position.x+getBounds().width <= world.getLevelBounds().x+world.getLevelBounds().width;
    }

    public void update(float delta) {
        Vector2 position = new Vector2(getPosition());
        if (velocity.y != 0f) {
            velocity.y -= 8*delta;  //I'm falling!
        }
        position.add(velocity.cpy().scl(delta).scl((health*0.75f)+0.25f));
        if (!isInsideLevelBounds(delta)){
            velocity.x = 0f;
            setState(State.IDLE); //we don't want the player to walk out of the world
            return;
        }
        if (position.y <= world.getLevelBounds().y) { //is the player at or below ground level?
            velocity.y = 0f;                           //set the player to ground level and his y-velocity to 0
            position.y = world.getLevelBounds().y;
            jumping = false;
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

    public void jump() {
        if (!jumping) {
            getVelocity().y = JUMP_VELOCITY;
            bounds.getPosition(new Vector2()).y = 5f;
            jumping = true;
        }
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

    public Vector2 getBarrelEnd() {
        return getPosition().cpy().add(new Vector2(0.26f, 0.72f)).add(barrelEnd); //this is the in-world position
    }                                                                             //of the tip of the gun's barrel

    public void setBarrelEnd(Vector2 barrelEnd) {
        this.barrelEnd = barrelEnd;
    }
}
