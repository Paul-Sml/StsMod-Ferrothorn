package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.FeedAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Feed;
import com.megacrit.cardcrawl.cards.red.PerfectedStrike;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;

import static ferrothorn.FerrothornMod.makeCardPath;

public class HeavySlam extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(HeavySlam.class.getSimpleName());
    public static final String IMG = makeCardPath("HeavySlam.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 2;

    public HeavySlam() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 11;
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            this.addToBot(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
        }
        this.addToBot(new WaitAction(0.8F));
        this.addToBot(new FeedAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), this.magicNumber));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int i = AbstractDungeon.player.maxHealth - 100;
        if (i > 0) {
            int realBaseDamage = this.baseDamage;
            this.baseDamage += i;
            super.calculateCardDamage(mo);
            this.baseDamage = realBaseDamage;
            this.isDamageModified = this.damage != this.baseDamage;
        } else
            super.calculateCardDamage(mo);
    }

    @Override
    public void applyPowers() {
        int i = AbstractDungeon.player.maxHealth - 100;
        if (i > 0) {
            int realBaseDamage = this.baseDamage;
            this.baseDamage += i;
            super.applyPowers();
            this.baseDamage = realBaseDamage;
            this.isDamageModified = this.damage != this.baseDamage;
        } else
            super.applyPowers();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
