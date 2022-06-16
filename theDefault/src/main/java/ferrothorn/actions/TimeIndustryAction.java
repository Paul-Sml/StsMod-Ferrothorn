//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ferrothorn.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ferrothorn.FerrothornMod;

import java.util.ArrayList;

public class TimeIndustryAction extends AbstractGameAction {

    AbstractMonster t;
    public TimeIndustryAction(AbstractMonster target) {
        t = target;
    }

    public void update() {
        ArrayList<String> cardIDsPlayed = new ArrayList<>();
        this.tickDuration();
        this.isDone = true;
    }
}
