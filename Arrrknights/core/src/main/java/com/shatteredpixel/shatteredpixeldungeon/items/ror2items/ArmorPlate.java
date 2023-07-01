package com.shatteredpixel.shatteredpixeldungeon.items.ror2items;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class ArmorPlate extends ROR2item{
    {
        tier = 1;
        image = ItemSpriteSheet.UNDONE_MARK;
    }
    @Override
    public int defenseProc(Char attacker, Char defender, int damage ) {
        damage -= 5;
        return damage;
    }

    @Override
    protected ROR2item.ROR2itemBuff passiveBuff() {
        return new ArmorPlateBuff();
    }

    public class ArmorPlateBuff extends ROR2itemBuff{}
}
