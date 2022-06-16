package ferrothorn.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

@SpirePatch(
        clz = AbstractCard.class,
        method = "freeToPlay"
)
public class FreeStrikesPatch {
    public static boolean Postfix( boolean __result, AbstractCard __instance) {
        if (CardCrawlGame.isInARun()) {
//            if (__instance.hasTag(AbstractCard.CardTags.STARTER_STRIKE) && AbstractDungeon.player.hasPower(NashorsToothPower.POWER_ID) && AbstractDungeon.player.getPower(NashorsToothPower.POWER_ID).amount >= 3)
//                return true;
            return __result;
        }
        return __result;
    }
}