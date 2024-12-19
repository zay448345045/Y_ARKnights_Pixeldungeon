package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

public class PermanentArmorReduction extends Buff{
    {
        type = buffType.POSITIVE;
    }
    private int armorReduction = 0;

    @Override
    public int icon() {
        return BuffIndicator.BLEEDING;
    }
    @Override
    public void tintIcon(Image icon) {
        icon.hardlight(0x770000);
    }
    @Override
    public String toString() {
        return Messages.get(this, "name");
    }
    @Override
    public String desc() {
        return Messages.get(this, "desc", armorReduction);
    }
    public void set(int i){
        armorReduction = i;
    }
    public void add(int i){
        armorReduction += i;
    }
    public int get(){
        return armorReduction;
    }
    private static final String ARMORREDUCTION	= "armorReduction";
    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( ARMORREDUCTION, armorReduction );
    }
    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        armorReduction = bundle.getInt( ARMORREDUCTION );
    }
}
