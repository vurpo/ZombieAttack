package org.sandholm.max.zombieattack;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by max on 1/17/14.
 */
public class World {

    Rectangle levelBounds = new Rectangle();
    Player player = new Player(new Vector2(8f, 4f));
    Array<Zombie> zombies = new Array<Zombie>();

    public Player getPlayer() {
        return player;
    }

    public Zombie spawnZombie(boolean leftSide) {
        if (leftSide) {
            zombies.add(new Zombie(new Vector2(-0.45f, levelBounds.y), false));
        }
        else {
            zombies.add(new Zombie(new Vector2(levelBounds.width-0.05f, levelBounds.y), true));
        }
        return zombies.peek();
    }

    public Array<Zombie> getZombies() {
        return zombies;
    }

    public World() {
        createWorld();
    }

    private void createWorld() {
        levelBounds.width = 16.0f;
        levelBounds.height = 5.0f;
        levelBounds.x = 0f;
        levelBounds.y = 4.0f;
    }
}
