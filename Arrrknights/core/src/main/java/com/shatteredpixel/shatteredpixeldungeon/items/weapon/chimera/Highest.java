package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.AlchemyKit;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.utils.Random;

public class Highest extends Weapon.Chimera{
    @Override
    public String beforeName() {
        return Messages.get(this, "name");
    }
    @Override
    public int rchFactor(){
        return 3;
    }
    @Override
    public float dmgFactor(){
        return 0.6f;
    }

    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {

        if (attacker instanceof Hero) {
            if (Dungeon.hero.belongings.getItem(AlchemyKit.class) != null) {
                if (Dungeon.hero.belongings.getItem(AlchemyKit.class).isEquipped(Dungeon.hero)) {
                    if (Random.Int(12 + weapon.buffedLvl()) > 10) {
                        if (defender.buff(Poison.class) == null)
                            Buff.affect(defender, Poison.class).set(5f);
                        else Buff.affect(defender, Poison.class).extend(5f);
                    }
                }
            }
        }

        return damage;
    }
}
