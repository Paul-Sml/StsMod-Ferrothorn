package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;

import static ferrothorn.FerrothornMod.makeCardPath;

public class GrassKnot extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(GrassKnot.class.getSimpleName());
    public static final String IMG = makeCardPath("GrassKnot.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 1;

    public GrassKnot() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.isMultiDamage = true;
        this.baseDamage = 7;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int i = 1;
        for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (q.currentHealth > p.currentHealth)
                i++;
        }
        for (int j = 0; j < i; j++) {
            this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (q.currentHealth > AbstractDungeon.player.currentHealth)
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }
}
