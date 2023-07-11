package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.Transmuting;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SakuraSword;
import com.shatteredpixel.shatteredpixeldungeon.journal.Catalog;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.watabou.utils.Reflection;

import java.util.ArrayList;

public class SakuraProof extends Item{
    private static final String AC_APPLY = "APPLY";
    {
        image = ItemSpriteSheet.SAKURAPROOF;
        bones = false;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_APPLY);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_APPLY)) {
            curUser = hero;
            detachaa();
            GameScene.selectItem(itemSelector, WndBag.Mode.WEAPON, Messages.get(this, "prompt"));

        }
    }
    private final WndBag.Listener itemSelector = new WndBag.Listener() {
        @Override
        public void onSelect(Item item) {
            Item result;
            if (item != null) {
                if (item instanceof MeleeWeapon) {
                    if(((MeleeWeapon) item).tier==5){
                        result = changeIntoSakura( (MeleeWeapon)item );

                        if (result == null){
                            //This shouldn't ever trigger
                            GLog.n( Messages.get(ScrollOfTransmutation.class, "nothing") );
                            curItem.collect( curUser.belongings.backpack );
                        } else {
                            if (item.isEquipped(Dungeon.hero)){
                                item.cursed = false; //to allow it to be unequipped
                                ((EquipableItem)item).doUnequip(Dungeon.hero, false);
                                ((EquipableItem)result).doEquip(Dungeon.hero);
                            } else {
                                item.detach(Dungeon.hero.belongings.backpack);
                                if (!result.collect()){
                                    Dungeon.level.drop(result, curUser.pos).sprite.drop();
                                }
                            }
                            if (result.isIdentified()){
                                Catalog.setSeen(result.getClass());
                            }
                            Transmuting.show(curUser, item, result);
                            curUser.sprite.emitter().start(Speck.factory(Speck.CHANGE), 0.2f, 10);
                            GLog.p( Messages.get(SakuraProof.class, "morph") );
                        }

                        updateQuickslot();

                    }else{
                        if (!new SakuraProof().collect()){
                            Dungeon.level.drop(new SakuraProof(), curUser.pos).sprite.drop();
                        }
                    }
                } else {
                    if (!new SakuraProof().collect()){
                        Dungeon.level.drop(new SakuraProof(), curUser.pos).sprite.drop();
                    }
                }
            } else {
                if (!new SakuraProof().collect()){
                    Dungeon.level.drop(new SakuraProof(), curUser.pos).sprite.drop();
                }
            }
        }
    };

    private Weapon changeIntoSakura( Weapon w ) {

        Weapon n;

        n = new SakuraSword();

        int level = w.level();
        if (w.curseInfusionBonus) level--;
        if (level > 0) {
            n.upgrade( level );
        } else if (level < 0) {
            n.degrade( -level );
        }

        n.enchantment = w.enchantment;
        n.curseInfusionBonus = w.curseInfusionBonus;
        n.levelKnown = w.levelKnown;
        n.cursedKnown = w.cursedKnown;
        n.cursed = w.cursed;
        n.augment = w.augment;

        return n;
    }

    private void detachaa() {
        this.detach(Dungeon.hero.belongings.backpack);
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
