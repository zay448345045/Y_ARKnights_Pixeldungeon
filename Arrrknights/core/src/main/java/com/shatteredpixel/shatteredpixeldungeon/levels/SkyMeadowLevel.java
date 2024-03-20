package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.GavialPainter;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.watabou.noosa.Group;
import com.watabou.utils.Random;

public class SkyMeadowLevel extends RegularLevel {
    {
        color1 = 0xbb44bb;
        color2 = 0xf2f2f2;
    }

    @Override
    protected int standardRooms(boolean forceMax) {
        if (forceMax) return 10;
        //8 to 10, average 8.67
        return 7+ Random.chances(new float[]{3, 2, 1});
    }

    @Override
    protected int specialRooms(boolean forceMax) {
        if (forceMax) return 3;
        return 1 + Random.chances(new float[]{1, 1});
    }

    @Override
    protected void createItems() {

        if (Dungeon.depth == 32 || Dungeon.depth == 34 || Dungeon.depth == 37 || Dungeon.depth == 39) {
            addItemToSpawn(new PotionOfStrength());}
        else  if (Dungeon.depth == 31 || Dungeon.depth == 33 || Dungeon.depth == 36 || Dungeon.depth == 38) addItemToSpawn(new ScrollOfUpgrade());

        super.createItems();
    }

    @Override
    public int nMobs() {
        // 다른 계층보다 몬스터가 4마리 많이 등장합니다. 컨셉 : 맵 넓고 몹이 존나 많음.
        return super.nMobs()+4;
    }

    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_CAVES;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_HALLS;
    }

    @Override
    protected Painter painter() {
        return new GavialPainter()
                .setWater(feeling == Feeling.WATER ? 0.38f : 0.18f, 4)
                .setGrass(feeling == Feeling.GRASS ? 0.99f : 0.30f, 3);
    }

    @Override
    protected void createMobs() {
        super.createMobs();
    }


    @Override
    public Group addVisuals() {
        super.addVisuals();
        return visuals;
    }
}
