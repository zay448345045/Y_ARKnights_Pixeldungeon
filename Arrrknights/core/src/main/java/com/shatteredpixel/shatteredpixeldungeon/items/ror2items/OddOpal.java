package com.shatteredpixel.shatteredpixeldungeon.items.ror2items;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;

public class OddOpal extends ROR2item{
    {
        tier = 1;
        image = ItemSpriteSheet.UNDONE_MARK;
    }

    @Override
    public int defenseProc(Char attacker, Char defender, int damage ) {
        Buff.prolong(Dungeon.hero, OddOpalTracker.class,7);
        if(Dungeon.hero.buffs(OddOpalTracker.class)==null){
            damage/=2;
        }
        return damage;
    }

    @Override
    public boolean doUnequip(Hero hero, boolean collect, boolean single ) {
        if (super.doUnequip( hero, collect, single )) {
            OddOpalTracker oot;
            oot = Dungeon.hero.buff(OddOpalTracker.class);
            if (oot != null) {
                oot.detach();
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected ROR2item.ROR2itemBuff passiveBuff() {return new OddOpalBuff();}
    public class OddOpalBuff extends ROR2itemBuff{}

    public static class OddOpalTracker extends FlavourBuff {
        public static final float DURATION	= 7f;
        @Override
        public int icon() {
            return BuffIndicator.ARMOR;
        }
        @Override
        public float iconFadePercent() {
            return Math.max(0, visualcooldown() / DURATION);
        }
        @Override
        public String toString() {
            return Messages.get(this, "name");
        }
        @Override
        public String desc() {
            return Messages.get(this, "desc");
        }
    }
}
