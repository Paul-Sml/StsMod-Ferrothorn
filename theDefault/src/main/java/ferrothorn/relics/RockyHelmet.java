package ferrothorn.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ThornsPower;
import ferrothorn.FerrothornMod;
import ferrothorn.powers.Scales;
import ferrothorn.util.TextureLoader;

import static ferrothorn.FerrothornMod.makeRelicOutlinePath;
import static ferrothorn.FerrothornMod.makeRelicPath;

public class RockyHelmet extends CustomRelic {

    // ID, images, text.
    public static final String ID = FerrothornMod.makeID("RockyHelmet");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RockyHelmet.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RockyHelmet.png"));

    private static int AMOUNT = 4;

    public RockyHelmet() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.HEAVY);
    }


    @Override
    public void atBattleStart() {
        this.flash();
        AbstractPlayer p = AbstractDungeon.player;
        this.addToTop(new ApplyPowerAction(p, p, new Scales(p, p, AMOUNT), AMOUNT));
        this.addToTop(new ApplyPowerAction(p, p, new ThornsPower(p, AMOUNT), AMOUNT));
    }

    @Override
    public void obtain() {
        // Replace the starter relic, or just give the relic if starter isn't found
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hasRelic(IronBarbs.ID)) {
            for (int i=0; i<p.relics.size(); ++i) {
                if (p.relics.get(i).relicId.equals(IronBarbs.ID)) {
                    instantObtain(p, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(IronBarbs.ID);
    }
    
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1] + AMOUNT + DESCRIPTIONS[2];
    }

}
