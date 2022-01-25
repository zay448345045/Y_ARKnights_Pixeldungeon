package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class Tiacauh_ImpalerSprite extends MobSprite {

    public Tiacauh_ImpalerSprite() {
        super();

        texture( Assets.Sprites.TIACAUH_IMPALER);

        TextureFilm frames = new TextureFilm( texture, 56, 38 );

        idle = new Animation( 2, true );
        idle.frames( frames, 0, 0, 0 );

        run = new Animation( 18, true );
        run.frames( frames, 0 );

        attack = new Animation( 15, false );
        attack.frames( frames, 0 );

        die = new Animation( 10, false );
        die.frames( frames, 0 );

        play( idle );
    }
}