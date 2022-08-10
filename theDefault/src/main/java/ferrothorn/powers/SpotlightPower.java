package ferrothorn.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import ferrothorn.FerrothornMod;
import ferrothorn.actions.PowerAboveCreatureAction;
import ferrothorn.util.TextureLoader;

public class SpotlightPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = FerrothornMod.makeID("SpotlightPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(FerrothornMod.makePowerPath("Spotlight84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(FerrothornMod.makePowerPath("Spotlight32.png"));

    public SpotlightPower(final AbstractCreature owner, final AbstractCreature source, int amount) {
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

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (this.amount == 1)
            description = DESCRIPTIONS[0] + DESCRIPTIONS[1];
        else if (this.amount == 2)
            description = DESCRIPTIONS[0] + DESCRIPTIONS[2];
        else
            description = DESCRIPTIONS[0] + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[4];
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        AbstractPlayer p = AbstractDungeon.player;

        if (this.amount > 0) {
            AbstractMonster monster = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng);

            if (!monster.isDeadOrEscaped()) {
                DamageInfo info = new DamageInfo(monster, 0, DamageInfo.DamageType.NORMAL);
                this.addToBot(new PowerAboveCreatureAction(monster, this));

                for (int i = 0; i < this.amount; i++) {
                    this.addToBot(new DamageAction(p, info, AbstractGameAction.AttackEffect.NONE));
                }



                this.addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        if (p.hasPower(Scales.POWER_ID))
                            p.getPower(Scales.POWER_ID).onSpecificTrigger();
                        isDone = true;
                    }
            });

            }
        }

        super.atStartOfTurn();
    }

    @Override
    public AbstractPower makeCopy() {
        return new SpotlightPower(owner, source, amount);
    }
}

