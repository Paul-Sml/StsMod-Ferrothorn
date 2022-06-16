package ferrothorn.subscribers;

import basemod.interfaces.OnStartBattleSubscriber;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import ferrothorn.patches.WeatherField;

public class FerroWeatherSubscriber implements OnStartBattleSubscriber {
    @Override
    public void receiveOnBattleStart(AbstractRoom room) {
        /*if (WeatherField.get(node) != null) {
            System.out.println("Rain entrance");
            AbstractDungeon.actionManager.addToTop(new ChangeStanceAction(new ferrothorn.stances.Rain()));
        }*/
    }
}
