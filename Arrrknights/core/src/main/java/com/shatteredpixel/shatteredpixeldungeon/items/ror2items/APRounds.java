package com.shatteredpixel.shatteredpixeldungeon.items.ror2items;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class APRounds extends ROR2item{
    {
        tier = 1;
        image = ItemSpriteSheet.UNDONE_MARK;
    }

    @Override
    public int attackProc(Char attacker, Char defender, int damage ) {
        if(defender.properties().contains(Char.Property.BOSS)){
            damage *= 1.2;
        }
        return damage;
    }
    @Override
    protected ROR2item.ROR2itemBuff passiveBuff() {
        return new APRBuff();
    }
    public class APRBuff extends ROR2itemBuff{}
}
