package com.shatteredpixel.shatteredpixeldungeon.items.testtool;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.InventoryStone;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Archery;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Artorius;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Assault;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Blade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Bloody;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Blossoming;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Boiling;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Breeder;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Clush;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Demon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Dial;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.EX;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Flame;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Form;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Frostcraft;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Gloves;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Highest;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Horoscope;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Hyphen200;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Hyphen3;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Journey;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.LightOf;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Mountain;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Patriot;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Rhine;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Shadow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Song;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Surrender;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Sylvestris;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Table;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Teller;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Thermit;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.chimera.Winter;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.CheckBox;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.ScrollPane;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ChimeraGiver extends Generators {
    {
        image = ItemSpriteSheet.UNDONE_MARK;
        defaultAction = AC_ATTACH;
    }
    private long chiOn = 0L;
    public static final ArrayList<Weapon.Chimera> chiList = new ArrayList<>();
    public static final LinkedHashMap<Class<? extends Weapon.Chimera>, Integer> chiPrio = new LinkedHashMap<>();
    private Weapon selectedWeapon;
    static{
        chiPrio.put(Archery.class, 0);
        chiPrio.put(Gloves.class, 0);
        chiPrio.put(Bloody.class, 0);
        chiPrio.put(EX.class, 0);

        chiPrio.put(Assault.class, 0);
        chiPrio.put(Flame.class, 0);
        chiPrio.put(Hyphen3.class, 0);

        chiPrio.put(Thermit.class, 0);
        chiPrio.put(Rhine.class, 0);
        chiPrio.put(Highest.class, 0);
        chiPrio.put(Table.class, 0);
        chiPrio.put(Hyphen200.class, 0);

        chiPrio.put(Blade.class, 0);
        chiPrio.put(Demon.class, 0);
        chiPrio.put(Dial.class, 0);
        chiPrio.put(Horoscope.class, 0);
        chiPrio.put(Breeder.class, 0);
        chiPrio.put(Song.class, 0);
        chiPrio.put(Sylvestris.class, 0);
        chiPrio.put(Blossoming.class, 0);

        chiPrio.put(Winter.class, 0);
        chiPrio.put(Boiling.class, 0);
        chiPrio.put(Shadow.class, 0);
        chiPrio.put(Surrender.class, 0);
        chiPrio.put(Artorius.class, 0);
        chiPrio.put(LightOf.class, 0);
        chiPrio.put(Teller.class, 0);
        chiPrio.put(Patriot.class, 0);
        chiPrio.put(Mountain.class, 0);
        chiPrio.put(Form.class, 0);
        chiPrio.put(Journey.class, 0);

        chiPrio.put(Clush.class, 0);
        chiPrio.put(Frostcraft.class, 0);
    }
    public static final String AC_ATTACH	= "ATTACH";

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_ATTACH );
        return actions;
    }
    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if (action.equals(AC_ATTACH)) {
            GameScene.selectItem( itemSelector, WndBag.Mode.WEAPON, Messages.get(this, "inv_title") );
        }
    }

    protected final WndBag.Listener itemSelector = new WndBag.Listener() {
        @Override
        public void onSelect( Item item ) {

            //FIXME this safety check shouldn't be necessary
            //it would be better to eliminate the curItem static variable.
            if (!(curItem instanceof ChimeraGiver)){
                return;
            }

            if (item != null) {
                if(item instanceof Weapon){
                    selectedWeapon = (Weapon) item;
                    onItemSelected( item );
                }
            }
        }
    };

    protected  void onItemSelected( Item item ){
        GameScene.show(new EnchantWindow());
    }

    private class EnchantWindow extends Window {
        private ArrayList<CustomWeapon.canScrollCheckBox> checkBoxes = new ArrayList<>();
        public EnchantWindow(){

            super();
            resize(120, 108 + 36);
            int placed = 0;
            ScrollPane list = new ScrollPane(new Component()) {

                @Override
                public void onClick(float x, float y) {
                    int max_size = checkBoxes.size();
                    for (int i = 0; i < max_size; ++i) {
                        if (checkBoxes.get(i).onClick(x, y))
                            break;
                    }
                }

            };
            add(list);
            Component content = list.content();
            for(Class<? extends Weapon.Chimera> chi : chiPrio.keySet()){
                CustomWeapon.canScrollCheckBox cb = new CustomWeapon.canScrollCheckBox(Reflection.newInstance(chi).name()){
                    protected boolean onClick(float x, float y){
                        if(!inside(x,y)) return false;
                        onClick();

                        return true;
                    }

                    @Override
                    protected void onClick(){
                        super.onClick();
                        checked(!checked());
                    }

                    @Override
                    protected void layout(){
                        super.layout();
                        hotArea.width = hotArea.height = 0;
                    }
                };
                cb.checked((chiOn&(1L<<placed)) > 0 );
                cb.setRect(0, 18*placed, 120, 16);
                PixelScene.align(cb);
                placed ++;
                content.add(cb);
                checkBoxes.add(cb);
            }
            content.setSize(120, checkBoxes.get(checkBoxes.size()-1).bottom());
            list.setSize( list.width(), list.height() );
            list.setRect(0, 0, 120, 108);
            list.scrollTo(0,0);

            RedButton allOn = new RedButton(Messages.get(this, "all_on")) {
                @Override
                protected void onClick() {
                    allChiOn();
                }
            };
            allOn.setRect(0, 110, 59, 16);
            add(allOn);

            RedButton allOff = new RedButton(Messages.get(this, "all_off")) {
                @Override
                protected void onClick() {
                    allChiOff();
                }
            };
            allOff.setRect(61, 110, 59, 16);
            add(allOff);
            RedButton posOn = new RedButton(Messages.get(this, "attach_to")) {
                @Override
                protected void onClick() {
                    attachTo();
                }
            };
            posOn.setRect(0, 128, 59, 16);
            add(posOn);

            RedButton negOn = new RedButton(Messages.get(this, "detach_from")) {
                @Override
                protected void onClick() {
                    detachFrom();
                }
            };
            negOn.setRect(61, 128, 59, 16);
            add(negOn);
        }

        private void allChiOn(){
            chiOn = 0L;
            int len = chiPrio.size();
            chiOn = (1L<<len) - 1L;
            updateCheckBox();
        }

        private void allChiOff(){
            chiOn = 0L;
            updateCheckBox();
        }
        private void attachTo(){
            int max_size = checkBoxes.size();
            long chi = 0L;
            for(int i=0;i<max_size;i++){
                chi += (checkBoxes.get(i).checked()?1L<<i:0);
            }
            setChimera(chi);
            onBackPressed();
        }
        private void detachFrom(){
            int max_size = checkBoxes.size();
            long chi = 0L;
            for(int i=0;i<max_size;i++){
                chi += (checkBoxes.get(i).checked()?1L<<i:0);
            }
            removeChimera(chi);
            onBackPressed();
        }
        @Override
        public void onBackPressed() {
            super.onBackPressed();
        }

        private void updateCheckBox(){
            int i=0;
            for(CheckBox cb: checkBoxes){
                cb.checked((chiOn&(1L<<i)) > 0 );
                ++i;
            }
        }
    }

    public void setChimera(long index){
        dispelAllChi();
        int id = 0;
        for(Class<? extends Weapon.Chimera> chi : chiPrio.keySet()){
            if(((index >> id)&1)!=0){
                if(selectedWeapon != null)selectedWeapon.chimera(Reflection.newInstance(chi));
            }
            ++id;
        }
    }
    public void removeChimera(long index){
        dispelAllChi();
        int id = 0;
        ArrayList<Class<? extends Weapon.Chimera>>  toRemove = new ArrayList<>();
        for(Class<? extends Weapon.Chimera> chi : chiPrio.keySet()){
            if(((index >> id)&1)!=0){
                if(selectedWeapon != null && selectedWeapon.hasChimera(chi)) toRemove.add(chi);
            }
            ++id;
        }
        if(!toRemove.isEmpty()){
            for(Class<? extends Weapon.Chimera> chis : toRemove){
                selectedWeapon.removeChimera(chis);
            }
        }
    }
    public void dispelAllChi(){
        chiList.clear();
        chiOn = 0L;
    }

    public void chimera( Weapon.Chimera chi ) {
        if(chi!=null){
            if(!chiList.contains(chi)){
                chiList.add(chi);
                int i = 0;
                for(Class<? extends Weapon.Chimera> e: chiPrio.keySet()){
                    if (chi.getClass() == e){
                        chiOn += 1L<<i;
                        break;
                    }
                    ++i;
                }
            }
        }
        return;
    }
}
