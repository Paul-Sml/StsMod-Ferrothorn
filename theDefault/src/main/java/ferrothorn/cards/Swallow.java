package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ReinforcedBodyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.ReinforcedBody;
import com.megacrit.cardcrawl.cards.purple.Collect;
import com.megacrit.cardcrawl.cards.red.Whirlwind;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;

import static ferrothorn.FerrothornMod.FERROTHORN_GREEN;
import static ferrothorn.FerrothornMod.makeCardPath;

public class Swallow extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(Swallow.class.getSimpleName());
    public static final String IMG = makeCardPath("Swallow.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = -1;

    private static final int BLOCK = 8;
    private static final int UPGRADE_PLUS_BLOCK = 3;

    public Swallow(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        baseBlock = BLOCK;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ReinforcedBodyAction(p, this.block, this.freeToPlayOnce, this.energyOnUse));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
        }
    }
}
