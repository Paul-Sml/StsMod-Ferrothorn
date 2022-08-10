package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.stances.NeutralStance;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static ferrothorn.FerrothornMod.makeCardPath;

public class Defog extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(Defog.class.getSimpleName());
    public static final String IMG = makeCardPath("Defog.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;  //
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 0;

    public Defog() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 2;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.upgraded) {
            this.addToBot(new ChangeStanceAction(NeutralStance.STANCE_ID));

            AbstractPower powPlayer = p.getPower(StrengthPower.POWER_ID);
            if (powPlayer != null)
                this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, -powPlayer.amount), -powPlayer.amount));
        }

        for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!q.isDeadOrEscaped()) {
                if (this.upgraded) {
                    AbstractPower pow2 = q.getPower(ArtifactPower.POWER_ID);
                    if (pow2 != null)
                        this.addToBot(new RemoveSpecificPowerAction(q, q, pow2));

                    //this.addToBot(new ApplyPowerAction(q, p, new ArtifactPower(q, -pow2.amount), -pow2.amount));
                }
                AbstractPower pow = q.getPower(StrengthPower.POWER_ID);
                if (pow != null)
                    this.addToBot(new RemoveSpecificPowerAction(q, q, pow));

            }
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
