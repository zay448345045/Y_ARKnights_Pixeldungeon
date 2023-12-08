package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfMagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.utils.Reflection;

public class Journey extends Weapon.Chimera {
    @Override
    public String afterName() {
        return Messages.get(this, "name");
    }
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        defender.damage(defender.drRoll()/2, Reflection.newInstance(WandOfMagicMissile.class));
        return damage;
    }
}
