package ferrothorn.map;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import ferrothorn.characters.Ferrothorn;
import ferrothorn.patches.WeatherField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;

public class FerroWeatherMap {
    public static final Logger logger = LogManager.getLogger(FerroWeatherMap.class.getName());

    public static void markWeatherNodes() {
        Random rng = new Random(Settings.seed + AbstractDungeon.actNum);

        ArrayList<MapRoomNode> potentialWeatherNodes = new ArrayList<>();
        for (int i = 0; i < AbstractDungeon.map.size(); i++) {
            for (int j = 0; j < AbstractDungeon.map.get(i).size(); j++) {
                MapRoomNode node = AbstractDungeon.map.get(i).get(j);
                if (node.getRoom() instanceof MonsterRoom || node.getRoom() instanceof MonsterRoomElite) {
                    potentialWeatherNodes.add(node);
                }
            }
        }

        double percentCorrupt = 0.5;
        int baseCorrupt = (int)Math.ceil(potentialWeatherNodes.size() * percentCorrupt);

        Collections.shuffle(potentialWeatherNodes, rng.random);

        for (int i = 0; i < baseCorrupt; i++) {
            MapRoomNode node = potentialWeatherNodes.get(i);
            if (node != null) {
                Ferrothorn.WeatherType weather = getRandomWeather(rng);
                MarkWeather(node, weather);
            }
        }
    }

    private static Ferrothorn.WeatherType getRandomWeather(Random rng) {
        switch (rng.random(2)) {
            case 0:
                return Ferrothorn.WeatherType.Rain;
            case 1:
                return Ferrothorn.WeatherType.Sandstorm;
            case 2:
                return Ferrothorn.WeatherType.Sun;
            default:
                throw new RuntimeException("Impossible case");
        }
    }

    private static void MarkWeather(MapRoomNode node, Ferrothorn.WeatherType weather) {
        WeatherField.weather.set(node, weather);
    }
}
