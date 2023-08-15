package com.shatteredpixel.shatteredpixeldungeon.items.ror2items;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

import java.util.function.DoubleUnaryOperator;

public class TopazBrooch extends ROR2item{
    {
        tier = 1;
        image = ItemSpriteSheet.TOPAZ_BROOCH;
    }
    @Override
    public void uponKill(Char attacker, Char defender, int damage ) {
        Buff.affect(attacker, Barrier.class).incShield(3+ Dungeon.depth/5);
    }

    @Override
    protected ROR2item.ROR2itemBuff passiveBuff() {return new TopazBroochBuff();}
    public class TopazBroochBuff extends ROR2itemBuff{}

}
