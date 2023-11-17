package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Web;
import com.shatteredpixel.shatteredpixeldungeon.effects.Beam;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.PurpleParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Artorius extends Weapon.Chimera {
    @Override
    public String beforeName() {
        return Messages.get(this, "name");
    }
    @Override
    public float dmgFactor(){
        return 0.5f;
    }
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        Ballistica beam = new Ballistica(attacker.pos, defender.pos, Ballistica.WONT_STOP);
        int maxDistance = Math.min(5, beam.dist);
        int cell = beam.path.get(Math.min(beam.dist, maxDistance));
        attacker.sprite.parent.add(new Beam.DeathRay(attacker.sprite.center(), DungeonTilemap.raisedTileCenterToWorld(cell)));
        boolean terrainAffected = false;

        ArrayList<Char> chars = new ArrayList<>();

        Blob web = Dungeon.level.blobs.get(Web.class);

        int terrainPassed = 2;
        for (int c : beam.subPath(1, maxDistance)) {

            Char ch;
            if ((ch = Actor.findChar( c )) != null) {

                //we don't want to count passed terrain after the last enemy hit. That would be a lot of bonus levels.
                //terrainPassed starts at 2, equivalent of rounding up when /3 for integer arithmetic.
                terrainPassed = terrainPassed%3;

                chars.add( ch );
            }

            if (Dungeon.level.solid[c]) {
                terrainPassed++;
            }

            if (Dungeon.level.flamable[c]) {

                Dungeon.level.destroy( c );
                GameScene.updateMap( c );
                terrainAffected = true;

            }

            CellEmitter.center( c ).burst( PurpleParticle.BURST, Random.IntRange( 1, 2 ) );
        }

        if (terrainAffected) {
            Dungeon.observe();
        }

        int dmg = Math.round(damage*1.2f);
        for (Char ch : chars) {
            ch.damage(dmg, this );
            ch.sprite.centerEmitter().burst( PurpleParticle.BURST, Random.IntRange( 1, 2 ) );
            ch.sprite.flash();
        }
        return damage;
    }
}
