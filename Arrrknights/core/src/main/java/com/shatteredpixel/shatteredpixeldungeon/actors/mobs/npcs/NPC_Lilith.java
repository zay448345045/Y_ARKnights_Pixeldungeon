package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NPC_LilithSprite;

public class NPC_Lilith extends NPC {
    {
        spriteClass = NPC_LilithSprite.class;
        //properties.add(Char.Property.IMMOVABLE);
        properties.add(Property.NPC);
        state = WANDERING;
    }

    @Override
    public int defenseSkill(Char enemy) {
        return INFINITE_EVASION;
    }

    @Override
    public void damage(int dmg, Object src) {
    }

    @Override
    public float speed() {
        return 0.5f;
    }

    @Override
    public boolean interact(Char c) {
        return super.interact(c);
    }

    @Override
    protected boolean act() {
        return super.act();
    }

    public static void spawn(Level level, int ppos) {
        NPC_Lilith lilith = new NPC_Lilith();
        do {
            lilith.pos = ppos;
        } while (lilith.pos == -1);
        level.mobs.add(lilith);
    }
}
