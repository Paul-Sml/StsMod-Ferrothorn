package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.defect.ReinforcedBodyAction;
import com.megacrit.cardcrawl.actions.unique.WhirlwindAction;
import com.megacrit.cardcrawl.cards.red.Whirlwind;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ferrothorn.FerrothornMod;

import static ferrothorn.FerrothornMod.makeCardPath;

public class SpitUp extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(SpitUp.class.getSimpleName());
    public static final String IMG = makeCardPath("Seed.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -1;

    private static final int DAMAGE = 5;
    private static final int UPGRADE_PLUS_DMG = 2;

    public SpitUp(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        baseDamage = DAMAGE;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int i = AbstractDungeon.actionManager.cardQueue.get(0).energyOnUse;
        this.addToBot(new WhirlwindAction(p, this.multiDamage, this.damageTypeForTurn, this.freeToPlayOnce, i));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}
