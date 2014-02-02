package org.sandholm.max.zombieattack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/**
 * Created by max on 1/17/14.
 */
class ZombieGame extends Game {
    public GameScreen gameScreen;
    public TitleScreen titleScreen;

    @Override
    public void create() {
        gameScreen = new GameScreen(this);
        titleScreen = new TitleScreen(this);
        setScreen(titleScreen);
    }
}
