package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;
import ferrothorn.powers.LeechSeedPower;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static ferrothorn.FerrothornMod.makeCardPath;

public class LeechSeed extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(LeechSeed.class.getSimpleName());
    public static final String IMG = makeCardPath("LeechSeed.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;  //
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 1;
    //private static final int UPGRADED_COST = 0;

    public LeechSeed() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 2;
//        this.isInnate = false;
        this.tags.add(FerrothornMod.SEED);
        this.tags.add(CardTags.HEALING);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, p, new LeechSeedPower(m, p, this.magicNumber), this.magicNumber));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            //upgradeBaseCost(UPGRADED_COST);
//            this.rawDescription = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
//            initializeDescription();
//            this.isInnate = true;
        }
    }
}
