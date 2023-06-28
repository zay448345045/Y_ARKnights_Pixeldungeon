package com.shatteredpixel.shatteredpixeldungeon.items.ror2items;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.utils.Random;

public class Raincoat extends ROR2item{
    {
        tier = 3;
        image = ItemSpriteSheet.UNDONE_MARK;
    }

    @Override
    protected ROR2itemBuff passiveBuff() {
        return new RaincoatBuff();
    }

    public class RaincoatBuff extends ROR2itemBuff{
        private int cooldown = 5;
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
        public int icon() {
            return BuffIndicator.IMMUNITY;
        }
        @Override
        public float iconFadePercent() {
            return Math.max(0, cooldown / 5.0f);
        }
    }
}
