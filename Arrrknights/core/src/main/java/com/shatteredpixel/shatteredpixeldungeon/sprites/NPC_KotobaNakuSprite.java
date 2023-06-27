package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;

public class NPC_KotobaNakuSprite extends MobSprite {
    public NPC_KotobaNakuSprite() {
        super();

        texture( Assets.Sprites.NPC_KOTOBANAKU );

        TextureFilm frames = new TextureFilm( texture, 36, 34 );

        idle = new MovieClip.Animation( 2, false );
        idle.frames( frames, 0);
        run = new MovieClip.Animation( 15, true );
        run.frames( frames, 0 );

        attack = new MovieClip.Animation( 15, false );
        attack.frames( frames, 0 );

        zap = attack.clone();

        die = new MovieClip.Animation( 8, false );
        die.frames( frames, 0 );

        play( idle.clone() );
    }

    @Override
    public void idle() {
        isMoving = false;
        super.idle();
    }

}