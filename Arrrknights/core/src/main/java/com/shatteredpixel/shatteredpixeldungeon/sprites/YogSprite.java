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
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.watabou.noosa.TextureFilm;

public class YogSprite extends MobSprite {
	
	public YogSprite() {
		super();

		perspectiveRaise = 5 / 16f;

		texture( Assets.Sprites.YOG );
		
		TextureFilm frames = new TextureFilm( texture, 62, 40 );
		
		idle = new Animation( 10, true );
		idle.frames( frames, 0 );
		
		run = new Animation( 12, true );
		run.frames( frames, 0 );
		
		attack = new Animation( 16, false );
		attack.frames( frames, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 );

		zap = attack.clone();
		
		die = new Animation( 10, false );
		die.frames( frames, 0 );
		
		play( idle );
	}

	@Override
	public void link(Char ch) {
		super.link(ch);
		renderShadow = false;
	}

	@Override
	public void die() {
		super.die();
		
		Splash.at( center(), blood(), 12 );
	}
}
