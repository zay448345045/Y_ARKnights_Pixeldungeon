package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;

public class Demon extends Weapon.Chimera{
    @Override
    public String afterName() {
        return Messages.get(this, "name");
    }
}
