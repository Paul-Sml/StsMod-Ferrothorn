package ferrothorn.cards;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.SpotWeakness;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;
import ferrothorn.powers.Scales;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static ferrothorn.FerrothornMod.makeCardPath;

public class RagePowder extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(RagePowder.class.getSimpleName());
    public static final String IMG = makeCardPath("RagePowder.png");

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;  //
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 2;

    public RagePowder() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = 27;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int attackDamage = m.getIntentDmg();
        int hits = ReflectionHacks.getPrivate(m, AbstractMonster.class, "isMultiDmg") ? ReflectionHacks.getPrivate(m, AbstractMonster.class, "intentMultiAmt") : 1;

        this.addToBot(new GainBlockAction(p, this.block));

        DamageInfo info = new DamageInfo(m, attackDamage, DamageInfo.DamageType.NORMAL);

        for (int i = 0; i < hits; i++)
            this.addToBot(new DamageAction(p, info, AbstractGameAction.AttackEffect.NONE));

        if (!upgraded) {
            for (int i = 0; i < hits; i++)
                this.addToBot(new DamageAction(m, info, AbstractGameAction.AttackEffect.NONE));
        } else {
            for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!q.isDying && !q.isDead) {
                    for (int i = 0; i < hits; i++)
                        this.addToBot(new DamageAction(q, info, AbstractGameAction.AttackEffect.NONE));
                }
            }
        }

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
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);

        if (m != null)
            if (m.getIntentBaseDmg() > 0) {
                return canUse;
            }

        this.cantUseMessage = languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
        return false;
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeBlock(3);
            this.rawDescription = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
            initializeDescription();
            upgradeName();
        }
    }
}
