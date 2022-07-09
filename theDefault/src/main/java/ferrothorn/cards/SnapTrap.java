package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;
import ferrothorn.powers.LeechSeedPower;

import static ferrothorn.FerrothornMod.makeCardPath;

public class SnapTrap extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(SnapTrap.class.getSimpleName());
    public static final String IMG = makeCardPath("SnapTrap.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;  //
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 2;
    private static final int BLOCK = 9;

    public SnapTrap() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 2;
        this.tags.add(FerrothornMod.SEED);
        this.tags.add(CardTags.HEALING);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, this.block));
        this.addToBot(new DrawCardAction(this.magicNumber));
        this.addToBot(new ApplyPowerAction(m, p, new LeechSeedPower(m, p, this.magicNumber), this.magicNumber));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(1);
            upgradeMagicNumber(1);
        }
    }
}
