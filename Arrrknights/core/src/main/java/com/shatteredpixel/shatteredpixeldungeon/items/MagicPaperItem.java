package com.shatteredpixel.shatteredpixeldungeon.items;

import static com.shatteredpixel.shatteredpixeldungeon.items.food.Food.AC_EAT;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.InventoryStone;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.Runestone;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.DamageWand;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;

import java.util.ArrayList;

public class MagicPaperItem extends Item{
    public static final String AC_USE = "USE";
    {
        image = ItemSpriteSheet.ALCH_PAGE;

        cursedKnown = levelKnown = true;

        defaultAction = AC_USE;
    }
    private Item itemType;

    public void setItemType(Item i){
        itemType = i;
    }
    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions =  super.actions(hero);
        if(itemType instanceof Scroll ||
                itemType instanceof Potion||
                itemType instanceof InventoryStone ||
                itemType instanceof Food)
        {
            actions.add(AC_USE);
        }else{
            defaultAction = AC_THROW;
        }
        return actions;
    }
    @Override
    public String desc() {
            return Messages.get(this, "desc",itemType.name()) + "\n\n"
                    + itemType.desc();
    }
    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_USE)){
            curItem = this;
            if(itemType instanceof Scroll){
                curItem = itemType;
                ((Scroll)itemType).doRead();
            }
            if(itemType instanceof Potion){
                curItem = itemType;
                ((Potion)itemType).apply(hero);
            }
            if(itemType instanceof InventoryStone){
                curItem = itemType;
                ((InventoryStone)itemType).activate(hero.pos);
            }
            if(itemType instanceof Food){
                curItem = itemType;
                (itemType).execute(hero, AC_EAT);
                curItem.collect( curUser.belongings.backpack );
            }
        }
    }
}
