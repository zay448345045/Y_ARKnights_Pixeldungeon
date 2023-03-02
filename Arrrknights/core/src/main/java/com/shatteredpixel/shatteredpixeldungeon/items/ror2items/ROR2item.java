package com.shatteredpixel.shatteredpixeldungeon.items.ror2items;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.KindofMisc;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;

public class ROR2item extends KindofMisc {

    protected Buff passiveBuff;

    public void activate( Char ch ) {
        passiveBuff = passiveBuff();
        passiveBuff.attachTo(ch);
    }

    @Override
    public boolean doUnequip( Hero hero, boolean collect, boolean single ) {
        if (super.doUnequip( hero, collect, single )) {

            if (passiveBuff != null) {
                passiveBuff.detach();
                passiveBuff = null;
            }

            return true;

        } else {

            return false;

        }
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    protected ROR2item.ROR2itemBuff passiveBuff() {
        return null;
    }

    public class ROR2itemBuff extends Buff {

    }

    @Override
    public void storeInBundle( Bundle bundle ) {

    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {

    }
}