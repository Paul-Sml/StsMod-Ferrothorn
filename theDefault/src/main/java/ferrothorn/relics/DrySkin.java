//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ferrothorn.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;
import ferrothorn.FerrothornMod;
import ferrothorn.stances.HarshSunlight;
import ferrothorn.stances.Rain;
import ferrothorn.stances.Sandstorm;
import ferrothorn.util.TextureLoader;

import static ferrothorn.FerrothornMod.makeRelicOutlinePath;
import static ferrothorn.FerrothornMod.makeRelicPath;

public class DrySkin extends CustomRelic {
    public static final String ID = FerrothornMod.makeID("DrySkin");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DrySkin.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DrySkin.png"));

    private static final int BL_GAIN = 4;
    private static final int BL_LOSS = 2;

    public DrySkin() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayerEndTurn() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.stance.ID.equals(Rain.STANCE_ID)) {
            this.flash();
            this.addToBot(new GainBlockAction(p, p, BL_GAIN));
        }
        if (p.stance.ID.equals(HarshSunlight.STANCE_ID) || p.stance.ID.equals(Sandstorm.STANCE_ID)) {
            this.flash();
            this.addToBot(new LoseBlockAction(p, p, BL_LOSS));
        }
    }

    @Override
    public void onChangeStance(AbstractStance prevStance, AbstractStance newStance) {
        super.onChangeStance(prevStance, newStance);
        if (newStance.ID.equals(Rain.STANCE_ID)) {
            this.pulse = true;// 26
            this.beginPulse();// 27
        } else {
            stopPulse();
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + BL_GAIN + DESCRIPTIONS[1] + BL_LOSS + DESCRIPTIONS[2];
    }
}
