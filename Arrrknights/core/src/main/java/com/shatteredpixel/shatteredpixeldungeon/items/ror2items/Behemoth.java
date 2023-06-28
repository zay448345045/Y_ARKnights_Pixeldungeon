package com.shatteredpixel.shatteredpixeldungeon.items.ror2items;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Bomb;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.PathFinder;

import java.util.ArrayList;

public class Behemoth extends ROR2item{
    {
        tier = 3;

        image = ItemSpriteSheet.BEHEMOTH;
    }

    @Override
    public int attackProc(Char attacker, Char defender, int damage ) {
            if (Dungeon.level.heroFOV[defender.pos]) {
                CellEmitter.center(defender.pos).burst(BlastParticle.FACTORY, 30);
            }
            ArrayList<Char> affected = new ArrayList<>();
            for (int n : PathFinder.NEIGHBOURS9) {
                int c = defender.pos + n;
                if (c >= 0 && c < Dungeon.level.length()) {
                    Char ch = Actor.findChar(c);
                    if (ch != null) {
                        affected.add(ch);
                    }
                }
            }
            for (Char ch : affected){
                //if they have already been killed by another bomb
                if(!ch.isAlive()){
                    continue;
                }

                int dmg = (int) Math.round(damage*0.6);

                dmg -= ch.drRoll();

                if (dmg > 0 && ch!=Dungeon.hero) {
                    ch.damage(dmg, Bomb.class);
                }

            }
        return damage;
    }
    @Override
    protected ROR2itemBuff passiveBuff() {
        return new Behemoth.BehemothBuff();
    }

    public class BehemothBuff extends ROR2itemBuff{}
}
