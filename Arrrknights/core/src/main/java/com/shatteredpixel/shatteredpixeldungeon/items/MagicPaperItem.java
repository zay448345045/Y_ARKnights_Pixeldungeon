package com.shatteredpixel.shatteredpixeldungeon.items;

import static com.shatteredpixel.shatteredpixeldungeon.items.food.Food.AC_EAT;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.PotatoAimReady;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Blandfruit;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.InventoryStone;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.Runestone;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Reflection;

import java.util.ArrayList;

public class MagicPaperItem extends Item{
    public static final String AC_USE = "USE";
    {
        image = ItemSpriteSheet.ALCH_PAGE;

        cursedKnown = levelKnown = true;

        defaultAction = AC_USE;
    }
    private Item itemType;
    private Potion fruitPotion = null;

    public void setItemType(Item i){
        itemType = i;
    }
    public void setPotionType(Potion p){
        if(itemType instanceof Blandfruit){
            fruitPotion = p;
        }
    }
    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions =  super.actions(hero);
        if(itemType instanceof Scroll ||
                itemType instanceof Potion||
                itemType instanceof InventoryStone ||
                itemType instanceof Food ||
                itemType instanceof Plant.Seed)
        {
            actions.add(AC_USE);
        }else{
            defaultAction = AC_THROW;
        }
        return actions;
    }
    @Override
    public String desc() {
        if(itemType instanceof Potion){return Messages.get(this, "desc",
                (itemType.isIdentified())?itemType.name():Messages.get(itemType, ((Potion) itemType).getColor()))
                + "\n\n" +
                ((itemType.isIdentified())?itemType.desc():Messages.get(itemType, "unknown_desc"));
        }
        if(itemType instanceof Scroll){return Messages.get(this, "desc",
                (itemType.isIdentified())?itemType.name():Messages.get(itemType, ((Scroll) itemType).getRune()))
                + "\n\n" +
                ((itemType.isIdentified())?itemType.desc():Messages.get(itemType, "unknown_desc"));
        }
        return  Messages.get(this, "desc",itemType.name())+ "\n\n" + itemType.desc();
    }
    @Override
    public boolean doPickUp(Hero hero) {
        MagicPaperItem ampi;
        ampi = Dungeon.hero.belongings.getItem(MagicPaperItem.class);
        if(ampi!=null){
            GLog.w(Messages.get(this, "cant_pickup"));
            return false;
        }
        return super.doPickUp(hero);
    }
    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);
        curItem = Reflection.newInstance(itemType.getClass());
        if (action.equals(AC_USE)){
            if(itemType instanceof Scroll){
                ((Scroll)itemType).doRead();
            }
            if(itemType instanceof Potion){
                ((Potion)itemType).apply(hero);
                hero.spend( 1f );
                hero.busy();
                Dungeon.hero.sprite.operate( Dungeon.hero.pos );
            }
            if(itemType instanceof InventoryStone){
                ((InventoryStone)itemType).activate(hero.pos);
            }
            if(itemType instanceof Plant.Seed){
                hero.spend( 1f );
                hero.busy();
                curItem.detach( hero.belongings.backpack ).onThrow( hero.pos );
                hero.sprite.operate( hero.pos );
            }
            if(itemType instanceof Food){
                if(curItem instanceof Blandfruit){
                    if(fruitPotion!=null) ((Blandfruit)curItem).potionAttrib = fruitPotion;
                }
                (curItem).execute(hero, AC_EAT);
                fruitPotion = null;
                if(Dungeon.hero.hasTalent(Talent.PIE_IN_THE_PAPER)) Food.satisfy(hero, 100);
            }
            this.detach(curUser.belongings.backpack);
        }
        if(action.equals(AC_THROW)){
            if(itemType instanceof Plant.Seed ||
                    itemType instanceof Runestone ||
                    itemType instanceof Potion){
                curItem.doThrow(hero);
                this.detach(curUser.belongings.backpack);
            }else{
                curItem = this;
                this.doThrow(hero);
            }
        }
    }
    public static final String ITEMTYPE = "itemtype";
    @Override
    public void storeInBundle(Bundle bundle){
        super.storeInBundle(bundle);
        bundle.put( ITEMTYPE , itemType);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        if (bundle.contains(ITEMTYPE)) {
            setItemType((Item)bundle.get(ITEMTYPE));
        }
    }
}
