package ferrothorn.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import ferrothorn.FerrothornMod;
import ferrothorn.util.TextureLoader;

public class CounterPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = FerrothornMod.makeID("CounterPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(FerrothornMod.makePowerPath("Counter84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(FerrothornMod.makePowerPath("Counter32.png"));

    public CounterPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
    }

    @Override
    public int onAttacked(final DamageInfo info, final int damageAmount) {
        if (info.owner != null && info.owner != this.owner && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount <= 0 && info.output > 0) {
            this.flash();
            int counterDamage = info.output * this.amount;
            this.addToTop(new DamageAction(info.owner, new DamageInfo(this.owner, counterDamage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
        }
        return damageAmount;
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1)
            description = DESCRIPTIONS[0] + DESCRIPTIONS[1];
        else
            description = DESCRIPTIONS[0] + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3] + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new CounterPower(owner, source, amount);
    }
}

