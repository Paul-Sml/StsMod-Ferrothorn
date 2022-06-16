//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ferrothorn.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.Orrery;
import com.megacrit.cardcrawl.relics.Strawberry;
import com.megacrit.cardcrawl.relics.Waffle;
import ferrothorn.FerrothornMod;
import ferrothorn.util.TextureLoader;

import static ferrothorn.FerrothornMod.makeRelicOutlinePath;
import static ferrothorn.FerrothornMod.makeRelicPath;

public class RareCandy extends CustomRelic {
    public static final String ID = FerrothornMod.makeID("RareCandy");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RareCandy.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RareCandy.png"));
    private static final int HP_AMT = 6;

    public RareCandy() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.FLAT);
    }

    public void onEquip() {
        AbstractDungeon.player.increaseMaxHp(HP_AMT, false);
        AbstractDungeon.getCurrRoom().addCardToRewards();
        AbstractDungeon.combatRewardScreen.open(this.DESCRIPTIONS[1]);
        AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.0F;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + HP_AMT + DESCRIPTIONS[1];
    }
}
