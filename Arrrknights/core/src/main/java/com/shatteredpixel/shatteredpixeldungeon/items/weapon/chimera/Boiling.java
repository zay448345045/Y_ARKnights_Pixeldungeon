package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;

public class Boiling extends Weapon.Chimera{
    @Override
    public String beforeName() {
        return Messages.get(this, "name");
    }
    @Override
    public int rchFactor(){
        return 1;
    }
    @Override
    public float dlyFactor(){
        return 0.5f;
    }
    @Override
    public float dmgFactor(){
        return 0.65f;
    }
    @Override
    public float accFactor(){
        return 0.85f;
    }
}
