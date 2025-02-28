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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ChampionEnemy;
import com.shatteredpixel.shatteredpixeldungeon.effects.DarkBlock;
import com.shatteredpixel.shatteredpixeldungeon.effects.EmoIcon;
import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.effects.FloatingText;
import com.shatteredpixel.shatteredpixeldungeon.effects.IceBlock;
import com.shatteredpixel.shatteredpixeldungeon.effects.ShieldHalo;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.effects.TorchHalo;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BloodParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.FlameParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SnowParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SparkParticle;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.ui.CharHealthIndicator;
import com.watabou.glwrap.Matrix;
import com.watabou.glwrap.Vertexbuffer;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.NoosaScript;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.noosa.tweeners.PosTweener;
import com.watabou.noosa.tweeners.Tweener;
import com.watabou.utils.Callback;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.nio.Buffer;
import java.util.HashSet;

public class CharSprite extends MovieClip implements Tweener.Listener, MovieClip.Listener {
	
	// Color constants for floating text
	public static final int DEFAULT		= 0xFFFFFF;
	public static final int POSITIVE	= 0x00FF00;
	public static final int NEGATIVE	= 0xFF0000;
	public static final int WARNING		= 0xFF8800;
	public static final int NEUTRAL		= 0xFFFF00;
	public static final int MAGIC 		= 0xFF00FF;
	
	public static final float DEFAULT_MOVE_INTERVAL = 0.1f;
	private static float moveInterval = DEFAULT_MOVE_INTERVAL;
	private static final float FLASH_INTERVAL	= 0.05f;

	//the amount the sprite is raised from flat when viewed in a raised perspective
	protected float perspectiveRaise    = 6 / 16f; //6 pixels

	//the width and height of the shadow are a percentage of sprite size
	//offset is the number of pixels the shadow is moved down or up (handy for some animations)
	protected boolean renderShadow  = false;
	protected float shadowWidth     = 1.2f;
	protected float shadowHeight    = 0.25f;
	protected float shadowOffset    = 0.25f;

	public enum State {
		BURNING,
		LEVITATING,
		INVISIBLE,
		PARALYSED,
		FROZEN,
		ILLUMINATED,
		CHILLED,
		DARKENED,
		MARKED,
		HEALING,
		SHIELDED,
		TALU_BOSS,
		HIKARI,
		BLACK_FOG,
		HUNTING_MARK,
		VANISH,
		ELECTRIC,
		AURA
	}
	protected Animation idle;
	protected Animation run;
	protected Animation attack;
	protected Animation Sattack;
	protected Animation operate;
	protected Animation zap;
	protected Animation die;
	
	protected Callback animCallback;
	
	protected PosTweener motion;
	
	protected Emitter burning;
	protected Emitter chilled;
	protected Emitter marked;
	protected Emitter levitation;
	protected Emitter healing;
	protected Emitter taluboss;
	protected Emitter taluboss2;
	protected Emitter hikari;
	protected Emitter blackfog;
	protected Emitter huntingmark;
	protected Emitter electric;
	
	protected IceBlock iceBlock;
	protected DarkBlock darkBlock;
	protected TorchHalo light;
	protected ShieldHalo shield;
	protected AlphaTweener invisible;
	protected AlphaTweener vanish;
	protected Flare aura;
	
	protected EmoIcon emo;
	protected CharHealthIndicator health;

	private Tweener jumpTweener;
	private Callback jumpCallback;

	protected float flashTime = 0;
	
	protected boolean sleeping = false;

	public Char ch;

	//used to prevent the actor associated with this sprite from acting until movement completes
	public volatile boolean isMoving = false;
	
	public CharSprite() {
		super();
		listener = this;
	}
	public boolean hasEmitter(State s){
		switch (s) {
			case BURNING:
				return burning!=null;
			case LEVITATING:
				return levitation !=null;
			case INVISIBLE:
				return invisible != null;
			case PARALYSED:
				return paused; 
			case FROZEN:
				return iceBlock != null;
			case ILLUMINATED:
				return light != null;
			case CHILLED:
				return chilled != null;
			case DARKENED:
				return darkBlock != null;
			case MARKED:
				return marked != null;
			case HEALING:
				return healing != null;
			case SHIELDED:
				return shield != null;
			case TALU_BOSS:
				return (taluboss != null || taluboss2 != null);
			case HIKARI:
				return hikari != null;
			case BLACK_FOG:
				return blackfog != null;
			case HUNTING_MARK:
				return huntingmark != null;
			case VANISH:
				return vanish != null;
			case ELECTRIC:
				return electric != null;
			default:
				return false;
		}
	}
	@Override
	public void play(Animation anim) {
		//Shouldn't interrupt the dieing animation
		if (curAnim == null || curAnim != die) {
			super.play(anim);
		}
	}
	
