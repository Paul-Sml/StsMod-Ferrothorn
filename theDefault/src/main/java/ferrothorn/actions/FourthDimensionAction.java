//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ferrothorn.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FourthDimensionAction extends AbstractGameAction {

    public FourthDimensionAction() {

    }

    public void update() {
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
//            if (!c.cardID.equals(FourthDimension.ID))
//                this.addToBot(new MakeTempCardInHandAction(c, 1));
        }

        this.tickDuration();
        this.isDone = true;
    }
}
