package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class VoidInfestorSprite extends MobSprite {
    public VoidInfestorSprite(){
        super();
        texture( Assets.Sprites.VOID_INFESTOR );
        TextureFilm frames = new TextureFilm( texture, 22, 16 );
        idle = new Animation( 2, true );
        idle.frames( frames, 0);

        run = new Animation( 8, true );
        run.frames( frames, 0 );

        attack = new Animation( 20, false );
        attack.frames( frames, 0 );

        die = new Animation( 10, false );
        die.frames( frames, 0 );

        play( idle );
    }
}
