package org.sandholm.max.zombieattack;

import com.badlogic.gdx.utils.Array;

/**
 * Created by max on 2/1/14.
 */
public class AmmoPackController {
    World world;
    Array<AmmoPack> ammoPacks;

    public AmmoPackController(World world) {
        this.world = world;
        this.ammoPacks = world.getAmmoPacks();
    }

    public void update(float delta) {
        for (AmmoPack ammoPack : ammoPacks) {
            ammoPack.update(delta);
        }
    }
}
