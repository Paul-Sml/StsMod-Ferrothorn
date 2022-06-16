package ferrothorn.cards;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Anger;
import com.megacrit.cardcrawl.cards.red.Rampage;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;

import static ferrothorn.FerrothornMod.makeCardPath;

public class Rollout extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(Rollout.class.getSimpleName());
    public static final String IMG = makeCardPath("Rollout.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 1;

    public Rollout() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 8;
        this.magicNumber = this.baseMagicNumber = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.addToBot(new ModifyDamageAction(this.uuid, this.magicNumber));
        /*c.baseDamage += this.magicNumber;
        if (c.baseDamage < 0) {
            c.baseDamage = 0;
        }
*/
        /*AbstractCard c = this;
        addToBot(new AbstractGameAction() {
                     @Override
                     public void update() {
                         if (this.duration == this.startDuration) {
                             AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c.makeStatEquivalentCopy()));
                             this.duration -= Gdx.graphics.getDeltaTime();
                         }

                         isDone = true;
                         this.tickDuration();
                     }
        });*/

        //this.addToBot(new ModifyDamageAction(c.uuid, this.magicNumber));
        this.addToBot(new MakeTempCardInDiscardAction(this, 1));
        /*addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (this.duration == this.startDuration) {

                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.makeNewCard()));
                    this.duration -= Gdx.graphics.getDeltaTime();
                }

                this.tickDuration();
            }

            private AbstractCard makeNewCard() {
                return this.sameUUID ? this.c.makeSameInstanceOf() : this.c.makeStatEquivalentCopy();
            }
        }*/
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }
}
