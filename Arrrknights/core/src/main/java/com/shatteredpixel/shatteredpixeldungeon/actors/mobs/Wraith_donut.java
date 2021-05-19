package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.I_GolemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.LurkerSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MirrorSprite;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Wraith_donut extends Mob{
        private static final float SPAWN_DELAY	= 2f;

        private int level;

	{
            spriteClass = I_GolemSprite.class;

		HP = HT = 1;
                EXP = 0;

                maxLvl = -2;

                flying = true;

                }

private static final String LEVEL = "level";

@Override
public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( LEVEL, level );
        }

@Override
public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        level = bundle.getInt( LEVEL );
        adjustStats( level );
        }

@Override
public int damageRoll() {
        return Random.NormalIntRange( 1 + level/2, 2 + level );
        }

@Override
public int attackSkill( Char target ) {
        return 10 + level;
        }

public void adjustStats( int level ) {
        this.level = level;
        defenseSkill = attackSkill( null ) * 5;
        enemySeen = true;
        }

@Override
public float spawningWeight() {
        return 0f;
        }

@Override
public boolean reset() {
        state = WANDERING;
        return true;
        }

public static void spawnAround( int pos ) {
        for (int n : PathFinder.NEIGHBOURS4) {
        spawnAt( pos + n );
        }
        }

public static Wraith_donut spawnAt( int pos ) {
        if (!Dungeon.level.solid[pos] && Actor.findChar( pos ) == null) {

                Wraith_donut w = new Wraith_donut();
        w.adjustStats( Dungeon.depth );
        w.pos = pos;
        w.state = w.HUNTING;
        GameScene.add( w, SPAWN_DELAY );

        w.sprite.alpha( 0 );
        w.sprite.parent.add( new AlphaTweener( w.sprite, 1, 0.5f ) );

        w.sprite.emitter().burst( ShadowParticle.CURSE, 5 );

        return w;
        } else {
        return null;
        }
        }

        }
