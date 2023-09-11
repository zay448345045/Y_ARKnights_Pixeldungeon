package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;

public class ItsHighNoon extends Buff{
    public static final float DURATION	= 100f;
    {
        type = buffType.POSITIVE;
        announced = true;
    }
    @Override
    public String toString() {
        return Messages.get(this, "name");
    }
    @Override
    public String desc() {
        return Messages.get(this, "desc");
    }
    @Override
    public int icon() {
        return BuffIndicator.HEX;
    }
}

