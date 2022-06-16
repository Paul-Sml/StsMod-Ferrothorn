package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;
import ferrothorn.powers.KingsShieldPower;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static ferrothorn.FerrothornMod.makeCardPath;

public class ShiftGear extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(ShiftGear.class.getSimpleName());
    public static final String IMG = makeCardPath("ShiftGear.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;  //
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 1;

    private static final int BLOCK = 10;
    private static final int UPGRADE_PLUS_BLOCK = 2;

    public ShiftGear() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        this.addToBot(new GainBlockAction(p, p, block));
    }

    @Override
    public void applyPowers()
    {
        AbstractPower dexterity = AbstractDungeon.player.getPower(DexterityPower.POWER_ID);
        if (dexterity != null) {
            dexterity.amount *= magicNumber;
        }

        super.applyPowers();

        if (dexterity != null) {
            dexterity.amount /= magicNumber;
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeMagicNumber(1);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            this.rawDescription = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
