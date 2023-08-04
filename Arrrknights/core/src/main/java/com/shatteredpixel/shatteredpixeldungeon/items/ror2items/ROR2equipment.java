package com.shatteredpixel.shatteredpixeldungeon.items.ror2items;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.utils.Bundle;

abstract public class ROR2equipment extends ROR2item{

    protected Buff activeBuff;
    //the current artifact charge
    protected int charge = 0;
    //the build towards next charge, usually rolls over at 1.
    //better to keep charge as an int and use a separate float than casting.
    protected float partialCharge = 0;
    //the maximum charge, varies per artifact, not all artifacts use this.
    protected int chargeCap = 0;

    @Override
    public String status() {

        //if the artifact isn't IDed, or is cursed, don't display anything
        if (!isIdentified() || cursed){
            return null;
        }

        //display as percent
        if (chargeCap == 100)
            return Messages.format( "%d%%", charge );

        //display as #/#
        if (chargeCap > 0)
            return Messages.format( "%d/%d", charge, chargeCap );

        //if there's no cap -
        //- but there is charge anyway, display that charge
        if (charge != 0)
            return Messages.format( "%d", charge );

        //otherwise, if there's no charge, return null.
        return null;
    }

    protected ROR2item.ROR2itemBuff activeBuff() {return null; }
    private static final String CHARGE = "charge";
    private static final String PARTIALCHARGE = "partialcharge";
    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);
        bundle.put( CHARGE , charge );
        bundle.put( PARTIALCHARGE , partialCharge );
    }
    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        if (chargeCap > 0)  charge = Math.min( chargeCap, bundle.getInt( CHARGE ));
        else                charge = bundle.getInt( CHARGE );
        partialCharge = bundle.getFloat( PARTIALCHARGE );
    }
}
