package com.shatteredpixel.shatteredpixeldungeon.items.ror2items;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.levels.DeadEndLevel;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Crowbar extends ROR2item{
    {
        tier = 1;
        image = ItemSpriteSheet.CROWBAR;
    }

    @Override
    public int attackProc(Char attacker, Char defender, int damage ) {
        if(defender.HP*10 > defender.HT*9){
            damage *= 1.75;
        }
        return damage;
    }
    @Override
    protected ROR2item.ROR2itemBuff passiveBuff() {return new CrowbarBuff();}
    public class CrowbarBuff extends ROR2itemBuff{}
}
