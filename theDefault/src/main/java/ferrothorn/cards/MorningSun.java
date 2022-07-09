package ferrothorn.cards;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;
import ferrothorn.powers.Scales;
import ferrothorn.powers.StickyWebPower;
import ferrothorn.powers.ToxicSpikesPower;
import ferrothorn.stances.HarshSunlight;
import ferrothorn.stances.Rain;
import ferrothorn.stances.Sandstorm;

import java.util.ArrayList;
import java.util.List;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static ferrothorn.FerrothornMod.makeCardPath;

public class MorningSun extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(MorningSun.class.getSimpleName());
    public static final String IMG = makeCardPath("MorningSun.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 1;

    public MorningSun() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        if (p.stance.ID.equals(Sandstorm.STANCE_ID))
            this.addToBot(new ApplyPowerAction(p, p, new Scales(p, p, 4), 4));

        else if (p.stance.ID.equals(Rain.STANCE_ID))
            this.addToBot(new ApplyPowerAction(p, p, new ToxicSpikesPower(p, p, 2), 2));

        else if (p.stance.ID.equals(HarshSunlight.STANCE_ID))
            this.addToBot(new ApplyPowerAction(p, p, new ThornsPower(p, 3), 3));

        else
            this.addToBot(new ApplyPowerAction(p, p, new StickyWebPower(p, p, 1), 1));

    }

    @Override
    public void triggerWhenDrawn() {
        this.updateStuff();
    }

    @Override
    public void switchedStance() {
        this.updateStuff();
    }

    private void updateStuff() {
        AbstractPlayer p = AbstractDungeon.player;
        String text = cardStrings.DESCRIPTION;
        if (this.upgraded)
            text = cardStrings.UPGRADE_DESCRIPTION;

        if (p != null && p.stance != null && (p.stance.ID.equals(Sandstorm.STANCE_ID) || p.stance.ID.equals(Rain.STANCE_ID) || p.stance.ID.equals(HarshSunlight.STANCE_ID))) {
            if (p.stance.ID.equals(Sandstorm.STANCE_ID)) {//Sandstorm
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] + text;
                initializeDescription();
            }
            else if (p.stance.ID.equals(Rain.STANCE_ID)) {//Rain
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1] + text;
                initializeDescription();
            }
            else {//Harsh Sunlight
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[2] + text;
                initializeDescription();
            }

        } else {//No Weather
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[3]  + text;
            initializeDescription();
        }
    }

    @Override
    public void applyPowers() {
        updateStuff();
        super.applyPowers();
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> thing = new ArrayList<TooltipInfo>();
        thing.add(new TooltipInfo(cardStrings.EXTENDED_DESCRIPTION[4], cardStrings.EXTENDED_DESCRIPTION[5]));
        return thing;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.exhaust = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
