/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.windows;

import static com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent.SMOKE_BOMB;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Blacksmith;
import com.shatteredpixel.shatteredpixeldungeon.items.ArmorKit;
import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.RingKit;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.DarkGold;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.journal.Notes;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

public class WndBlacksmithAfter extends Window {

    private static final int WIDTH_P = 120;
    private static final int WIDTH_L = 160;

    private static final int GAP  = 2;

    public WndBlacksmithAfter( Blacksmith troll, Hero hero ) {
        super();
        Window wba = this;

        int width = PixelScene.landscape() ? WIDTH_L : WIDTH_P;

        IconTitle titlebar = new IconTitle();
        titlebar.icon( troll.sprite() );
        titlebar.label( Messages.titleCase( troll.name() ) );
        titlebar.setRect( 0, 0, width, 0 );
        add( titlebar );

        RenderedTextBlock message = PixelScene.renderTextBlock( Messages.get(this, "prompt"), 6 );
        message.maxWidth( width );
        message.setPos(0, titlebar.bottom() + GAP);
        add( message );

        ArrayList<RedButton> buttons = new ArrayList<>();

        RedButton addChi = new RedButton(Messages.get(this, "addchi"), 6){
            @Override
            protected void onClick() {
                wba.hide();
                GameScene.selectItem( addSelector, WndBag.Mode.WEAPON, Messages.get(this, "select") );
            }
        };
        buttons.add(addChi);

        RedButton delChi = new RedButton(Messages.get(this, "delchi"), 6){
            @Override
            protected void onClick() {
                wba.hide();
                GameScene.selectItem( delSelector, WndBag.Mode.WEAPON, Messages.get(this, "select") );
            }
        };
        buttons.add(delChi);

        RedButton changeChi = new RedButton(Messages.get(this, "changechi"), 6){
            @Override
            protected void onClick() {
                wba.hide();
                GameScene.selectItem( changeSelector, WndBag.Mode.WEAPON, Messages.get(this, "select") );
            }
        };
        buttons.add(changeChi);

        float pos = message.bottom() + 3*GAP;
        for (RedButton b : buttons){
            b.leftJustify = true;
            b.multiline = true;
            b.setSize(width, b.reqHeight());
            b.setRect(0, pos, width, b.reqHeight());
            b.enable(b.active); //so that it's visually reflected
            add(b);
            pos = b.bottom() + GAP;
        }

        resize(width, (int)pos);

    }

    private final WndBag.Listener addSelector = new WndBag.Listener() {
        @Override
        public void onSelect( Item item ) {
            if (item instanceof MeleeWeapon) {
                if(((MeleeWeapon) item).chimeras.size()==3) GameScene.show( new WndMessage( Messages.get(Blacksmith.class, "chis_full")) );
                else{
                    ((MeleeWeapon) item).chimera();
                    DarkGold gold = Dungeon.hero.belongings.getItem( DarkGold.class );
                    gold.detachAmount(Dungeon.hero.belongings.backpack, 15);
                }
            }
        }
    };

    private final WndBag.Listener delSelector = new WndBag.Listener() {
        @Override
        public void onSelect( Item item ) {
            if (item instanceof MeleeWeapon) {
                if(((MeleeWeapon) item).chimeras.isEmpty()) GameScene.show( new WndMessage( Messages.get(Blacksmith.class, "chis_null")) );
                else{
                    int i=0;
                    final Weapon.Chimera chis[] = new Weapon.Chimera[3];
                    for(Weapon.Chimera c: ((MeleeWeapon) item).chimeras){
                        chis[i]=c;
                        i++;
                    }
                    final boolean[] enabled = new boolean[3];
                    enabled[0] = true;
                    enabled[1] = ((MeleeWeapon) item).chimeras.size()>=2;
                    enabled[2] = ((MeleeWeapon) item).chimeras.size()>=3;
                    GameScene.show(new WndOptions(Messages.titleCase(Messages.get(Blacksmith.class, "name")),
                            Messages.get(Blacksmith.class, "chis_del_title") +
                                    "\n\n" +
                                    Messages.get(RingKit.class, "chis_del_msg"),
                            chis[0].name(),
                            ((chis[1]!=null) ? chis[1].name() : "---"),
                            ((chis[2]!=null) ? chis[2].name() : "---")) {

                        @Override
                        protected void onSelect(int index) {
                            if (index < 3 && chis[index]!=null) {
                                ((MeleeWeapon) item).removeChimera(chis[index].getClass());
                            }
                        }

                        @Override
                        public void onBackPressed() {
                            super.onBackPressed();
                        }
                        @Override
                        protected boolean enabled(int index) {
                            return enabled[index];
                        }
                    });
                    DarkGold gold = Dungeon.hero.belongings.getItem( DarkGold.class );
                    gold.detachAmount(Dungeon.hero.belongings.backpack, 15);
                }
            }
        }
    };
    private final WndBag.Listener changeSelector = new WndBag.Listener() {
        @Override
        public void onSelect( Item item ) {
            if (item instanceof MeleeWeapon) {
                if(((MeleeWeapon) item).chimeras.isEmpty()) GameScene.show( new WndMessage( Messages.get(Blacksmith.class, "chis_null")) );
                else{
                    int i=0;
                    final Weapon.Chimera chis[] = new Weapon.Chimera[3];
                    for(Weapon.Chimera c: ((MeleeWeapon) item).chimeras){
                        chis[i]=c;
                        i++;
                    }
                    final boolean[] enabled = new boolean[3];
                    enabled[0] = true;
                    enabled[1] = ((MeleeWeapon) item).chimeras.size()>=2;
                    enabled[2] = ((MeleeWeapon) item).chimeras.size()>=3;
                    GameScene.show(new WndOptions(Messages.titleCase(Messages.get(Blacksmith.class, "name")),
                            Messages.get(Blacksmith.class, "chis_del_title") +
                                    "\n\n" +
                                    Messages.get(RingKit.class, "chis_del_msg"),
                            chis[0].name(),
                            ((chis[1]!=null) ? chis[1].name() : "---"),
                            ((chis[2]!=null) ? chis[2].name() : "---")) {

                        @Override
                        protected void onSelect(int index) {
                            if (index < 3 && chis[index]!=null) {
                                ((MeleeWeapon) item).removeChimera(chis[index].getClass());
                                ((MeleeWeapon) item).chimera();
                            }
                        }

                        @Override
                        public void onBackPressed() {
                            super.onBackPressed();
                        }
                        @Override
                        protected boolean enabled(int index) {
                            return enabled[index];
                        }
                    });
                    DarkGold gold = Dungeon.hero.belongings.getItem( DarkGold.class );
                    gold.detachAmount(Dungeon.hero.belongings.backpack, 15);
                }
            }
        }
    };
}