package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Grim;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.watabou.utils.Bundle;

public class PotatoAimReady extends Buff{
    private int pos;
    private int potatoAimCount = 0;
    public void addCount(){
        potatoAimCount++;
    }
    public void set(){
        pos = target.pos;
    }
    public boolean isReady(){
        return (potatoAimCount >= 6 - Dungeon.hero.pointsInTalent(Talent.POTATO_AIM));
    }
    public static void PotatoKill(Char ch){
        if(!(ch.properties().contains(Char.Property.MINIBOSS) || ch.properties().contains(Char.Property.BOSS))) {
            ch.HP = 0;
            if (!ch.isAlive()) {
                ch.sprite.showStatus(CharSprite.NEGATIVE, Messages.get(Preparation.class, "assassinated"));
                ch.die(Dungeon.hero);
                Dungeon.hero.buff(PotatoAimReady.class).detach();
            }
        }else{
            Grim grim = new Grim();
            ch.damage( ch.HP, grim);
            ch.sprite.emitter().burst( ShadowParticle.UP, 5 );
            Dungeon.hero.buff(PotatoAimReady.class).detach();
        }
    }
    @Override
    public boolean act() {
        if (target.pos != pos) {
            detach();
        }
        spend( TICK );
        return true;
    }
    private static final String POS	= "pos";
    private static final String POTATOAIMCOUNT	= "potatoaimcount";
    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( POS, pos );
        bundle.put( POTATOAIMCOUNT, potatoAimCount );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        pos = bundle.getInt( POS );
        potatoAimCount = bundle.getInt( POTATOAIMCOUNT );
    }
}
