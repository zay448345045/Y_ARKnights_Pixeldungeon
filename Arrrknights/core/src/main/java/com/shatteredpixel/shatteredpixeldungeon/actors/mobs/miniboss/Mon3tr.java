package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.miniboss;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Necromancer;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BreakerSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Mon3tr extends Mob {
    {
        spriteClass = BreakerSprite.class;

        HP = HT = 500;
        defenseSkill = 30;

        EXP = 0;
        maxLvl = 45;

        state = HUNTING;

        properties.add(Property.BOSS);
        immunities.add(Charm.class);
        immunities.add(Silence.class);
        immunities.add(Amok.class);
        immunities.add(Terror.class);
    }

    private int spell = 0; // 1 def, 2 knokback, 3 atk
    private int cooldown = 0; // 1 def, 2 knokback, 3 atk

    @Override
    public float spawningWeight() {
        return 0;
    }

    public void teleportSpend(){
        spend(TICK);
    }

    @Override
    protected boolean act() {
        if (cooldown <= 0) {
            spell = Random.Int(1,4);
            switch (spell) {
                case 1:
                    GLog.w(Messages.get(Kaltsit.class, "skill1")); break;
                case 2:
                    GLog.w(Messages.get(Kaltsit.class, "skill2")); break;
                case 3:
                    GLog.w(Messages.get(Kaltsit.class, "skill3")); break;
            }
            cooldown = Random.Int(4,8);
            return super.act();
        }
        else cooldown -= 1;

        return super.act();
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        if (spell == 2) {
            Ballistica trajectory = new Ballistica(this.pos, enemy.pos, Ballistica.STOP_TARGET);
            trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);
            WandOfBlastWave.throwChar(enemy, trajectory, 3); // 넉백 효과
        }
        return super.attackProc(enemy, damage);
    }

    @Override
    public int damageRoll() {
        if (spell == 3) return Random.NormalIntRange( 50, 75 );
        return Random.NormalIntRange( 32, 48 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 45;
    }

    @Override
    public int drRoll() {
        if (spell == 1) return Random.NormalIntRange( 18, 36 );
        return Random.NormalIntRange(0, 20);
    }

    private static final String SPELL	    = "spell";
    private static final String CD	    = "cooldown";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( SPELL, spell );
        bundle.put( CD, cooldown );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        spell = bundle.getInt( SPELL );
        cooldown = bundle.getInt( CD );
    }

}