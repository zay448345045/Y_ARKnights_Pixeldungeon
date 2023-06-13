package com.shatteredpixel.shatteredpixeldungeon.items.ror2items;

import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Perforator extends ROR2item{
    {
        tier = 4;

        image = ItemSpriteSheet.PERFORATOR;
    }

    @Override
    protected ROR2item.ROR2itemBuff passiveBuff() {
        return new Perforator.PerforatorBuff();
    }

    public class PerforatorBuff extends ROR2itemBuff{}
}
