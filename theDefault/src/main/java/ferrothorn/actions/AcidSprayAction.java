package ferrothorn.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import ferrothorn.powers.Stamina;

import java.util.Iterator;

public class AcidSprayAction extends AbstractGameAction {
    public int[] damage;

    public AcidSprayAction(AbstractCreature source, int[] amount, DamageInfo.DamageType type, AttackEffect effect) {
        this.setValues((AbstractCreature)null, source, amount[0]);
        this.damage = amount;
        this.actionType = ActionType.DAMAGE;
        this.damageType = type;
        this.attackEffect = effect;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        int StaminaAmount = 0;
        AbstractPlayer player = AbstractDungeon.player;

        Iterator var5 = player.powers.iterator();

        while(var5.hasNext()) {
            AbstractPower p = (AbstractPower)var5.next();
            p.onDamageAllEnemies(this.damage);
        }

        int i;

        for(i = 0; i < AbstractDungeon.getCurrRoom().monsters.monsters.size(); ++i) {
            AbstractMonster target = (AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
            if (!target.isDying && target.currentHealth > 0 && !target.isEscaping) {
                target.damage(new DamageInfo(this.source, this.damage[i], this.damageType));
                if (target.lastDamageTaken > 0) {
                    StaminaAmount += target.lastDamageTaken;
                }
            }
        }
        if (StaminaAmount > 0)
            this.addToBot(new ApplyPowerAction(player, player, new Stamina(player, player, StaminaAmount), StaminaAmount));


        this.tickDuration();
        this.isDone = true;
    }
}
