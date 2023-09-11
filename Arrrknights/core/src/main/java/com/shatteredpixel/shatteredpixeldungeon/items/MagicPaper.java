package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndUseItem;

import java.util.ArrayList;

public class MagicPaper extends Item{
    public static final String AC_DRAW = "DRAW";
    {
        image = ItemSpriteSheet.GUIDE_PAGE;

        cursedKnown = levelKnown = true;

        defaultAction = AC_DRAW;
    }
    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions =  super.actions(hero);
        actions.add(AC_DRAW);
        return actions;
    }
    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_DRAW)){
            curItem = this;
            GameScene.selectItem(drawSelector, WndBag.Mode.DRAWABLE, Messages.get(this, "prompt"));
        }
    }
    protected static WndBag.Listener drawSelector = new WndBag.Listener() {
        @Override
        public void onSelect( Item item ) {
            MagicPaperItem mpi = new MagicPaperItem();
            mpi.setItemType(item);
            mpi.collect();
        }
    };
}
