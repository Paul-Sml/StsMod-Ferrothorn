package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;
import ferrothorn.powers.Scales;

import static ferrothorn.FerrothornMod.makeCardPath;

public class HeadCharge extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(HeadCharge.class.getSimpleName());
    public static final String IMG = makeCardPath("HeadCharge.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 1;

    public HeadCharge() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 9;
        this.magicNumber = this.baseMagicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        DamageInfo i = new DamageInfo(m, this.magicNumber, DamageInfo.DamageType.NORMAL);

        this.addToBot(new DamageAction(p, i, AbstractGameAction.AttackEffect.NONE));

        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (p.hasPower(Scales.POWER_ID))
                    p.getPower(Scales.POWER_ID).onSpecificTrigger();
                isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(-1);
        }
    }
}