	//intended to be used for placing a character in the game world
	public void link( Char ch ) {
		linkVisuals( ch );
		
		this.ch = ch;
		ch.sprite = this;
		
		place( ch.pos );
		turnTo( ch.pos, Random.Int( Dungeon.level.length() ) );
		renderShadow = true;
		
		if (ch != Dungeon.hero) {
			if (health == null) {
				health = new CharHealthIndicator(ch);
			} else {
				health.target(ch);
			}
		}

		ch.updateSpriteState();
	}
	
	//used for just updating a sprite based on a given character, not linking them or placing in the game
	public void linkVisuals( Char ch ){
		//do nothin by default
	}
	
	public PointF worldToCamera( int cell ) {
		
		final int csize = DungeonTilemap.SIZE;
		
		return new PointF(
			PixelScene.align(Camera.main, ((cell % Dungeon.level.width()) + 0.5f) * csize - width() * 0.5f),
			PixelScene.align(Camera.main, ((cell / Dungeon.level.width()) + 1.0f) * csize - height() - csize * perspectiveRaise)
		);
	}
	
	public void place( int cell ) {
		point( worldToCamera( cell ) );
	}
	
	public void showStatus( int color, String text, Object... args ) {
		if (visible) {
			if (args.length > 0) {
				text = Messages.format( text, args );
			}
			float x = destinationCenter().x;
			float y = destinationCenter().y - height()/2f;
			if (ch != null) {
				FloatingText.show( x, y, ch.pos, text, color );
			} else {
				FloatingText.show( x, y, text, color );
			}
		}
	}
	
	public void idle() {
		play(idle);
	}
	
	public void move( int from, int to ) {
		turnTo( from , to );

		play( run );
		
		motion = new PosTweener( this, worldToCamera( to ), moveInterval );
		motion.listener = this;
		parent.add( motion );

		isMoving = true;
		
		if (visible && Dungeon.level.water[from] && !ch.flying) {
			GameScene.ripple( from );
		}

	}
	
	public static void setMoveInterval( float interval){
		moveInterval = interval;
	}
	
	//returns where the center of this sprite will be after it completes any motion in progress
	public PointF destinationCenter(){
		PosTweener motion = this.motion;
		if (motion != null && motion.elapsed >= 0){
			return new PointF(motion.end.x + width()/2f, motion.end.y + height()/2f);
		} else {
			return center();
		}
	}
	
	public void interruptMotion() {
		if (motion != null) {
			motion.stop(false);
		}
	}
	
	public void attack( int cell ) {
		turnTo( ch.pos, cell );
		play( attack );
	}
	
	public void attack( int cell, Callback callback ) {
		animCallback = callback;
		turnTo( ch.pos, cell );
		play( attack );
	}

	public void Sattack( int cell ) {
		turnTo( ch.pos, cell );
		play( Sattack );
	}

	public void Sattack( int cell, Callback callback ) {
		animCallback = callback;
		turnTo( ch.pos, cell );
		play( Sattack );
	}
	
	public void operate( int cell ) {
		turnTo( ch.pos, cell );
		play( operate );
	}
	
	public void operate( int cell, Callback callback ) {
		animCallback = callback;
		turnTo( ch.pos, cell );
		play( operate );
	}
	
	public void zap( int cell ) {
		turnTo( ch.pos, cell );
		play( zap );
	}
	
	public void zap( int cell, Callback callback ) {
		animCallback = callback;
		zap( cell );
	}
	
	public void turnTo( int from, int to ) {
		int fx = from % Dungeon.level.width();
		int tx = to % Dungeon.level.width();
		if (tx > fx) {
			flipHorizontal = false;
		} else if (tx < fx) {
			flipHorizontal = true;
		}
	}

	public void jump( int from, int to, Callback callback ) {
		float distance = Dungeon.level.trueDistance( from, to );
		jump( from, to, callback, distance * 2, distance * 0.1f );
	}

