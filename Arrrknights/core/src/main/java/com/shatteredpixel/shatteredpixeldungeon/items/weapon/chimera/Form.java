package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;

public class Form extends Weapon.Chimera {
    @Override
    public String afterName() {
        return Messages.get(this, "name");
    }

    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        int bouns = Statistics.enemiesSlain / 50;
        return damage+Math.min(bouns, 15);
    }
}
