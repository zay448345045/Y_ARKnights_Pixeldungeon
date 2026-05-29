package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ChampionEnemy;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.VoidInfestorSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.HashSet;

public class VoidInfestor extends Mob {

    private int level;
    {
        spriteClass = VoidInfestorSprite.class;
        HP = HT = 1;
        EXP = 0;
        maxLvl = -2;
        HUNTING = new Hunting();
    }

    private static final String LEVEL = "level";
    private static final String LEAP_POS = "leap_pos";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(LEVEL, level);
        bundle.put(LEAP_POS, leapPos);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        level = bundle.getInt(LEVEL);
        adjustStats(level);
        leapPos = bundle.getInt(LEAP_POS);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(1 + level / 2, 2 + level);
    }

    @Override
    public int attackSkill(Char target) {
        return 10 + level;
    }

    public void adjustStats(int level) {
        this.level = level;
        defenseSkill = attackSkill(null) * 5;
        enemySeen = true;
    }

    @Override
    public float spawningWeight() {
        return 0f;
    }

    private int leapPos = -1;

    private class Hunting extends Mob.Hunting {

        @Override
        public boolean act(boolean enemyInFOV, boolean justAlerted) {

            if (leapPos != -1) {
                Ballistica b = new Ballistica(pos, leapPos, Ballistica.STOP_TARGET | Ballistica.STOP_SOLID);

                if (rooted || b.collisionPos != leapPos) {
                    leapPos = -1;
                    return true;
                }

                final Char leapVictim = Actor.findChar(leapPos);
                final int endPos;

                if (leapVictim != null) {
                    int bouncepos = -1;
                    for (int i : PathFinder.NEIGHBOURS8) {
                        if ((bouncepos == -1 || Dungeon.level.trueDistance(pos, leapPos + i) < Dungeon.level.trueDistance(pos, bouncepos))
                                && Actor.findChar(leapPos + i) == null && Dungeon.level.passable[leapPos + i]) {
                            bouncepos = leapPos + i;
                        }
                    }
                    if (bouncepos == -1) {
                        leapPos = -1;
                        return true;
                    } else {
                        endPos = bouncepos;
                    }
                } else {
                    endPos = leapPos;
                }

                sprite.visible = Dungeon.level.heroFOV[pos] || Dungeon.level.heroFOV[leapPos] || Dungeon.level.heroFOV[endPos];
                sprite.aura( 0x9900FF );
                sprite.jump(pos, leapPos, new Callback() {
                    @Override
                    public void call() {
                        if (leapVictim != null) {
                            if(leapVictim instanceof Hero){
                                int dmg = damageRoll() - leapVictim.drRoll();
                                leapVictim.damage(dmg, VoidInfestor.this);
                                leapVictim.sprite.flash();
                                Sample.INSTANCE.play(Assets.Sounds.HIT);
                                sprite.clearAura();
                            }else{
                                Buff.affect(leapVictim, ChampionEnemy.R2Void.class);
                                sprite.clearAura();
                                sprite.alpha( 0 );
                                VoidInfestor.this.die(VoidInfestor.this);
                            }
                        }

                        if (endPos != leapPos) {
                            Actor.addDelayed(new Pushing(VoidInfestor.this, leapPos, endPos), -1);
                        }

                        pos = endPos;
                        leapPos = -1;
                        sprite.idle();
                        Dungeon.level.occupyCell(VoidInfestor.this);
                        spend(TICK);
                        next();
                    }
                });
                return false;
            }

            enemySeen = enemyInFOV;

            if (enemyInFOV && enemy != null && !isCharmedBy(enemy)) {
                int dist = Dungeon.level.distance(pos, enemy.pos);

                if (dist == 2 && !rooted) {
                    Ballistica ball = new Ballistica(pos, enemy.pos, Ballistica.STOP_TARGET | Ballistica.STOP_SOLID);
                    if (ball.collisionPos == enemy.pos) {
                        leapPos = enemy.pos;
                        //spend(attackDelay());
                        return true;
                    }
                }

                target = enemy.pos;
                int oldPos = pos;
                if (dist < 2) {
                    getFurther(target);
                    spend(1 / speed());
                    return moveSprite(oldPos, pos);
                } else {
                    getCloser(target);
                    spend(1 / speed());
                    return moveSprite(oldPos, pos);
                }
            }

            if (enemy == null) {
                state = WANDERING;
                target = Dungeon.level.randomDestination(VoidInfestor.this);
                return true;
            }

            int oldPos = pos;
            if (target != -1 && getCloser(target)) {
                spend(1 / speed());
                return moveSprite(oldPos, pos);
            } else {
                spend(TICK);
                if (!enemyInFOV) {
                    sprite.showLost();
                    state = WANDERING;
                    target = Dungeon.level.randomDestination(VoidInfestor.this);
                }
                return true;
            }
        }
    }

    @Override
    protected Char chooseEnemy() {
        Terror terror = buff(Terror.class);
        if (terror != null) {
            Char source = (Char) Actor.findById(terror.object);
            if (source != null) {
                return source;
            }
        }

        HashSet<Char> enemies = new HashSet<>();

        if (Dungeon.hero.isAlive() && fieldOfView[Dungeon.hero.pos] && Dungeon.hero.invisible <= 0) {
            if (!Dungeon.hero.isInvulnerable(getClass())) {
                enemies.add(Dungeon.hero);
            }
        }

        for (Mob mob : Dungeon.level.mobs) {
            if (mob == this || mob instanceof RotLasher || mob instanceof RotHeart) {
                continue;
            }
            if (mob.isAlive() && fieldOfView[mob.pos] && mob.invisible <= 0) {
                if (!mob.isInvulnerable(getClass())) {
                    enemies.add(mob);
                }
            }
        }

        Charm charm = buff(Charm.class);
        if (charm != null) {
            Char source = (Char) Actor.findById(charm.object);
            if (source != null && enemies.contains(source) && enemies.size() > 1) {
                enemies.remove(source);
            }
        }

        if (enemies.isEmpty()) {
            return null;
        }

        Char closest = null;
        for (Char curr : enemies) {
            if (closest == null
                    || Dungeon.level.distance(pos, curr.pos) < Dungeon.level.distance(pos, closest.pos)
                    || (Dungeon.level.distance(pos, curr.pos) == Dungeon.level.distance(pos, closest.pos) && curr == Dungeon.hero)) {
                closest = curr;
            }
        }
        return closest;
    }
    public static VoidInfestor spawnAt( int pos ) {
        if (!Dungeon.level.solid[pos] && Actor.findChar( pos ) == null) {

            VoidInfestor w = new VoidInfestor();
            w.adjustStats( Dungeon.depth );
            w.pos = pos;
            w.state = w.HUNTING;
            GameScene.add( w, 1f );

            w.sprite.alpha( 0 );
            w.sprite.parent.add( new AlphaTweener( w.sprite, 1, 0.5f ) );

            w.sprite.emitter().burst( ShadowParticle.CURSE, 5 );

            return w;
        } else {
            return null;
        }
    }
}