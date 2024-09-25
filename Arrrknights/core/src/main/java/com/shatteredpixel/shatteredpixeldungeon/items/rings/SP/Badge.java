package com.shatteredpixel.shatteredpixeldungeon.items.rings.SP;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.Recipe;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfHaste;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Firmament;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KollamSword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShadowFirmament;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SwordofArtorius;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;

import java.util.ArrayList;

public class Badge extends Ring {
    @Override
    public void activate( Char ch ) {
    }
    @Override
    public boolean isIdentified() {
        return true;
    }
    @Override
    public boolean isKnown() {
        return true;
    }
    @Override
    public String info(){
        String desc = super.info();
        desc = Messages.get(this, "self_desc") + "\n\n" + desc + "\n\n" + Messages.get(this, "special_ability");
        return desc;
    }

    protected float HasteMultiper(int lvl){return 1f;}
    protected float SunlightMultiper(int lvl){return 1f;}
    protected float MistressMultiper(int lvl){return 1f;}

    public static float HasteMultiplier(){
        float multiper = 1;
        for(Item i : Dungeon.hero.belongings){
            if(i instanceof Badge){
                multiper += ((Badge) i).HasteMultiper(((Badge) i).soloBuffedBonus())-1;
            }
        }
        return multiper;
    }
    public static float SunlightMultiper(){
        float multiper = 1;
        for(Item i : Dungeon.hero.belongings){
            if(i instanceof Badge){
                multiper += ((Badge) i).SunlightMultiper(((Badge) i).soloBuffedBonus())-1;
            }
        }
        return multiper;
    }
    public static float MistressMultiper(){
        float multiper = 1;
        for(Item i : Dungeon.hero.belongings){
            if(i instanceof Badge){
                multiper += ((Badge) i).MistressMultiper(((Badge) i).soloBuffedBonus())-1;
            }
        }
        return multiper;
    }
}
