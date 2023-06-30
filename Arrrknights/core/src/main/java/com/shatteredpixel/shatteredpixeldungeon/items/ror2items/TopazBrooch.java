package com.shatteredpixel.shatteredpixeldungeon.items.ror2items;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class TopazBrooch extends ROR2item{
    {
        tier = 1;
        image = ItemSpriteSheet.UNDONE_MARK;
    }
    @Override
    public void uponKill(Char attacker, Char defender, int damage ) {
        Buff.affect(attacker, Barrier.class).incShield(10);
    }

    @Override
    protected ROR2item.ROR2itemBuff passiveBuff() {return new TopazBroochBuff();}
    public class TopazBroochBuff extends ROR2itemBuff{}

}
