//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ferrothorn.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import ferrothorn.powers.RegenFerrothornPower;

public class RegenFerrothornAction extends AbstractGameAction {
    public RegenFerrothornAction(AbstractCreature target, int amount) {
        this.target = target;
        this.amount = amount;
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().phase != RoomPhase.COMBAT) {
            this.isDone = true;
        } else {
            if (this.duration == Settings.ACTION_DUR_FAST) {
                if (this.target.currentHealth > 0) {
                    this.target.tint.color = Color.CHARTREUSE.cpy();
                    this.target.tint.changeColor(Color.WHITE.cpy());
                    this.target.heal(this.amount, true);
                }


                    AbstractPower p = this.target.getPower(RegenFerrothornPower.POWER_ID);
                    if (p != null) {
                        --p.amount;
                        if (p.amount == 0) {
                            this.target.powers.remove(p);
                        } else {
                            p.updateDescription();
                        }
                    }

            }

            this.tickDuration();
        }
    }
}
