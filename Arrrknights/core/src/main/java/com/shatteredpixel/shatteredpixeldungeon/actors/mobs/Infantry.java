package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Ceylon;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.sprites.InfantrySprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Siesta_InfantrySprite;
import com.watabou.utils.Random;

// 화람지심 보병
public class Infantry extends Mob {
    {
        spriteClass = Siesta_InfantrySprite.class;

        HP = HT = 125;
        damageMax = 45;
        damageMin = 35;
        drMax = 20;
        drMin = 0;
        attackSkill = 40;
        defenseSkill = 25;

        EXP = 15;
        maxLvl = 28;

        loot = Gold.class;
        lootChance = 0.28f;

        immunities.add(Silence.class);
    }
    @Override
    public void damage(int dmg, Object src) {

        if (HP == HT) {
            dmg = Math.min(HT-1, dmg);
        }

        super.damage(dmg, src);
    }

    @Override
    public void rollToDropLoot() {
        Ceylon.Quest.process( this );

        super.rollToDropLoot();
    }
}
