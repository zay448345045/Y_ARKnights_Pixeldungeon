package com.shatteredpixel.shatteredpixeldungeon.items.ror2items;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Raincoat extends ROR2item{
    {
        tier = 3;
        image = ItemSpriteSheet.RAINCOAT;
    }

    @Override
    protected ROR2itemBuff passiveBuff() {
        return new RaincoatBuff();
    }

    public class RaincoatBuff extends ROR2itemBuff{
        public int cooldown = 5;
        @Override
        public boolean act() {
            if(cooldown>0)cooldown--;
            if(cooldown<=0){
                for (Buff b : target.buffs()){
                    if (b.type == Buff.buffType.NEGATIVE && !(b instanceof Corruption)){
                        b.detach();
                        target.sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "resist"));
                        cooldown = 5;
                        break;
                    }
                }
            }
            spend(TICK);
            return true;
        }

        @Override
        public String toString() {
            return Messages.get(this, "name");
        }
        @Override
        public String desc() {
            return Messages.get(this, "desc", cooldown);
        }
        @Override
        public int icon() {
            return BuffIndicator.RAINCOAT_BUFF;
        }
        @Override
        public float iconFadePercent() {
            return Math.max(0, cooldown / 5.0f);
        }
        private static final String COOLDOWN	= "cooldown";

        @Override
        public void storeInBundle( Bundle bundle ) {
            super.storeInBundle( bundle );
            bundle.put( COOLDOWN, cooldown );
        }

        @Override
        public void restoreFromBundle( Bundle bundle ) {
            super.restoreFromBundle( bundle );
            cooldown = bundle.getInt( COOLDOWN );
        }
    }
}
