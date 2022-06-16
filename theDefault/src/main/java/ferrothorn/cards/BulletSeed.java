package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.BladeDance;
import com.megacrit.cardcrawl.cards.red.InfernalBlade;
import com.megacrit.cardcrawl.cards.red.TrueGrit;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;
import static ferrothorn.FerrothornMod.makeCardPath;

public class BulletSeed extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(BulletSeed.class.getSimpleName());
    public static final String IMG = makeCardPath("BulletSeed.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 1;

    public BulletSeed() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new Seed();
        this.tags.add(FerrothornMod.SEED);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int i = cardRandomRng.random(this.magicNumber, 5) ;
        this.addToBot(new MakeTempCardInHandAction(new Seed(), i));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }
}
