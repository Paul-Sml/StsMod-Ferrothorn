package ferrothorn.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.HeavyBlade;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;

import java.util.ArrayList;
import java.util.Iterator;

import static ferrothorn.FerrothornMod.makeCardPath;

public class FoulPlay extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(FoulPlay.class.getSimpleName());
    public static final String IMG = makeCardPath("FoulPlay.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 1;

    public FoulPlay() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 7;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void applyPowers() {
        damage = baseDamage;
        isDamageModified = false;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        ArrayList<AbstractPower> temp = AbstractDungeon.player.powers;
        AbstractDungeon.player.powers = mo.powers;

        super.calculateCardDamage(mo);

        AbstractDungeon.player.powers = temp;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }
}
