package ferrothorn.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.CalmParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;
import ferrothorn.FerrothornMod;

public class HarshSunlight extends AbstractStance
{
    public static final String STANCE_ID = FerrothornMod.makeID(HarshSunlight.class.getSimpleName());
    private static final StanceStrings stanceString;
    private static long sfxId;

    boolean lightningCycle = false;
    float secondParticleTimer = 0;
    //float thirdParticleTimer = 0;
    //float fourthParticleTimer = 0;
    AbstractGameEffect Sun;

    public HarshSunlight() {
        this.ID = STANCE_ID;
        this.name = HarshSunlight.stanceString.NAME;
        this.updateDescription();
    }

    @Override
    public void updateAnimation() {
        /*if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.04F;
                AbstractDungeon.effectsQueue.add(new CalmParticleEffect());
            }
        }

        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.45F, 0.55F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Wrath"));
        }
*/
        this.particleTimer -= Gdx.graphics.getDeltaTime();
        if (lightningCycle){
            secondParticleTimer -= Gdx.graphics.getDeltaTime();
            //thirdParticleTimer -= Gdx.graphics.getDeltaTime();
            //fourthParticleTimer -= Gdx.graphics.getDeltaTime();
            if (secondParticleTimer < 0.0F){
                AbstractDungeon.effectsQueue.add(new HarshSunlightEffect());
                secondParticleTimer = MathUtils.random(2.0f,3.5f);
            }
            /*if (thirdParticleTimer < 0.0F){
                //AbstractDungeon.effectsQueue.add(new HarshSunlightEffect());
                thirdParticleTimer = MathUtils.random(1.0f,1.5f);
            }*/
        }
        if (this.particleTimer < 0.0F) {
            this.particleTimer = 0.3F;
            if (!lightningCycle) {
                Sun = new HarshSunHaloEffect();
                AbstractDungeon.effectsQueue.add(Sun);
                secondParticleTimer = MathUtils.random(1.0f,1.5f);
                //thirdParticleTimer = MathUtils.random(1.4f,2.3f);
                lightningCycle = true;
            }
        }
    }

    @Override
    public void updateDescription() {
        this.description = HarshSunlight.stanceString.DESCRIPTION[0];
    }

    @Override
    public void onEnterStance() {
        if (HarshSunlight.sfxId != -1L) {
            this.stopIdleSfx();
        }
        CardCrawlGame.sound.play("POWER_DEXTERITY");
        HarshSunlight.sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_CALM");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.LIME, true));
        AbstractDungeon.effectsQueue.add(new StanceChangeParticleGenerator(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, "Wrath"));
    }

    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
    }

    @Override
    public void onExitStance() {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        this.stopIdleSfx();
        Sun.isDone = true;
    }

    @Override
    public void stopIdleSfx() {
        if (HarshSunlight.sfxId != -1L) {
            CardCrawlGame.sound.stop("STANCE_LOOP_CALM", HarshSunlight.sfxId);
            HarshSunlight.sfxId = -1L;
        }
    }

    static {
        stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
        HarshSunlight.sfxId = -1L;
    }
}