package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;
import ferrothorn.stances.HarshSunlight;
import ferrothorn.stances.Rain;
import ferrothorn.stances.Sandstorm;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;
import static ferrothorn.FerrothornMod.makeCardPath;

public class Growth extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(Growth.class.getSimpleName());
    public static final String IMG = makeCardPath("Growth.png");

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 1;

    public Growth() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        if (p.stance.ID.equals(Sandstorm.STANCE_ID)) {
            for (int i = 0; i < 2; i++) {
                this.addToBot(new ApplyPowerAction(p, p, new MetallicizePower(p, 1), 1));
            }
        } else if (p.stance.ID.equals(Rain.STANCE_ID)) {
            AbstractCard c = new Seed();
            c.upgrade();
            for (int i = 0; i < 2; i++) {
                this.addToBot(new MakeTempCardInHandAction(c, 1));
            }
        } else if (p.stance.ID.equals(HarshSunlight.STANCE_ID)) {
            for (int i = 0; i < 2; i++) {
                this.addToBot(new GainEnergyAction(1));
            }
        } else if (!this.upgraded) {
            int i = cardRandomRng.random(0, 2);
            if (i == 0)
                this.addToBot(new ChangeStanceAction(new ferrothorn.stances.Sandstorm()));
            else if (i == 1)
                this.addToBot(new ChangeStanceAction(new ferrothorn.stances.Rain()));
            else
                this.addToBot(new ChangeStanceAction(new ferrothorn.stances.HarshSunlight()));
        } else {
            ArrayList<AbstractCard> stanceChoices = new ArrayList();
            AbstractCard ss = new ferrothorn.cards.Sandstorm();
            AbstractCard rd = new RainDance();
            AbstractCard sd = new SunnyDay();
            ss.cost = -2;
            rd.cost = -2;
            sd.cost = -2;
            stanceChoices.add(ss);
            stanceChoices.add(rd);
            stanceChoices.add(sd);
            this.addToBot(new ChooseOneAction(stanceChoices));
        }


    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
