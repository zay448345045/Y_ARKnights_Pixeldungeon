package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Violin;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Clush extends Weapon.Chimera {
    @Override
    public String beforeName() {
        return Messages.get(this, "name");
    }
    private int comboCount = 0;

    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        if(attacker instanceof Hero){
            if(((Hero) attacker).justAttacked) {
                comboCount++;
            }else {
                comboCount = 0;
            }
            if(comboCount+1< Random.Int(comboCount+9)) {
                Buff.affect(attacker, Violin.InstantViolin.class);
            }
        }
        return damage;
    }
    private static final String COMBOCOUNT	= "combocount";
    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( COMBOCOUNT, comboCount );
    }
    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        comboCount = bundle.getInt( COMBOCOUNT );
    }
}
