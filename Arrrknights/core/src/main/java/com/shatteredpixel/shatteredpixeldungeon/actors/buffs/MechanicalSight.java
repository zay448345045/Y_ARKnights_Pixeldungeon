package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class MechanicalSight extends Buff{
    {
        type = buffType.POSITIVE;
    }
    public int times = 0;

    @Override
    public int icon() {
        return BuffIndicator.PINCUSHION;
    }

    public void set(){
        times = Dungeon.hero.pointsInTalent(Talent.MECHANICAL_SIGHT)*5 +5;
    }

    @Override
    public float iconFadePercent() {
        int maxCount = Dungeon.hero.pointsInTalent(Talent.MECHANICAL_SIGHT)*5+5;
        return Math.max(0, (maxCount-times)*1f / maxCount);
    }
    public void decrement(){
        times--;
        if(times<=0) detach();
    }
    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", times);
    }

    private static final String TIMES = "times";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put( TIMES, times );
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        times = bundle.getInt( TIMES );
    }
}
