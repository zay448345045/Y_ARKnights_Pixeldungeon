package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Striker_EliteSprite;
import com.watabou.utils.Random;

public class StrikerElite extends Striker {
    {
        spriteClass = Striker_EliteSprite.class;

        HP = HT = 130;
        drMax = 24;
        drMin = 8;
        attackSkill = 33;
        defenseSkill = 20;

        loot = new ScrollOfTeleportation();
        lootChance = 1f;
    }
    @Override
    public int damageRoll() {
        if (HP <= HT/2) return Random.NormalIntRange(45+damageMinInc,60+damageMaxInc);
        return Random.NormalIntRange(30+damageMinInc,40+damageMaxInc);
    }
}
