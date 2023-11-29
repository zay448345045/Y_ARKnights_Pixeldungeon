package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfEvasion;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.watabou.utils.Random;

public class Surrender extends Weapon.Chimera {
    @Override
    public String beforeName() {
        return Messages.get(this, "name");
    }
    @Override
    public float accFactor(){
        return 1.3f;
    }
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {

        float healPer = 0.12f;
        int thisHP = attacker.HP;
        int thisHT = attacker.HT;
        if (Dungeon.hero.belongings.getItem(RingOfEvasion.class) != null) {
            if (Dungeon.hero.belongings.getItem(RingOfEvasion.class).isEquipped(Dungeon.hero)) {
                healPer = 0.18f;
            }}

        if (thisHP <= thisHT/4) {
            damage *= 1.5f;

            if (Random.Int(2) < 1) {
                int healAmt = Math.round(damage * healPer);
                healAmt = Math.min(healAmt, attacker.HT - attacker.HP);

                if (healAmt > 0 && attacker.isAlive()) {

                    attacker.HP += healAmt;
                    attacker.sprite.emitter().start(Speck.factory(Speck.HEALING), 0.4f, 1);
                    attacker.sprite.showStatus(CharSprite.POSITIVE, Integer.toString(healAmt));

                }
            }
        }
        else if (thisHP <= thisHT / 2){
            damage *= 1.3f;
            if (Random.Int(4) < 1) {
                int healAmt = Math.round(damage * healPer);
                healAmt = Math.min(healAmt, attacker.HT - attacker.HP);

                if (healAmt > 0 && attacker.isAlive()) {

                    attacker.HP += healAmt;
                    attacker.sprite.emitter().start(Speck.factory(Speck.HEALING), 0.4f, 1);
                    attacker.sprite.showStatus(CharSprite.POSITIVE, Integer.toString(healAmt));

                }
            }
        }
        else if (thisHP <= thisHT - thisHT/4) {
            damage *= 1.1f;
        }
        return damage;
    }
}
