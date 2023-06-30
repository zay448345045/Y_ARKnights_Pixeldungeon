package com.shatteredpixel.shatteredpixeldungeon.items.ror2items;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class TougherTimes extends ROR2item{
    {
        tier = 1;
        image = ItemSpriteSheet.TOUGHER_TIMES;
    }

    @Override
    protected ROR2item.ROR2itemBuff passiveBuff() {return new TougherTimesBuff();}

    public class TougherTimesBuff extends ROR2itemBuff{}
}
