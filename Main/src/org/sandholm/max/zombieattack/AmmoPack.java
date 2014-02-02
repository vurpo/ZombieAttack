package org.sandholm.max.zombieattack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by max on 2/1/14.
 */
public class AmmoPack {
    private Rectangle bounds;
    private World world;
    private Vector2 velocity;

    private Texture texture;

    public Texture getTexture() {
        return texture;
    }

    public AmmoPack(Vector2 position, World world) {
        texture = new Texture(Gdx.files.internal("ammo_1080.png"));
        bounds = new Rectangle();
        bounds.setPosition(position);
        bounds.setSize(0.25f, 0.2f);
        this.world = world;
        this.velocity = new Vector2(0f, 0f);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getPosition() {
        return bounds.getPosition(new Vector2());
    }

    public void update(float delta) {
        if (getPosition().y > world.getLevelBounds().y) {
            this.velocity.y -= World.GRAVITY*delta;
        }
        bounds.setPosition(getPosition().add(this.velocity.cpy().scl(delta)));
        if (getPosition().y <= world.getLevelBounds().y) {
            this.getPosition().y = world.getLevelBounds().y;
            this.velocity.set(0f, 0f);
        }
    }
}
