package org.sandholm.max.zombieattack;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

/**
 * Created by max on 1/19/14.
 */
public class ZombieController {
    World world;
    Array<Zombie> zombies;
    int spawnZombieTimeout;
    int zombieFrequency = 80;
    float timeCount = 0f;
    Random random = new Random();

    public ZombieController(World world) {
        spawnZombieTimeout = (random.nextInt(20)+zombieFrequency);
        this.world = world;
        this.zombies = world.getZombies();
    }

    public void update(float delta) {
        timeCount += delta;
        if (timeCount >= 4f) {
            if (zombieFrequency >= 5) {
                zombieFrequency /= 1.05f;
            }
            timeCount = 0f;
        }
        if (spawnZombieTimeout == 0) {
            world.spawnZombie(random.nextBoolean());
            spawnZombieTimeout = (random.nextInt(20)+zombieFrequency);
        }
        spawnZombieTimeout--;
        for (Zombie zombie : zombies) {
            if (world.getLevelBounds().overlaps(zombie.getBounds())) {
                zombie.update(delta);
                for (Bullet bullet : world.getBullets()) {
                    if (aabbContainsSegment(bullet.getPosition(), bullet.getPosition().cpy().add(bullet.getVelocity().cpy().clamp(0.8f,0.8f)), new Rectangle(zombie.getBounds()).setWidth(0.3f).setCenter(zombie.getBounds().getCenter(new Vector2())))) {
                        zombie.damage(0.2f);
                        world.hits += 1;
                        world.deleteBullet(bullet);
                    }
                }
            }
            else {
                world.killZombie(zombie);
            }
        }
    }

    public boolean aabbContainsSegment (Vector2 position1, Vector2 position2, Rectangle rectangle) {
        // Completely outside.
        if ((position1.x <= rectangle.x && position2.x <= rectangle.x) || (position1.y <= rectangle.y && position2.y <= rectangle.y) || (position1.x >= rectangle.x+rectangle.width && position2.x >= rectangle.x+rectangle.width) || (position1.y >= rectangle.y+rectangle.height && position2.y >= rectangle.y+rectangle.height))
            return false;

        float m = (position2.y - position1.y) / (position2.x - position1.x);

        float y = m * (rectangle.x - position1.x) + position1.y;
        if (y > rectangle.y && y < rectangle.y+rectangle.height) return true;

        y = m * (rectangle.x+rectangle.width - position1.x) + position1.y;
        if (y > rectangle.y && y < rectangle.y+rectangle.height) return true;

        float x = (rectangle.y - position1.y) / m + position1.x;
        if (x > rectangle.x && x < rectangle.x+rectangle.width) return true;

        x = (rectangle.y+rectangle.height - position1.y) / m + position1.x;
        if (x > rectangle.x && x < rectangle.x+rectangle.width) return true;

        return false;
    }

}
