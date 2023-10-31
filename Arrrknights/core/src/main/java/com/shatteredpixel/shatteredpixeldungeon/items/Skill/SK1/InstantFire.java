package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1;

import static com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent.SKILL_ENHANCEMENT;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.DamageWand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DP27;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GunWeapon;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.QuickSlotButton;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Callback;

public class InstantFire extends Skill {
    @Override
    public void doSkill() {
        if(!(Dungeon.hero.belongings.weapon instanceof GunWeapon)) {
            GLog.i( Messages.get(this, "need_to_equip") );
            Buff.affect(Dungeon.hero, Haste.class, 10f);
            return;
        }else{
            GunWeapon gun = (GunWeapon) Dungeon.hero.belongings.weapon;
            if(gun.getBulletNum()>0){
                if (gun.cursed != true) {
                    cursedKnown = true;
                    GameScene.selectCell(zapper);
                }
                else {
                    Buff.affect(Dungeon.hero, Burning.class).reignite(Dungeon.hero,4f);
                    cursedKnown = true;
                    gun.decBullet(1);
                }
            }
        }
    }
    protected static CellSelector.Listener zapper = new CellSelector.Listener() {

        @Override
        public void onSelect(Integer target) {

            if (target != null) {

                final GunWeapon gun;
                if (Dungeon.hero.belongings.weapon instanceof GunWeapon) {
                    gun = (GunWeapon) Dungeon.hero.belongings.weapon;

                    Ballistica shot = new Ballistica(curUser.pos, target, Ballistica.PROJECTILE);
                    int cell = shot.collisionPos;

                    if (target == curUser.pos || cell == curUser.pos) {
                        GLog.i(Messages.get(DP27.class, "self_target"));
                        return;
                    }
                    curUser.sprite.zap(cell);
                    //attempts to target the cell aimed at if something is there, otherwise targets the collision pos.
                    if (Actor.findChar(target) != null) {
                        QuickSlotButton.target(Actor.findChar(target));
                    } else {
                        QuickSlotButton.target(Actor.findChar(cell));
                    }
                    if (gun.tryToShoot(target, shot, true, 1,1)) {
                        Dungeon.hero.spend(-gun.getFireTick());
                    }
                    Buff.affect(Dungeon.hero, Haste.class, 2f);
                }
            }
        }
        @Override
        public String prompt() {
            return Messages.get(DP27.class, "prompt");
        }
    };
}