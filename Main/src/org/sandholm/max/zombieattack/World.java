package org.sandholm.max.zombieattack;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by max on 1/17/14.
 */
public class World {
    public static float GRAVITY = 4.905f;

    int bulletsShot = 0;
    int hits = 0;
    int zombiesKilled = 0;

    public Rectangle getLevelBounds() {
        return levelBounds;
    }

    private Rectangle levelBounds = new Rectangle();

    Player player;
    Array<Zombie> zombies = new Array<Zombie>();
    Array<AmmoPack> ammoPacks = new Array<AmmoPack>();
    Array<Bullet> bullets = new Array<Bullet>();

    public Player getPlayer() {
        return player;
    }

    public Zombie spawnZombie(boolean leftSide) {
        if (leftSide) {
            zombies.add(new Zombie(new Vector2(-0.45f, levelBounds.y), false, this));
        }
        else {
            zombies.add(new Zombie(new Vector2(levelBounds.width-0.05f, levelBounds.y), true, this));
        }
        return zombies.peek();
    }

    public void spawnAmmoPack(Vector2 position) {
        ammoPacks.add(new AmmoPack(position, this));
    }

    public void removeAmmoPack(AmmoPack ammoPack) {
        ammoPacks.removeValue(ammoPack, false);
    }

    public void killZombie(Zombie zombie){
        zombies.removeValue(zombie, false);
    }

    public Array<Zombie> getZombies() {
        return zombies;
    }

    public Bullet spawnBullet (Vector2 position, float direction, float velocity) {
        bulletsShot += 1;
        bullets.add(new Bullet(position, direction, velocity));
        return bullets.peek();
    }

    public void deleteBullet(Bullet bullet) {
        bullets.removeValue(bullet, false);
    }

    public Array<Bullet> getBullets() {
        return bullets;
    }

    public World() {
        createWorld();
    }

    private void createWorld() {
        player = new Player(new Vector2(8f, 4f), this);
        levelBounds.width = 16.0f;
        levelBounds.height = 5.0f;
        levelBounds.x = 0f;
        levelBounds.y = 4.0f;
    }

    public Array<AmmoPack> getAmmoPacks() {
        return ammoPacks;
    }
}
