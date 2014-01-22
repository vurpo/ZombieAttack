package org.sandholm.max.zombieattack;

import com.badlogic.gdx.utils.Array;

/**
 * Created by max on 1/20/14.
 */
public class BulletController {
    World world;
    Array<Bullet> bullets;

    public BulletController(World world) {
        this.world = world;
        bullets = world.getBullets();
    }

    public void update(float delta) {
        for (Bullet bullet : bullets) {
            if (world.getLevelBounds().contains(bullet.getPosition())) {
                bullet.update(delta);
            }
            else {
                bullets.removeValue(bullet, false);
            }
        }
    }
}
