package ferrothorn.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ferrothorn.FerrothornMod;
import javassist.CtBehavior;

import static ferrothorn.FerrothornMod.ResonanceCheck;

@SpirePatch(
        clz = GameActionManager.class,
        method = "getNextAction"
)
public class TurnStartCheck {
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(GameActionManager __instance) {
        FerrothornMod.gainedStrDexThisTurn = false;
        FerrothornMod.gainedStrDexThisTurnAmt = 0;
        FerrothornMod.hpAtTurnStart = AbstractDungeon.player.currentHealth;
        ResonanceCheck = false;
        FerrothornMod.checkEnergy = 0;
        FerrothornMod.usedEnergy = 0;
        FerrothornMod.exhaustedThisTurn = false;
    }
    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "applyStartOfTurnRelics");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}