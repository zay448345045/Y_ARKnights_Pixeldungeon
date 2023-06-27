package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NPC_KotobaNakuSprite;

public class NPC_KotobaNaku extends NPC {
    {
        spriteClass = NPC_KotobaNakuSprite.class;
        properties.add(Char.Property.IMMOVABLE);
        properties.add(Property.NPC);
    }

    @Override
    public int defenseSkill(Char enemy) {
        return INFINITE_EVASION;
    }

    @Override
    public void damage(int dmg, Object src) {
    }

    @Override
    public boolean interact(Char c) {
        sprite.turnTo(pos, c.pos);
        return true;
    }

    public static void spawn(Level level, int ppos) {
        NPC_KotobaNaku wordless = new NPC_KotobaNaku();
        do {
            wordless.pos = ppos;
        } while (wordless.pos == -1);
        level.mobs.add(wordless);
    }
}
