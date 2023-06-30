package com.shatteredpixel.shatteredpixeldungeon.items.bags;

import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class EquipmentsBag extends Bag{
    {
        image = ItemSpriteSheet.UNDONE_MARK;
    }
    @Override
    public boolean canHold( Item item ) {
        if (item instanceof EquipableItem){
            return super.canHold(item);
        } else {
            return false;
        }
    }

    public int capacity(){
        return 19;
    }

    @Override
    public int value() {
        return 40;
    }
}
