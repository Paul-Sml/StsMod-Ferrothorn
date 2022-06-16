package ferrothorn.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

public class SimpletonPatch
{

    @SpirePatch(clz = ShowCardAndAddToDiscardEffect.class, method = "update")
    @SpirePatch(clz = ShowCardAndAddToHandEffect.class, method = "update")
    @SpirePatch(clz = ShowCardAndAddToDrawPileEffect.class, method = "update")
    public static class InDraw
    {
        public static void Prefix(AbstractGameEffect __instance, float ___EFFECT_DUR, AbstractCard ___card)
        {
            if (__instance.duration == ___EFFECT_DUR)
            {
//                if (___card.cardID.equals(Process.ID))
//                    ___card.setCostForTurn(___card.costForTurn - FerrothornMod.gainedStrDexThisTurnAmt);

            }
        }
    }
}