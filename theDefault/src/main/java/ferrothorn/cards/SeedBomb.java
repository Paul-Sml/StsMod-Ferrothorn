package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static ferrothorn.FerrothornMod.makeCardPath;

public class SeedBomb extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(SeedBomb.class.getSimpleName());
    public static final String IMG = makeCardPath("SeedBomb.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 1;

    public SeedBomb() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.tags.add(FerrothornMod.SEED);
        AbstractCard c = new Seed();
        c.upgrade();
        this.cardsToPreview = c;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard card = this.cardsToPreview;
        if (!this.upgraded)
            card.baseDamage *= this.magicNumber;

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card, 1));

        if (this.upgraded) {
            int i = this.magicNumber;
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    for (AbstractCard c : AbstractDungeon.player.hand.group)
                        if (c.hasTag(FerrothornMod.SEED)) {
                            c.baseDamage *= i;
                        }
                    isDone = true;
                }
            });
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            //upgradeMagicNumber(1);
            this.rawDescription = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
