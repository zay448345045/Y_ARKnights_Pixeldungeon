package com.shatteredpixel.shatteredpixeldungeon.items.wands.SSP;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SoulMark;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.TargetedCell;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TalismanOfForesight;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.DamageWand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Blazing;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class StaffOfValstrax extends DamageWand {
    private static ItemSprite.Glowing COL = new ItemSprite.Glowing(0xFF3333);
    {
        image = ItemSpriteSheet.WAND_FIREBOLT;
        collisionProperties = Ballistica.PROJECTILE;
    }
    @Override
    public ItemSprite.Glowing glowing() {
        return COL;
    }
    @Override
    public int min(int lvl) {
        return 1+lvl;
    }

    @Override
    public int max(int lvl) {
        return 9+3*lvl;
    }

    @Override
    protected void onZap(Ballistica bolt) {
        Buff.append(Dungeon.hero, ValstraxIncoming.class).setup(bolt.collisionPos, Dungeon.depth, chargesPerCast(), buffedLvl());
    }

    @Override
    protected int chargesPerCast() {
        //consumes 30% of current charges, rounded up, with a minimum of one.
        return Math.max(1, (int)Math.ceil(curCharges*0.3f));
    }

    @Override
    public void onHit(MagesStaff staff, Char attacker, Char defender, int damage) {
        new Blazing().proc(staff, attacker, defender, damage);
    }

    @Override
    public String statsDesc() {
        if (levelKnown)
            return Messages.get(this, "stats_known_desc", chargesPerCast(), min(), max(), min()*(chargesPerCast()+1), max()*(chargesPerCast()+1) );
        else
            return Messages.get(this, "stats_desc", chargesPerCast(), min(0), max(0));
    }

    public static class ValstraxIncoming extends Buff{
        private int targetpos;
        private int explodeDepth;
        private int left;
        private int phase;
        private int originphase;
        private int wandLevel;
        public void setup( int pos, int explodeDepth, int phase, int lvl){
            this.targetpos = pos;
            this.explodeDepth = explodeDepth;
            left = phase+1;
            this.phase = phase;
            originphase = phase;
            wandLevel = lvl;
        }

        @Override
        public boolean act() {
            if (explodeDepth == Dungeon.depth){
                left--;
                if (left <= 0){
                    StaffOfValstrax sov = new StaffOfValstrax();
                    ArrayList<Char> affected = new ArrayList<>();
                    do{
                        PathFinder.buildDistanceMap( targetpos, BArray.not( Dungeon.level.solid, null ), phase );
                        for (int i = 0; i < PathFinder.distance.length; i++) {
                            if (PathFinder.distance[i] < Integer.MAX_VALUE) {
                                Char ch = Actor.findChar(i);
                                if (ch != null){
                                    affected.add(ch);
                                }
                            }
                        }
                        PathFinder.buildDistanceMap( targetpos, BArray.not( Dungeon.level.solid, null ), phase-1 );
                        for (int i = 0; i < PathFinder.distance.length; i++) {
                            if (PathFinder.distance[i] < Integer.MAX_VALUE) {
                                Char ch = Actor.findChar(i);
                                if (ch != null){
                                    affected.remove(ch);
                                }
                            }
                        }
                        for (Char ch : affected){
                            int damage = Random.NormalIntRange( 1 + wandLevel, 9 + 3*wandLevel );
                            ch.damage(Math.round(damage*(originphase-phase+1)), sov);
                            if (ch == Dungeon.hero && !ch.isAlive()){
                                Dungeon.fail(StaffOfValstrax.class);
                            }
                        }
                        affected.clear();
                        phase--;
                    }while(phase>0);
                    if (Actor.findChar( targetpos ) != null){
                        int damage = Random.NormalIntRange( 1 + wandLevel, 9 + 3*wandLevel );
                        Actor.findChar( targetpos ).damage(Math.round(damage*(originphase+1)), sov);
                    }
                    PathFinder.buildDistanceMap( targetpos, BArray.not( Dungeon.level.solid, null ), originphase );
                    for (int i = 0; i < PathFinder.distance.length; i++) {
                        if (PathFinder.distance[i] < Integer.MAX_VALUE) {
                            Char ch = Actor.findChar(i);
                            if (ch != null){
                                affected.add(ch);
                            }
                        }
                    }
                    for ( Char ch : affected ){
                        if (Dungeon.hero.hasTalent(Talent.ARCANE_SNIPE)) {
                            int dur = 5*Dungeon.hero.pointsInTalent(Talent.ARCANE_SNIPE);
                            Buff.append(Dungeon.hero, TalismanOfForesight.CharAwareness.class, dur).charID = ch.id();
                        }
                        if (ch != Dungeon.hero &&
                                Dungeon.hero.subClass == HeroSubClass.WARLOCK &&
                                Random.Float() > (Math.pow(0.95f - Dungeon.hero.pointsInTalent(Talent.EMOTION) / 15f, (wandLevel*originphase)+1) - 0.07f)){//change from budding
                            SoulMark.prolong(ch, SoulMark.class, SoulMark.DURATION + wandLevel + (Dungeon.hero.pointsInTalent(Talent.LORD) * 2));
                        }
                        if (ch.isAlive()) {
                            Buff.affect(ch, Burning.class).reignite(ch);
                            switch (phase) {
                                case 1:
                                    break; //no effects
                                case 2:
                                    Buff.affect(ch, Cripple.class, 4f);
                                    break;
                                case 3:
                                    Buff.affect(ch, Paralysis.class, 4f);
                                    break;
                                case 4:
                                    Buff.affect(ch, Cripple.class, 8f);
                                    Buff.affect(ch, Paralysis.class, 4f);
                                    break;
                            }
                        }
                    }
                    next();
                    detach();
                    return false;
                }else {
                    PathFinder.buildDistanceMap(targetpos, BArray.not(Dungeon.level.solid, null), (phase-left)+1);
                    for (int i = 0; i < PathFinder.distance.length; i++) {
                        if (PathFinder.distance[i] < Integer.MAX_VALUE) {
                            curUser.sprite.parent.addToBack(new TargetedCell(i, 0xFF0000));
                        }
                    }
                }
            }
            spend( TICK );
            return true;
        }

    }
}
