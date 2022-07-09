//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ferrothorn.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ferrothorn.FerrothornMod;
import ferrothorn.util.TextureLoader;

import static ferrothorn.FerrothornMod.makeRelicOutlinePath;
import static ferrothorn.FerrothornMod.makeRelicPath;

public class Defeatist extends CustomRelic {
    public static final String ID = FerrothornMod.makeID("Defeatist");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Defeatist.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Defeatist.png"));

    public Defeatist() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    public void atTurnStart() {
        if (AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth / 2) {
            this.flash();
            this.addToBot(new GainEnergyAction(1));
        }
    }

    public void onBloodied() {
        this.stopPulse();
    }

    public void onNotBloodied() {
        this.flash();
        this.pulse = true;
        this.beginPulse();// 27
    }

    public void atPreBattle() {
        if (AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth / 2) {
            this.pulse = true;// 26
            this.beginPulse();// 27
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
