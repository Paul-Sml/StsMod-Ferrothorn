package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;
import ferrothorn.powers.Stamina;
import ferrothorn.stances.Sandstorm;

import static ferrothorn.FerrothornMod.makeCardPath;

public class VenomDrench extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(VenomDrench.class.getSimpleName());
    public static final String IMG = makeCardPath("VenomDrench.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 1;

    public VenomDrench() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = 7;
        this.magicNumber = this.baseMagicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, p, block));

        for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!q.isDying && !q.isDead && q.hasPower(PoisonPower.POWER_ID)) {
                this.addToBot(new ApplyPowerAction(p, p, new Stamina(p, p, this.magicNumber), this.magicNumber));
                break;
            }
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!q.isDying && !q.isDead && q.hasPower(PoisonPower.POWER_ID)) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                break;
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(2);
            upgradeMagicNumber(2);
        }
    }
}
