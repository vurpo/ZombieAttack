package org.sandholm.max.zombieattack;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by max on 1/18/14.
 */
public class PlayerController {
    private float lastDelta;

    enum Keys {
        LEFT, RIGHT, JUMP, FIRE
    }

    private World world;
    private Player player;

    static Map<Keys, Boolean> keys = new HashMap<PlayerController.Keys, Boolean>();
    static {
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.JUMP, false);
        keys.put(Keys.FIRE, false);
    }

    public PlayerController(World world) {
        this.world = world;
        this.player = world.getPlayer();
    }

    private float deadZone(float value, float deadzone) {
        if (Math.abs(value) < Math.abs(deadzone)) {
            return 0f;
        }
        return value;
    }

    private Vector2 deadZone(Vector2 vector, float deadzone) {
        if (vector.len() < Math.abs(deadzone)) {
            return new Vector2(0f, 0f);
        }
        return vector;
    }

    // ** Key presses and touches **************** //

    public void leftPressed() {
        keys.get(keys.put(Keys.LEFT, true));
    }

    public void rightPressed() {
        keys.get(keys.put(Keys.RIGHT, true));
    }

    private float xAxisAmount;
    public void xAxis(float amount) {
        xAxisAmount = deadZone(amount, 0.1f);
    }

    Vector2 rightAxis = new Vector2(0f,0f);
    public void rightXAxis(float amount) {
        rightAxis.x = amount;
    }

    public void rightYAxis(float amount) {
        rightAxis.y = -amount; //invert the Y axis because the libGDX Y axis is inverted compared to the controller's Y axis
    }

    float rightTrigger;
    public void rightTrigger(float amount) {
        rightTrigger = amount;
    }

    public void jumpPressed() {
        keys.get(keys.put(Keys.JUMP, true));
    }

    public void leftReleased() {
        keys.get(keys.put(Keys.LEFT, false));
    }

    public void rightReleased() {
        keys.get(keys.put(Keys.RIGHT, false));
    }

    public void jumpReleased() {
        keys.get(keys.put(Keys.JUMP, false));
    }

    public void fireBullet() {
        if (player.getAmmo() > 0) {
            world.spawnBullet(player.getBarrelEnd(), player.getGunAngle(), Player.BULLET_VELOCITY);
            player.addAmmo(-1);
        }
    }

    float fireFrequency = -1f;

    float fireCounter = 0f;
    private void setFireFrequency(float trigger) {
        if (trigger > 0.1f) {
            fireFrequency = 1f-(trigger*0.6f);
        }
        else {
            fireFrequency = -1f;
        }
    }

    /** The main update method **/
    public void update(float delta) {
        processInput();
        if (rightTrigger > 0.1f && fireCounter <= 0f) {
            fireBullet();
            fireCounter = fireFrequency;
        }
        else if (rightTrigger > 0.1f) {
            fireCounter -= delta;
        }
        for (Zombie zombie : world.getZombies()) {
            if (player.getBounds().overlaps(zombie.getBounds())) {
                player.damage(0.005f*(-zombie.sizeModifier+0.8f));
            }
        }
        for (AmmoPack ammoPack : world.getAmmoPacks()) {
            if (player.getBounds().overlaps(ammoPack.getBounds())) {
                player.addAmmo(50);
                world.removeAmmoPack(ammoPack);
            }
        }
        player.update(delta);
    }

    /** Change the player's state and parameters based on input controls **/
    private void processInput() {
        Rectangle tempPlayerBounds = new Rectangle(player.getBounds());
        Vector2 tempPlayerVelocity = new Vector2(player.getVelocity());
        Player.State tempPlayerState = player.getState();
        //velocity controls
        if (keys.get(Keys.LEFT)) {
            // left is pressed
            player.setFacingLeft(true);
            tempPlayerState = Player.State.WALKING;
            tempPlayerVelocity.x = -Player.SPEED;
        }
        if (keys.get(Keys.RIGHT)) {
            // left is pressed
            player.setFacingLeft(false);
            tempPlayerState = Player.State.WALKING;
            tempPlayerVelocity.x = Player.SPEED;
        }

        if (xAxisAmount != 0) {
            player.setFacingLeft(xAxisAmount < 0);
            tempPlayerState = Player.State.WALKING;
            tempPlayerVelocity.x = Player.SPEED*xAxisAmount;
        }
        else if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) ||
                (!keys.get(Keys.LEFT) && !(keys.get(Keys.RIGHT)))) {
            player.setState(Player.State.IDLE);
            // horizontal speed is 0
            tempPlayerVelocity.x = 0;
        }


        player.setState(tempPlayerState);
        player.setVelocity(tempPlayerVelocity);


        if (keys.get(Keys.JUMP)) {
            player.jump();
        }

        rightAxis.clamp(0f, 1f);
        //gun controls
        if (rightAxis.len() > 0.1f) { //deadzone
            player.setGunAngle(rightAxis.angle());
        }

        setFireFrequency(rightTrigger);
    }
}
