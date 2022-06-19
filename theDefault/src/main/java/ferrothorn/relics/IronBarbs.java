package ferrothorn.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.relics.BronzeScales;
import ferrothorn.FerrothornMod;
import ferrothorn.powers.Scales;
import ferrothorn.util.TextureLoader;

import static ferrothorn.FerrothornMod.makeRelicOutlinePath;
import static ferrothorn.FerrothornMod.makeRelicPath;

public class IronBarbs extends CustomRelic {

    // ID, images, text.
    public static final String ID = FerrothornMod.makeID("IronBarbs");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("IronBarbs.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("IronBarbs.png"));

    public IronBarbs() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
//        this.tips.add(new PowerTip(TipHelper.capitalize(BaseMod.getKeywordTitle("thedragonknight:Graft")), BaseMod.getKeywordDescription("thedragonknight:Graft")));
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Scales(AbstractDungeon.player, AbstractDungeon.player, 3), 3));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
