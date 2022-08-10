package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Eruption;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;
import ferrothorn.powers.LeechSeedPower;
import ferrothorn.stances.HarshSunlight;
import ferrothorn.stances.Rain;
import ferrothorn.stances.Sandstorm;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static ferrothorn.FerrothornMod.makeCardPath;

public class RainDance extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(RainDance.class.getSimpleName());
    public static final String IMG = makeCardPath("RainDance.png");

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;  //
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    public RainDance() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        AbstractCard c = new Seed();
        c.upgrade();
        this.cardsToPreview = c;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        if (p.stance.ID.equals(Sandstorm.STANCE_ID))
            this.addToBot(new ApplyPowerAction(p, p, new MetallicizePower(p, 1), 1));

        else if (p.stance.ID.equals(Rain.STANCE_ID)) {
            AbstractCard c = new Seed();
            c.upgrade();
            this.addToBot(new MakeTempCardInHandAction(c, 1));
        }

        else if (p.stance.ID.equals(HarshSunlight.STANCE_ID))
            this.addToBot(new GainEnergyAction(1));


        this.addToBot(new ChangeStanceAction(new ferrothorn.stances.Rain()));
    }

    @Override
    public void onChoseThisOption() {
        this.addToBot(new ChangeStanceAction(new ferrothorn.stances.Rain()));
        if (this.magicNumber == -2) {
            AbstractPlayer p = AbstractDungeon.player;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MetallicizePower(p, 1), 1));
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        }
    }



    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            this.exhaust = true;
            this.rawDescription = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
