package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;

public class Hyphen200 extends Weapon.Chimera {
    @Override
    public String afterName() {
        return Messages.get(this, "name");
    }
    @Override
    public float dlyFactor(){return 3f;}
    @Override
    public float dmgFactor(){return 3f;}
    @Override
    public int rchFactor(){return 50;}
}
