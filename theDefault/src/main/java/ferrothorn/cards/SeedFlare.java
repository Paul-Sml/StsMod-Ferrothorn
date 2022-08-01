package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;
import ferrothorn.powers.LeechSeedPower;

import java.util.ArrayList;

import static ferrothorn.FerrothornMod.makeCardPath;

public class SeedFlare extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(SeedFlare.class.getSimpleName());
    public static final String IMG = makeCardPath("SeedFlare.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;  //
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 2;
    private static final int DAMAGE = 10;

    public SeedFlare() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = 1;
        this.tags.add(FerrothornMod.SEED);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        this.addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
    }

    @Override
    public void applyPowers() {
        this.magicNumber = this.baseMagicNumber;
        AbstractPlayer p = AbstractDungeon.player;
        if (!p.hand.isEmpty()) {
            for (AbstractCard c : p.hand.group) {
                if (c.hasTag(FerrothornMod.SEED)) {
                    this.magicNumber++;
                }
            }
        }
        super.applyPowers();
        this.isMagicNumberModified = this.isMagicNumberModified || (baseMagicNumber != magicNumber);
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeMagicNumber(1);
        }
    }
}
