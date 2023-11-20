package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import static com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent.BULLET_SUPPLY;
import static com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent.FREE_FIRE;
import static com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent.FULL_FIREPOWER;
import static com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent.IMPACT_BULLET;
import static com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent.INSPIRATION;
import static com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent.MYSTERY_SHOT;
import static com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent.PAPER_BULLET;
import static com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent.PREWAR;
import static com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent.SMOKE_BOMB;
import static com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent.XTRM_MEASURES;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.SPChallenges;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.CorrosiveGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.SmokeScreen;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Camouflage;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ChenShooterBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.CloserangeShot;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Combo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.DrawingArt;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ItsHighNoon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ItsHighNoonMark;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Momentum;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.PotatoAimReady;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.PowerMeal;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SnipersMark;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SmokeParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Bonk;
import com.shatteredpixel.shatteredpixeldungeon.items.Dewdrop;
import com.shatteredpixel.shatteredpixeldungeon.items.Gunaccessories.Accessories;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.MagicPaper;
import com.shatteredpixel.shatteredpixeldungeon.items.MidoriAccessories;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.IsekaiItem;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ExoticScroll;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfMagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.Thunderbolt;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.UpMagazine;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Swiftthistle;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.QuickSlotButton;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;

import javax.print.DocFlavor;

public class GunWeapon extends MeleeWeapon {
    public static final String AC_ZAP = "ZAP";
    public static final String AC_RELOAD = "RELOAD";
    public static final String AC_REMOVE = "REMOVE";
    public static final String AC_SPAMMO = "SPAMMO";

    protected int bullettier = 3;
    protected int bullet = 5;
    protected int bulletCap = 25;
    protected boolean spshot = false; // 특수 사격 여부
    protected boolean gamza = false; // 썬더볼트 장착 여부
    protected float FIREACC = 1f;
    protected float FIRETICK = 1f;
    protected int Maccessories = 0;
    protected float tryShootDamageFactor = 1;
    protected float tryShootAccFactor = 1;

    protected boolean precisely = false;
    @Override
    public int min(int lvl) {
        return  tier +  //base
                lvl +  //level scaling
                ((Dungeon.hero.hasTalent(Talent.BAYONET)&& Maccessories>0)? 1:0);
    }
    @Override
    public int max(int lvl) {
        return  3*(tier) +    // 3티어 기준 9+1, 5티어는 15+3
                lvl*(tier-2) +
                ((Dungeon.hero.hasTalent(Talent.BAYONET)&& Maccessories>0)? Dungeon.hero.pointsInTalent(Talent.BAYONET):0);
    }

    public int shotmin() {
        return 3 + level() + Maccessories;
    }
    public int shotmax() {
        return 4 + (bullettier * 3) + (level() * bullettier) + 2*Maccessories;
    }
    @Override
    public int STRReq(int lvl){
        int strreq=STRReq(tier, lvl);//change from budding
        strreq += Maccessories;
        return strreq;//change from budding
    }

    public int ShotDamageRoll() {
        return Random.Int(shotmin(), shotmax());
    }
    public void addAccessories(){
        Maccessories++;
        bulletCap++;
        bullet++;
    }
    public boolean decAccessories(){
        if(Maccessories>0){
            Maccessories--;
            bulletCap--;
            if(bullet>bulletCap) bullet=bulletCap;
            return true;
        }
        return false;
    }
    boolean pala = false;
    protected float RELOAD_TIME = 3f;

