package com.shatteredpixel.shatteredpixeldungeon.items.ror2items;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class LightFluxPauldron extends ROR2item{
    {
        tier = 6;
        image = ItemSpriteSheet.LIGHTFLUX;
    }
    public static float LFPChargeMultiplier(){
        return ((Dungeon.hero.buff(LightFluxPauldron.LightFluxPauldronBuff.class)!= null)?1.5f:1);
    }
    @Override
    protected ROR2item.ROR2itemBuff passiveBuff() {return new LightFluxPauldronBuff();}
    public class LightFluxPauldronBuff extends ROR2itemBuff{}
}
