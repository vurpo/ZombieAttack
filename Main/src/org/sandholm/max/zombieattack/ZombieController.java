package org.sandholm.max.zombieattack;

import com.badlogic.gdx.utils.Array;

import java.util.Random;

/**
 * Created by max on 1/19/14.
 */
public class ZombieController {
    World world;
    Array<Zombie> zombies;
    int spawnZombieTimeout;
    int zombieFrequency = 90;
    Random random = new Random();

    public ZombieController(World world) {
        spawnZombieTimeout = (random.nextInt(20)+zombieFrequency);
        this.world = world;
        this.zombies = world.getZombies();
    }

    public void update(float delta) {
        if (spawnZombieTimeout == 0) {
            world.spawnZombie(random.nextBoolean());
            spawnZombieTimeout = (random.nextInt(20)+zombieFrequency);
        }
        spawnZombieTimeout--;
        for (Zombie zombie : zombies) {
            if (world.levelBounds.overlaps(zombie.getBounds())) {
                zombie.update(delta);
            }
            else {
                zombies.removeValue(zombie, false);
            }
        }
    }
}
