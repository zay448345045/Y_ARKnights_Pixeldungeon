package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;

public class ItsHighNoonMark extends Buff{
    {
        //act before mob
        actPriority = MOB_PRIO + 1;
    }
    @Override
    public int icon() {
        return BuffIndicator.CORRUPT;
    }
    @Override
    public void fx(boolean on) {
        if (on) target.sprite.add(CharSprite.State.HUNTING_MARK);
        else target.sprite.remove(CharSprite.State.HUNTING_MARK);
    }
    @Override
    public boolean act() {
        if (Dungeon.hero.buff(ItsHighNoon.class)==null) target.die(this);
        spend( TICK );
        return true;
    }
    @Override
    public String toString() {
        return Messages.get(this, "name");
    }
    @Override
    public String desc() {
        return Messages.get(this, "desc");
    }
}
