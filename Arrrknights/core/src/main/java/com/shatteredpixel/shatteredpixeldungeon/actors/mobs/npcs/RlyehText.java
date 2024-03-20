package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
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
        if (!(Dungeon.DLC == Dungeon.ROR)) {
            Dungeon.DLC = Dungeon.ROR;
            sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "go_ror"));
        } else {
            Dungeon.DLC = Dungeon.SIESTA;
            sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "cancel_ror"));
        }
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
