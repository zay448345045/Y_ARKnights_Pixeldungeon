package com.shatteredpixel.shatteredpixeldungeon.effects.coversprite;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class FrostBlastCover extends CoverSprite{
    public FrostBlastCover(){
        super();

        texture( Assets.Sprites.FROSTBLASTCOVER );

        TextureFilm frames = new TextureFilm( texture, 180, 180 );

        defaultAnim = new Animation( 30, false );
        defaultAnim.frames( frames, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21);

        play(defaultAnim);
    }
}
