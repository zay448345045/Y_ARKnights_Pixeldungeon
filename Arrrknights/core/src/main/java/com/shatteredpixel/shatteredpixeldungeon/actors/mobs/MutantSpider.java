package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Ooze;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Mutant_SpiderSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.StrikerSprite;
import com.watabou.utils.Random;

public class MutantSpider extends Mob {
    {
        spriteClass = Mutant_SpiderSprite.class;

        HP = HT = 250;
        damageMax = 40;
        damageMin = 32;
        drMax = 18;
        drMin = 4;
        attackSkill = 37;
        defenseSkill = 18;

        EXP = 23;
        maxLvl = 38;

        loot = new StoneOfEnchantment();
        lootChance = 1f;

        immunities.add(Corruption.class);
        immunities.add(Silence.class);
        immunities.add(Ooze.class);
        properties.add(Property.INFECTED);
    }

    @Override
    protected boolean canAttack(Char enemy) {
        if (super.canAttack(enemy)) return true;//change from budding
        return this.fieldOfView[enemy.pos] && Dungeon.level.distance(this.pos, enemy.pos) <= 2;
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        Buff.affect(enemy, Slow.class, 2f);
        return super.attackProc(enemy, damage);
    }
}
