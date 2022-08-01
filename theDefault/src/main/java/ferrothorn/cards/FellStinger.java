package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.FeedAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import ferrothorn.FerrothornMod;
import ferrothorn.actions.FellStingerAction;
import ferrothorn.characters.Ferrothorn;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static ferrothorn.FerrothornMod.makeCardPath;

public class FellStinger extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(FellStinger.class.getSimpleName());
    public static final String IMG = makeCardPath("FellStinger.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 1;
    private static final int DAMAGE = 13;

    public FellStinger() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new FellStingerAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), this, this.upgraded));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
//            this.rawDescription = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
//            initializeDescription();
        }
    }
}
