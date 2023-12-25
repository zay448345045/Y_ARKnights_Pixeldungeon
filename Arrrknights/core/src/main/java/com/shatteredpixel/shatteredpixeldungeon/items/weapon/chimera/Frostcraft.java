package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.BattleAxe;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

import java.security.PublicKey;

public class Frostcraft extends Weapon.Chimera {
    @Override
    public String beforeName() {
        return Messages.get(this, "name");
    }
    public int frostPhase = 0;
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        if(attacker instanceof Hero){
            if (frostPhase == 3) {
                damage*= 1.25f;
            }else if(frostPhase == 2){
                damage*= 1.2f;
            }else if(frostPhase == 1) {
                damage*= 1.05f;
            }
            frostPhase--;
            if(frostPhase<=0) Buff.detach(Dungeon.hero, FrostcraftBuff.class);
        }

        return damage;
    }

    public void addFrostPhase(){ frostPhase++; }

    private int getFrostPhase(){return frostPhase;}
    private static final String FROSTPHASE	= "frostphase";
    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( FROSTPHASE, frostPhase );
    }
    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        frostPhase = bundle.getInt( FROSTPHASE );
    }

    public static class FrostcraftBuff extends Buff {
        {
            type = buffType.POSITIVE;
        }
        private int count;
        private boolean recharging = false;
        private static final String COUNT	= "count";
        @Override
        public boolean act() {
            if (Dungeon.hero.justAttacked) {
                recharging = false;
            }
            if(recharging &&
                    Dungeon.hero.belongings.weapon!=null &&
                    ((Weapon)Dungeon.hero.belongings.weapon).hasChimera(Frostcraft.class) &&
                    ((Frostcraft)((Weapon)Dungeon.hero.belongings.weapon).theChi(Frostcraft.class)).getFrostPhase() < 3
            ){
                CellEmitter.get( Dungeon.hero.pos ).burst( ElmoParticle.FACTORY, 6);
                ((Frostcraft)((Weapon)Dungeon.hero.belongings.weapon).theChi(Frostcraft.class)).addFrostPhase();
            }
            spend(TICK*2);
            return true;
        }
        public void startRecharge(){
            recharging = true;
        }
        @Override
        public void storeInBundle( Bundle bundle ) {
            super.storeInBundle( bundle );
            bundle.put( COUNT, count );
        }
        @Override
        public void restoreFromBundle( Bundle bundle ) {
            super.restoreFromBundle(bundle);
            count = bundle.getInt( COUNT );
        }

    }
}
