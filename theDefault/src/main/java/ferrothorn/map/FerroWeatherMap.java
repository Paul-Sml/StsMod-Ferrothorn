package ferrothorn.map;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
//import corruptthespire.Cor;
//import corruptthespire.patches.core.WeatherField;
import ferrothorn.patches.WeatherField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;

public class FerroWeatherMap {
    public static final Logger logger = LogManager.getLogger(FerroWeatherMap.class.getName());

    public static void markCorruptedNodes() {
        ArrayList<MapRoomNode> potentialCorruptNodes = new ArrayList<>();
        for (int i = 0; i < AbstractDungeon.map.size(); i++) {
            for (int j = 0; j < AbstractDungeon.map.get(i).size(); j++) {
                MapRoomNode node = AbstractDungeon.map.get(i).get(j);
                if (node.getRoom() instanceof MonsterRoom || node.getRoom() instanceof MonsterRoomElite) {
                    potentialCorruptNodes.add(node);
                }
            }
        }
        Random rng = new Random(Settings.seed);

        double percentCorrupt = .33;
        int baseCorrupt = (int)Math.ceil(potentialCorruptNodes.size() * percentCorrupt);
        System.out.println(baseCorrupt);

        Collections.shuffle(potentialCorruptNodes, rng.random);

        for (int i = 0; i < baseCorrupt; i++) {
            MapRoomNode node = potentialCorruptNodes.get(i);
            if (node != null) {
                MarkCorrupted(node);
            }
        }
    }

    private static void MarkCorrupted(MapRoomNode node) {
        //WeatherField.corrupted.set(node, true);
    }
}
