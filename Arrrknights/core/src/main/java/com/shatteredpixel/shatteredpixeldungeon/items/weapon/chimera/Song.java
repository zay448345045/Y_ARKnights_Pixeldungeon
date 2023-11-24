package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;

public class Song extends Weapon.Chimera {
    @Override
    public String afterName() {
        return Messages.get(this, "name");
    }
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        float dmgbouns = 1f;
        if (Dungeon.level.map[attacker.pos] == Terrain.WATER) {
            dmgbouns += 0.15f;
        }
        if (Dungeon.level.map[defender.pos] == Terrain.WATER) {
            dmgbouns += 0.15f;
        }

        damage *= dmgbouns;
        return damage;
    }
}
