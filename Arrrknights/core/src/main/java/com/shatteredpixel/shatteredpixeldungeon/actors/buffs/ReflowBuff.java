package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;

public class ReflowBuff extends FlavourBuff{
        public static final float DURATION	= 15f;

    {
            type = buffType.POSITIVE;
            announced = true;
            }

@Override
public int icon() {
        return BuffIndicator.BLESS;
        }

@Override
public float iconFadePercent() {
        return Math.max(0, (DURATION - visualcooldown()) / DURATION);
        }

@Override
public String toString() {
        return Messages.get(this, "name");
        }

@Override
public String desc() {
        return Messages.get(this, "desc", dispTurns());
        }
}
