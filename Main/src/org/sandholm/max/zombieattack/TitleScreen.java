package org.sandholm.max.zombieattack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by max on 1/27/14.
 */
public class TitleScreen implements Screen, ControllerListener, InputProcessor {
    static final String titleText = "Zombie Attack!";

    public static final float CAMERA_WIDTH = 16f;
    public static final float CAMERA_HEIGHT = 9f;

    private float ppuX;
    private float ppuY;

    private OrthographicCamera cam;
    private BitmapFont bigFont;
    private SpriteBatch batch;

    private ZombieGame game;

    public TitleScreen(ZombieGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        batch.begin();
            drawTexts();
        batch.end();
    }

    private void drawTexts() {
        BitmapFont.TextBounds titleBounds = bigFont.getBounds(titleText);

        bigFont.draw(batch, titleText, 8f*ppuX-titleBounds.width/2, 6f*ppuY+titleBounds.height/2);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        ppuX = (float) Gdx.graphics.getWidth() / CAMERA_WIDTH;
        ppuY = (float) Gdx.graphics.getHeight() / CAMERA_HEIGHT;
        loadAssets();
        batch = new SpriteBatch();

        Controllers.addListener(this);
    }

    private void loadAssets() {
        bigFont = new BitmapFont(Gdx.files.internal("Ubuntu-R-144.fnt"), Gdx.files.internal("Ubuntu-R.png"), false);
        bigFont.setColor(0f, 0f, 0f, 1f);
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

    //ControllerListener
    @Override
    public void connected(Controller controller) {

    }

    @Override
    public void disconnected(Controller controller) {

    }

    @Override
    public boolean buttonDown(Controller controller, int i) {
        Controllers.removeListener(this);
        game.setScreen(game.gameScreen); // look how good I can program!! (to be replaced by proper controller code, like in GameScreen)
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int i) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int i, float v) {
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

    //InputProcessor
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
