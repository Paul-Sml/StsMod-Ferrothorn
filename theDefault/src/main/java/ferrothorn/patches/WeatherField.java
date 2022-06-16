package ferrothorn.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.map.MapRoomNode;
import ferrothorn.characters.Ferrothorn;

@SpirePatch(
        clz = MapRoomNode.class,
        method = SpirePatch.CLASS
)
public class WeatherField {
    //public static final SpireField<Ferrothorn.WeatherType> corrupted = new SpireField<>(() -> );
}