//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ferrothorn.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.Boot;
import ferrothorn.FerrothornMod;
import ferrothorn.stances.HarshSunlight;
import ferrothorn.util.TextureLoader;

import static ferrothorn.FerrothornMod.makeRelicOutlinePath;
import static ferrothorn.FerrothornMod.makeRelicPath;

public class Moxie extends CustomRelic {
    public static final String ID = FerrothornMod.makeID("Moxie");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Moxie.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Moxie.png"));

    private static final int DMG_AMT = 14;
    private static final int DRAW_AMT = 1;

    public Moxie() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onAttack(info, damageAmount, target);
        if (damageAmount >= DMG_AMT)
            this.addToBot(new DrawCardAction(DRAW_AMT));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DMG_AMT + DESCRIPTIONS[1] + DRAW_AMT + DESCRIPTIONS[2];
    }
}
