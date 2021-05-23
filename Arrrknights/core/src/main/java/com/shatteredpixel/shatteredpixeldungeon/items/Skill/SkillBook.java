package com.shatteredpixel.shatteredpixeldungeon.items.Skill;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ActiveOriginium;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class SkillBook extends Item {
    {
        image = ItemSpriteSheet.ARTIFACT_GREAVES;
        stackable = false;
        bones = true;
        unique = true;
    }
    private static final String AC_SKL1 = "SKL1";
    private static final String AC_SKL2 = "SKL2";
    private static final String AC_SKL3 = "SKL3";
    public int charge = 0;
    public int chargeCap = 100;

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_SKL1);
        actions.add(AC_SKL2);
        actions.add(AC_SKL3);
        return actions;}

    @Override
    public void execute (Hero hero, String action ) {
        super.execute(hero, action);
        if (action.equals(AC_SKL1)) {
            if (hero.SK1 != null) {
                if (charge < 30) {
                    GLog.w(Messages.get(SkillBook.class, "low_charge"));
                } else {
                    charge-=30;
                    hero.SK1.doSkill();
                }
            }
            else GLog.w(Messages.get(SkillBook.class, "no_skill"));
        }

        if (action.equals(AC_SKL2)) {
            if (hero.SK2 != null) {
                if (charge < 60) {
                    GLog.w(Messages.get(SkillBook.class, "low_charge"));
                } else {
                    charge -=60;
                    hero.SK2.doSkill();}
            }
            else GLog.w(Messages.get(SkillBook.class, "no_skill"));
        }

        if (action.equals(AC_SKL3)) {
            if (hero.SK3 != null) {
                if (charge < 100) {
                    GLog.w(Messages.get(SkillBook.class, "low_charge"));
                } else {
                    charge -=100;
                    hero.SK3.doSkill();}
            }
            else GLog.w(Messages.get(SkillBook.class, "no_skill"));
        }
    }
    //

            @Override
            public String info() {
                String info = desc();

                curUser = Dungeon.hero;

                info += "\n\n" + Messages.get(this, "spcharge", Math.round(charge));

                if (curUser.SK1 != null) {
                    info += "\n\n" + curUser.SK1.name();
                    info += " " + curUser.SK1.desc();
                }

                if (curUser.SK2 != null) {
                    info += "\n\n" + curUser.SK2.name();
                    info += " " + curUser.SK2.desc();
                }

                if (curUser.SK3 != null) {
                    info += "\n\n" + curUser.SK3.name();
                    info += " " + curUser.SK3.desc();
                }

                return info;
            }

    public void onHeroGainExp(float levelPercent, Hero hero) {
        super.onHeroGainExp(levelPercent, hero);
        charge += 80 * levelPercent;
        if (charge > 100) charge = 100;
        updateQuickslot();
    }

    private static final String CHARGE = "charge";
    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);
        bundle.put( CHARGE , charge );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        if (chargeCap > 0)  charge = Math.min( chargeCap, bundle.getInt( CHARGE ));
        else                charge = bundle.getInt( CHARGE );
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