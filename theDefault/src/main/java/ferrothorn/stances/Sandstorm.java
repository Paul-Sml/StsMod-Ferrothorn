package ferrothorn.stances;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.relics.MercuryHourglass;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.badlogic.gdx.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.core.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.stances.WrathStance;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.stance.*;
import ferrothorn.FerrothornMod;

import static ferrothorn.FerrothornMod.enablePlaceholder;

public class Sandstorm extends AbstractStance
{
    public static final String STANCE_ID = FerrothornMod.makeID(Sandstorm.class.getSimpleName());
    private static final StanceStrings stanceString;
    private static long sfxId;
    private int j;

    public Sandstorm() {
        this.ID = STANCE_ID;
        this.name = Sandstorm.stanceString.NAME;
        this.updateDescription();
        j = 50;
        if (enablePlaceholder)
            j = 225;
    }

    @Override
    public void updateAnimation() {
        this.particleTimer -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer < 0.0F) {
            this.particleTimer = 0.2F;

            for (int i = 0; i < j; i++) {
                AbstractDungeon.effectsQueue.add(new SandstormEffect());
            }
        }
    }

    @Override
    public void updateDescription() {
        this.description = Sandstorm.stanceString.DESCRIPTION[0];
    }

    @Override
    public void onEnterStance() {
        if (Sandstorm.sfxId != -1L) {
            this.stopIdleSfx();
        }
    }

    @Override
    public void onEndOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction((AbstractCreature)null, DamageInfo.createDamageMatrix(4, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void onExitStance() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MetallicizePower(p, 1), 1));
        this.stopIdleSfx();
    }

    @Override
    public void stopIdleSfx() {
        if (Sandstorm.sfxId != -1L) {
            CardCrawlGame.sound.stop("STANCE_LOOP_CALM", Sandstorm.sfxId);
            Sandstorm.sfxId = -1L;
        }
    }

    static {
        stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
        Sandstorm.sfxId = -1L;
    }
}