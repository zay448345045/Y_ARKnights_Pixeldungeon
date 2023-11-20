package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.BladeDemon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

public class Flame extends Weapon.Chimera{
    @Override
    public String beforeName() {
        return Messages.get(this, "name");
    }
    private int killpoint = 0;
    public void GetKillPoint() {
        killpoint++;

        if (killpoint == 125) {

            KindOfWeapon n = Dungeon.hero.belongings.weapon;
            if(Random.Int(2)==1){
                ((Weapon)n).chimera(Reflection.newInstance(Blade.class));
            }else{
                ((Weapon)n).chimera(Reflection.newInstance(Demon.class));
            }
            ((Weapon)n).removeChimera(Flame.class);

            Dungeon.hero.sprite.emitter().burst(Speck.factory(Speck.RED_LIGHT),12);
        }
    }
}
