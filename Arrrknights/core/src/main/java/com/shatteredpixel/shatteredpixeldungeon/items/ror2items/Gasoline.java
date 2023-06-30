package com.shatteredpixel.shatteredpixeldungeon.items.ror2items;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.FlameParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Bomb;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Gasoline extends ROR2item{
    {
        tier = 1;
        image = ItemSpriteSheet.GASOLINE;
    }

    @Override
    public void uponKill(Char attacker, Char defender, int damage ) {
        defender.sprite.emitter().burst( FlameParticle.FACTORY, 5 );
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
            if(!ch.isAlive() || ch == Dungeon.hero){
                continue;
            }
            if (ch.buff(Burning.class) != null){
                Buff.affect(ch, Burning.class).reignite(ch, 8f);
                int burnDamage = Random.NormalIntRange( 1, 3 + Dungeon.depth/4 );
                ch.damage( Math.round(burnDamage * 0.67f), this );
            } else {
                Buff.affect(ch, Burning.class).reignite(ch, 8f);
            }
        }
    }

    @Override
    protected ROR2item.ROR2itemBuff passiveBuff() {return new GasolineBuff();}
    public class GasolineBuff extends ROR2itemBuff{}
}
