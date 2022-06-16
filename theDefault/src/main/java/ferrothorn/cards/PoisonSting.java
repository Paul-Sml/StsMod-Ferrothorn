package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.Wallop;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ferrothorn.FerrothornMod;
import ferrothorn.actions.PoisonStingAction;
import ferrothorn.characters.Ferrothorn;
import ferrothorn.powers.Stamina;

import static ferrothorn.FerrothornMod.makeCardPath;

public class PoisonSting extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(PoisonSting.class.getSimpleName());
    public static final String IMG = makeCardPath("PoisonSting.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 1;

    public PoisonSting() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.baseDamage = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new PoisonStingAction(m ,new DamageInfo(p, this.damage, this.damageTypeForTurn)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(1);
        }
    }
}
