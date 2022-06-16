package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ferrothorn.FerrothornMod;

import java.util.ArrayList;

import static ferrothorn.FerrothornMod.makeCardPath;

public class SpitUpSwallow extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(SpitUpSwallow.class.getSimpleName());
    public static final String IMG = makeCardPath("SpitUpSwallow.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -1;

    private static final int DAMAGE = 5;
    private static final int UPGRADE_PLUS_DMG = 2;

    private static final int BLOCK = 7;
    private static final int UPGRADE_PLUS_BLOCK = 3;

    public SpitUpSwallow(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        this.baseDamage = DAMAGE;
        this.baseBlock = BLOCK;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> stanceChoices = new ArrayList();
        AbstractCard one = new SpitUp();
        if (upgraded)
            one.upgrade();
        stanceChoices.add(one);
        AbstractCard two = new Swallow();
        if (upgraded)
            two.upgrade();
        stanceChoices.add(two);
        this.addToBot(new ChooseOneAction(stanceChoices));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
        }
    }
}
