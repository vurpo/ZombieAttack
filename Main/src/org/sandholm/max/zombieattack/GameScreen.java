package org.sandholm.max.zombieattack;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.controllers.mappings.Ouya;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by max on 1/17/14.
 */
public class GameScreen implements Screen, ControllerListener, InputProcessor {
    private World world;
    private WorldRenderer renderer;
    private PlayerController playerController;
    private ZombieController zombieController;

    private boolean runningOnOuya;
    private boolean controllerIsOuya;
    private Controller controller;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        playerController.update(delta);
        zombieController.update(delta);
        renderer.render();
    }


    @Override
    public void resize(int w, int h) {
        //renderer.setSize(w, h);
    }

    @Override
    public void show() {
        world = new World();
        renderer = new WorldRenderer(world, true);  //world, debug rendering
        playerController = new PlayerController(world);
        zombieController = new ZombieController(world);
        if (Controllers.getControllers().size >= 1) {
            controller = Controllers.getControllers().first();
        }
        Controllers.addListener(this);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    //CONTROLLER METHODS
    @Override
    public void connected(Controller controller) {
        if (this.controller == null) {
            this.controller = controller;
        }
    }

    @Override
    public void disconnected(Controller controller) {
        if (this.controller == controller) {
            this.controller = null;
        }
    }

    @Override
    public boolean buttonDown(Controller controller, int i) {
        if (controller == this.controller) {
            if (i == Ouya.BUTTON_DPAD_LEFT) playerController.leftPressed();
            else if (i == Ouya.BUTTON_DPAD_RIGHT) playerController.rightPressed();
            else if (i == Ouya.BUTTON_DPAD_UP) playerController.jumpPressed();
            return true;
        }
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int i) {
        if (controller == this.controller) {
            if (i == Ouya.BUTTON_DPAD_LEFT) playerController.leftReleased();
            else if (i == Ouya.BUTTON_DPAD_RIGHT) playerController.rightReleased();
            else if (i == Ouya.BUTTON_DPAD_UP) playerController.jumpReleased();
            /*FOR DEBUG PURPOSES, REMOVE WHEN THERE'S PROPER ZOMBIE SPAWNING*/
            else if (i == Ouya.BUTTON_O) world.spawnZombie(true);
            else if (i == Ouya.BUTTON_A) world.spawnZombie(false);
            return true;
        }
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int i, float v) {
        if (controller == this.controller) {
            if (i == Ouya.AXIS_LEFT_X) {
                playerController.xAxis(v);
                return true;
            }
            else if (i == Ouya.AXIS_LEFT_Y && v <= -0.5f) {
                if (v <= -0.5f) {
                    playerController.jumpPressed();
                }
                else {
                    playerController.jumpReleased();
                }
            }
            else if (i == Ouya.AXIS_RIGHT_X) {
                playerController.rightXAxis(v);
                return true;
            }
            else if (i == Ouya.AXIS_RIGHT_Y) {
                playerController.rightYAxis(v);
            }
            else if (i == Ouya.AXIS_RIGHT_TRIGGER) {
                playerController.rightTrigger(v);
            }
        }
        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int i, PovDirection povDirection) {
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int i, boolean b) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int i, boolean b) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int i, Vector3 vector3) {
        return false;
    }

    //InputProcessor METHODS
    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i2, int i3, int i4) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i2, int i3, int i4) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i2, int i3) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i2) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }
}
