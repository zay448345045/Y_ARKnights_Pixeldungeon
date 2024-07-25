package com.shatteredpixel.shatteredpixeldungeon.items.Skill;

import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;

abstract public class Skill extends Item {
    abstract public void doSkill();
    public boolean bySkillBook = true;
    public String desc_wnd() {
        return Messages.get(this, "desc_wnd");
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    public void activatedBySkillbook(boolean a){ bySkillBook = a;}
}
