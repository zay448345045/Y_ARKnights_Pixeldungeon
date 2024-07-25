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

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.ScholarNotebook;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.EtherealChains;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SSP.StaffOfMageHand;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BanditSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Ghost2Sprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Bandit extends Thief {
	
	public Item item;
	
	{
		spriteClass = Ghost2Sprite.class;

		//guaranteed first drop, then 1/3, 1/9, etc.
		lootChance = 1f;
	}
	
	@Override
	protected boolean steal( Hero hero ) {
		if (super.steal( hero )) {
			
			Buff.prolong( hero, Blindness.class, Blindness.DURATION/2f );
			Buff.affect( hero, Poison.class ).set(Random.Int(5, 7) );
			Buff.prolong( hero, Cripple.class, Cripple.DURATION/2f );
			if (Dungeon.isChallenged(Challenges.TACTICAL_UPGRADE)) Buff.affect(hero, Silence.class, 5f);
			Dungeon.observe();
			
			return true;
		} else {
			return false;
		}
	}
	@Override
	public boolean hasNotebookSkill(){ return true;}

	@Override
	public void notebookSkill(ScholarNotebook notebook, int index){
		GameScene.selectCell(caster);
	}
	private CellSelector.Listener caster = new CellSelector.Listener(){
		@Override
		public void onSelect(Integer target) {
			if(target == null)return;
			if(!Dungeon.level.adjacent(Dungeon.hero.pos, target)) {
				GLog.i(Messages.get(Bandit.class, "notadjacent"));return;
			}
			if (Actor.findChar(target) == null) {
				GLog.i(Messages.get(Bandit.class, "notarget"));return;
			}
			Char m = Actor.findChar(target);
			if(!(m instanceof Mob)){
				GLog.i(Messages.get(Bandit.class, "notamob"));return;
			}
			if(((Mob)m).loot == null){
				GLog.i(Messages.get(Bandit.class, "noloot"));return;
			}
			Item i = ((Mob) m).createLoot();
			if (i.doPickUp( hero )) {
				GLog.i( Messages.get(hero, "you_now_have", i.name()) );
			} else {
				Dungeon.level.drop( i, hero.pos ).sprite.drop();
			}
			Buff.affect(m, StaffOfMageHand.MageHandStolenTracker.class);
		}
		@Override
		public String prompt() {
			return Messages.get(EtherealChains.class, "prompt");
		}
	};
}
