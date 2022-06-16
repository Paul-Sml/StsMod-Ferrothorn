package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Normality;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;
import ferrothorn.powers.Stamina;

import static ferrothorn.FerrothornMod.makeCardPath;

public class AerialAce extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(AerialAce.class.getSimpleName());
    public static final String IMG = makeCardPath("AerialAce.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 0;

    public AerialAce() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void applyPowers() {}

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        damage = baseDamage;
    }

    @Override
    public boolean hasEnoughEnergy() {
        if (EnergyPanel.totalCount < this.costForTurn && !this.freeToPlay() && !this.isInAutoplay) {
            this.cantUseMessage = TEXT[11];
            return false;
        }
        return true;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return true;
    }

    @Override
    public boolean canPlay(AbstractCard card) {
        if (card == this)
            return true;
        return super.canPlay(card);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }
}
