/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon;

import com.shatteredpixel.shatteredpixeldungeon.items.Dewdrop;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.NewGameItem.Certificate;

public class SPChallenges {

    //Some of these internal IDs are outdated and don't represent what these challenges do
    public static final int GLASS				= 1;
    public static final int HONOR				= 2;
    public static final int SPITE				= 4;
    public static final int CHIMERA				= 8;
    public static final int SWARMS				= 16;
    public static final int MAX_VALUE           = 31;

    public static final String[] NAME_IDS = {
            "chimera","glass","honor","spite","swarms"
    };

    public static final int[] MASKS = {
            CHIMERA,GLASS,HONOR,SPITE,SWARMS
    };

//    public static int activeSPChallenges(){
//        int chCount = 0;
//        for (int ch : SPChallenges.MASKS){
//            if ((Dungeon.spchallenges & ch) != 0) chCount++;
//        }if ((Dungeon.spchallenges & 4096)!=0) chCount=0;//change from budding
//        return chCount;
//    }

//    public static boolean isItemBlocked( Item item ){
//        return false;
//    }

}