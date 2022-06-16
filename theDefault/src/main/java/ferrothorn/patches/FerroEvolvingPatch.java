package ferrothorn.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ferrothorn.characters.Ferrothorn;

@SpirePatch(clz = AbstractDungeon.class, method = "generateMap")
public class FerroEvolvingPatch {

    @SpirePostfixPatch
    public static void EvolvePokemon() {
        if(AbstractDungeon.player!=null) {
            if(AbstractDungeon.player instanceof Ferrothorn)
                ((Ferrothorn)AbstractDungeon.player).evolvePokemons();
        }
    }
}