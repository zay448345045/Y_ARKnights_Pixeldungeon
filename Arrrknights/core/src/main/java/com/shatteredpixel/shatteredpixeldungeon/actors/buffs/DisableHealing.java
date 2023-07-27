package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

import java.text.DecimalFormat;

public class DisableHealing extends Buff{
    private int limitedHP;
    public static final float DURATION = 5f;
    protected float left;
    {
        actPriority = HERO_PRIO - 2;
        type = buffType.NEGATIVE;
        announced = true;
    }
    public void set(int HP, float time){
        this.limitedHP = HP;
        left = time;
    }
    @Override
    public int icon() {
        return BuffIndicator.POISON;
    }
    @Override
    public void tintIcon(Image icon) {
        icon.hardlight(0f, 0.3f, 0f);
    }
    @Override
    public boolean act() {
        if(limitedHP>target.HP){
            limitedHP=target.HP;
        }else{
            target.HP=limitedHP;
        }
        if (target.HP <= 0) {
            target.die(this);
        }
        spend(TICK);
        left -= TICK;
        if (left <= 0){
            detach();
        }
        return true;
    }
    @Override
    public String toString() {
        return Messages.get(this, "name");
    }
    @Override
    public float iconFadePercent() {
        return Math.max(0, (DURATION - left+1) / DURATION);
    }
    public String left(){return new DecimalFormat("#.##").format(left);}
    @Override
    public String desc() {
        return Messages.get(this, "desc", left());
    }
    private static final String LEFT	= "left";
    private static final String LIMITED_HP	= "limited_hp";
    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( LEFT, left );
        bundle.put( LIMITED_HP, limitedHP );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        left = bundle.getFloat( LEFT );
        limitedHP = bundle.getInt( LIMITED_HP );
    }
}
