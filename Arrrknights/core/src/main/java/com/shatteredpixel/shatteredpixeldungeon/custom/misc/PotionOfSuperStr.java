package com.shatteredpixel.shatteredpixeldungeon.custom.misc;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class PotionOfSuperStr extends Potion {
    {
        image = ItemSpriteSheet.EXOTIC_AMBER;
        icon = ItemSpriteSheet.Icons.SCROLL_ENCHANT;
        bones = false;

    }

    @Override
    public void apply( Hero hero ) {
        for(int i = 0; i<10;i++){
            hero.STR++;
        }
    }
    @Override
    public boolean isIdentified() {
        return true;
    }
}
