package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;

public class Rose_Force extends FlavourBuff {
    public static final float DURATION = 8f;
    {
        type = buffType.POSITIVE;
    }

    @Override
    public int icon() { return BuffIndicator.FURY; }

    @Override
    public String toString() { return Messages.get(this, "name"); }

    @Override
    public String desc() {
        return Messages.get(this, "desc", dispTurns());
    }
}
