package com.shatteredpixel.shatteredpixeldungeon.items.ror2items;

import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class LuckyLeaf extends ROR2item{
    {
        tier = 3;

        image = ItemSpriteSheet.LUCKYLEAF;
    }

    @Override
    protected ROR2item.ROR2itemBuff passiveBuff() {
        return new LuckyLeafBuff();
    }

    public class LuckyLeafBuff extends ROR2itemBuff{}

}