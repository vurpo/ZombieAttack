package org.sandholm.max.zombieattack;

import com.badlogic.gdx.math.Vector2;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by max on 1/18/14.
 */
public class PlayerController {
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
    };

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

    /** The main update method **/
    public void update(float delta) {
        processInput();
        player.update(delta);
    }

    /** Change the player's state and parameters based on input controls **/
    private void processInput() {
        //velocity controls
        if (keys.get(Keys.LEFT)) {
            // left is pressed
            player.setFacingLeft(true);
            player.setState(Player.State.WALKING);
            player.getVelocity().x = -Player.SPEED;
        }
        if (keys.get(Keys.RIGHT)) {
            // left is pressed
            player.setFacingLeft(false);
            player.setState(Player.State.WALKING);
            player.getVelocity().x = Player.SPEED;
        }

        if (xAxisAmount != 0) {
            player.setFacingLeft(xAxisAmount < 0);
            player.setState(Player.State.WALKING);
            player.getVelocity().x = Player.SPEED*xAxisAmount;
        }
        else if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) ||
                (!keys.get(Keys.LEFT) && !(keys.get(Keys.RIGHT)))) {
            player.setState(Player.State.IDLE);
            // horizontal speed is 0
            player.getVelocity().x = 0;
        }

        //gun controls
        if (rightAxis.len() > 0.5f) { //deadzone
            player.setGunAngle(rightAxis.angle());
        }

        player.setFireFrequency(rightTrigger);
    }
}