    public Accessories GunAccessories;

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (attacker instanceof Hero) {
            curUser = Dungeon.hero;
            if (Dungeon.hero.subClass == HeroSubClass.GLADIATOR) {
                if (Random.Int(4) < 1) {
                    bullet = Math.min(bullet +1, bulletCap);
                    updateQuickslot();
                }
            }
            if(Dungeon.hero.heroClass == HeroClass.MIDORI){
                if(Random.Int(40)<(6+Dungeon.hero.pointsInTalent(BULLET_SUPPLY)*3)) addBullet(1);
                if(Dungeon.hero.hasTalent(FREE_FIRE)) {
                    if(Dungeon.hero.pointsInTalent(FREE_FIRE) == 2) precisely = true;
                    Ballistica shot = new Ballistica(attacker.pos, defender.pos, Ballistica.PROJECTILE);
                    int cell = shot.collisionPos;
                    final GunWeapon ss;
                    ss = this;
                    if(ss.tryToShoot(defender.pos, shot, precisely, 1,Dungeon.hero.pointsInTalent(FREE_FIRE)/2f)){
                        Dungeon.hero.spend(-ss.getFireTick());
                    }
                }
            }
        }
        return super.proc(attacker, defender, damage);
    }

    protected void SPShot(Char ch) { }

    protected float Fire_accFactor(float acc) {
        if (GunAccessories != null) {
            acc *= GunAccessories.GetACCcorrectionvalue();

            if (Dungeon.hero.hasTalent(Talent.SHARPSHOOTER)) {
                acc += Dungeon.hero.pointsInTalent(Talent.SHARPSHOOTER) * 0.2f;
            }

            if (Dungeon.hero.hasTalent(Talent.BLITZKRIEG)) {
                acc += (Dungeon.hero.pointsInTalent(Talent.BLITZKRIEG) * 0.1f);
            }
        }

        CloserangeShot closerrange = Dungeon.hero.buff(CloserangeShot.class);
        if (closerrange != null && Dungeon.hero.hasTalent(Talent.PINPOINT)) {
            acc += Dungeon.hero.pointsInTalent(Talent.PINPOINT) * 0.2f;
        }
        if(Dungeon.hero.subClass == HeroSubClass.MARKSMIDORI) acc *= 1.25f;
        acc *= tryShootAccFactor;

        return acc;
    }

    protected float Fire_dlyFactor(float dly) {
        if (pala) dly/=4;
        if (GunAccessories != null) dly *= GunAccessories.GetDLYcorrectionvalue();
        return dly;
    }

    protected int Fire_dmgFactor(int dmg) {
        float accessoriesbouns = 1f;
        if (GunAccessories != null) accessoriesbouns = GunAccessories.GetDMGcorrectionvalue();

        float talentbouns = 1f;
        if (Dungeon.hero.hasTalent(Talent.PROJECTILE_MOMENTUM) && Dungeon.hero.buff(Momentum.class) != null &&  Dungeon.hero.buff(Momentum.class).freerunning()) {
            talentbouns += (Dungeon.hero.pointsInTalent(Talent.PROJECTILE_MOMENTUM) * 0.1f); }

        if (Dungeon.hero.hasTalent(Talent.BLITZKRIEG)) {
            talentbouns += (Dungeon.hero.pointsInTalent(Talent.BLITZKRIEG) * 0.1f);
        }

        CloserangeShot closerrange = Dungeon.hero.buff(CloserangeShot.class);
        if (closerrange != null) {
            if (closerrange.state() == true){
                talentbouns += 0.5f;
                if (Dungeon.hero.hasTalent(Talent.ZERO_RANGE_SHOT)) talentbouns += Dungeon.hero.pointsInTalent(Talent.ZERO_RANGE_SHOT) * 0.1f;
            }
        }
        if(Dungeon.hero.hasTalent(Talent.DEVELOP_BONUS)){
            talentbouns += (0.07f*Dungeon.hero.pointsInTalent(Talent.DEVELOP_BONUS));
        }

        dmg *= accessoriesbouns * talentbouns * tryShootDamageFactor;
        return dmg;
    }

    public boolean AffixAccessories(Accessories accessories) {
        if (GunAccessories != null) return false;
        else {
            GunAccessories = accessories;
            return true;
        }
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_ZAP);
        actions.add(AC_RELOAD);
        if (GunAccessories != null) actions.add(AC_REMOVE);
        if (hero.subClass == HeroSubClass.MARKSMIDORI){
            actions.add(AC_SPAMMO);
        }
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_ZAP) && bullet > 0 && Dungeon.hero.belongings.weapon == this) {
            if (this.cursed != true) {
                cursedKnown = true;
                GameScene.selectCell(zapper);
            }
            else {
                Buff.affect(Dungeon.hero, Burning.class).reignite(Dungeon.hero,4f);
                cursedKnown = true;
                bullet -= 1;
            }
        }

        if (action.equals(AC_RELOAD)) {
            curUser = hero;
            if(Dungeon.hero.heroClass == HeroClass.MIDORI) {
                if ((Dungeon.hero.hasTalent(XTRM_MEASURES)) ||
                (Dungeon.hero.subClass == HeroSubClass.KEYANIMATOR) && Dungeon.hero.hasTalent(PAPER_BULLET)){
                    GameScene.selectItem(itemSelector, WndBag.Mode.MIDORI_AMMO, Messages.get(this, "prompt"));
                }else{
                    GameScene.selectItem(itemSelector, WndBag.Mode.AMMO, Messages.get(this, "prompt"));
                }
            }
            else GameScene.selectItem(itemSelector, WndBag.Mode.MISSILEWEAPON, Messages.get(this, "prompt"));
        }

        if (action.equals(AC_REMOVE)) {
            curUser = hero;
            Accessories ac = GunAccessories;
            if (ac.doPickUp( Dungeon.hero )) {
                GLog.i( Messages.get(Dungeon.hero, "you_now_have", ac.name()) );
            } else {
                Dungeon.level.drop( ac, curUser.pos ).sprite.drop();
            }
            GunAccessories = null;
            curUser.spendAndNext(1f);
        }

        if (action.equals(AC_SPAMMO)) {
            if(Dungeon.hero.belongings.weapon != this) {
                GLog.i(Messages.get(this, "need_to_equip"));
                return;
            }
            curUser = hero;
            if(Maccessories<=0 && (Dungeon.hero.buff(SPAmmoTracker.class)!=null || !(Dungeon.hero.pointsInTalent(SMOKE_BOMB)==3))){
                GLog.i(Messages.get(this, "no_maccessories"));
            }
            else{
                final boolean[] enabled = new boolean[3];
                enabled[0] = true;
                enabled[1] = Dungeon.hero.hasTalent(SMOKE_BOMB);
                enabled[2] = Dungeon.hero.pointsInTalent(SMOKE_BOMB)>=2;
                GameScene.show(new WndOptions(
                        Messages.get(this, "prompt"),
                        Messages.get(this, "mac_desc"),
                        Messages.get(this, "mac1"),
                        ((Dungeon.hero.hasTalent(SMOKE_BOMB)) ? Messages.get(this, "mac2") : "---"),
                        ((Dungeon.hero.pointsInTalent(SMOKE_BOMB)>=2) ? Messages.get(this, "mac3") : "---")
                ){
                    @Override
                    protected void onSelect(int index) {
                        if (index == 0){
                            GameScene.selectCell(RifleGrenade);
                        } else if (index == 1){
                            GameScene.selectCell(SmokeBomb);
                        }else{
                            GameScene.selectCell(PoisonGas);
                        }
                    }
                    @Override
                    protected boolean enabled(int index) {
                        return enabled[index];
                    }
                });
            }
        }
    }

    public void reload(int tier, boolean sp) {
        bullettier = tier;
        bullet = bulletCap;

        spshot = sp;
        if(Dungeon.hero.hasTalent(PREWAR)&&Dungeon.hero.pointsInTalent(PREWAR)==2) Buff.affect(Dungeon.hero, Barrier.class).incShield(2);

        if (Dungeon.hero.subClass == HeroSubClass.FREERUNNER) Dungeon.hero.spendAndNext(RELOAD_TIME / 2);
        else Dungeon.hero.spendAndNext(RELOAD_TIME);
        Dungeon.hero.sprite.operate( Dungeon.hero.pos );
        defaultAction=AC_ZAP;
    }
    public void addBullet(int num) {
        bullet = Math.min(bulletCap, bullet+num);
        defaultAction = AC_ZAP;
        if(Dungeon.hero.hasTalent(PREWAR)&&Dungeon.hero.pointsInTalent(PREWAR)==2) Buff.affect(Dungeon.hero, Barrier.class).incShield(2);
    }
    public boolean decBullet(int num) {
        if(bullet<=0) return false;
        bullet = Math.max(0, bullet-num);
        if(bullet <= 0)defaultAction = AC_RELOAD;
        return true;
    }
    public int getBulletNum(){return bullet;}
    public float getFireTick(){return Fire_dlyFactor(FIRETICK);}
    protected int initialCap() {
        return 25;
    }
    public void updateCap(){
        int Bonuscharge = 0;
        if (Dungeon.hero.hasTalent(INSPIRATION)) Bonuscharge += Dungeon.hero.pointsInTalent(INSPIRATION);
        if(!(this instanceof FreshInspiration)) Bonuscharge*=5;
        if (gamza) Bonuscharge += 3;
        this.bulletCap = this.initialCap() + Bonuscharge + Maccessories;
        updateQuickslot();
    }
    protected static CellSelector.Listener zapper = new CellSelector.Listener() {

        @Override
        public void onSelect(Integer target) {

            if (target != null) {

                final GunWeapon ss;
                if (curItem instanceof GunWeapon) {
                    ss = (GunWeapon) GunWeapon.curItem;

                    Ballistica shot = new Ballistica(curUser.pos, target, Ballistica.PROJECTILE);
                    int cell = shot.collisionPos;

                    if (target == curUser.pos || cell == curUser.pos) {
                        GLog.i(Messages.get(DP27.class, "self_target"));
                        return;
                    }

                    curUser.sprite.zap(cell);

                    //attempts to target the cell aimed at if something is there, otherwise targets the collision pos.
                    if (Actor.findChar(target) != null)
                        QuickSlotButton.target(Actor.findChar(target));
                    else
                        QuickSlotButton.target(Actor.findChar(cell));

                    if (ss.tryToZap(curUser, target)) {
                        ss.fx(shot, new Callback() {
                            public void call() {
                                ss.onZap(shot);
                            }
                        });
                    }

                }
            }

        }

        @Override
        public String prompt() {
            return Messages.get(DP27.class, "prompt");
        }
    };
    protected static CellSelector.Listener RifleGrenade = new CellSelector.Listener() {

        @Override
        public void onSelect(Integer target) {

            if (target != null) {

                final GunWeapon gun;
                if (curItem instanceof GunWeapon) {
                    gun = (GunWeapon) curItem;

                    Ballistica shot = new Ballistica(curUser.pos, target, Ballistica.PROJECTILE);
                    int cell = shot.collisionPos;

                    if (target == curUser.pos || cell == curUser.pos) {
                        GLog.i(Messages.get(Suffering.class, "self_target"));
                        return;
                    }

                    curUser.sprite.zap(cell);

                    //attempts to target the cell aimed at if something is there, otherwise targets the collision pos.
                    if (Actor.findChar(target) != null){
                        QuickSlotButton.target(Actor.findChar(target));}
                    else{
                        QuickSlotButton.target(Actor.findChar(cell));}
                        gun.fx(shot, new Callback() {
                            public void call() {
                                gun.onRifleGrenade(shot);
                            }
                        });
                }
            }
        }

        @Override
        public String prompt() {
            return Messages.get(Suffering.class, "prompt");
        }
    };
    protected static CellSelector.Listener SmokeBomb = new CellSelector.Listener() {
        @Override
        public void onSelect(Integer target) {
            if (target != null) {
                final GunWeapon gun;
                if (curItem instanceof GunWeapon) {
                    gun = (GunWeapon) curItem;

                    Ballistica shot = new Ballistica(curUser.pos, target, Ballistica.PROJECTILE);
                    int cell = shot.collisionPos;

                    if (target == curUser.pos || cell == curUser.pos) {
                        GLog.i(Messages.get(Suffering.class, "self_target"));
                        return;
                    }

                    curUser.sprite.zap(cell);

                    //attempts to target the cell aimed at if something is there, otherwise targets the collision pos.
                    if (Actor.findChar(target) != null){
                        QuickSlotButton.target(Actor.findChar(target));}
                    else{
                        QuickSlotButton.target(Actor.findChar(cell));}
                    gun.fx(shot, new Callback() {
                        public void call() {
                            gun.onSmokeBomb(shot);
                        }
                    });
                }
            }
        }

        @Override
        public String prompt() {
            return Messages.get(Suffering.class, "prompt");
        }
    };
    protected static CellSelector.Listener PoisonGas = new CellSelector.Listener() {
        @Override
        public void onSelect(Integer target) {
            if (target != null) {
                final GunWeapon gun;
                if (curItem instanceof GunWeapon) {
                    gun = (GunWeapon) curItem;

                    Ballistica shot = new Ballistica(curUser.pos, target, Ballistica.PROJECTILE);
                    int cell = shot.collisionPos;

                    if (target == curUser.pos || cell == curUser.pos) {
                        GLog.i(Messages.get(Suffering.class, "self_target"));
                        return;
                    }

                    curUser.sprite.zap(cell);

                    //attempts to target the cell aimed at if something is there, otherwise targets the collision pos.
                    if (Actor.findChar(target) != null){
                        QuickSlotButton.target(Actor.findChar(target));}
                    else{
                        QuickSlotButton.target(Actor.findChar(cell));}
                    gun.fx(shot, new Callback() {
                        public void call() {
                            gun.onPoisonGas(shot);
                        }
                    });
                }
            }
        }

        @Override
        public String prompt() {
            return Messages.get(Suffering.class, "prompt");
        }
    };

    protected void fx( Ballistica bolt, Callback callback ) {
        MagicMissile.boltFromChar( curUser.sprite.parent,
                MagicMissile.GUN_SHOT,
                curUser.sprite,
                bolt.collisionPos,
                callback);
        Sample.INSTANCE.play( Assets.Sounds.ZAP_GUN );
    }

    public boolean tryToZap(Hero owner, int target) {

        if (bullet >= 1) {
            return true;
        } else {
            GLog.w(Messages.get(this, "fizzles"));
            return false;
        }
    }


    protected void onZap( Ballistica bolt ) {
        CloserangeShot closerrange = Dungeon.hero.buff(CloserangeShot.class);
        if(Dungeon.hero.hasTalent(PREWAR)) Buff.affect(curUser, Barrier.class).incShield(1);

        Char ch = Actor.findChar( bolt.collisionPos );
        float oldacc = ACC;
        pala = false;

        if (ch != null) {
            int dmg = Fire_dmgFactor(ShotDamageRoll());

            // 사격 스롯 판정
            if (Dungeon.hero.subClass == HeroSubClass.SNIPER) dmg -= (ch.drRoll() / 2);
            else dmg -= ch.drRoll();

            if (this instanceof R4C && Dungeon.hero.belongings.getItem(IsekaiItem.class) != null) {
                if (Dungeon.hero.belongings.getItem(IsekaiItem.class).isEquipped(Dungeon.hero)) {
                    if (ch.buff(Paralysis.class) != null) {pala = true;}
                }}

            PowerMeal powerMeal = Dungeon.hero.buff(PowerMeal.class);
            if(powerMeal!=null) {
            dmg+=powerMeal.dmgBoost;
            powerMeal.left--;
            if(powerMeal.left<=0) powerMeal.detach();
            }

            ACC = Fire_accFactor(FIREACC);
            if (ch.hit(Dungeon.hero, ch, false) || precisely) {//这里用precisely判断单次必中
                precisely = false;
                // 첸 특성
                if (Dungeon.hero.hasTalent(Talent.TARGET_FOCUSING)) {
                    if (Random.Int(3) < Dungeon.hero.pointsInTalent(Talent.TARGET_FOCUSING)) {
                        Buff.detach(ch, Camouflage.class);
                    }
                }

                if(Dungeon.hero.subClass == HeroSubClass.MARKSMIDORI && Dungeon.hero.hasTalent(MYSTERY_SHOT)){
                    MysteryShotHandler(ch, dmg);
                }
                else ch.damage(dmg, this);
                if(Dungeon.hero.buff(PotatoAimReady.class)!=null && Dungeon.hero.buff(PotatoAimReady.class).isReady()){
                    PotatoAimReady.PotatoKill(ch);
                }

                Sample.INSTANCE.play(Assets.Sounds.HIT_GUN, 1, Random.Float(0.87f, 1.15f));

                if (spshot) SPShot(ch);
                if (this instanceof C1_9mm) {
                    if (Random.Int(8) == 0) Buff.affect(ch, Chill.class, 2f);
                }

                ch.sprite.burst(0xFFFFFFFF, buffedLvl() / 2 + 2);

                // 사격 그레이스롯 판정
                int bonusTurns = Dungeon.hero.hasTalent(Talent.SHARED_UPGRADES) ? this.buffedLvl() : 0;
                if (Dungeon.hero.subClass == HeroSubClass.SNIPER) Buff.prolong(Dungeon.hero, SnipersMark.class, SnipersMark.DURATION).set(ch.id(), bonusTurns);

                // 연계 블레이즈 판정
                if (Dungeon.hero.subClass == HeroSubClass.GLADIATOR) {
                    Buff.affect(Dungeon.hero, Combo.class).hit(ch);

                    if (Dungeon.hero.hasTalent(Talent.CLEAVE)) {
                        if (Random.Int(10) < Dungeon.hero.pointsInTalent(Talent.CLEAVE)) {
                            Buff.affect(Dungeon.hero, Combo.class).hit(ch);
                        }
                    }
                }

                // 산사수 첸 판정
                if (Dungeon.hero.subClass == HeroSubClass.SPSHOOTER && ch.isAlive() && Dungeon.hero.buff(ChenShooterBuff.TACMoveCooldown.class) == null) {
                        Buff.prolong(Dungeon.hero, ChenShooterBuff.class, 5f).set(ch.id());
                }

                if (closerrange != null && ch.isAlive() && closerrange.state() == true) {
                    if (Dungeon.hero.hasTalent(Talent.WATER_PLAY) && Random.Int(5) < Dungeon.hero.pointsInTalent(Talent.WATER_PLAY)) {
                        Buff.affect(ch, Blindness.class, 1f);
                    }

                    if (Dungeon.hero.hasTalent(Talent.TAC_SHOT) && Dungeon.hero.buff(ChenShooterBuff.TACMove_tacshot.class) != null) {
                        int min = Dungeon.hero.pointsInTalent(Talent.TAC_SHOT) / 2;
                        int max = 1 + Dungeon.hero.pointsInTalent(Talent.TAC_SHOT) / 3;

                        Ballistica trajectory = new Ballistica(curUser.pos, ch.pos, Ballistica.STOP_TARGET);
                        trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);
                        WandOfBlastWave.throwChar(ch, trajectory, Random.IntRange(min, max)); // 넉백 효과

                        Buff.detach(Dungeon.hero,ChenShooterBuff.TACMove_tacshot.class);
                    }
                }

                if(Dungeon.hero.hasTalent(IMPACT_BULLET) && this.bullet>(this.bulletCap*0.75f)){
                    Ballistica trajectory = new Ballistica(curUser.pos, ch.pos, Ballistica.STOP_TARGET);
                    trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);
                    WandOfBlastWave.throwChar(ch, trajectory, Dungeon.hero.pointsInTalent(IMPACT_BULLET)*2);
                    this.bullet--;
                }

                if(Dungeon.hero.hasTalent(FULL_FIREPOWER) && Dungeon.hero.pointsInTalent(FULL_FIREPOWER)==3){
                    DrawingArt drawingArt = Dungeon.hero.buff(DrawingArt.class);
                    if(drawingArt != null){
                        drawingArt.gainCharge();
                    }
                }

                if(Dungeon.hero.buff(ItsHighNoon.class)!=null&&
                        !(ch.properties().contains(Char.Property.MINIBOSS)) &&
                        !(ch.properties().contains(Char.Property.BOSS)))
                {
                    Buff.affect(ch, ItsHighNoonMark.class);
                }
            }
            else {
                String defense = ch.defenseVerb();
                ch.sprite.showStatus( CharSprite.NEUTRAL, defense );

                //TODO enemy.defenseSound? currently miss plays for monks/crab even when they parry
                Sample.INSTANCE.play(Assets.Sounds.MISS);

            }

        } else {
            Dungeon.level.pressCell(bolt.collisionPos);
        }

        Buff buff = Dungeon.hero.buff(TimekeepersHourglass.timeFreeze.class);
        if (buff != null) buff.detach();
        buff = Dungeon.hero.buff(Swiftthistle.TimeBubble.class);
        if (buff != null) buff.detach();
        //rabbittime在这里不取消的话会怎么样？

        if (Dungeon.hero.buff(Bonk.BonkBuff.class) != null) Buff.detach(Dungeon.hero, Bonk.BonkBuff.class);

        Invisibility.dispel();

        if (GunAccessories != null) {
            if (GunAccessories.GetSavingChance() < Random.Int(100)) {
                bullet -=1;
            }
        }
        else if (closerrange != null && closerrange.state() == true && Dungeon.hero.hasTalent(Talent.FRUGALITY)) {
            if (Random.Int(100) > Dungeon.hero.pointsInTalent(Talent.FRUGALITY) * 15) bullet-=1;
        }
        else if(Dungeon.hero.heroClass == HeroClass.MIDORI){
            if(Random.Int(40)>(6+Dungeon.hero.pointsInTalent(BULLET_SUPPLY)*3)) bullet -=1;
        }
        else bullet -=1;
        updateQuickslot();

        ACC = oldacc;

        curUser.spendAndNext(Fire_dlyFactor(FIRETICK));

        if (ch != null && !ch.isAlive() && Dungeon.hero.hasTalent(Talent.BF_RULL) && Random.Int(5) < Dungeon.hero.pointsInTalent(Talent.BF_RULL)) {
            Buff.affect(Dungeon.hero, Swiftthistle.TimeBubble.class).bufftime(1f);
        }

        if(this.bullet<=0) defaultAction=AC_RELOAD;
    }
    protected void onRifleGrenade(Ballistica bolt){
        Sample.INSTANCE.play(Assets.Sounds.GAS);
        Sample.INSTANCE.play(Assets.Sounds.BLAST);
        for (int i : PathFinder.NEIGHBOURS9) {
            if (!Dungeon.level.solid[bolt.collisionPos+i]) {
                if (Dungeon.level.heroFOV[bolt.collisionPos+i]) {
                    CellEmitter.get(bolt.collisionPos+i).burst(SmokeParticle.FACTORY, 4);
                }
            }
            Char ch = Actor.findChar(bolt.collisionPos + i);

            if (ch != null) {
                if (ch.alignment != Char.Alignment.ALLY) ch.damage(Dungeon.depth*3, this);

                if (ch.isAlive() && ch.pos == bolt.collisionPos + i) {
                    Buff.affect(ch, Vulnerable.class, Dungeon.depth);
                } else if (ch == Dungeon.hero) {
                    Dungeon.fail(getClass());
                    GLog.n(Messages.get(this, "ondeath"));
                }
            }
        }
        if(Dungeon.hero.buff(SPAmmoTracker.class)!=null) { decAccessories();}
        else{ Buff.affect(Dungeon.hero, SPAmmoTracker.class, SPAmmoTracker.DURATION);}
        curUser.spendAndNext(1f);
    }
    protected void onSmokeBomb(Ballistica bolt){
        Sample.INSTANCE.play(Assets.Sounds.GAS);
        Sample.INSTANCE.play(Assets.Sounds.BLAST);
        for (int i : PathFinder.NEIGHBOURS9) {
            if (!Dungeon.level.solid[bolt.collisionPos+i]) {
                if (Dungeon.level.heroFOV[bolt.collisionPos+i]) {
                    CellEmitter.get(bolt.collisionPos+i).burst(SmokeParticle.FACTORY, 4);
                }
            }
            GameScene.add( Blob.seed( bolt.collisionPos, 250*Dungeon.hero.pointsInTalent(SMOKE_BOMB), SmokeScreen.class ) );
        }
        if(Dungeon.hero.buff(SPAmmoTracker.class)!=null) { decAccessories();}
        else{ Buff.affect(Dungeon.hero, SPAmmoTracker.class, SPAmmoTracker.DURATION);}
        curUser.spendAndNext(1f);
    }
    protected void onPoisonGas(Ballistica bolt){
        for (int i : PathFinder.NEIGHBOURS9) {
            if (!Dungeon.level.solid[bolt.collisionPos+i]) {
                if (Dungeon.level.heroFOV[bolt.collisionPos+i]) {
                    CellEmitter.get(bolt.collisionPos+i).burst(SmokeParticle.FACTORY, 4);
                }
            }
            GameScene.add( Blob.seed( bolt.collisionPos, 300, ToxicGas.class ) );
            GameScene.add( Blob.seed( bolt.collisionPos, 125, CorrosiveGas.class ).setStrength( 1 + Dungeon.depth/5));
            Sample.INSTANCE.play(Assets.Sounds.GAS);
            Sample.INSTANCE.play(Assets.Sounds.BLAST);
        }
        if(Dungeon.hero.buff(SPAmmoTracker.class)!=null) { decAccessories();}
        else{ Buff.affect(Dungeon.hero, SPAmmoTracker.class, SPAmmoTracker.DURATION);}
        curUser.spendAndNext(1f);
    }

    public boolean tryToShoot(int target, Ballistica shot, boolean isPrecisely, float damageFactor, float accFactor){
        curUser = Dungeon.hero;
        precisely = isPrecisely;
        tryShootDamageFactor = damageFactor;
        tryShootAccFactor = accFactor;
        GunWeapon gun = this;
        if (target == curUser.pos || shot.collisionPos == curUser.pos) {
            GLog.i(Messages.get(DP27.class, "self_target"));
            return false;
        }
        if (gun.tryToZap(curUser, target)) {
            Dungeon.hero.sprite.Sattack(target);
            gun.fx(shot, new Callback() {
                public void call() {
                    gun.onZap(shot);
                }
            });
            return true;
        }else return false;
    }
    public void MysteryShotHandler(Char ch, int damage){
        if(Dungeon.hero.pointsInTalent(MYSTERY_SHOT)==1){
            if(ch.buff(MysteryShotTracker.class)==null){
                WandOfMagicMissile womm = new WandOfMagicMissile();
                Buff.affect(ch, MysteryShotTracker.class);
                ch.buff(MysteryShotTracker.class).addCount();
                ch.damage(damage, womm);
            }else{
                ch.damage(damage,this);
            }
        }else{
            if(ch.buff(MysteryShotTracker.class)==null ||
                    ch.buff(MysteryShotTracker.class).getCount() < Dungeon.hero.pointsInTalent(MYSTERY_SHOT)-1){
                Buff.affect(ch, MysteryShotTracker.class);
                ch.buff(MysteryShotTracker.class).addCount();
                ch.sprite.showStatus(CharSprite.DEFAULT, Integer.toString(damage));
                ch.HP -= damage;
                ((Mob)ch).beckon(Dungeon.hero.pos);
                if(ch.HP<0)ch.HP=0;
                if (!ch.isAlive()) {
                    ch.die(this);
                }
            }else{
                ch.damage(damage,this);
            }
        }
    }
    @Override
    public String status() { return bullet+"/"+bulletCap; }

    @Override
    public String desc() {
        return Messages.get(this, "desc", bullettier);
    }

    @Override
    public String info() {
        String info = super.info();
        if (GunAccessories != null) {
            info += "\n\n" + Messages.get(GunAccessories, "desc");
        }
        return info;
    }

    private final WndBag.Listener itemSelector = new WndBag.Listener() {
        @Override
        public void onSelect( final Item item ) {
            if (item != null) {
                if(item instanceof MissileWeapon){
                    if (item instanceof Thunderbolt) {
                        bulletCap+=3;
                        gamza = true;}
                    reload(((MissileWeapon)item).tier, item instanceof UpMagazine);
                    item.detach(Dungeon.hero.belongings.backpack);
                }
                else if(item instanceof MidoriAccessories){
                    reload(Dungeon.hero.pointsInTalent(XTRM_MEASURES)+2, false);
                    item.detach(Dungeon.hero.belongings.backpack);
                    if(Dungeon.hero.pointsInTalent(XTRM_MEASURES)==2)Dungeon.hero.spend(-RELOAD_TIME);
                }else if(item instanceof MagicPaper){
                    addBullet(Math.round(Dungeon.hero.pointsInTalent(PAPER_BULLET)*0.4f)+1);
                    if(!(curItem instanceof FreshInspiration)){addBullet(Math.round(Dungeon.hero.pointsInTalent(PAPER_BULLET)*0.4f)+1);}
                    item.detach(Dungeon.hero.belongings.backpack);
                    if(!(Dungeon.hero.pointsInTalent(PAPER_BULLET)==3))Dungeon.hero.spendAndNext(RELOAD_TIME);;
                }
            }
        }
    };

    @Override
    public String name() {
        if (gamza) return Messages.get(this, "gamza_name");
        return super.name();
    }

    public String statsInfo() {
        if (spshot) return Messages.get(this, "stats_desc_sp", shotmin(),shotmax());
        return Messages.get(this, "stats_desc", shotmin(),shotmax());
    }

    private static final String BULLET = "bullet";
    private static final String BULLET_CAP = "bulletCap";
    private static final String GAMZA = "gamza";
    private static final String TIER = "bullettier";
    private static final String SP = "spshot";
    private static final String ACCESSORIES = "GunAccessories";
    private static final String MACCESSORIES = "maccessories";
    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(BULLET, bullet);
        bundle.put(BULLET_CAP, bulletCap);
        bundle.put(GAMZA, gamza);
        bundle.put(TIER, bullettier);
        bundle.put(SP, spshot);
        bundle.put( ACCESSORIES, GunAccessories);
        bundle.put(MACCESSORIES, Maccessories);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        bulletCap = bundle.getInt(BULLET_CAP);
        if (bulletCap > 0) bullet = Math.min(bulletCap, bundle.getInt(BULLET));
        else bullet = bundle.getInt(BULLET);

        bullettier = bundle.getInt(TIER);
        spshot = bundle.getBoolean(SP);
        gamza = bundle.getBoolean(GAMZA);
        GunAccessories = (Accessories) bundle.get(ACCESSORIES);
        Maccessories = bundle.getInt(MACCESSORIES);
    }
    public static class MysteryShotTracker extends Buff {
        int count = 0;
        public int getCount(){return count;}
        public void addCount(){count++;}
        private static final String COUNT = "count";
        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put(COUNT, count);
        }
        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            count = bundle.getInt(COUNT);
        }
    };
    public static class SPAmmoTracker extends FlavourBuff {
        public static final float DURATION	= 200f;
        @Override
        public int icon() {
            return BuffIndicator.PINCUSHION;
        }
        @Override
        public float iconFadePercent() {
            return Math.max(0, visualcooldown() / DURATION);
        }
        @Override
        public String toString() {
            return Messages.get(this, "name");
        }
        @Override
        public String desc() {
            return Messages.get(this, "desc", String.valueOf(visualcooldown()));
        }
    };
}
