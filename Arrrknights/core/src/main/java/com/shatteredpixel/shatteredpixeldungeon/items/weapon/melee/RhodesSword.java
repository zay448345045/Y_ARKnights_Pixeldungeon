package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class RhodesSword extends MeleeWeapon {
    {
        image = ItemSpriteSheet.YATO;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 1.06f;
        tier = 1;

        bones = false;
    }
    @Override
    public float wepCorrect(){
        return 0.55f;
    }
}
