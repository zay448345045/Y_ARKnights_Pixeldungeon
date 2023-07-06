package com.shatteredpixel.shatteredpixeldungeon.items.spells;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.MetalShard;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class CurseSolidifying extends InventorySpell{
    {
        image = ItemSpriteSheet.CURSE_INFUSE;
        mode = WndBag.Mode.WAND;
    }


    @Override
    protected void onItemSelected(Item item) {
        CellEmitter.get(curUser.pos).burst(ShadowParticle.UP, 5);
        Sample.INSTANCE.play(Assets.Sounds.CURSED);
        item.cursed = true;
        ((Wand) item).solidified = true;
        ((Wand) item).solidrarity = Random.Int(4);
        ((Wand) item).solidtype = Random.Int(4);
        updateQuickslot();
    }
    @Override
    public int value() {
        //prices of ingredients, divided by output quantity
        return Math.round(quantity * ((30 + 100) / 3f));
    }
    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

        {
            inputs =  new Class[]{ScrollOfRemoveCurse.class, ArcaneCatalyst.class};
            inQuantity = new int[]{1, 1};
            cost = 4;
            output = CurseSolidifying.class;
            outQuantity = 1;
        }

    }
}
