package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Freezing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Frost;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.coversprite.FrostBlastCover;
import com.shatteredpixel.shatteredpixeldungeon.effects.coversprite.GawainCover;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.CustomeSet;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.WoundsofWar;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAssassin;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfWealth;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.AttackIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class WintersScar extends MeleeWeapon {
    {
        image = ItemSpriteSheet.WINTER;
        hitSound = Assets.Sounds.FROST;
        hitSoundPitch = 1.12f;

        tier = 5;
    }

    @Override
    public int max(int lvl) {
        return  4*(tier+1) +    //20 base
                lvl*(tier+1);
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (defender.buff(Chill.class) != null) damage = Math.round(damage * 1.25f);

        if (defender instanceof Mob && ((Mob) defender).surprisedBy(attacker)) {
            Buff.affect(defender, Chill.class, 4f);
        }
        return super.proc(attacker, defender, damage);
    }
    @Override
    public float wepCorrect(){
        if (setbouns()) return 0.45f;
        return 0.25f;
    }

    private boolean setbouns() {
        if (Dungeon.hero.belongings.getItem(RingOfAssassin.class) != null && Dungeon.hero.belongings.getItem(WoundsofWar.class) != null) {
            if (Dungeon.hero.belongings.getItem(RingOfAssassin.class).isEquipped(Dungeon.hero) && Dungeon.hero.belongings.getItem(WoundsofWar.class).isEquipped(Dungeon.hero))
                return true;
        }
        return false;
    }

    @Override
    public String desc() {
        String info = Messages.get(this, "desc");
        if (setbouns()) info += "\n\n" + Messages.get( this, "setbouns");

        return info;
    }


    @Override
    protected void duelistAbility(Hero hero, Integer target) {
        int dmgBoost = augment.damageFactor(10 + 3*buffedLvl());
        hero.belongings.abilityWeapon = this;
        MeleeWeapon wep = this;

        FrostBlastCover frostCover = new FrostBlastCover();
        frostCover.centerAndPlay(hero.pos);
        hero.sprite.attack(hero.pos, new Callback() {
            @Override
            public void call() {
                wep.beforeAbilityUsed(hero, null);
                Invisibility.dispel();

                PathFinder.buildDistanceMap( hero.pos, BArray.not( Dungeon.level.solid, null ), 2 );
                for (int i = 0; i < PathFinder.distance.length; i++) {
                    if (PathFinder.distance[i] < Integer.MAX_VALUE) {
                        Char ch = Actor.findChar(i);
                        if (ch != null && ch.alignment == Char.Alignment.ENEMY){
                            ch.damage(wep.damageRoll(hero)+dmgBoost, Dungeon.hero);
                            if(ch.buff(Chill.class)!=null) Buff.affect(ch, Frost.class, 5+buffedLvl());
                            Buff.affect(ch, Chill.class, 5+buffedLvl());
                        }
                    }
                }

                wep.afterAbilityUsed(hero);
            }
        });
        hero.spendAndNext(Actor.TICK);
    }
}
