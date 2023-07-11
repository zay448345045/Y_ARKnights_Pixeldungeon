package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK3;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TalismanOfForesight;

public class CaerbannogNoSekai extends Skill {
    @Override
    public void doSkill() {
        for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
            mob.beckon(Dungeon.hero.pos);
            Buff.prolong(mob, Hex.class, Integer.MAX_VALUE);
            Buff.append(Dungeon.hero, TalismanOfForesight.CharAwareness.class, 100).charID = mob.id();
            Buff.affect(Dungeon.hero, Bless.class, 50);
            Buff.affect(Dungeon.hero, Adrenaline.class, 20);
        }
        curUser.spendAndNext(1f);
    }
}