	public void jump( int from, int to, Callback callback, float height, float duration ) {
		jumpCallback = callback;

		jumpTweener = new JumpTweener( this, worldToCamera( to ), height, duration );
		jumpTweener.listener = this;
		parent.add( jumpTweener );

		turnTo( from, to );
	}

	public void die() {
		sleeping = false;
		play( die );

		hideEmo();
		
		if (health != null){
			health.killAndErase();
		}
	}
	
	public Emitter emitter() {
		Emitter emitter = GameScene.emitter();
		if (emitter != null) emitter.pos( this );
		return emitter;
	}
	
	public Emitter centerEmitter() {
		Emitter emitter = GameScene.emitter();
		if (emitter != null) emitter.pos( center() );
		return emitter;
	}
	
	public Emitter bottomEmitter() {
		Emitter emitter = GameScene.emitter();
		if (emitter != null) emitter.pos( x, y + height, width, 0 );
		return emitter;
	}
	
	public void burst( final int color, int n ) {
		if (visible) {
			Splash.at( center(), color, n );
		}
	}
	
	public void bloodBurstA( PointF from, int damage ) {
		if (visible) {
			PointF c = center();
			int n = (int)Math.min( 9 * Math.sqrt( (double)damage / ch.HT ), 9 );
			Splash.at( c, PointF.angle( from, c ), 3.1415926f / 2, blood(), n );
		}
	}

	public int blood() {
		return 0xFFBB0000;
	}
	
	public void flash() {
		ra = ba = ga = 1f;
		flashTime = FLASH_INTERVAL;
	}

	private final HashSet<State> stateAdditions = new HashSet<>();
	
	public void add( State state ) {
		synchronized (State.class) {
			stateRemovals.remove(state);
			stateAdditions.add(state);
		}
//		switch (state) {
//			case BURNING:
//				burning = emitter();
//				burning.pour( FlameParticle.FACTORY, 0.06f );
//				if (visible) {
//					Sample.INSTANCE.play( Assets.Sounds.BURNING );
//				}
//				break;
//			case LEVITATING:
//				levitation = emitter();
//				levitation.pour( Speck.factory( Speck.JET ), 0.02f );
//				break;
//			case INVISIBLE:
//				if (invisible != null) {
//					invisible.killAndErase();
//				}
//				invisible = new AlphaTweener( this, 0.4f, 0.4f );
//				if (parent != null){
//					parent.add(invisible);
//				} else
//					alpha( 0.4f );
//				break;
//			case PARALYSED:
//				paused = true;
//				break;
//			case FROZEN:
//				iceBlock = IceBlock.freeze( this );
//				break;
//			case ILLUMINATED:
//				GameScene.effect( light = new TorchHalo( this ) );
//				break;
//			case CHILLED:
//				chilled = emitter();
//				chilled.pour(SnowParticle.FACTORY, 0.1f);
//				break;
//			case DARKENED:
//				darkBlock = DarkBlock.darken( this );
//				break;
//			case MARKED:
//				marked = emitter();
//				marked.pour(ShadowParticle.UP, 0.1f);
//				break;
//			case HEALING:
//				healing = emitter();
//				healing.pour(Speck.factory(Speck.HEALING), 0.5f);
//				break;
//			case SHIELDED:
//				GameScene.effect( shield = new ShieldHalo( this ));
//				break;
//			case TALU_BOSS:
//				taluboss = emitter();
//				taluboss.pour(FlameParticle.FACTORY, 0.03f);
//				taluboss2 = emitter();
//				taluboss2.pour(ShadowParticle.UP, 0.018f);
//				break;
//			case HIKARI:
//				hikari = emitter();
//				hikari.pour(ElmoParticle.FACTORY, 0.03f);
//				break;
//			case BLACK_FOG:
//				blackfog = emitter();
//				blackfog.pour(ShadowParticle.UP, 0.02f);
//				break;
//			case HUNTING_MARK:
//				huntingmark = emitter();
//				huntingmark.pour(BloodParticle.BURST, 0.02f);
//				break;
//			case VANISH:
//				if (vanish != null) {
//					vanish.killAndErase();
//				}
//				vanish = new AlphaTweener( this, 0f, 0.4f );
//				if (parent != null){
//					parent.add(vanish);
//				} else
//					alpha( 0.4f );
//				break;
//			case ELECTRIC:
//				electric = emitter();
//				electric.pour(SparkParticle.FACTORY, 0.04f);
//				break;
//		}
	}

