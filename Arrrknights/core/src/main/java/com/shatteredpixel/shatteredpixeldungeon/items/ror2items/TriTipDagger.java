package com.shatteredpixel.shatteredpixeldungeon.items.ror2items;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.effects.Lightning;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SparkParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfLightning;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class TriTipDagger extends ROR2item{
    {
        tier = 1;
        image = ItemSpriteSheet.UNDONE_MARK;
    }
    @Override
    public int attackProc(Char attacker, Char defender, int damage ) {
        if(Random.IntRange(0,10)==0){
            defender.sprite.burst(0x00FF00FF, 20);
            Buff.affect( defender, Bleeding.class ).set( Math.round(damage*0.5f) );
            Sample.INSTANCE.play( Assets.Sounds.LIGHTNING );
        }
        return damage;
    }

    @Override
    protected ROR2item.ROR2itemBuff passiveBuff() {
        return new TriTipDaggerBuff();
    }

    public class TriTipDaggerBuff extends ROR2itemBuff{}
}
