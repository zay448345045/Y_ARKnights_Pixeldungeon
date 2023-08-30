package com.shatteredpixel.shatteredpixeldungeon.items;

import static com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent.PROFICIENCY;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Enfild;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Enfild2;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FreshInspiration;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gloves;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GunWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

import java.util.ArrayList;

import javax.xml.stream.events.EndDocument;

public class MidoriAccessories extends Item {
    private static final String AC_USE = "USE";
    {
        image = ItemSpriteSheet.NULL_DEF;

        stackable = true;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_USE);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if (action.equals(AC_USE)) {
            if (Dungeon.hero.belongings.weapon instanceof GunWeapon) {
                this.detach(Dungeon.hero.belongings.backpack);
                GunWeapon fi;
                fi = (GunWeapon) Dungeon.hero.belongings.weapon;
                fi.addAccessories();
            }else if((Dungeon.hero.belongings.weapon instanceof Enfild || Dungeon.hero.belongings.weapon instanceof Enfild2)&&Dungeon.hero.hasTalent(PROFICIENCY)){
                this.detach(Dungeon.hero.belongings.backpack);
                if(Dungeon.hero.belongings.weapon instanceof Enfild){
                    Enfild fi;
                    fi = (Enfild) Dungeon.hero.belongings.weapon;
                    fi.addAccessories();
                }else{
                    Enfild2 fi;
                    fi = (Enfild2) Dungeon.hero.belongings.weapon;
                    fi.addAccessories();
                }
            }else{
                GLog.i(Messages.get(this, "does_nothing"));
            }
        }
    }
    @Override
    public boolean isUpgradable() {
        return false;
    }
    @Override
    public boolean isIdentified() {
        return true;
    }
}