	private int auraColor = 0;
	//Aura needs color data too
	public void aura( int color ){
		add(State.AURA);
		auraColor = color;
	}

	protected synchronized void processStateAddition( State state ) {
		switch (state) {
			case BURNING:
				if (burning != null) burning.on = false;
				burning = emitter();
				burning.pour(FlameParticle.FACTORY, 0.06f);
				if (visible) {
					Sample.INSTANCE.play(Assets.Sounds.BURNING);
				}
				break;
			case LEVITATING:
				if (levitation != null) levitation.on = false;
				levitation = emitter();
				levitation.pour(Speck.factory(Speck.JET), 0.02f);
				break;
			case INVISIBLE:
				if (invisible != null) invisible.killAndErase();
				invisible = new AlphaTweener(this, 0.4f, 0.4f);
				if (parent != null) {
					parent.add(invisible);
				} else
					alpha(0.4f);
				break;
			case PARALYSED:
				paused = true;
				break;
			case FROZEN:
				if (iceBlock != null) iceBlock.killAndErase();
				iceBlock = IceBlock.freeze(this);
				break;
			case ILLUMINATED:
				if (light != null) light.putOut();
				GameScene.effect(light = new TorchHalo(this));
				break;
			case CHILLED:
				if (chilled != null) chilled.on = false;
				chilled = emitter();
				chilled.pour(SnowParticle.FACTORY, 0.1f);
				break;
			case DARKENED:
				if (darkBlock != null) darkBlock.killAndErase();
				darkBlock = DarkBlock.darken(this);
				break;
			case MARKED:
				if (marked != null) marked.on = false;
				marked = emitter();
				marked.pour(ShadowParticle.UP, 0.1f);
				break;
			case HEALING:
				if (healing != null) healing.on = false;
				healing = emitter();
				healing.pour(Speck.factory(Speck.HEALING), 0.5f);
				break;
			case SHIELDED:
				if (shield != null) shield.killAndErase();
				GameScene.effect(shield = new ShieldHalo(this));
				break;
//			case HEARTS:
//				if (hearts != null) hearts.on = false;
//				hearts = emitter();
//				hearts.pour(Speck.factory(Speck.HEART), 0.5f);
//				break;
//			case GLOWING:
//				if (glowBlock != null) glowBlock.killAndErase();
//				glowBlock = GlowBlock.lighten(this);
//				break;
			case AURA:
				if (aura != null)   aura.killAndErase();
				float size = Math.max(width(), height());
				size = Math.max(size+4, 16);
				aura = new Flare(5, size);
				aura.angularSpeed = 90;
				aura.color(auraColor, true);
				aura.visible = visible;

				if (parent != null) {
					aura.show(this, 0);
				}
				break;
			case TALU_BOSS:
				if(taluboss!=null) taluboss.killAndErase();
				taluboss = emitter();
				taluboss.pour(FlameParticle.FACTORY, 0.03f);
				taluboss2 = emitter();
				taluboss2.pour(ShadowParticle.UP, 0.018f);
				break;
			case HIKARI:
				if(hikari!=null) hikari.killAndErase();
				hikari = emitter();
				hikari.pour(ElmoParticle.FACTORY, 0.03f);
				break;
			case BLACK_FOG:
				if(blackfog!=null) blackfog.killAndErase();
				blackfog = emitter();
				blackfog.pour(ShadowParticle.UP, 0.02f);
				break;
			case HUNTING_MARK:
				if(huntingmark!=null) huntingmark.killAndErase();
				huntingmark = emitter();
				huntingmark.pour(BloodParticle.BURST, 0.02f);
				break;
			case VANISH:
				if (vanish != null)  vanish.killAndErase();
				vanish = new AlphaTweener( this, 0f, 0.4f );
				if (parent != null){
					parent.add(vanish);
				} else
					alpha( 0.4f );
				break;
			case ELECTRIC:
				if(electric!=null) electric.killAndErase();
				electric = emitter();
				electric.pour(SparkParticle.FACTORY, 0.04f);
				break;
		}
	}

	private final HashSet<State> stateRemovals = new HashSet<>();

	public void remove( State state ) {
		synchronized (State.class) {
			stateAdditions.remove(state);
			stateRemovals.add(state);
		}
	}

