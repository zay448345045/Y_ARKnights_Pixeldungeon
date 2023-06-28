package com.shatteredpixel.shatteredpixeldungeon.items.ror2items;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.effects.Lightning;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SparkParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfLightning;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class Perforator extends ROR2item{
    {
        tier = 4;

        image = ItemSpriteSheet.PERFORATOR;
    }
    @Override
    public int attackProc(Char attacker, Char defender, int damage ) {
        if(Random.IntRange(0,10)==0){
            WandOfLightning wol = new WandOfLightning();
            defender.damage(damage*5, wol);
            Camera.main.shake( 2, 0.3f );
            Dungeon.hero.sprite.centerEmitter().burst( SparkParticle.FACTORY, 15 );
            defender.sprite.parent.addToFront( new Lightning( Dungeon.hero.sprite.center(), defender.sprite.center(), null ) );
            Sample.INSTANCE.play( Assets.Sounds.LIGHTNING );
        }
        return damage;
    }

    @Override
    protected ROR2item.ROR2itemBuff passiveBuff() {
        return new Perforator.PerforatorBuff();
    }

    public class PerforatorBuff extends ROR2itemBuff{}
}
