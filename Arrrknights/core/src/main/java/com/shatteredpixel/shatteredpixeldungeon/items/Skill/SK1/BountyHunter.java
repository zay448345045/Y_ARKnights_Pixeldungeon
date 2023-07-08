package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TalismanOfForesight;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfWealth;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.noosa.Visual;
import com.watabou.noosa.audio.Sample;

public class BountyHunter extends Skill {
    public void doSkill() {
        for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
            if (mob.alignment == Char.Alignment.ENEMY && !mob.properties().contains(Char.Property.BOSS) && !mob.properties().contains(Char.Property.MINIBOSS)) {
                Buff.affect(mob, PriceOnHead.class);
                Buff.append(Dungeon.hero, TalismanOfForesight.CharAwareness.class, Float.MAX_VALUE).charID = mob.id();
                break;}
        }
        GameScene.flash( 0x80FFFFFF );
        Sample.INSTANCE.play( Assets.Sounds.GOLD, 1f, 1.32f );
        curUser.spendAndNext(1f);
    }

    public static class PriceOnHead extends Buff {}
    public static Item genLoot(){
        //60% common, 30% uncommon, 10% rare
        return RingOfWealth.genConsumableDrop(0);
    }
    public static void showFlare( Visual vis ){
        RingOfWealth.showFlareForBonusDrop(vis);
    }
}
