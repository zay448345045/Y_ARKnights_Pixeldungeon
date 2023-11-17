package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;

public class Bloody extends Weapon.Chimera{
    @Override
    public String beforeName() {
        return Messages.get(this, "name");
    }
    @Override
    public float dlyFactor(){return 0.4f;}
    @Override
    public float accFactor(){return 0.6f;}
}
