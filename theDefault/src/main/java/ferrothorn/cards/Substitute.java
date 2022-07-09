package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Hemokinesis;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;
import ferrothorn.powers.LeechSeedPower;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static ferrothorn.FerrothornMod.makeCardPath;

public class Substitute extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(Substitute.class.getSimpleName());
    public static final String IMG = makeCardPath("Substitute.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;  //
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 0;
    //private static final int UPGRADED_COST = 0;

    public Substitute() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 7;
        this.isInnate = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new LoseHPAction(p, p, this.magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new BufferPower(p, 1), 1));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(-3);
            this.isInnate = true;
        }
    }
}
