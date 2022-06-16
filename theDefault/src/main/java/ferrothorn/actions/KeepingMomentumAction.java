//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ferrothorn.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class KeepingMomentumAction extends AbstractGameAction {

    public AbstractCard card;

    public KeepingMomentumAction(AbstractCard card) {
        this.card = card;
    }
    public void update() {
        if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
            if (AbstractDungeon.player.discardPile.contains(this.card)) {
                AbstractDungeon.player.hand.addToHand(this.card);
                this.card.unhover();
                this.card.setAngle(0.0F, true);
                this.card.lighten(false);
                this.card.drawScale = 0.12F;
                this.card.targetDrawScale = 0.75F;
                this.card.applyPowers();
                AbstractDungeon.player.discardPile.removeCard(this.card);
            } else if (AbstractDungeon.player.drawPile.contains(this.card)) {
                AbstractDungeon.player.hand.addToHand(this.card);
                this.card.unhover();
                this.card.setAngle(0.0F, true);
                this.card.lighten(false);
                this.card.drawScale = 0.12F;
                this.card.targetDrawScale = 0.75F;
                this.card.applyPowers();
                AbstractDungeon.player.drawPile.removeCard(this.card);
            } else if (AbstractDungeon.player.exhaustPile.group.contains(this.card)) {
                AbstractDungeon.player.hand.addToHand(this.card);
                this.card.stopGlowing();
                this.card.unhover();
                this.card.unfadeOut();
                this.card.setAngle(0.0F, true);
                this.card.lighten(false);
                this.card.drawScale = 0.12F;
                this.card.targetDrawScale = 0.75F;
                this.card.applyPowers();
                AbstractDungeon.player.exhaustPile.removeCard(this.card);
            }
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.applyPowers();
            System.out.println(1);
        }
        this.tickDuration();
        this.isDone = true;
    }
 }


