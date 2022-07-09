//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ferrothorn.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ferrothorn.FerrothornMod;
import ferrothorn.map.FerroWeatherMap;
import ferrothorn.util.TextureLoader;

import static ferrothorn.FerrothornMod.makeRelicOutlinePath;
import static ferrothorn.FerrothornMod.makeRelicPath;

public class Drizzle extends CustomRelic {
    public static final String ID = FerrothornMod.makeID("Drizzle");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Drizzle.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Drizzle.png"));

    public Drizzle() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void obtain() {
        super.obtain();
        FerroWeatherMap.markWeatherNodes();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
