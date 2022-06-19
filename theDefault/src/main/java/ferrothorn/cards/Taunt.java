package ferrothorn.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;
import ferrothorn.powers.Scales;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static ferrothorn.FerrothornMod.makeCardPath;

public class Taunt extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(Taunt.class.getSimpleName());
    public static final String IMG = makeCardPath("Taunt.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;  //
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 0;

    public Taunt() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        DamageInfo i = new DamageInfo(m, this.magicNumber, DamageInfo.DamageType.NORMAL);

        this.addToBot(new DamageAction(p, i, AbstractGameAction.AttackEffect.NONE));
        this.addToBot(new DamageAction(p, i, AbstractGameAction.AttackEffect.NONE));
        if (upgraded)
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

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(-2);
            this.rawDescription = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
