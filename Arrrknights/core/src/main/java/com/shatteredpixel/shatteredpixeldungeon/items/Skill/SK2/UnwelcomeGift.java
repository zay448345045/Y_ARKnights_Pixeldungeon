package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.ArcaneBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Bomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Firebomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Flashbang;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.FrostBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.HolyBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.LensBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Noisemaker;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.RegrowthBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.ShockBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.ShrapnelBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.WoollyBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.watabou.utils.Random;

public class UnwelcomeGift extends Skill {
    public void doSkill() {
        Bomb bomb;
        switch (Random.Int(12)){
            case 0: default:
                bomb = new Bomb();
                break;
            case 1:
                bomb = new ArcaneBomb();
                break;
            case 2:
                bomb = new Firebomb();
                break;
            case 3:
                bomb = new Flashbang();
                break;
            case 4:
                bomb = new FrostBomb();
                break;
            case 5:
                bomb = new HolyBomb();
                break;
            case 6:
                bomb = new LensBomb();
                break;
            case 7:
                bomb = new Noisemaker();
                break;
            case 8:
                bomb = new RegrowthBomb();
                break;
            case 9:
                bomb = new ShockBomb();
                break;
            case 10:
                bomb = new ShrapnelBomb();
                break;
            case 11:
                bomb = new WoollyBomb();
                break;
        }
        Actor.addDelayed(bomb.fuse = new Bomb.Fuse().ignite(bomb), 2);
        Dungeon.level.drop(bomb, curUser.pos);
        ScrollOfTeleportation.teleportChar(curUser);
        Buff.affect(curUser, Invisibility.class, Invisibility.DURATION);
        curUser.spendAndNext(1f);
    }
}
