//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ferrothorn.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.green.PiercingWail;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import ferrothorn.FerrothornMod;
import ferrothorn.stances.HarshSunlight;
import ferrothorn.stances.Rain;
import ferrothorn.stances.Sandstorm;
import ferrothorn.util.TextureLoader;

import static ferrothorn.FerrothornMod.makeRelicOutlinePath;
import static ferrothorn.FerrothornMod.makeRelicPath;

public class SandVeil extends CustomRelic {
    public static final String ID = FerrothornMod.makeID("SandVeil");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SandVeil.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SandVeil.png"));

    private static final int STR_LOSS = 2;

    public SandVeil() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayerEndTurn() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.stance.ID.equals(Sandstorm.STANCE_ID) && AbstractDungeon.getCurrRoom().monsters != null) {
            this.flash();
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters)
                if (!m.isDying && !m.isDead) {
                    this.addToBot(new ApplyPowerAction(m, p, new WeakPower (m, 1, false), 1));
                }
        }
    }

    @Override
    public void onChangeStance(AbstractStance prevStance, AbstractStance newStance) {
        AbstractPlayer p = AbstractDungeon.player;
        super.onChangeStance(prevStance, newStance);
        if (newStance.ID.equals(Sandstorm.STANCE_ID))
            this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, -STR_LOSS), -STR_LOSS));
        else if (prevStance.ID.equals(Sandstorm.STANCE_ID))
            this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, STR_LOSS), STR_LOSS));

        if (newStance.ID.equals(Sandstorm.STANCE_ID)) {
            this.pulse = true;// 26
            this.beginPulse();// 27
        } else {
            stopPulse();
        }
    }



    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + STR_LOSS + DESCRIPTIONS[1];
    }
}
