package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KazemaruWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Blossoming extends Weapon.Chimera {
    @Override
    public String afterName() {
        return Messages.get(this, "name");
    }
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        if (Random.Int(10) == 0) {
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

                KazemaruWeapon.KazemaruSummon summon = new KazemaruWeapon.KazemaruSummon();
                summon.GetWeaponLvl(weapon.buffedLvl());
                summon.GetTarget(defender);
                summon.alignment=attacker.alignment;
                GameScene.add(summon);
                ScrollOfTeleportation.appear(summon, respawnPoints.get(index));

                respawnPoints.remove(index);
                spawnd++;
            }
        }

        return damage;
    }
}
