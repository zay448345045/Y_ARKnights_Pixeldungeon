package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;

public class Archery extends Weapon.Chimera{
    @Override
    public String beforeName() {
        return Messages.get(this, "name");
    }

    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        int df = damage;
        damage = Math.min(damage + defender.drRoll(), damage + defender.drRoll());

        if (damage >= df+2) damage = df+2;

        return damage;
    }
}
