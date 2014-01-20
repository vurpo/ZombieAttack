package org.sandholm.max.zombieattack;

import com.badlogic.gdx.Game;

/**
 * Created by max on 1/17/14.
 */
class ZombieGame extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen());
    }
}
