package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;
import ferrothorn.stances.HarshSunlight;
import ferrothorn.stances.Rain;
import ferrothorn.stances.Sandstorm;

import static ferrothorn.FerrothornMod.makeCardPath;

public class Synthesis extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(Synthesis.class.getSimpleName());
    public static final String IMG = makeCardPath("Synthesis.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 1;

    public Synthesis() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = 7;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        this.addToBot(new GainBlockAction(p, this.block));

        if (p.stance.ID.equals(Sandstorm.STANCE_ID))
            this.addToBot(new ApplyPowerAction(p, p, new MetallicizePower(p, 1), 1));

        else if (p.stance.ID.equals(Rain.STANCE_ID)) {
            AbstractCard c = new Seed();
            c.upgrade();
            this.addToBot(new MakeTempCardInHandAction(c, 1));
        }

        else if (p.stance.ID.equals(HarshSunlight.STANCE_ID))
            this.addToBot(new GainEnergyAction(1));

    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        AbstractPlayer p = AbstractDungeon.player;
        if (p.stance.ID.equals(Sandstorm.STANCE_ID) || p.stance.ID.equals(Rain.STANCE_ID) || p.stance.ID.equals(HarshSunlight.STANCE_ID))
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }
}