	public void clearAura(){
		remove(State.AURA);
	}
	protected synchronized void processStateRemoval( State state ) {
		switch (state) {
			case BURNING:
				if (burning != null) {
					burning.on = false;
					burning = null;
				}
				break;
			case LEVITATING:
				if (levitation != null) {
					levitation.on = false;
					levitation = null;
				}
				break;
			case INVISIBLE:
				if (invisible != null) {
					invisible.killAndErase();
					invisible = null;
				}
				alpha(1f);
				break;
			case PARALYSED:
				paused = false;
				break;
			case FROZEN:
				if (iceBlock != null) {
					iceBlock.melt();
					iceBlock = null;
				}
				break;
			case ILLUMINATED:
				if (light != null) {
					light.putOut();
					light = null;
				}
				break;
			case CHILLED:
				if (chilled != null) {
					chilled.on = false;
					chilled = null;
				}
				break;
			case DARKENED:
				if (darkBlock != null) {
					darkBlock.lighten();
					darkBlock = null;
				}
				break;
			case MARKED:
				if (marked != null) {
					marked.on = false;
					marked = null;
				}
				break;
			case HEALING:
				if (healing != null) {
					healing.on = false;
					healing = null;
				}
				break;
			case SHIELDED:
				if (shield != null) {
					shield.putOut();
				}
				break;
//			case HEARTS:
//				if (hearts != null) {
//					hearts.on = false;
//					hearts = null;
//				}
//				break;
//			case GLOWING:
//				if (glowBlock != null){
//					glowBlock.darken();
//					glowBlock = null;
//				}
//				break;
			case AURA:
				if (aura != null){
					aura.killAndErase();
					aura = null;
				}
				break;
			case TALU_BOSS:
				if (taluboss != null){
					taluboss.on = false;
				}
				if (taluboss2 != null){
					taluboss2.on = false;
				}
				break;
			case HIKARI:
				if (hikari != null){
					hikari.on = false;
				}
				break;
			case BLACK_FOG:
				if (blackfog != null){
					blackfog.on = false;
					blackfog = null;
				}
				break;
			case HUNTING_MARK:
				if (huntingmark != null){
					huntingmark.on = false;
					huntingmark = null;
				}
				break;
			case VANISH:
				if (vanish != null) {
					vanish.killAndErase();
					vanish = null;
				}
				alpha( 1f );
				break;
			case ELECTRIC:
				if (electric != null){
					electric.on = false;
					electric = null;
				}
				break;
		}
	}
	@Override
	public void update() {
		if (paused && ch != null && curAnim != null && !curAnim.looped && !finished){
			listener.onComplete(curAnim);
			finished = true;
		}
		
		super.update();

		synchronized (State.class) {
			for (State s : stateAdditions) {
				processStateAddition(s);
			}
			stateAdditions.clear();
			for (State s : stateRemovals) {
				processStateRemoval(s);
			}
			stateRemovals.clear();
		}
		
		if (flashTime > 0 && (flashTime -= Game.elapsed) <= 0) {
			resetColor();
		}
		
		if (burning != null) {
			burning.visible = visible;
		}
		if (levitation != null) {
			levitation.visible = visible;
		}
		if (iceBlock != null) {
			iceBlock.visible = visible;
		}
		if (chilled != null) {
			chilled.visible = visible;
		}
		if (marked != null) {
			marked.visible = visible;
		}
		if (aura != null){
			aura.visible = visible;
			aura.point(center());
		}
		if (sleeping) {
			showSleep();
		} else {
			hideSleep();
		}
		synchronized (EmoIcon.class) {
			if (emo != null && emo.alive) {
				emo.visible = visible;
			}
		}
		if (ch != null){
			for (ChampionEnemy buff : ch.buffs(ChampionEnemy.class)) {
				if(buff instanceof ChampionEnemy.R2Blazing||
						buff instanceof ChampionEnemy.R2Overloading||
						buff instanceof ChampionEnemy.R2Glacial||
						buff instanceof ChampionEnemy.R2Malachite||
						buff instanceof ChampionEnemy.R2Celestine||
						buff instanceof ChampionEnemy.R2Perfected||
						buff instanceof ChampionEnemy.R2Mending) {
					hardlight(buff.color);
				}
			}
		}
	}
	
	@Override
	public void resetColor() {
		super.resetColor();
		if (invisible != null){
			alpha(0.4f);
		}
		if (vanish != null){
			alpha(0f);
		}
	}
	
