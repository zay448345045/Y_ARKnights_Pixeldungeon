package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.watabou.utils.Random;

public class GuerrillaSiegebreaker extends AirborneSoldier{
    @Override
    public int attackProc(Char enemy, int damage) {
        if (Dungeon.level.heraldAlive) {
            damage = Math.round(damage*1.3f);
        }
        return super.attackProc(enemy, damage);
    }
}
