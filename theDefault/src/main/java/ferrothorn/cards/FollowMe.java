package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;
import ferrothorn.powers.Scales;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static ferrothorn.FerrothornMod.makeCardPath;

public class FollowMe extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(FollowMe.class.getSimpleName());
    public static final String IMG = makeCardPath("FollowMe.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;  //
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 1;

    public FollowMe() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = 10;
        this.magicNumber = this.baseMagicNumber = 4;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        this.addToBot(new GainBlockAction(p, this.block));

        for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!q.isDying && !q.isDead) {
                DamageInfo i = new DamageInfo(q, this.magicNumber, DamageInfo.DamageType.NORMAL);
                this.addToBot(new DamageAction(p, i, AbstractGameAction.AttackEffect.NONE));
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

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(-2);
        }
    }
}
