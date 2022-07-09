package ferrothorn.cards;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.SashWhip;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;
import ferrothorn.powers.Scales;

import java.util.ArrayList;
import java.util.List;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static ferrothorn.FerrothornMod.makeCardPath;

public class Encore extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(Encore.class.getSimpleName());
    public static final String IMG = makeCardPath("Encore.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;  //
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private ArrayList<AbstractCard> list = AbstractDungeon.actionManager.cardsPlayedThisCombat;
    private boolean infinitePrevent = false;

    private static final int COST = 1;

    public Encore() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.cardsToPreview = checkCards();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (list.size() >= 2) {
            AbstractCard c = this.cardsToPreview;
            if (c != null) {
                AbstractCard tmp = c.makeStatEquivalentCopy();
                AbstractDungeon.player.limbo.addToBottom(tmp);
                tmp.current_x = c.current_x;
                tmp.current_y = c.current_y;
                tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                tmp.target_y = (float) Settings.HEIGHT / 2.0F;

                tmp.applyPowers();
                if (m != null) {
                    tmp.calculateCardDamage(m);
                }

                tmp.purgeOnUse = true;
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, this.costForTurn, true, true), true);
            }
        }
    }

    private void updateThing(AbstractMonster m) {
        this.cardsToPreview = checkCards();
        if (this.cardsToPreview != null) {
            this.target = this.cardsToPreview.target;
            this.baseBlock = this.cardsToPreview.baseBlock;
            this.baseDamage = this.cardsToPreview.baseDamage;
            this.baseMagicNumber = this.cardsToPreview.baseMagicNumber;
            this.cardsToPreview.applyPowers();
            if (m != null)
                this.cardsToPreview.calculateCardDamage(m);
            this.damage = this.cardsToPreview.damage;
            this.block = this.cardsToPreview.block;
            this.magicNumber = this.cardsToPreview.magicNumber;
            if (this.cardsToPreview instanceof AbstractDefaultCard) {
                this.defaultSecondMagicNumber = ((AbstractDefaultCard) this.cardsToPreview).defaultSecondMagicNumber;
            }
            ExhaustiveVariable.setBaseValue(this, ExhaustiveField.ExhaustiveFields.exhaustive.get(this.cardsToPreview));

            this.isDamageModified = this.cardsToPreview.isDamageModified;
            this.isBlockModified = this.cardsToPreview.isBlockModified;
            this.rawDescription = this.cardsToPreview.rawDescription;
            if (!upgraded)
                this.rawDescription += " NL [#F082E6]This [#F082E6]Exhausts.";
            else
                this.rawDescription += " NL [#F082E6]This [#F082E6]doesn't [#F082E6]Exhaust.";
            initializeDescription();
        }

    }

    private AbstractCard checkCards() {
        for (int i = 0; i < list.size(); i++) {
            AbstractCard c = list.get(list.size() - i - 1);
            if (!c.cardID.equals(this.cardID)) {
                if (!infinitePrevent) {
                    infinitePrevent = true;
                    c = c.makeStatEquivalentCopy();
                    infinitePrevent = false;
                }
                return c;
            }
        }
        return null;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (checkCards() != null)
            return super.canUse(p, m);
        else
            return false;
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (!list.isEmpty())
            this.updateThing(null);
    }

    @Override
    public void triggerWhenDrawn() {
        if (!list.isEmpty())
            this.updateThing( null);
    }

    @Override
    public void applyPowers() {
        if (!list.isEmpty())
            this.updateThing( null);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (!list.isEmpty())
            this.updateThing(mo);
    }

    @Override
    public void triggerOnGlowCheck() {
        if (!list.isEmpty())
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        else
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> thing = new ArrayList<TooltipInfo>();
        thing.add(new TooltipInfo(cardStrings.NAME, cardStrings.EXTENDED_DESCRIPTION[0]));
        thing.add(new TooltipInfo(cardStrings.NAME, cardStrings.EXTENDED_DESCRIPTION[1]));
        return thing;
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            this.exhaust = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            upgradeName();
        }
    }
}
