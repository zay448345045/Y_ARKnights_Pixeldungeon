package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;

public class Rhine extends Weapon.Chimera{
    @Override
    public String beforeName() {
        return Messages.get(this, "name");
    }
    @Override
    public int defenseFactor(int lvl){return 4+2*lvl;}
    @Override
    public float dmgFactor(){return 0.5f;}
}
