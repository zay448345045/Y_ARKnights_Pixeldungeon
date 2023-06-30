package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import static com.shatteredpixel.shatteredpixeldungeon.actors.Actor.TICK;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.HuntingMark;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LockedFloor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RabbitTime;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.RipperDemon;
import com.shatteredpixel.shatteredpixeldungeon.effects.Chains;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.EtherealChains;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfEnergy;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Door;
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
    public int marked = 0;

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
                    ProcessHuntingMark(Actor.findChar( chain.collisionPos ));
                    //Buff.affect(Actor.findChar( chain.collisionPos ), HuntingMark.class,5+Dungeon.hero.pointsInTalent(Talent.RHAPSODY));
                    if(Dungeon.level.adjacent(curUser.pos, target) && Dungeon.hero.hasTalent(Talent.MINUET)){
                        int paratime = Math.max(0,Dungeon.hero.pointsInTalent(Talent.MINUET)*2-3);
                        if(paratime>0) {
                            Buff.affect(Actor.findChar(chain.collisionPos), Paralysis.class, paratime);
                        }
                        Ballistica trajectory = new Ballistica(target, curUser.pos, Ballistica.STOP_TARGET);
                        trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);
                        moveChar(Actor.findChar( chain.collisionPos ), trajectory, 1, curUser.pos, false, false);
                        curUser.spendAndNext(TICK);
                    }else{
                        chainEnemy( chain, curUser, Actor.findChar( chain.collisionPos ));
                    }
                    if (Dungeon.hero.hasTalent(Talent.SIMPLE_COMBO)) {
                        Buff.affect(curUser,InstantViolin.class);
                    }
                    charge = 1;
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

    private void moveChar(final Char ch, final Ballistica trajectory, int power, int enemypos,
                          boolean closeDoors, boolean collideDmg){
        if (ch.properties().contains(Char.Property.BOSS)) {
            GLog.w( Messages.get(this, "cant_pull") );
        }
        int dist = Math.min(trajectory.dist, power);
        boolean collided = dist == trajectory.dist;
        if (dist == 0 || ch.properties().contains(Char.Property.IMMOVABLE)) return;
        //large characters cannot be moved into non-open space
        if (Char.hasProp(ch, Char.Property.LARGE)) {
            for (int i = 1; i <= dist; i++) {
                if (!Dungeon.level.openSpace[trajectory.path.get(i)]){
                    dist = i-1;
                    collided = true;
                    break;
                }
            }
        }
        if (Actor.findChar(trajectory.path.get(dist)) != null){
            dist--;
            collided = true;
        }
        if (dist < 0) return;
        final int newPos = trajectory.path.get(dist);
        if (newPos == enemypos) return;
        final int finalDist = dist;
        final boolean finalCollided = collided && collideDmg;
        final int initialpos = ch.pos;
        Actor.addDelayed(new Pushing(ch, ch.pos, newPos, new Callback() {
            public void call() {
                if (initialpos != ch.pos) {
                    //something caused movement before pushing resolved, cancel to be safe.
                    ch.sprite.place(ch.pos);
                    return;
                }
                int oldPos = ch.pos;
                ch.pos = newPos;
                if (finalCollided && ch.isAlive()) {
                    ch.damage(Random.NormalIntRange(finalDist, 2*finalDist), this);
                    Paralysis.prolong(ch, Paralysis.class, 1 + finalDist/2f);
                }
                if (closeDoors && Dungeon.level.map[oldPos] == Terrain.OPEN_DOOR){
                    Door.leave(oldPos);
                }
                Dungeon.level.occupyCell(ch);
            }
        }), -1);
    }

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
        if (pullDistance > (int)Math.floor((Math.sqrt(8*Dungeon.hero.lvl + 1)-1)/2f)+Dungeon.hero.pointsInTalent(Talent.VIOLINIST)*2+1) {
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

    public void ProcessHuntingMark(Char enemy){
        if(marked< 1 +
                ((Dungeon.hero.pointsInTalent(Talent.RHAPSODY)>=2) ? 1:0) +
                ((Dungeon.hero.buff(RabbitTime.class)!=null) ? Math.abs(Dungeon.hero.pointsInTalent(Talent.RHAPSODY)-2):0))
        {
            Buff.affect(enemy, HuntingMark.class, 5+ Dungeon.hero.pointsInTalent(Talent.RHAPSODY));
            marked++;
        }
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
                    float turnsToCharge = (50f - Dungeon.hero.lvl/5f - missing - Dungeon.hero.pointsInTalent(Talent.VIOLINIST)*5f);
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