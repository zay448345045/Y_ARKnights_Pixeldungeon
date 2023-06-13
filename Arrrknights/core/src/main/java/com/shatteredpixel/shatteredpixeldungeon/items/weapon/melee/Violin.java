package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LockedFloor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.Chains;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.EtherealChains;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfEnergy;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.QuickSlotButton;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Violin extends MeleeWeapon {

    public static final String AC_CAST = "CAST";
    {
        image = ItemSpriteSheet.VIOLIN;
        hitSound = Assets.Sounds.HIT_KNIFE;
        hitSoundPitch = 1.1f;

        tier = 1;

        unique = true;
        bones = false;

        usesTargeting = true;
        defaultAction = AC_CAST;

        chargeCap=1;
        charge=1;
    }

    private float particalArts = 0;

    protected Buff passiveBuff;

    @Override
    public int max(int lvl) {
        return  4*(tier+1) + 1 +    //9 base, down from 10
                lvl*(tier+1);   //scaling unchanged
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        return super.proc(attacker, defender, damage);
    }

    public void SPCharge(int n) {
        charge += n;
        if (chargeCap < charge) charge = chargeCap;
        updateQuickslot();
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_CAST);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_CAST)){

            curUser = hero;
            cursedKnown = true;

            if (charge < 1) {
                GLog.i( Messages.get(this, "no_charge") );
                QuickSlotButton.cancel();
            } else if (cursed) {
                GLog.w( Messages.get(this, "cursed") );
                QuickSlotButton.cancel();
            } else {
                GameScene.selectCell(caster);
            }

        }
    }

    @Override
    public String status() {
        return charge + "/" + chargeCap;
    }

    private static final String PARTICALARTS = "particalarts";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put( PARTICALARTS , particalArts );
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        particalArts = bundle.getFloat(PARTICALARTS);
    }

    private CellSelector.Listener caster = new CellSelector.Listener(){

        @Override
        public void onSelect(Integer target) {
            if (target != null && (Dungeon.level.visited[target] || Dungeon.level.mapped[target])){

                //chains cannot be used to go where it is impossible to walk to
                PathFinder.buildDistanceMap(target, BArray.or(Dungeon.level.passable, Dungeon.level.avoid, null));
                if (PathFinder.distance[curUser.pos] == Integer.MAX_VALUE){
                    GLog.w( Messages.get(EtherealChains.class, "cant_reach") );
                    return;
                }

                final Ballistica chain = new Ballistica(curUser.pos, target, Ballistica.STOP_TARGET);

                if (Actor.findChar( chain.collisionPos ) != null){
                    chainEnemy( chain, curUser, Actor.findChar( chain.collisionPos ));
                    if (Dungeon.hero.hasTalent(Talent.SIMPLE_COMBO)) {
                        Buff.affect(curUser,InstantViolin.class);
                    }
                    charge -= 1;
                    updateQuickslot();
                }
                throwSound();
                Sample.INSTANCE.play( Assets.Sounds.CHAINS );

            }

        }

        @Override
        public String prompt() {
            return Messages.get(EtherealChains.class, "prompt");
        }
    };

    //pulls an enemy to a position along the chain's path, as close to the hero as possible
    private void chainEnemy( Ballistica chain, final Hero hero, final Char enemy ){

        if (enemy.properties().contains(Char.Property.IMMOVABLE)) {
            GLog.w( Messages.get(this, "cant_pull") );
            return;
        }

        int bestPos = -1;
        for (int i : chain.subPath(1, chain.dist)){
            //prefer to the earliest point on the path
            if (!Dungeon.level.solid[i]
                    && Actor.findChar(i) == null
                    && (!Char.hasProp(enemy, Char.Property.LARGE) || Dungeon.level.openSpace[i])){
                bestPos = i;
                break;
            }
        }

        if (bestPos == -1) {
            GLog.i(Messages.get(this, "does_nothing"));
            return;
        }

        final int pulledPos = bestPos;
        int pullDistance = Dungeon.level.distance(enemy.pos, pulledPos);
        if (pullDistance > Dungeon.hero.lvl) {
            GLog.w( Messages.get(this, "not_long_enough") );
            return;
        } else {
            updateQuickslot();
        }

        hero.busy();
        hero.sprite.parent.add(new Chains(hero.sprite.center(), enemy.sprite.center(), new Callback() {
            public void call() {
                Actor.add(new Pushing(enemy, enemy.pos, pulledPos, new Callback() {
                    public void call() {
                        enemy.pos = pulledPos;
                        Dungeon.level.occupyCell(enemy);
                        Dungeon.observe();
                        GameScene.updateFog();
                        //hero.spendAndNext(1f);
                    }
                }));
                hero.next();
            }
        }));
    }

    public static class InstantViolin extends Buff {}

    public class ViolinBuff extends Buff{}

    protected ViolinBuff passiveBuff(){return new violinRecharge();}

    @Override
    public void activate(Char ch){
        passiveBuff = passiveBuff();
        passiveBuff.attachTo(ch);
    }

    @Override
    public boolean collect( Bag container ) {
        if (super.collect(container)){
            if (container.owner instanceof Hero
                    && passiveBuff == null)
            {
                activate((Hero) container.owner);
            }
            return true;
        } else{
            return false;
        }
    }

    @Override
    protected void onDetach() {
        if (passiveBuff != null){
            passiveBuff.detach();
            passiveBuff = null;
        }
    }

    public void charge(Hero target, float amount) {
        if (charge < chargeCap) {
            if (!isEquipped(target)) amount *= 0.75f*target.pointsInTalent(Talent.LIGHT_CLOAK)/3f;
            particalArts += 0.25f*amount;
            if (particalArts >= 1){
                particalArts--;
                charge++;
                updateQuickslot();
            }
        }
    }

    public class violinRecharge extends ViolinBuff{
        @Override
        public boolean act() {
            chargeCap=1+Dungeon.hero.lvl/10;
            if (charge < chargeCap) {
                LockedFloor lock = target.buff(LockedFloor.class);
                if ((lock == null || lock.regenOn())  && !(Dungeon.depth >= 26 && Dungeon.depth <= 30)) {
                    float missing = (chargeCap - charge);
                    float turnsToCharge = (50f - Dungeon.hero.lvl/5f - missing - Dungeon.hero.pointsInTalent(Talent.VIOLINIST)*2f);
                    float chargeToGain = (1f / turnsToCharge);
                    particalArts += chargeToGain;
                }

                if (particalArts >= 1) {
                    charge++;
                    particalArts -= 1;
                    if (charge == chargeCap){
                        particalArts = 0;
                    }

                }
            } else
                particalArts = 0;

            updateQuickslot();

            spend( TICK );

            return true;
        }
    }
}