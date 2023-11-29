package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TalismanOfForesight;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.BattleAxe;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

public class Horoscope extends Weapon.Chimera {
    @Override
    public String beforeName() {
        return Messages.get(this, "name");
    }
    @Override
    public float accFactor(){return 1.15f;}
    public int starpower = 0 ;
    public int count = 0;
    private int starpowercap = 3;
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {

        if (starpower >= 1 && attacker instanceof Hero) {
            for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
                if (mob.alignment != Char.Alignment.ALLY && Dungeon.level.heroFOV[mob.pos]) {
                    int dmg = attacker.damageRoll() - defender.drRoll();
                    dmg = Math.round(dmg * (starpower * 0.7f));

                    mob.damage(dmg, attacker);
                }
            }
            if (starpower == 3) GameScene.flash( 0x80FFFFFF );
            Camera.main.shake(2, starpower / 3);

            Sample.INSTANCE.play(Assets.Sounds.HIT_SLASH, 1.76f, 1.76f);
            attacker.sprite.showStatus(CharSprite.POSITIVE, Messages.get(BattleAxe.class, "attack"));
        }

        starpower = 0;
        return damage;
    }

    public void addCount(){
        if(starpower<starpowercap){
            count++;
            if (Dungeon.hero.belongings.getItem(TalismanOfForesight.class) != null) {
                if (Dungeon.hero.belongings.getItem(TalismanOfForesight.class).isEquipped(Dungeon.hero)) {
                    count++;
                }
            }
            if(count>=2) starpower++;
        }
    }
    public void clearCount(){
        count = 0;
    }

    public static class HoroscopeCharge extends Buff {
        private int count = 0;
        public int pos = -1;
        @Override
        public boolean act() {
            if (pos == -1) pos = target.pos;
            if (pos != target.pos) {
                detach();
            } else {
                spend(TICK);
            }
            return true;
        }
        private static final String COUNT = "count";
        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put(COUNT, count);
        }
        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            count = bundle.getInt(COUNT);
        }
    }
}
