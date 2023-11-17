package com.shatteredpixel.shatteredpixeldungeon.items.bags;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Swiftthistle;
import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Game;

import java.util.ArrayList;

public class EquipmentsBag extends Bag{
    public static final String AC_DOWN = "DOWN";
    public static final String AC_GENERATE = "GENERATE";
    {
        image = ItemSpriteSheet.UNDONE_MARK;
        defaultAction = AC_DOWN;
    }
    @Override
    public boolean canHold( Item item ) {
        if (item instanceof EquipableItem){
            return super.canHold(item);
        } else {
            return false;
        }
    }

    public int capacity(){
        return 19;
    }

    @Override
    public int value() {
        return 40;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions =  super.actions(hero);
        actions.add(AC_DOWN);
        actions.add(AC_GENERATE);
        return actions;
    }
    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_DOWN)){
            defaultAction = AC_DOWN;
            if(Dungeon.level.locked || Dungeon.depth==26 || Dungeon.depth==40) {
                GLog.w(Messages.get(this,"cannot_send"));
                return;
            }
            Buff buff = Dungeon.hero.buff(TimekeepersHourglass.timeFreeze.class);
            if (buff != null) buff.detach();
            buff = Dungeon.hero.buff(Swiftthistle.TimeBubble.class);
            if (buff != null) buff.detach();

            InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
            Game.switchScene( InterlevelScene.class );
        }
        if (action.equals(AC_GENERATE)){
            defaultAction = AC_GENERATE;
            Dungeon.level.drop(Generator.randomWeapon(2), Dungeon.hero.pos).sprite.drop();
        }
    }
}
