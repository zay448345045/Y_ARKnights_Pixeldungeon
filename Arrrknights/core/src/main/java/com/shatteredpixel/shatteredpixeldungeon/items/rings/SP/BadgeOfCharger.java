package com.shatteredpixel.shatteredpixeldungeon.items.rings.SP;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfHaste;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfMistress;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSunLight;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ArcaneCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ForceCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Firmament;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gloves;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KollamSword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShadowFirmament;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SwordofArtorius;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BadgeOfCharger extends Badge {
    {
        image = ItemSpriteSheet.BADGE_CHARGER;
    }
    public String statsInfo() {
        if (isIdentified()){
            return Messages.get(this, "stats",
                    new DecimalFormat("#.##").format(100f * (Math.pow(1.2f, soloBuffedBonus()/3f) - 1f)),
                    new DecimalFormat("#.##").format(100f * (Math.pow(1.14f, soloBuffedBonus()/3f) - 1f)),
            new DecimalFormat("#.##").format(100f * (Math.pow(1.15f, soloBuffedBonus()/3f) - 1f))
            );
        } else {
            return Messages.get(this, "typical_stats",
                    new DecimalFormat("#.##").format(7f),
                    new DecimalFormat("#.##").format(5f),
                    new DecimalFormat("#.##").format(5f)
            );
        }
    }
    @Override
    protected float HasteMultiper(int lvl){
        return (float)Math.pow(1.2, lvl/3f);
    }
    @Override
    protected float SunlightMultiper(int lvl){
        return (float)Math.pow(1.14, lvl/3f);
    }
    @Override
    protected float MistressMultiper(int lvl){
        return (float)Math.pow(1.15, lvl/3f);
    }
    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe {
        int levelSum = 0;
        @Override
        public boolean testIngredients(ArrayList<Item> ingredients) {
            levelSum = 0;
            boolean haste = false;
            boolean sunlight = false;
            boolean mistress = false;
            for (Item i : ingredients){
                if (i instanceof RingOfHaste){
                    haste = true;
                    levelSum += i.level();
                } else if (i instanceof RingOfSunLight) {
                    sunlight = true;
                    levelSum += i.level();
                }else if (i instanceof RingOfMistress){
                    mistress = true;
                    levelSum += i.level();
                }
            }

            return haste && sunlight && mistress;
        }

        @Override
        public int cost(ArrayList<Item> ingredients) {
            return 0;
        }

        @Override
        public Item brew(ArrayList<Item> ingredients) {
            if (!testIngredients(ingredients)) return null;
            for (Item ingredient : ingredients){
                ingredient.quantity(ingredient.quantity() - 1);
            }
            return sampleOutput(null);
        }

        @Override
        public Item sampleOutput(ArrayList<Item> ingredients) {
            BadgeOfCharger boc = new BadgeOfCharger();
            boc.level(Math.round(levelSum/3f));
            boc.levelKnown = true;
            boc.identify();
            return boc;
        }
    }
}
