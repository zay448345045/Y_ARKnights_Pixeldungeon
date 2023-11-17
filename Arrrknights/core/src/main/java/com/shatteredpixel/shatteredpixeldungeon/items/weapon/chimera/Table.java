package com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.utils.Random;

public class Table extends Weapon.Chimera{
    @Override
    public String beforeName() {
        return Messages.get(this, "name");
    }

    private int backchane = 0;
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        int level = Math.max( 0, weapon.buffedLvl() );
        if (Random.Int(15) < 2 + backchane) {
            Ballistica trajectory = new Ballistica(attacker/*curUser*/.pos, defender.pos, Ballistica.STOP_TARGET);//change from budding
            trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);
            WandOfBlastWave.throwChar(defender, trajectory, 2+(level/ 5)); // 넉백 효과
            backchane = 0;
        }
        else backchane++;
        return damage;
    }
}
