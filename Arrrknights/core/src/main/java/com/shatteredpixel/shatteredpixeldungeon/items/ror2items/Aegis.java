package com.shatteredpixel.shatteredpixeldungeon.items.ror2items;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Aegis extends ROR2item{
    {
        tier = 3;

        image = ItemSpriteSheet.AEGIS;
    }

    public static void addShield(float HealAmount){
            Buff.affect(Dungeon.hero, Barrier.class).incShield(
                    Math.max(0,Math.round((HealAmount-(Dungeon.hero.HT-Dungeon.hero.HP))/2))
            );
    }

    @Override
    protected ROR2itemBuff passiveBuff() {
        return new AegisBuff();
    }

    public class AegisBuff extends ROR2itemBuff{}

}