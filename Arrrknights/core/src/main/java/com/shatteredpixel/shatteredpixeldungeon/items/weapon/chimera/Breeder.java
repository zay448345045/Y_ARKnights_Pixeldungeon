package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hallucination;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.CustomeSet;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfMistress;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfWealth;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.SP.Badge;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrabGun;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Breeder extends Weapon.Chimera {
    @Override
    public String afterName() {
        return Messages.get(this, "name");
    }

    public int charge = 100;
    public int chargeCap = 100;
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        if (attacker instanceof Hero || attacker instanceof DriedRose.GhostHero) {
            if (charge >= chargeCap) {
                ArrayList<Integer> respawnPoints = new ArrayList<>();

                for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
                    int p = defender.pos + PathFinder.NEIGHBOURS8[i];
                    if (Actor.findChar(p) == null && Dungeon.level.passable[p]) {
                        respawnPoints.add(p);
                    }
                }
                int spawnd = 0;
                while (respawnPoints.size() > 0 && spawnd == 0) {
                    int index = Random.index(respawnPoints);

                    CrabGun.MetalCrab crab = new CrabGun.MetalCrab();
                    crab.setting(weapon.buffedLvl());
                    GameScene.add(crab);
                    ScrollOfTeleportation.appear(crab, respawnPoints.get(index));

                    if (setbouns()) {
                        for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
                            if (mob.alignment != Char.Alignment.ALLY && Dungeon.level.heroFOV[mob.pos]) {
                                Buff.affect(mob, Hallucination.class).set(Hallucination.DURATION / 2);
                            }
                        }
                    }

                    respawnPoints.remove(index);
                    spawnd++;
                }
                charge = 0;
            } else SPCharge((Random.IntRange(7 + weapon.buffedLvl() / 4, 11 + weapon.buffedLvl() / 2)));
        }
        return damage;
    }
    private boolean setbouns() {
        if (Dungeon.hero.belongings.getItem(RingOfWealth.class) != null && Dungeon.hero.belongings.getItem(CustomeSet.class) != null) {
            if (Dungeon.hero.belongings.getItem(RingOfWealth.class).isEquipped(Dungeon.hero) && Dungeon.hero.belongings.getItem(CustomeSet.class).isEquipped(Dungeon.hero))
                return true;
        }
        return false;
    }
    public void SPCharge(int value) {
        int chargevalue = value;
        chargevalue *= Math.round(RingOfMistress.SPMultiplier(Dungeon.hero) + Badge.MistressMultiper() -1);
        charge = Math.min(charge+chargevalue, chargeCap);
    }
    private static final String CHARGE = "charge";
    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(CHARGE, charge);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        if (chargeCap > 0) charge = Math.min(chargeCap, bundle.getInt(CHARGE));
        else charge = bundle.getInt(CHARGE);
    }
}
