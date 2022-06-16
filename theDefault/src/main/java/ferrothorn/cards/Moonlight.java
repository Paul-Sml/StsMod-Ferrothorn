package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static ferrothorn.FerrothornMod.makeCardPath;

public class Moonlight extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(Moonlight.class.getSimpleName());
    public static final String IMG = makeCardPath("Moonlight.png");

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;  //
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 1;

    public Moonlight() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.isInnate = false;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> stanceChoices = new ArrayList();
        AbstractCard ss = new Sandstorm();
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

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.isInnate = true;
            this.rawDescription = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
