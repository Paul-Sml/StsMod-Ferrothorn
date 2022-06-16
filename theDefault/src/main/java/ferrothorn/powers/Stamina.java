package ferrothorn.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.RitualDagger;
import com.megacrit.cardcrawl.cards.green.DeadlyPoison;
import com.megacrit.cardcrawl.cards.purple.Wish;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import ferrothorn.FerrothornMod;
import ferrothorn.cards.ShoreUp;
import ferrothorn.stances.Sandstorm;
import ferrothorn.util.TextureLoader;

import java.util.HashSet;

public class Stamina extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = FerrothornMod.makeID("Stamina");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(FerrothornMod.makePowerPath("Stamina84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(FerrothornMod.makePowerPath("Stamina32.png"));

    public Stamina(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = true  ;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public float modifyBlock(float blockAmount, AbstractCard card) {
        if (card.baseBlock > 0)
            return blockAmount + amount;
        else
            return blockAmount;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.baseBlock > 0 && !card.cardID.equals(RitualDagger.ID) && !card.cardID.equals(Wish.ID) && (AbstractDungeon.player.stance.ID.equals(Sandstorm.STANCE_ID) || !card.cardID.equals(ShoreUp.ID))) {
            flash();
            addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
        }
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new Stamina(owner, source, amount);
    }
}

