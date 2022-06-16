package ferrothorn.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ferrothorn.FerrothornMod;

@SpirePatch(
        clz = GameActionManager.class,
        method = "callEndOfTurnActions"
)
public class EndOfTurnGrow {
    public static void Postfix(GameActionManager __instance) {
        FerrothornMod.lastTurnAttacked = false;
        for (AbstractCard q : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (q.type == AbstractCard.CardType.ATTACK) {
                FerrothornMod.lastTurnAttacked = true;
                break;
            }
        }
    }
}