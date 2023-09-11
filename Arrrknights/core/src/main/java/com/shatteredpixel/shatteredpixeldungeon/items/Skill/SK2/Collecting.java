package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.MidoriAccessories;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class Collecting extends Skill {
    @Override
    public void doSkill() {
        MidoriAccessories ma = new MidoriAccessories();
        if (! ma.doPickUp(Dungeon.hero)){
            Dungeon.level.drop( ma, Dungeon.hero.pos);
        }else{
            Dungeon.hero.spend(-TIME_TO_PICK_UP);
            GLog.i( Messages.get(Dungeon.hero, "you_now_have", ma.name()) );
        }
        Item n;
        n = Generator.random( Generator.Category.SEED );
        if (! n.doPickUp(Dungeon.hero)){
            Dungeon.level.drop(n, Dungeon.hero.pos);
        }else{
            Dungeon.hero.spend(-TIME_TO_PICK_UP);
            GLog.i( Messages.get(Dungeon.hero, "you_now_have", n.name()) );
        }
        Item m;
        m = Generator.random( Generator.Category.STONE );
        if (! m.doPickUp(Dungeon.hero)){
            Dungeon.level.drop(m, Dungeon.hero.pos);
        }else{
            Dungeon.hero.spend(-TIME_TO_PICK_UP);
            GLog.i( Messages.get(Dungeon.hero, "you_now_have", m.name()) );
        }
    }
}
