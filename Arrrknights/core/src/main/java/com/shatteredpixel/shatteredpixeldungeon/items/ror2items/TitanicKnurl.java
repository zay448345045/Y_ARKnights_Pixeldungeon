package com.shatteredpixel.shatteredpixeldungeon.items.ror2items;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class TitanicKnurl extends ROR2item{
    {
        tier = 4;

        image = ItemSpriteSheet.TITANICKNURL;
    }

    @Override
    public boolean doEquip(Hero hero) {
        if (super.doEquip(hero)){
            hero.updateHT( false );
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean doUnequip(Hero hero, boolean collect, boolean single) {
        if (super.doUnequip(hero, collect, single)){
            hero.updateHT( false );
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected ROR2item.ROR2itemBuff passiveBuff() {
        return new TitanicKnurlBuff();
    }

    public class TitanicKnurlBuff extends ROR2itemBuff{}
}
