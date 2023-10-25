package com.shatteredpixel.shatteredpixeldungeon.items;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.SandalsOfNature;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Blandfruit;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfMight;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirsOfIronSkin;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfAdrenalineSurge;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Violin;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndUseItem;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.Collections;

import jdk.dynalink.beans.StaticClass;

public class MagicPaper extends Item{

    public static final String AC_DRAW = "DRAW";
    {
        image = ItemSpriteSheet.GUIDE_PAGE;

        cursedKnown = levelKnown = true;

        defaultAction = AC_DRAW;

        stackable = true;

        bones = false;
    }
    private int needAmount = 10;
    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions =  super.actions(hero);
        actions.add(AC_DRAW);
        return actions;
    }
    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);
        if(Dungeon.hero.pointsInTalent(Talent.SUPERB_ARTS)==3) needAmount = 8;
        if (action.equals(AC_DRAW)){
            if(this.quantity>=needAmount){
                curItem = this;
                GameScene.selectItem(drawSelector, WndBag.Mode.DRAWABLE, Messages.get(this, "prompt"));
            }else{
                GLog.i(Messages.get(this, "not_enough"));
            }
        }
    }
    public static boolean canDrawItem(Item item){
        return !Statistics.drawn.contains(item.getClass());
    }
    public static Class getRandomDrawn(){
        if(Statistics.drawn.size()>0)return Statistics.drawn.get(Random.Int(Statistics.drawn.size()));
        else return null;
    }
    public static boolean isLegal(){
        boolean goodToGo = false;
        for(Class cls: Statistics.drawn){
            if(!(cls == ScrollOfUpgrade.class ||
                    cls == ScrollOfEnchantment.class ||
                    cls == PotionOfStrength.class ||
                    cls == PotionOfAdrenalineSurge.class ||
                    cls == ElixirsOfIronSkin.class ||
                    cls == ElixirOfMight.class
            ))
            {
                goodToGo = true;
            }
        }
        return goodToGo;
    }
    public static Class getLegalRandomDrawn(){
        Class c;
        if(Statistics.drawn.size()>0 && isLegal()){
            do {c = Statistics.drawn.get(Random.Int(Statistics.drawn.size()));}
            while(c == ScrollOfUpgrade.class ||
                    c == ScrollOfEnchantment.class ||
                    c == PotionOfStrength.class ||
                    c == PotionOfAdrenalineSurge.class ||
                    c == ElixirsOfIronSkin.class ||
                    c == ElixirOfMight.class);
            return c;
        }
        else return null;
    }

    protected WndBag.Listener drawSelector = new WndBag.Listener() {
        @Override
        public void onSelect( Item item ) {
            MagicPaperItem ampi;
            ampi = Dungeon.hero.belongings.getItem(MagicPaperItem.class);
            if(item != null){
                if(ampi == null) {
                    MagicPaperItem mpi = new MagicPaperItem();
                    mpi.setItemType(item);
                    if(item instanceof Blandfruit && ((Blandfruit)item).potionAttrib != null){
                        mpi.setPotionType(((Blandfruit)item).potionAttrib);
                    }
                    if (!mpi.collect()) {
                        Dungeon.level.drop(mpi, Dungeon.hero.pos).sprite.drop();
                    }
                    Statistics.drawn.add(item.getClass());
                    if(Dungeon.hero.pointsInTalent(Talent.PIE_IN_THE_PAPER)==3){
                        Statistics.drawn.remove(Food.class);
                    }
                    if(Dungeon.hero.pointsInTalent(Talent.SUPERB_ARTS)==3) needAmount = 8;
                    curItem.detachAmount(Dungeon.hero.belongings.backpack, needAmount);
                    Dungeon.hero.spendAndNext(1f);
                    Dungeon.hero.busy();
                    Dungeon.hero.sprite.operate( Dungeon.hero.pos );
                }else{
                    GLog.i(Messages.get(MagicPaper.class,"already_have"));
                }
            }
        }
    };
    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
    }
}
