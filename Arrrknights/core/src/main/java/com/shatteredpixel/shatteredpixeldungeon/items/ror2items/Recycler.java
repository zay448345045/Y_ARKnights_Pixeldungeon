package com.shatteredpixel.shatteredpixeldungeon.items.ror2items;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.WaterOfTransmutation;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.WellWater;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LockedFloor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.Transmuting;
import com.shatteredpixel.shatteredpixeldungeon.items.ArmorKit;
import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.Stone;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.CustomeSet;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfEnergy;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.Runestone;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.journal.Catalog;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;

public class Recycler extends ROR2equipment{
    {
        tier = 5;
        image = ItemSpriteSheet.ARTIFACT_THORNS;
        charge = 100;
        partialCharge = 0;
        chargeCap = 100;

        defaultAction = AC_CHANGE;
    }

    public static final String AC_CHANGE = "CHANGE";

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        if (isEquipped(hero) && !cursed)
            actions.add(AC_CHANGE);
        return actions;
    }

    private MagesStaff changeStaff(MagesStaff staff ){
        Class<?extends Wand> wandClass = staff.wandClass();

        if (wandClass == null){
            return null;
        } else {
            Wand n;
            do {
                n = (Wand) Generator.random(Generator.Category.WAND);
            } while (Challenges.isItemBlocked(n) || n.getClass() == wandClass);
            n.level(0);
            n.identify();
            staff.imbueWand(n, null);
        }

        return staff;
    }
    private Weapon changeWeapon(MeleeWeapon w ) {

        Weapon n;
        Generator.Category c = Generator.wepTiers[w.tier-1];

        do {
            n = (MeleeWeapon) Reflection.newInstance(c.classes[Random.chances(c.probs)]);
        } while (Challenges.isItemBlocked(n) || n.getClass() == w.getClass());

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
    private Ring changeRing(Ring r ) {
        Ring n;
        do {
            n = (Ring)Generator.random( Generator.Category.RING );
        } while (Challenges.isItemBlocked(n) || n.getClass() == r.getClass());

        n.level(0);

        int level = r.level();
        if (level > 0) {
            n.upgrade( level );
        } else if (level < 0) {
            n.degrade( -level );
        }

        n.levelKnown = r.levelKnown;
        n.cursedKnown = r.cursedKnown;
        n.cursed = r.cursed;

        return n;
    }
    private Artifact changeArtifact( Artifact a ) {
        Artifact n = Generator.randomArtifact();

        if (n != null && !Challenges.isItemBlocked(n)){
            n.cursedKnown = a.cursedKnown;
            n.cursed = a.cursed;
            n.levelKnown = a.levelKnown;
            n.transferUpgrade(a.visiblyUpgraded());
            return n;
        }

        return null;
    }
    private Wand changeWand( Wand w ) {

        Wand n;
        do {
            n = (Wand)Generator.random( Generator.Category.WAND );
        } while ( Challenges.isItemBlocked(n) || n.getClass() == w.getClass());

        n.level( 0 );
        int level = w.level();
        if (w.curseInfusionBonus) level--;
        n.upgrade( level );

        n.levelKnown = w.levelKnown;
        n.cursedKnown = w.cursedKnown;
        n.cursed = w.cursed;
        n.curseInfusionBonus = w.curseInfusionBonus;

        return n;
    }
    private Plant.Seed changeSeed(Plant.Seed s ) {

        Plant.Seed n;

        do {
            n = (Plant.Seed)Generator.random( Generator.Category.SEED );
        } while (n.getClass() == s.getClass());

        return n;
    }
    private Runestone changeStone(Runestone r ) {

        Runestone n;

        do {
            n = (Runestone) Generator.random( Generator.Category.STONE );
        } while (n.getClass() == r.getClass());

        return n;
    }
    private Scroll changeScroll(Scroll s ) {
        if (s instanceof ScrollOfUpgrade) {

            return s;

        } else {

            Scroll n;
            do {
                n = (Scroll)Generator.random( Generator.Category.SCROLL );
            } while (n.getClass() == s.getClass());
            return n;
        }
    }
    private Potion changePotion(Potion p ) {
        if (p instanceof PotionOfStrength) {

            return p;

        } else {

            Potion n;
            do {
                n = (Potion)Generator.random( Generator.Category.POTION );
            } while (n.getClass() == p.getClass());
            return n;
        }
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_CHANGE)) {
            if (activeBuff == null) {
                if (!isEquipped(hero))
                    GLog.i(Messages.get(Artifact.class, "need_to_equip"));
                else if (charge < 100) GLog.i(Messages.get(this, "no_charge"));
                else {
                    GameScene.selectItem( itemSelector, WndBag.Mode.TRANMSUTABLE, Messages.get(this, "prompt") );
                    //charge = 0;
                    updateQuickslot();

                    curUser.spendAndNext(1f);
                }
            }
        }
    }

    private final WndBag.Listener itemSelector = new WndBag.Listener() {
        @Override
        public void onSelect( Item item ) {
            Item result;

            if (item != null) {
                if (item instanceof MagesStaff) {
                    result = Recycler.this.changeStaff( (MagesStaff)item );
                } else if (item instanceof MeleeWeapon) {
                    result = Recycler.this.changeWeapon( (MeleeWeapon)item );
                } else if (item instanceof Scroll) {
                    result = Recycler.this.changeScroll( (Scroll)item );
                } else if (item instanceof Potion) {
                    result = Recycler.this.changePotion((Potion) item);
                } else if (item instanceof Ring) {
                    result = Recycler.this.changeRing( (Ring)item );
                } else if (item instanceof Wand) {
                    result = Recycler.this.changeWand( (Wand)item );
                } else if (item instanceof Plant.Seed) {
                    result = Recycler.this.changeSeed( (Plant.Seed)item );
                } else if (item instanceof Runestone) {
                    result = Recycler.this.changeStone( (Runestone) item );
                }else if (item instanceof Artifact) {
                    result = Recycler.this.changeArtifact( (Artifact)item );
                } else {
                    result = null;
                }

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
                    curUser.sprite.emitter().start(Speck.factory(Speck.DISCOVER), 0.1f, 15);
                    GLog.p( Messages.get(ScrollOfTransmutation.class, "morph") );
                }
            }
        }
    };


    @Override
    protected ROR2itemBuff passiveBuff() {
        return new Recycler.RecyclerBuff();
    }

    public class RecyclerBuff extends ROR2itemBuff {
        @Override
        public boolean act() {
            LockedFloor lock = target.buff(LockedFloor.class);
            if (activeBuff == null && (lock == null || lock.regenOn()) && !(Dungeon.depth >= 26 && Dungeon.depth <= 30)) {
                if (charge < chargeCap && !cursed) {
                    float chargeGain = 0.1f;
                    partialCharge += chargeGain;

                    if (partialCharge > 1 && charge < chargeCap) {
                        partialCharge--;
                        charge++;
                        updateQuickslot();
                    }
                }
            }
            else partialCharge = 0;

            spend(TICK);
            return true;
        }
    }
}
