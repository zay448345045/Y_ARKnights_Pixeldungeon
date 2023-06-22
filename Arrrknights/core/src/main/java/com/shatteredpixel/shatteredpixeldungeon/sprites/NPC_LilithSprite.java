package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;

public class NPC_LilithSprite extends MobSprite {
    public NPC_LilithSprite() {
        super();

        texture( Assets.Sprites.NPC_LILITH );

        TextureFilm frames = new TextureFilm( texture, 36, 34 );

        idle = new MovieClip.Animation( 2, true );
        idle.frames( frames, 41, 42, 43, 44, 45, 46, 41, 42, 43, 44, 45, 46, 41, 42, 43, 44, 45, 46, 41, 42, 43, 44, 45, 46, 41, 42, 43, 47, 48, 46 );

        run = new MovieClip.Animation( 15, true );
        run.frames( frames, 1, 2, 3, 4, 5, 6, 7, 8 );

        attack = new MovieClip.Animation( 15, false );
        attack.frames( frames, 9, 10, 11, 12, 13, 14, 15, 16, 17 );

        zap = attack.clone();

        die = new MovieClip.Animation( 8, false );
        die.frames( frames, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36 );

        play( idle.clone() );
    }

    @Override
    public void idle() {
        isMoving = false;
        super.idle();
    }

}