package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HaundSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Tiacauh_warriorSprite;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class TiacauhWarrior extends Mob {
    {
        spriteClass = Tiacauh_warriorSprite.class;

        HP = HT = 110;
        damageMax = 40;
        damageMin = 28;
        drMax = 16;
        drMin = 0;
        attackSkill = 38;
        defenseSkill = 18;

        EXP = 14;
        maxLvl = 29;

        loot = new MysteryMeat();
        lootChance = 0.137f;

        immunities.add(Silence.class);
    }
    @Override
    public void damage(int dmg, Object src) {
        int grassCells = 0;
        for (int i : PathFinder.NEIGHBOURS9) {
            if (Dungeon.level.map[pos+i] == Terrain.FURROWED_GRASS
                    || Dungeon.level.map[pos+i] == Terrain.HIGH_GRASS){
                grassCells++;
            }
        }
        if (grassCells > 0) dmg = Math.round(dmg * (1f - (grassCells * 0.04f)));

        if (Dungeon.isChallenged(Challenges.TACTICAL_UPGRADE)) dmg *= 0.8f;

        if (src == Burning.class) dmg *= 2;

        super.damage(dmg, src);
    }
}
