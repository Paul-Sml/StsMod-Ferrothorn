package ferrothorn.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import ferrothorn.vfx.PowerAboveCreatureEffect;

public class PowerAboveCreatureAction extends AbstractGameAction {
    private boolean used = false;
    private AbstractPower power;

    public PowerAboveCreatureAction(AbstractCreature source, AbstractPower power) {
        this.setValues(source, source);
        this.power = power;
        this.actionType = AbstractGameAction.ActionType.TEXT;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        if (!this.used) {
            AbstractDungeon.effectList.add(new PowerAboveCreatureEffect(this.source.hb.cX - this.source.animX, this.source.hb.cY + this.source.hb.height / 2.0F - this.source.animY, this.power));
            this.used = true;
        }

        this.tickDuration();
    }
}
