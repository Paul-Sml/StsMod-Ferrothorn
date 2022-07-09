package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ferrothorn.FerrothornMod;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static ferrothorn.FerrothornMod.makeCardPath;

public class Insomnia extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(Insomnia.class.getSimpleName());
    public static final String IMG = makeCardPath("Insomnia.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 0;

    public Insomnia(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 1;
        this.baseBlock = 1;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, this.block));
        this.addToBot(new DrawCardAction(this.magicNumber));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(1);
            upgradeMagicNumber(1);
            this.rawDescription = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
