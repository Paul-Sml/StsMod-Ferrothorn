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
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;
import ferrothorn.powers.LeechSeedPower;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static ferrothorn.FerrothornMod.makeCardPath;

public class Sandstorm extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(Sandstorm.class.getSimpleName());
    public static final String IMG = makeCardPath("Sandstorm.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;  //
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    public Sandstorm() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ChangeStanceAction(new ferrothorn.stances.Sandstorm()));
    }

    public void onChoseThisOption() {
        this.addToBot(new ChangeStanceAction(new ferrothorn.stances.Sandstorm()));
        if (this.magicNumber == -2) {
            AbstractCard c = new Seed();
            c.upgrade();
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, 1));
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
        }
    }
}
