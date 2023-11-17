package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;

public class Highest extends Weapon.Chimera{
    @Override
    public String beforeName() {
        return Messages.get(this, "name");
    }
    @Override
    public int rchFactor(){
        return 3;
    }
    @Override
    public float dmgFactor(){
        return 0.6f;
    }
}
