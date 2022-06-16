//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ferrothorn.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import ferrothorn.FerrothornMod;
import ferrothorn.stances.HarshSunlight;
import ferrothorn.util.TextureLoader;

import static ferrothorn.FerrothornMod.makeRelicOutlinePath;
import static ferrothorn.FerrothornMod.makeRelicPath;

public class SolarPower extends CustomRelic {
    public static final String ID = FerrothornMod.makeID("SolarPower");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SolarPower.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SolarPower.png"));

    private static final int HP_AMT = 2;
    private static final int DMG_AMT = 5;

    public SolarPower() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayerEndTurn() {
        if (AbstractDungeon.player.stance.ID.equals(HarshSunlight.STANCE_ID)) {
            this.flash();
            this.addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, HP_AMT));
        }
    }

    public float atDamageModify(float damage, AbstractCard c) {
        if (AbstractDungeon.player.stance.ID.equals(HarshSunlight.STANCE_ID))
            return damage + DMG_AMT;
        return damage;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DMG_AMT + DESCRIPTIONS[1] + HP_AMT + DESCRIPTIONS[2];
    }
}
