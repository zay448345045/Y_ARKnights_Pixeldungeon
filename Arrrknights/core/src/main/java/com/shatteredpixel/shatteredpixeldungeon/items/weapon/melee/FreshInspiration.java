package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LockedFloor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class FreshInspiration extends GunWeapon{
    {
        image = ItemSpriteSheet.FINSPIRATION;
        hitSound = Assets.Sounds.HIT_AR;
        hitSoundPitch = 0.9f;

        FIREACC = 1f;
        FIRETICK = 1f;
        bullet = 4;
        bulletCap = 4;

        usesTargeting = true;

        defaultAction = AC_ZAP;

        tier = 1;
    }
    protected Buff passiveBuff;
    private float particalBullets = 0;
    @Override
    public int max(int lvl) {
        return  9*(tier) +    // 9+2
                lvl*(tier+1) +
                ((Dungeon.hero.hasTalent(Talent.BAYONET)&& Maccessories>0)? Dungeon.hero.pointsInTalent(Talent.BAYONET)+1:0);
    }
    public int shotmin() {
        return 1 + level() + Maccessories;
    }
    public int shotmax() {
        return 9 + 2*level() + 2*Maccessories;
    }
    @Override
    public int proc(Char attacker, Char defender, int damage) {
        return super.proc(attacker, defender, damage);
    }
    @Override
    protected void SPShot(Char ch) {
        //TODO
    }
    @Override
    public String status() { return bullet+"/"+bulletCap; }
    @Override
    public String desc() {
        return Messages.get(this, "desc", shotmin(),shotmax());
    }
    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);

    }
    public class FIBuff extends Buff {}

    protected FIBuff passiveBuff(){return new FIRecharge();}
    @Override
    public void activate(Char ch){
        passiveBuff = passiveBuff();
        passiveBuff.attachTo(ch);
    }
    @Override
    public boolean collect( Bag container ) {
        if (super.collect(container)){
            if (container.owner instanceof Hero
                    && passiveBuff == null)
            {
                activate((Hero) container.owner);
            }
            return true;
        } else{
            return false;
        }
    }
    @Override
    protected void onDetach() {
        if (passiveBuff != null){
            passiveBuff.detach();
            passiveBuff = null;
        }
    }
    public class FIRecharge extends FIBuff {
        @Override
        public boolean act() {
            if (bullet < bulletCap) {
                LockedFloor lock = target.buff(LockedFloor.class);
                if ((lock == null || lock.regenOn())  && !(Dungeon.depth >= 26 && Dungeon.depth <= 30)) {
                    float turnsToCharge = (40f - level()*1.5f - (float) Maccessories);
                    if(turnsToCharge<5) turnsToCharge=5;
                    float chargeToGain = (1f / turnsToCharge);
                    particalBullets += chargeToGain;
                }

                if (particalBullets >= 1) {
                    bullet++;
                    particalBullets -= 1;
                    if (bulletCap == bulletCap){
                        particalBullets = 0;
                    }
                }
            } else
                particalBullets = 0;

            updateQuickslot();

            spend( TICK );

            return true;
        }
    }

    private static final String PARTICALBULLETS = "particalbullets";
    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(PARTICALBULLETS, particalBullets);
    }
    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        particalBullets = bundle.getFloat(PARTICALBULLETS);
    }

}
