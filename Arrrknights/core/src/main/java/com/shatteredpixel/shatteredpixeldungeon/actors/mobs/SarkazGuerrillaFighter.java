package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.level;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfMagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SarkazCasterSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class SarkazGuerrillaFighter extends Mob{
    {
        spriteClass = SarkazCasterSprite.class;

        HP = HT = 40;
        damageMax = 1;
        damageMin = 1;
        drMax = 3;
        drMin = 0;
        attackSkill = 12;
        defenseSkill = 10;

        EXP = 7;
        maxLvl = 14;

        loot = Generator.Category.ARMOR;
        lootChance = 0.2f; //by default, see rollToDropLoot()

        properties.add(Property.SARKAZ);
    }

    @Override
    public boolean attack(Char enemy) {//change from budding
        if (enemy == null) return false;
        if(!level.heraldAlive) return super.attack(enemy);
        boolean visibleFight = level.heroFOV[pos] || level.heroFOV[enemy.pos];
        if (enemy.isInvulnerable(getClass())) {
            if (visibleFight) {
                enemy.sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "invulnerable"));
                Sample.INSTANCE.play(Assets.Sounds.HIT_PARRY, 1f, Random.Float(0.96f, 1.05f));
            }
            return false;
        }
        else if (hit( this, enemy, true )) {

            int effectiveDamage=damageRoll();//change from budding
            effectiveDamage = attackProc( enemy, effectiveDamage );
            if (visibleFight) {
                if (effectiveDamage > 0 || !enemy.blockSound(Random.Float(0.96f, 1.05f))) {
                    Sample.INSTANCE.play(Assets.Sounds.HIT_SLASH, 1f, Random.Float(0.96f, 1.05f));
                }
            }
            if (!enemy.isAlive()){
                return true;
            }
            WandOfMagicMissile womm = new WandOfMagicMissile();
            enemy.damage( effectiveDamage, womm );
            enemy.sprite.bloodBurstA( sprite.center(), effectiveDamage );
            enemy.sprite.flash();

            if (!enemy.isAlive() && visibleFight) {
                if (enemy == Dungeon.hero) {
                    Dungeon.fail( getClass() );
                    GLog.n( Messages.capitalize(Messages.get(Char.class, "kill", name())) );
                }
            }


            return true;
        } else {
            if (visibleFight) {
                String defense = enemy.defenseVerb();
                enemy.sprite.showStatus( CharSprite.NEUTRAL, defense );

                //TODO enemy.defenseSound? currently miss plays for monks/crab even when they parry
                Sample.INSTANCE.play(Assets.Sounds.MISS);
            }
            return false;
        }
    }
}
