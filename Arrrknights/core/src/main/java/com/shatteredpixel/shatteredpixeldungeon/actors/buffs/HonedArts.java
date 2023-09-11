package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.ror2items.Aegis;

import java.util.ArrayList;

public class HonedArts extends Buff{
    {
        actPriority = HERO_PRIO - 1;
        type = buffType.POSITIVE;
    }
    @Override
    public boolean act() {
        Mob lowest = null;
        int preHP = -1;
        for (Mob mob : Dungeon.level.mobs) {
            if(mob.alignment == Char.Alignment.ALLY){
                if(preHP == -1) {
                    preHP = mob.HP;
                    lowest = mob;
                }
                else if (preHP > mob.HP) {
                    preHP = mob.HP;
                    lowest = mob;
                }
            }
        }
        if(lowest != null){
            lowest.HP+=Dungeon.hero.pointsInTalent(Talent.HONED_ARTS);
            lowest.HP = Math.min(lowest.HP, lowest.HT);
            lowest.sprite.emitter().burst(Speck.factory(Speck.HEALING), 4);
        }
        spend( 25f );
        return true;
    }
}
