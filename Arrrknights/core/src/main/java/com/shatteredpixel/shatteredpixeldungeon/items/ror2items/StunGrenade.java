package com.shatteredpixel.shatteredpixeldungeon.items.ror2items;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class StunGrenade extends ROR2item{
    {
        tier = 1;
        image = ItemSpriteSheet.STUN_GRENADE;
    }
    //no effect here, see Char.attack

    @Override
    protected ROR2item.ROR2itemBuff passiveBuff() {
        return new StunGrenadeBuff();
    }

    public class StunGrenadeBuff extends ROR2itemBuff{}
}
