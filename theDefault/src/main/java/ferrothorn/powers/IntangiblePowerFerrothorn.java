package ferrothorn.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.IntangiblePower;

public class IntangiblePowerFerrothorn extends IntangiblePower {
    public IntangiblePowerFerrothorn(AbstractCreature owner, int turns) {
        super(owner, turns);
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        if (this.amount == 0) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "Intangible"));
        } else {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, "Intangible", 1));
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
    }
}
