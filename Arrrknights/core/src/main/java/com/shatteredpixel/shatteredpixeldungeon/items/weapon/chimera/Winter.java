package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.WoundsofWar;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAssassin;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;

public class Winter extends Weapon.Chimera {
    @Override
    public String beforeName() {
        return Messages.get(this, "name");
    }
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        if (defender.buff(Chill.class) != null) damage = Math.round(damage * 1.25f);
        if (defender instanceof Mob && ((Mob) defender).surprisedBy(attacker)) {
            Buff.affect(defender, Chill.class, 4f);
        }
        return damage;
    }
}
