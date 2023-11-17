package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;

public class Gloves extends Weapon.Chimera{
    @Override
    public String afterName() {
        return Messages.get(this, "name");
    }
    @Override
    public float dlyFactor(){return 0.4f;}
    @Override
    public float dmgFactor(){return 0.5f;}
}
