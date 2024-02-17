package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ChenCombo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Combo;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FlametailSword;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.utils.Bundle;

public class Sylvestris extends Weapon.Chimera {
    @Override
    public String beforeName() {
        return Messages.get(this, "name");
    }
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        boolean da = false;
        if (attacker.buff(FlametailSword.FlametaillBuff.class) != null) {
            Buff.detach(attacker, FlametailSword.FlametaillBuff.class);
            da = true;
        }
        if (da) {
            attacker.attack(defender);
            defender.sprite.bloodBurstA( defender.sprite.center(), 4 );
            defender.sprite.flash();
            if (attacker instanceof Hero && Dungeon.hero.subClass == HeroSubClass.GLADIATOR) {
                Buff.affect(attacker, Combo.class).bounshit(defender);
            }
            else if (attacker instanceof Hero && Dungeon.hero.heroClass == HeroClass.CHEN) {
                Buff.affect(attacker, ChenCombo.class).bounshit(defender);
            }
            damage *= 1.5f;
        }
        return damage;
    }
}
