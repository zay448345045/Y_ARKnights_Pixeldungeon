package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class FreshInspiration extends GunWeapon{
    {
        image = ItemSpriteSheet.R4C;
        hitSound = Assets.Sounds.HIT_AR;
        hitSoundPitch = 0.9f;

        FIREACC = 1f;
        FIRETICK = 1f;
        bulletCap = 4;

        usesTargeting = true;

        defaultAction = AC_ZAP;

        tier = 1;
    }
    @Override
    public int max(int lvl) {
        return  9*(tier) +    // 9+2
                lvl*(tier+1);
    }
    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (attacker instanceof Hero) {//TODO
            if(Random.Int(20)<3) bullet++;
        }
        return super.proc(attacker, defender, damage);
    }

    //TODO
    @Override
    protected void SPShot(Char ch) {

    }
    @Override
    public String status() { return bullet+"/"+bulletCap; }
}