	public void showSleep() {
		synchronized (EmoIcon.class) {
			if (!(emo instanceof EmoIcon.Sleep)) {
				if (emo != null) {
					emo.killAndErase();
				}
				emo = new EmoIcon.Sleep(this);
				emo.visible = visible;
			}
		}
		idle();
	}
	
	public void hideSleep() {
		synchronized (EmoIcon.class) {
			if (emo instanceof EmoIcon.Sleep) {
				emo.killAndErase();
				emo = null;
			}
		}
	}
	
	public void showAlert() {
		synchronized (EmoIcon.class) {
			if (!(emo instanceof EmoIcon.Alert)) {
				if (emo != null) {
					emo.killAndErase();
				}
				emo = new EmoIcon.Alert(this);
				emo.visible = visible;
			}
		}
	}
	
	public void hideAlert() {
		synchronized (EmoIcon.class) {
			if (emo instanceof EmoIcon.Alert) {
				emo.killAndErase();
				emo = null;
			}
		}
	}
	
	public void showLost() {
		synchronized (EmoIcon.class) {
			if (!(emo instanceof EmoIcon.Lost)) {
				if (emo != null) {
					emo.killAndErase();
				}
				emo = new EmoIcon.Lost(this);
				emo.visible = visible;
			}
		}
	}
	
	public void hideLost() {
		synchronized (EmoIcon.class) {
			if (emo instanceof EmoIcon.Lost) {
				emo.killAndErase();
				emo = null;
			}
		}
	}

	public void hideEmo(){
		synchronized (EmoIcon.class) {
			if (emo != null) {
				emo.killAndErase();
				emo = null;
			}
		}
	}
	
	@Override
	public void kill() {
		super.kill();
		
		hideEmo();
		
		for( State s : State.values()){
			remove(s);
		}
		
		if (health != null){
			health.killAndErase();
		}
	}

	private float[] shadowMatrix = new float[16];

	@Override
	protected void updateMatrix() {
		super.updateMatrix();
		Matrix.copy(matrix, shadowMatrix);
		Matrix.translate(shadowMatrix,
				(width * (1f - shadowWidth)) / 2f,
				(height * (1f - shadowHeight)) + shadowOffset);
		Matrix.scale(shadowMatrix, shadowWidth, shadowHeight);
	}

	@Override
	public void draw() {
		if (texture == null || (!dirty && buffer == null))
			return;

		if (renderShadow) {
			if (dirty) {
				((Buffer)verticesBuffer).position(0);
				verticesBuffer.put(vertices);
				if (buffer == null)
					buffer = new Vertexbuffer(verticesBuffer);
				else
					buffer.updateVertices(verticesBuffer);
				dirty = false;
			}

			NoosaScript script = script();

			texture.bind();

			script.camera(camera());

			updateMatrix();

			script.uModel.valueM4(shadowMatrix);
			script.lighting(
					0, 0, 0, am * .6f,
					0, 0, 0, aa * .6f);

			script.drawQuad(buffer);
		}

		super.draw();

	}

	@Override
	public void onComplete( Tweener tweener ) {
		if (tweener == jumpTweener) {

			if (visible && Dungeon.level.water[ch.pos] && !ch.flying) {
				GameScene.ripple( ch.pos );
			}
			if (jumpCallback != null) {
				jumpCallback.call();
			}

		} else if (tweener == motion) {

			synchronized (this) {
				isMoving = false;

				motion.killAndErase();
				motion = null;
				ch.onMotionComplete();

				notifyAll();
			}

		}
	}

	@Override
	public void onComplete( Animation anim ) {
		
		if (animCallback != null) {
			Callback executing = animCallback;
			animCallback = null;
			executing.call();
		} else {
			
			if (anim == attack) {
				
				idle();
				ch.onAttackComplete();
				
			} else if (anim == operate) {
				
				idle();
				ch.onOperateComplete();
				
			}
			
		}
	}

	private static class JumpTweener extends Tweener {

		public CharSprite visual;

		public PointF start;
		public PointF end;

		public float height;

		public JumpTweener( CharSprite visual, PointF pos, float height, float time ) {
			super( visual, time );

			this.visual = visual;
			start = visual.point();
			end = pos;

			this.height = height;
		}

		@Override
		protected void updateValues( float progress ) {
			float hVal = -height * 4 * progress * (1 - progress);
			visual.point( PointF.inter( start, end, progress ).offset( 0, hVal ) );
			visual.shadowOffset = 0.25f - hVal*0.8f;
		}
	}
}
