package ferrothorn.cards;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.GremlinWizard;
import com.megacrit.cardcrawl.powers.SplitPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;
import ferrothorn.stances.HarshSunlight;
import ferrothorn.stances.Rain;
import ferrothorn.stances.Sandstorm;

import java.util.ArrayList;
import java.util.List;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static com.megacrit.cardcrawl.core.CardCrawlGame.sound;
import static ferrothorn.FerrothornMod.makeCardPath;

public class WeatherBall extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(WeatherBall.class.getSimpleName());
    public static final String IMG = makeCardPath("WeatherBall.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 1;

    public WeatherBall() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 6;
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        if (p.stance.ID.equals(ferrothorn.stances.Sandstorm.STANCE_ID))
            this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        else if (p.stance.ID.equals(Rain.STANCE_ID))
            this.addToBot(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber));

        else if (p.stance.ID.equals(HarshSunlight.STANCE_ID))
            this.addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));

    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        AbstractPlayer p = AbstractDungeon.player;
        if (p.stance.ID.equals(Sandstorm.STANCE_ID) || p.stance.ID.equals(Rain.STANCE_ID) || p.stance.ID.equals(HarshSunlight.STANCE_ID))
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();

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
        if (p != null && p.stance != null && (p.stance.ID.equals(Sandstorm.STANCE_ID) || p.stance.ID.equals(Rain.STANCE_ID) || p.stance.ID.equals(HarshSunlight.STANCE_ID))) {
            if (p.stance.ID.equals(Sandstorm.STANCE_ID)) {//Sandstorm
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
                initializeDescription();
            }
            else if (p.stance.ID.equals(Rain.STANCE_ID)) {//Rain
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
                initializeDescription();
            }
            else {//Harsh Sunlight
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[2];
                initializeDescription();
            }

        } else {//No Weather
            this.rawDescription = cardStrings.DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> thing = new ArrayList<TooltipInfo>();
        thing.add(new TooltipInfo(cardStrings.EXTENDED_DESCRIPTION[3], cardStrings.EXTENDED_DESCRIPTION[4]));
        return thing;
    }

    @Override
    public void applyPowers() {
        updateStuff();
        super.applyPowers();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(1);
        }
    }
}
