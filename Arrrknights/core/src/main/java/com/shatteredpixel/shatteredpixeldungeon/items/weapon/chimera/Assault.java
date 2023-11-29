package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAssassin;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Assault extends Weapon.Chimera{
    @Override
    public String afterName() {
        return Messages.get(this, "name");
    }
    @Override
    public float correct(){
        if (Dungeon.hero.belongings.getItem(RingOfAssassin.class) != null && Dungeon.hero.belongings.getItem(RingOfAssassin.class).isEquipped(Dungeon.hero)) {
            return 1f;
        }
        return 0.67f;
    }
}
