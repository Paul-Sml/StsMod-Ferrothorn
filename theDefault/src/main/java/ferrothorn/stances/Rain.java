package ferrothorn.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.CalmStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.stance.CalmParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;
import ferrothorn.FerrothornMod;
import ferrothorn.cards.RainDance;
import ferrothorn.cards.Seed;

import static ferrothorn.FerrothornMod.enablePlaceholder;

public class Rain extends AbstractStance
{
    public static final String STANCE_ID = FerrothornMod.makeID(Rain.class.getSimpleName());
    private static final StanceStrings stanceString;
    private static long sfxId;

    boolean lightningCycle = false;
    float secondParticleTimer = 0;
    private int j;

    public Rain() {
        this.ID = STANCE_ID;
        this.name = stanceString.NAME;
        this.updateDescription();
        j = 25;
        if (enablePlaceholder)
            j = 40;
    }

    @Override
    public void updateAnimation() {
        this.particleTimer -= Gdx.graphics.getDeltaTime();
        if (lightningCycle){
            secondParticleTimer -= Gdx.graphics.getDeltaTime();
            if (secondParticleTimer < 0.0F){
                AbstractDungeon.actionManager.addToBottom(new SFXAction("ORB_LIGHTNING_EVOKE", 0.5F));
                AbstractDungeon.effectsQueue.add(new LightningEffect(MathUtils.random(-50.0F, 1500.0F),AbstractDungeon.floorY + MathUtils.random(0.0F, 320.0F) * Settings.scale));
                secondParticleTimer = MathUtils.random(3.5f,6.5f);
                //secondParticleTimer = 0.2F;
            }
        }
        if (this.particleTimer < 0.0F) {
            this.particleTimer = 0.3F;
            if (!lightningCycle) {
                secondParticleTimer = MathUtils.random(3.5f,6.5f);
                //secondParticleTimer = 0.2F;
                lightningCycle = true;
            }
            for (int i = 0; i < j; i++) {
                AbstractDungeon.effectsQueue.add(new RainEffect(8));

            }
        }
    }

    @Override
    public void updateDescription() {
        this.description = Rain.stanceString.DESCRIPTION[0];
    }

    @Override
    public void onEnterStance() {
        if (Rain.sfxId != -1L) {
            this.stopIdleSfx();
        }
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        return type == DamageInfo.DamageType.NORMAL ? damage * 0.80F : damage;
    }

    @Override
    public void onExitStance() {
        AbstractCard c = new Seed();
        c.upgrade();
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, 1));
        this.stopIdleSfx();
    }

    @Override
    public void stopIdleSfx() {
        if (Rain.sfxId != -1L) {
            CardCrawlGame.sound.stop("STANCE_LOOP_CALM", Rain.sfxId);
            Rain.sfxId = -1L;
        }
    }

    static {
        stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
        Rain.sfxId = -1L;
    }
}