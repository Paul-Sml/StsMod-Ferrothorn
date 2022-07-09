package ferrothorn.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import ferrothorn.FerrothornMod;
import ferrothorn.util.TextureLoader;

public class EndurePower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = FerrothornMod.makeID("EndurePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
//    private static final Texture tex84 = TextureLoader.getTexture(FerrothornMod.makePowerPath("LeechSeed84.png"));
//    private static final Texture tex32 = TextureLoader.getTexture(FerrothornMod.makePowerPath("LeechSeed32.png"));

    public EndurePower(final AbstractCreature owner, final AbstractCreature source, int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;


        //loadRegion(CurlUpPower.POWER_ID);
        loadRegion("closeUp");

        updateDescription();
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if (damageAmount < this.owner.currentHealth && damageAmount > 0) {
            this.flash();
            this.addToTop(new GainBlockAction(this.owner, this.owner, this.amount));
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new BlurPower(this.owner, 1), 1));
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
        return super.onLoseHp(damageAmount);
    }

    @Override
    public AbstractPower makeCopy() {
        return new EndurePower(owner, source, amount);
    }
}

