package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import static com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent.BULLET_SUPPLY;
import static com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent.FREE_FIRE;
import static com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent.XTRM_MEASURES;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Camouflage;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ChenShooterBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.CloserangeShot;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Combo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Momentum;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SnipersMark;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.Bonk;
import com.shatteredpixel.shatteredpixeldungeon.items.Gunaccessories.Accessories;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.MidoriAccessories;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.IsekaiItem;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.Thunderbolt;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.UpMagazine;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Swiftthistle;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.QuickSlotButton;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class GunWeapon extends MeleeWeapon {
    public static final String AC_ZAP = "ZAP";
    public static final String AC_RELOAD = "RELOAD";
    public static final String AC_REMOVE = "REMOVE";

    protected int bullettier = 3;
    protected int bullet = 5;
    protected int bulletCap = 25;
    protected boolean spshot = false; // 특수 사격 여부
    protected boolean gamza = false; // 썬더볼트 장착 여부
    protected float FIREACC = 1f;
    protected float FIRETICK = 1f;
    protected int Maccessories = 0;

    protected boolean precisely = false;
    @Override
    public int min(int lvl) {
        return  tier +  //base
                lvl +  //level scaling
                ((Dungeon.hero.hasTalent(Talent.BAYONET)&& Maccessories>0)? Dungeon.hero.pointsInTalent(Talent.BAYONET):0);
    }
    @Override
    public int max(int lvl) {
        return  3*(tier) +    // 3티어 기준 9+1, 5티어는 15+3
                lvl*(tier-2) +
                ((Dungeon.hero.hasTalent(Talent.BAYONET)&& Maccessories>0)? Dungeon.hero.pointsInTalent(Talent.BAYONET)+1:0);
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
                if(Random.Int(40)<(6+Dungeon.hero.pointsInTalent(BULLET_SUPPLY)*3)) bullet++;
                if(Dungeon.hero.hasTalent(FREE_FIRE)) {
                    if(Dungeon.hero.pointsInTalent(FREE_FIRE) == 2) precisely = true;
                    Ballistica shot = new Ballistica(attacker.pos, defender.pos, Ballistica.PROJECTILE);
                    int cell = shot.collisionPos;
                    curUser.sprite.Sattack(cell);
                    final GunWeapon ss;
                    ss = this;
                    if (ss.tryToZap((Hero)attacker, defender.pos)) {
                        ss.fx(shot, new Callback() {
                            public void call() {
                                ss.onZap(shot);
                            }
                        });
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

        return acc;
    }

    protected float Fire_dlyFactor(float dly) {
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

        dmg *= accessoriesbouns * talentbouns;
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
            if(Dungeon.hero.heroClass == HeroClass.MIDORI && Dungeon.hero.hasTalent(XTRM_MEASURES)) {
                GameScene.selectItem(itemSelector, WndBag.Mode.AMMO, Messages.get(this, "prompt"));
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
    }

    public void reload(int tier, boolean sp) {
        bullettier = tier;
        bullet = bulletCap;

        spshot = sp;

        if (Dungeon.hero.subClass == HeroSubClass.FREERUNNER) Dungeon.hero.spendAndNext(RELOAD_TIME / 2);
        else Dungeon.hero.spendAndNext(RELOAD_TIME);
        Dungeon.hero.sprite.operate( Dungeon.hero.pos );
    }
    public void addBullet(int num) {
        bullet = Math.min(bulletCap, bullet+num);
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

        Char ch = Actor.findChar( bolt.collisionPos );
        float oldacc = ACC;
        boolean pala = false;

        if (ch != null) {
            int dmg = Fire_dmgFactor(ShotDamageRoll());

            // 사격 스롯 판정
            if (Dungeon.hero.subClass == HeroSubClass.SNIPER) dmg -= (ch.drRoll() / 2);
            else dmg -= ch.drRoll();

            if (this instanceof R4C && Dungeon.hero.belongings.getItem(IsekaiItem.class) != null) {
                if (Dungeon.hero.belongings.getItem(IsekaiItem.class).isEquipped(Dungeon.hero)) {
                    if (ch.buff(Paralysis.class) != null) {pala = true;}
                }}

            ACC = Fire_accFactor(FIREACC);
            if (ch.hit(Dungeon.hero, ch, false) || precisely) {//这里用exactly判断单次必中
                precisely = false;
                // 첸 특성
                if (Dungeon.hero.hasTalent(Talent.TARGET_FOCUSING)) {
                    if (Random.Int(3) < Dungeon.hero.pointsInTalent(Talent.TARGET_FOCUSING)) {
                        Buff.detach(ch, Camouflage.class);
                    }
                }

                ch.damage(dmg, this);
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
        else if(Dungeon.hero.heroClass == HeroClass.MIDORI && Random.Int(20)>3){
            bullet -=1;
        }
        else bullet -=1;
        updateQuickslot();

        ACC = oldacc;

        if (pala) { curUser.spendAndNext(Fire_dlyFactor(FIRETICK / 4)); }
        else curUser.spendAndNext(Fire_dlyFactor(FIRETICK));

        if (ch != null && !ch.isAlive() && Dungeon.hero.hasTalent(Talent.BF_RULL) && Random.Int(5) < Dungeon.hero.pointsInTalent(Talent.BF_RULL)) {
            Buff.affect(Dungeon.hero, Swiftthistle.TimeBubble.class).bufftime(1f);
        }
    }

    @Override
    public String status() { return bullet+""; }

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
}
