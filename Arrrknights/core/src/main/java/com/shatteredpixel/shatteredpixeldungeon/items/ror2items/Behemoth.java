package com.shatteredpixel.shatteredpixeldungeon.items.ror2items;

import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Behemoth extends ROR2item{
    {
        tier = 3;

        image = ItemSpriteSheet.BEHEMOTH;
    }

    @Override
    protected ROR2itemBuff passiveBuff() {
        return new Behemoth.BehemothBuff();
    }

    public class BehemothBuff extends ROR2itemBuff{}
}
