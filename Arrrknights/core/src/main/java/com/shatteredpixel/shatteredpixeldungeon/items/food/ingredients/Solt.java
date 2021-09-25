package com.shatteredpixel.shatteredpixeldungeon.items.food.ingredients;

import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Solt extends Ingredients {
    {
        image = ItemSpriteSheet.GOLD;
    }

    @Override
    public int value() {
        return 8;
    }
}