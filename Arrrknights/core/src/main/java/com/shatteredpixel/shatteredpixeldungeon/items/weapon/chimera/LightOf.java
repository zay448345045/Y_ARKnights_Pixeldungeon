package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfHaste;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSunLight;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;

public class LightOf extends Weapon.Chimera {
    @Override
    public String beforeName() {
        return Messages.get(this, "name");
    }
    @Override
    public float dmgFactor(){
        return 0.9f;
    }
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        if (defender.buff(Blindness.class) != null) {
            Buff.affect(defender, Vulnerable.class, 3f); }

        if (defender.buff(Vulnerable.class) != null) {
            if (Dungeon.hero.belongings.getItem(RingOfSunLight.class) != null && Dungeon.hero.belongings.getItem(RingOfHaste.class) != null) {
                if (Dungeon.hero.belongings.getItem(RingOfSunLight.class).isEquipped(Dungeon.hero) && Dungeon.hero.belongings.getItem(RingOfHaste.class).isEquipped(Dungeon.hero)) {
                    damage += attacker.damageRoll() / 2;
                    return damage;
                }
            }
            damage += attacker.damageRoll() / 4;
        }

        return damage;
    }

}
