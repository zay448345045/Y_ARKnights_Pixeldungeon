package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.shatteredpixeldungeon.sprites.InfantrySprite;
import com.watabou.utils.Random;

public class RioterLeader extends Gnoll{
    {
        spriteClass = InfantrySprite.class;

        HP = HT = 12;
        damageMax = 10;
        damageMaxIncRate = 45;
        damageMaxInc = damageMaxIncRate*rounds;
        damageMin = 1;
        drMax = 2;
        drMaxIncRate = 13;
        drMaxInc = drMaxIncRate*rounds;
        drMin = 0;
        attackSkill = 10;
        defenseSkill = 4;

        EXP = 2;
        maxLvl = 8;

        loot = Gold.class;
        lootChance = 0.5f;
    }
}
