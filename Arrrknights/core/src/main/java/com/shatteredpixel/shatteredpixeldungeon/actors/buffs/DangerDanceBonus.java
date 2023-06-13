package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class DangerDanceBonus extends Buff{
    private int count = 0;
    @Override
    public int icon() {
        return BuffIndicator.HASTE;
    }

    public void add(int amount){
        if(count<30){
            count += amount;
        }
    }
    public void decrease(int point){
        switch (point){
            case 2:
                count = Math.round(count/2);
                break;
            case 3:
                count--;
                break;
            default:
                super.detach();
        }
    }
    public int getcount(){
        return count;
    }

    @Override
    public float iconFadePercent() {
        return Math.max(0, (30-count) / 30);
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc",count);
    }

    private static final String COUNT = "count";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(COUNT, count);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        count = bundle.getInt(COUNT);
    }
}
