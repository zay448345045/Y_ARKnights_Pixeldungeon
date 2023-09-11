package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

import javax.swing.plaf.PanelUI;

public class ROR2Shield extends ShieldBuff{
    {
        type = buffType.POSITIVE;
    }
    private float waitBeforeRecover;
    private int maxShield;
    public void setMaxShield(int maxShield){
        this.maxShield=maxShield;
        super.hardSetShield(maxShield);
    }
    public int getMaxShield(){
        return maxShield;
    }
    @Override
    public boolean act() {
        if(waitBeforeRecover<=0) super.incShield(maxShield/4);
        if(shielding()>maxShield) super.hardSetShield(maxShield);
        if(waitBeforeRecover>0)waitBeforeRecover--;
        spend(TICK);
        return true;
    }
    @Override
    public int absorbDamage( int dmg ){
        this.waitBeforeRecover = 10f;
        if (shielding() >= dmg){
            super.decShield(dmg);
            dmg = 0;
        } else {
            dmg -= shielding();
            super.hardSetShield(0);
        }
        if (target != null) target.needsShieldUpdate = true;
        return dmg;
    }
    @Override
    public int icon() {
        return BuffIndicator.ARMOR;
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", getMaxShield(), shielding());
    }
    private static final String MAX_SHIELD = "max_shield";
    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(MAX_SHIELD, maxShield);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        maxShield = bundle.getInt(MAX_SHIELD);
    }
}
