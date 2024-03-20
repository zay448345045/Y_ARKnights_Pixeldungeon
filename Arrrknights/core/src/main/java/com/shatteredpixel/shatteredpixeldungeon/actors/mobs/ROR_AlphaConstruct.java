package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ROR_AlphaConstructSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ROR_LesserWispSprite;
import com.watabou.utils.Random;

public class ROR_AlphaConstruct extends Mob {
    {
        spriteClass = ROR_AlphaConstructSprite.class;

        HP = HT = 60;
        defenseSkill = 40;

        EXP = 14;
        maxLvl = 29;
        HUNTING = new Hunting();
        properties.add(Property.IMMOVABLE);
    }
    private Ballistica beam;
    private int beamTarget = -1;
    private int beamCooldown;
    public boolean beamCharged;
    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 20, 30 );
    }
    @Override
    public int attackSkill( Char target ) {
        return 80;
    }
    @Override
    public int drRoll() {
        return 0;
    }
    @Override
    public float spawningWeight() {
        return 0.5f;
    }
    @Override
    protected boolean canAttack( Char enemy ) {

        if (beamCooldown == 0) {
            Ballistica aim = new Ballistica(pos, enemy.pos, Ballistica.STOP_SOLID);

            if (enemy.invisible == 0 && !isCharmedBy(enemy) && fieldOfView[enemy.pos] && aim.subPath(1, aim.dist).contains(enemy.pos)){
                beam = aim;
                beamTarget = aim.collisionPos;
                return true;
            } else
                //if the beam is charged, it has to attack, will aim at previous location of target.
                return beamCharged;
        } else
            return false;
    }
    @Override
    protected boolean act() {
        if (beamCharged && state != HUNTING) {
            beamCharged = false;
            sprite.idle();
        }
        if (beam == null && beamTarget != -1) {
            beam = new Ballistica(pos, beamTarget, Ballistica.INGORE_OTHERS);
            sprite.turnTo(pos, beamTarget);
        }
        if (beamCooldown > 0)
            beamCooldown--;
        return super.act();
    }
    @Override
    protected boolean doAttack( Char enemy ) {

        if (beamCooldown > 0) {
            return false;
        } else if (!beamCharged){
            ((ROR_LesserWispSprite)sprite).charge( enemy.pos );
            spend( attackDelay() );
            beamCharged = true;
            return true;
        } else {

            spend( attackDelay() );

            beam = new Ballistica(pos, beamTarget, Ballistica.PROJECTILE);
            if (Dungeon.level.heroFOV[pos] || Dungeon.level.heroFOV[beam.collisionPos] ) {
                sprite.attack( beam.collisionPos );
                beamCharged = false;
                beamCooldown = Random.IntRange(4, 6);
                return false;
            } else {
                sprite.idle();
                return true;
            }
        }

    }
    private class Hunting extends Mob.Hunting{
        @Override
        public boolean act(boolean enemyInFOV, boolean justAlerted) {
            //even if enemy isn't seen, attack them if the beam is charged
            if (beamCharged && enemy != null && canAttack(enemy)) {
                enemySeen = enemyInFOV;
                return doAttack(enemy);
            }
            return super.act(enemyInFOV, justAlerted);
        }
    }
}
