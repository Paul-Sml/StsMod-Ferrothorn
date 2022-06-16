package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.FiendFire;
import com.megacrit.cardcrawl.cards.red.SecondWind;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static ferrothorn.FerrothornMod.makeCardPath;

public class WorrySeed extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(WorrySeed.class.getSimpleName());
    public static final String IMG = makeCardPath("WorrySeed.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 1;

    public WorrySeed() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 2;
        this.tags.add(FerrothornMod.SEED);
        this.cardsToPreview = new Insomnia();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new MakeTempCardInHandAction(new Seed(), this.magicNumber));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (!p.hand.isEmpty()) {
                    int j = 0;
                    ArrayList<AbstractCard> cardsToExhaust = new ArrayList();
                    for (AbstractCard c : p.hand.group) {
                        if (c.hasTag(FerrothornMod.SEED)) {
                            cardsToExhaust.add(c);
                            ++j;
                        }
                    }
                    for (AbstractCard c : cardsToExhaust){
                        this.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                        this.addToBot(new MakeTempCardInHandAction(new Insomnia()));
                    }
                }
                isDone = true;
            }
        });

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
