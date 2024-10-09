package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CannotSprite;
import com.watabou.utils.Random;

public class Cannot extends Mob {
    {
        spriteClass = CannotSprite.class;

        HP = HT = 250;
        damageMax = 40;
        damageMin = 35;
        drMax = 12;
        drMin = 0;
        attackSkill = 35;
        defenseSkill = 20;

        EXP = 15;
        maxLvl = 30;

        loot = ScrollOfEnchantment.class;
        lootChance = 1f;
        properties.add(Property.MINIBOSS);
        immunities.add(Silence.class);
        immunities.add(Paralysis.class);
        immunities.add(Terror.class);

        state = HUNTING;
    }

    @Override
    protected boolean canAttack(Char enemy) {
        if (this.fieldOfView[enemy.pos]){ return true; }
        return false;
    }

    @Override
    public int attackProc( Char enemy, int damage ) {
        Buff.affect(enemy, Bleeding.class).set(10 + 5*rounds);
        Buff.affect(enemy, Vertigo.class, 2f);

        return damage;
    }
}
