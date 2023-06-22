package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NPC_TextSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.skins.TomimiSprite;

public class RlyehText extends NPC {
    {
        spriteClass = NPC_TextSprite.class;
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
        RlyehText text = new RlyehText();
        do {
            text.pos = ppos;
        } while (text.pos == -1);
        level.mobs.add(text);
    }
}
