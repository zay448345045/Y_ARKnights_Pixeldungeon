package com.shatteredpixel.shatteredpixeldungeon.items.ror2items;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ROR2Shield;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.DamageWand;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Transcendence extends ROR2item{
    {
        tier = 6;
        image = ItemSpriteSheet.TRANSCENDENCE;
    }
    @Override
    protected ROR2item.ROR2itemBuff passiveBuff() {return new TranscendenceBuff();}
    public class TranscendenceBuff extends ROR2itemBuff{}

    @Override
    public boolean doEquip(Hero hero) {
        if(super.doEquip(hero)){
            int toGainShield;
            toGainShield = (int)(Dungeon.hero.HP*1.5f);
            Buff.affect(Dungeon.hero, ROR2Shield.class).setMaxShield((int)(Dungeon.hero.HT*1.5),false);
            Dungeon.hero.buff(ROR2Shield.class).setShield(toGainShield);
            Dungeon.hero.updateHT(false);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean doUnequip(Hero hero, boolean collect, boolean single) {
        if(super.doUnequip(hero, collect, single)){
            int recoverHP;
            recoverHP=(int)Math.ceil(hero.shielding()/1.5f);
            Buff.detach(Dungeon.hero, ROR2Shield.class);
            Dungeon.hero.updateHT(false);
            Dungeon.hero.HP=Math.min(recoverHP+1, Dungeon.hero.HT);
            return true;
        }
        return false;
    }
}
