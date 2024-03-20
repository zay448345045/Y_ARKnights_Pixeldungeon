/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.ROR_LesserWisp;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Callback;

public class ROR_AlphaConstructSprite extends MobSprite {

    private int cellToAttack;
    private Animation charging;
    private Emitter chargeParticles;

    public ROR_AlphaConstructSprite() {
        super();

        texture( Assets.Sprites.SARKAZ_SNIPER );

        TextureFilm frames = new TextureFilm( texture, 38, 34 );

        idle = new Animation( 12, true );
        idle.frames( frames, 0, 0, 0, 0, 0 );

        run = new Animation( 14, true );
        run.frames( frames, 1, 2, 3, 4, 5, 6, 7, 8 );

        attack = new Animation( 15, false );
        attack.frames( frames, 9, 10, 11, 12, 13 );

        charging = new Animation( 12, true);
        charging.frames( frames, 11 );

        zap = attack.clone();

        die = new Animation( 12, false );
        die.frames( frames, 0 );

        play( idle );
    }

    @Override
    public void attack( int cell ) {
        if (!Dungeon.level.adjacent( cell, ch.pos )) {

            cellToAttack = cell;
            turnTo( ch.pos , cell );
            play( zap );

        } else {

            super.attack( cell );

        }
    }

    public void charge( int pos ){
        turnTo(ch.pos, pos);
        play(charging);
        if (visible) Sample.INSTANCE.play( Assets.Sounds.CHARGEUP );
    }

    @Override
    public void link(Char ch) {
        super.link(ch);

        chargeParticles = centerEmitter();
        chargeParticles.autoKill = false;
        chargeParticles.pour(MagicMissile.MagicParticle.ATTRACTING, 0.05f);
        chargeParticles.on = false;

        if (((ROR_LesserWisp)ch).beamCharged) play(charging);
    }
    @Override
    public void update() {
        super.update();
        if (chargeParticles != null){
            chargeParticles.pos( center() );
            chargeParticles.visible = visible;
        }
    }
    @Override
    public void onComplete( Animation anim ) {
        if (anim == zap) {
            idle();

            ((MissileSprite)parent.recycle( MissileSprite.class )).
                    reset( this, cellToAttack, new WispShot(), new Callback() {
                        @Override
                        public void call() {
                            ch.onAttackComplete();
                        }
                    } );
        } else if (anim == die){
            chargeParticles.killAndErase();
        }else{
            super.onComplete( anim );
        }
    }
    @Override
    public void kill() {
        super.kill();
        if (chargeParticles != null){
            chargeParticles.killAndErase();
        }
    }
    @Override
    public void play(Animation anim) {
        if (chargeParticles != null) chargeParticles.on = anim == charging;
        super.play(anim);
    }
    public class WispShot extends Item {
        {
            image = MagicMissile.FORCE;
        }
    }
}
