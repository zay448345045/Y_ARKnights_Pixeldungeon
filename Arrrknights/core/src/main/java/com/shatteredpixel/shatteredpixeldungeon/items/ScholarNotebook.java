package com.shatteredpixel.shatteredpixeldungeon.items;

import static com.shatteredpixel.shatteredpixeldungeon.Challenges.TEST;
import static com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent.SMOKE_BOMB;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicalSleep;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Ooze;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Albino;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.ArmoredBrute;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Bat;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Brute;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.CausticSlime;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.DM100;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.DM201;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Ghoul;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Gnoll;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Hound;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Monk;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.MudrockZealot;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Necromancer;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.NewDM300;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Senior;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Skeleton;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Slime;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Snake;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Swarm;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.miniboss.BloodMagister;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.miniboss.Centurion;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.miniboss.MagicGolem;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.ror2items.Aegis;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Chasm;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ScholarNotebook extends Item{
    public static final String AC_ACTIVE	= "active";
    {
        image = ItemSpriteSheet.ARTIFACT_SPELLBOOK;

        defaultAction = AC_ACTIVE;
        unique = true;
        bones = false;
    }
    public int curCharges = 1;
    public int maxCharges = 1;
    public static final HashSet<Class<? extends Mob>> INSTANT_PASSIVE = new HashSet<>();
    static {
        INSTANT_PASSIVE.add( Snake.class );
        INSTANT_PASSIVE.add( Slime.class );
        INSTANT_PASSIVE.add( Skeleton.class );
        INSTANT_PASSIVE.add( Bat.class );
        INSTANT_PASSIVE.add( Brute.class );
        INSTANT_PASSIVE.add( ArmoredBrute.class );
        INSTANT_PASSIVE.add( MudrockZealot.class );
        INSTANT_PASSIVE.add( Ghoul.class );
        INSTANT_PASSIVE.add( Monk.class );
        INSTANT_PASSIVE.add( Senior.class );
    }

    public static final HashSet<Class<? extends Mob>> PASSIVE_ACTIVE = new HashSet<>();
    static {
        PASSIVE_ACTIVE.add( Albino.class );
        PASSIVE_ACTIVE.add( CausticSlime.class );
        PASSIVE_ACTIVE.add( DM100.class );
        PASSIVE_ACTIVE.add( DM201.class );
        PASSIVE_ACTIVE.add( MagicGolem.class );
    }
    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_ACTIVE);
        return actions;
    }
    @Override
    public boolean isIdentified() {
        return true;
    }
    @Override
    public String status() {
        return curCharges + "/" + maxCharges;
    }
    public void updateLevel() {
        maxCharges = 1;
        if(Dungeon.hero.lvl >= 10) maxCharges +=1;
        if(Dungeon.hero.lvl >= 15) maxCharges +=1;

        curCharges = Math.min( curCharges, maxCharges );
    }
    public final Mob[] mobs = new Mob[4];
    List<Mob> activeSkill = new ArrayList<>();
    public void onKill(Mob mob) {
        //mobs[0] = Reflection.newInstance(NewDM300.class);
        if(Random.Int(100)<25+Dungeon.hero.lvl || Dungeon.isChallenged(TEST)){
            for(int i = 0; i < maxCharges; i++){
                if(mobs[i]!=null) continue;
                if(mob.hasNotebookSkill()) {
                    mobs[i] = mob;
                    //if(INSTANT_PASSIVE.contains(mob.getClass())) mob.notebookSkill(Dungeon.hero.belongings.getItem(ScholarNotebook.class),i);
                    GLog.i( Messages.get(this, "added") );
                }
            }
        }
    }
    public void removePassiveSkill(Class<? extends Mob> mob){
        for (int i = 0; i < mobs.length; i++) {
            if (mobs[i] != null && mobs[i].getClass() == mob) {
                mobs[i] = null;
                return;
            }
        }
    }
    public void removeActiveSkill(Class<? extends Mob> mob){
        for (Mob m : activeSkill) {
            if(m!=null && m.getClass() == mob){
                activeSkill.remove(m);
                return;
            }
        }
    }
    public boolean checkPassiveSkill(Class<? extends Mob> mob){
        return checkPassiveSkill(mob, true);
    }
    public boolean checkPassiveSkill(Class<? extends Mob> mob, boolean remove){
        ScholarNotebook notebook = Dungeon.hero.belongings.getItem(ScholarNotebook.class);
        if(notebook==null) return false;
        for (int i = 0; i < mobs.length; i++) {
            if (mobs[i]!=null && mobs[i].getClass() == mob) {
                if(remove)mobs[i] = null;
                return true;
            }
        }
        return false;
    }
    public boolean checkActiveSkill(Class<? extends Mob> mob){
        return checkActiveSkill(mob,true);
    }
    public boolean checkActiveSkill(Class<? extends Mob> mob, boolean remove){
        ScholarNotebook notebook = Dungeon.hero.belongings.getItem(ScholarNotebook.class);
        if(notebook==null) return false;
        for (Mob m : activeSkill) {
            if (m!=null && m.getClass() == mob) {
                if(remove) activeSkill.remove(m);
                return true;
            }
        }
        return false;
    }
    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if (action.equals(AC_ACTIVE)) {
            final boolean[] enabled = new boolean[4];
            enabled[0] = mobs[0] != null;
            enabled[1] = maxCharges>=2;
            enabled[2] = maxCharges>=3;
            enabled[3] = maxCharges>=4;
            GameScene.show(new WndOptions(
                    Messages.get(this, "prompt"),
                    Messages.get(this, "skillwnd_desc"),
                    mobs[0] != null ? mobs[0].name() : "---",
                    ((maxCharges>=2) ? (mobs[1] != null ? Messages.get(mobs[1].name()) : "---") : "未解锁"),
                    ((maxCharges>=3) ? (mobs[2] != null ? Messages.get(mobs[2].name()) : "---") : "未解锁"),
                    ((maxCharges>=4) ? (mobs[3] != null ? Messages.get(mobs[3].name()) : "---") : "未解锁")
            ) {
                @Override
                protected void onSelect(int index) {
                    int selectedIndex = index;
                    if (!INSTANT_PASSIVE.contains(mobs[index].getClass())) {
                        //给主动技能使用和删除按钮
                        GameScene.show(
                                new WndOptions(mobs[index].name(),
                                        Messages.get(ScholarNotebook.class, mobs[index].getClass().getSimpleName()),
                                        Messages.get(ScholarNotebook.class, "activate"),
                                        Messages.get(ScholarNotebook.class, "delete")) {
                                    @Override
                                    protected void onSelect(int index) {
                                        if (index == 0) {
                                            if(!PASSIVE_ACTIVE.contains(mobs[selectedIndex].getClass())) mobs[selectedIndex].notebookSkill(Dungeon.hero.belongings.getItem(ScholarNotebook.class),selectedIndex);
                                            else activeSkill.add(mobs[selectedIndex]);
                                        }
                                            mobs[selectedIndex] = null;
                                    }
                                }
                        );
                    } else {//对于被动，只给删除按钮
                        GameScene.show(
                                new WndOptions(mobs[index].name(),
                                        Messages.get(ScholarNotebook.class, mobs[index].getClass().getSimpleName()),
                                        Messages.get(ScholarNotebook.class, "delete")) {
                                    @Override
                                    protected void onSelect(int index) {
                                        if (index == 0) {
                                            mobs[selectedIndex] = null;
                                        }
                                    }
                                }
                        );
                    }
                }

                @Override
                protected boolean enabled(int index) {
                    return enabled[index];
                }
            });
        }
    }

    public int attackProc(Hero hero, Char enemy, int damage) {
        if(checkActiveSkill(Albino.class)) Buff.affect(enemy, Bleeding.class).set(5);
        if(checkActiveSkill(CausticSlime.class)) Buff.affect(enemy, Ooze.class).set(20f);
        if(checkPassiveSkill(Bat.class)) {
            int healAmt = Math.round(damage * 0.33f);
            if (Dungeon.hero.buff( Aegis.AegisBuff.class) != null){
                Aegis.addShield(healAmt);
            }
            healAmt = Math.min( healAmt, hero.HT - hero.HP );

            if (healAmt > 0 && hero.isAlive()) {
                hero.HP += healAmt;
                hero.sprite.emitter().start( Speck.factory( Speck.HEALING ), 0.4f, 1 );
                hero.sprite.showStatus( CharSprite.POSITIVE, Integer.toString( healAmt ) );
            }
        }
        if(hero.buff(BloodMagister.NotebookMadness.class)!=null) damage*=3;
        if(checkActiveSkill(DM201.class)) damage+=50;
        if(checkActiveSkill(MagicGolem.class)) damage+=100;
        return damage;
    }
    public int shootProc(Hero hero, Char enemy, int damage) {
        if(checkActiveSkill(Albino.class)) Buff.affect(enemy, Bleeding.class).set(5);
        if(checkActiveSkill(CausticSlime.class)) Buff.affect(enemy, Ooze.class).set(20f);
        if(checkPassiveSkill(Bat.class)) {
            int healAmt = Math.round(damage * 0.33f);
            if (Dungeon.hero.buff( Aegis.AegisBuff.class) != null){
                Aegis.addShield(healAmt);
            }
            healAmt = Math.min( healAmt, hero.HT - hero.HP );

            if (healAmt > 0 && hero.isAlive()) {
                hero.HP += healAmt;
                hero.sprite.emitter().start( Speck.factory( Speck.HEALING ), 0.4f, 1 );
                hero.sprite.showStatus( CharSprite.POSITIVE, Integer.toString( healAmt ) );
            }
        }
        if(hero.buff(BloodMagister.NotebookMadness.class)!=null) damage*=3;
        if(checkActiveSkill(DM201.class)) damage+=50;
        if(checkActiveSkill(MagicGolem.class)) damage+=100;
        return damage;
    }
    public void defenseProc(Char enemy, int damage) {
        if(checkPassiveSkill(Skeleton.class)) {
            for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
                CellEmitter.get(Dungeon.hero.pos + PathFinder.NEIGHBOURS8[i]).burst(BlastParticle.FACTORY, 12);
                Char ch = Actor.findChar( Dungeon.hero.pos + PathFinder.NEIGHBOURS8[i] );
                if (ch != null && ch.isAlive()) {
                    int explodeDamage = Random.NormalIntRange(17, 25);
                    explodeDamage = Math.max( 0,  explodeDamage - (ch.drRoll() +  ch.drRoll()) );
                    ch.damage( explodeDamage, this );
                    Buff.affect(ch, Burning.class).reignite(ch);
                }
            }
        }
    }
}
