package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;

public class Teller extends Weapon.Chimera {
    @Override
    public String afterName() {
        return Messages.get(this, "name");
    }
    @Override
    public float accFactor(){
        return 0.95f;
    }
    @Override
    public float dmgFactor(){
        return 0.7f;
    }
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        int target = 1;
        if (attacker instanceof Hero) {
            for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
                if (Dungeon.level.adjacent(mob.pos, defender.pos) && mob.alignment != Char.Alignment.ALLY) {
                    mob.damage(Dungeon.hero.damageRoll() - Math.max(defender.drRoll(), defender.drRoll()), this);
                    target++;
                }
            }
        }

        if (target == 1) damage *= 2;
        return damage;
    }
}
