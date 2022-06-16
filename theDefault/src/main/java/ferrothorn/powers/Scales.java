package ferrothorn.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.exordium.TheGuardian;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import ferrothorn.FerrothornMod;
import ferrothorn.util.TextureLoader;

import java.util.HashSet;
import java.util.Set;

public class Scales extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = FerrothornMod.makeID("Scales");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

//    private static final Texture tex84 = TextureLoader.getTexture(FerrothornMod.makePowerPath("LeechSeed84.png"));
//    private static final Texture tex32 = TextureLoader.getTexture(FerrothornMod.makePowerPath("LeechSeed32.png"));

    private final HashSet<AbstractCreature> attacked = new HashSet<>();

    public Scales(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = true  ;

        this.clearAttackedList();

        loadRegion("sharpHide");
//        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
//        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {

        if (!attacked.contains(info.owner) && info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner) {
            this.flash();
            this.addToBot(new DamageAction(info.owner, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
            attacked.add(info.owner);
        }

        return damageAmount;
    }

    @Override
    public void atStartOfTurn() {
        this.clearAttackedList();
        super.atStartOfTurn();
    }

    @Override
    public void onSpecificTrigger() {
        clearAttackedList();
    }

    public void clearAttackedList() {
        if (attacked != null)
            attacked.clear();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new Scales(owner, source, amount);
    }
}

