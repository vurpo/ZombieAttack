package org.sandholm.max.zombieattack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by max on 1/17/14.
 */
public class WorldRenderer {
    private static final float CAMERA_WIDTH = 16f;
    private static final float CAMERA_HEIGHT = 9f;

    private World world;
    private OrthographicCamera cam;

    /** for debug rendering **/
    ShapeRenderer debugRenderer = new ShapeRenderer();

    ShapeRenderer geometryRenderer = new ShapeRenderer();

    private Texture playerTexture;
    private Texture zombieTexture;
    private SpriteBatch spriteBatch;

    private boolean debug;

    private float ppuX;
    private float ppuY;

    public WorldRenderer(World world, boolean debug) {
        this.world = world;
        this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        this.cam.position.set(CAMERA_WIDTH/2f, CAMERA_HEIGHT/2f, 0f);
        this.cam.update();
        this.debug = debug;
        spriteBatch = new SpriteBatch();
        loadTextures();
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        ppuX = (float) width / CAMERA_WIDTH;    //calculate the points per unit
        ppuY = (float) height / CAMERA_HEIGHT;  //even if the window is resized,
                                               //we'll draw according to the initial points per unit
    }

    private void loadTextures() {
        playerTexture = new Texture(Gdx.files.internal("guy_1080.png"));
        zombieTexture = new Texture(Gdx.files.internal("zombie_1080.png"));
    }

    public void render() {
        spriteBatch.begin();
            drawZombies();
            drawPlayer();
        spriteBatch.end();
        drawLines();
        if(debug){
            drawDebug();
        }
    }

    private void drawPlayer() {
        Player player = world.getPlayer();
        spriteBatch.draw(playerTexture, (player.getPosition().x)*ppuX, player.getPosition().y*ppuY
                       , player.getBounds().width*ppuX, player.getBounds().height*ppuY);
    }

    private void drawZombies() {
        Array<Zombie> zombies = world.getZombies();
        for (Zombie zombie : zombies) {
            spriteBatch.draw(zombieTexture, (zombie.getPosition().x)*ppuX, zombie.getPosition().y*ppuY
                    , zombie.getBounds().width*ppuX, zombie.getBounds().height*ppuY);
        }
    }

    private void drawLines() {
        geometryRenderer.setProjectionMatrix(cam.combined);

        geometryRenderer.begin(ShapeType.Filled);
        // draw ground
        geometryRenderer.setColor(new Color(0, 0, 0, 1)); //set color of drawing
        geometryRenderer.rectLine(world.getLevelBounds().x, world.getLevelBounds().y
                                , world.getLevelBounds().width, world.getLevelBounds().y, 0.01f); //draw the ground

        // draw arm and gun
        Player player = world.getPlayer();
        Vector2 playerPosition = player.getPosition();

        geometryRenderer.translate(playerPosition.x+0.26f, playerPosition.y+0.7f, 0f);      //translate to shoulder
        geometryRenderer.rotate(0f, 0f, 1f, player.getGunAngle());                                 //rotate the arm
        geometryRenderer.rectLine(0, 0, 0.23f, 0, 0.015f);                                           //draw the arm
        float gunViewAngle = Math.abs((player.getGunAngle() % 180)-90)/90f; //this guy tilts the gun, but how much?
        if (player.getGunAngle() < 90 || player.getGunAngle() > 270) {                          //if pointing right
            geometryRenderer.rectLine(0.23f, 0.03f*gunViewAngle, 0.4f, 0.03f*gunViewAngle, 0.03f);   //draw the gun
            geometryRenderer.rectLine(0.23f, 0.03f*gunViewAngle, 0.16f, -0.03f*gunViewAngle, 0.02f);
            player.setBarrelEnd(new Vector2(0.4f, 0.03f*gunViewAngle).setAngle(player.getGunAngle()));//we're just saving the end of the barrel while we can
        }
        else {                                                                                   //if pointing left
            geometryRenderer.rectLine(0.23f, -0.03f*gunViewAngle, 0.4f, -0.03f*gunViewAngle, 0.03f); //draw the gun
            geometryRenderer.rectLine(0.23f, -0.03f*gunViewAngle, 0.16f, 0.03f*gunViewAngle, 0.02f);
            player.setBarrelEnd(new Vector2(0.4f, -0.03f*gunViewAngle).setAngle(player.getGunAngle()));//we're just saving the end of the barrel while we can
        }
        geometryRenderer.identity();
        //draw all bullets
        Array<Bullet> bullets = world.getBullets();
        for (Bullet bullet : bullets) {
            geometryRenderer.rectLine(bullet.getPosition(), bullet.getPosition().cpy().add(bullet.getVelocity().cpy().clamp(0.8f,0.8f)), 0.01f);
        }
        geometryRenderer.end();
    }

    private void drawDebug() {
        // render level
        debugRenderer.setProjectionMatrix(cam.combined);
        debugRenderer.begin(ShapeType.Line);
        debugRenderer.setColor(new Color(1, 0, 0, 1));
        debugRenderer.rect(world.getLevelBounds().x, world.getLevelBounds().y
                         , world.getLevelBounds().width, world.getLevelBounds().height);
        // render player
        Player player = world.getPlayer();
        Rectangle rect = player.getBounds();
        debugRenderer.setColor(new Color(1, 0, 0, 1));
        debugRenderer.rect(player.getPosition().x, player.getPosition().y, player.getBounds().width, player.getBounds().height);
        //draw zombies
        for (Zombie zombie : world.getZombies()) {
            debugRenderer.rect(zombie.getPosition().x, zombie.getPosition().y, zombie.getBounds().width, zombie.getBounds().height);
        }
        debugRenderer.end();

    }
}
