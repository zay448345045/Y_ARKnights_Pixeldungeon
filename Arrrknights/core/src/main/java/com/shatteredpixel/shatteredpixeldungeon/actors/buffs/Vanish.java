package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;

public class Vanish extends Invisibility {
    {
        announced = false;
    }
    @Override
    public void fx(boolean on) {
        if (on) {
            target.sprite.add( CharSprite.State.VANISH );
        }
        else if (target.invisible == 0) {
            target.sprite.remove( CharSprite.State.VANISH );
        }
    }
}
