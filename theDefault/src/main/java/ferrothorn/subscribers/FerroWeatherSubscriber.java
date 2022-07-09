package ferrothorn.subscribers;

import basemod.interfaces.OnStartBattleSubscriber;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.stances.AbstractStance;
import ferrothorn.characters.Ferrothorn;
import ferrothorn.patches.WeatherField;
import ferrothorn.stances.HarshSunlight;
import ferrothorn.stances.Rain;
import ferrothorn.stances.Sandstorm;

public class FerroWeatherSubscriber implements OnStartBattleSubscriber {
    @Override
    public void receiveOnBattleStart(AbstractRoom room) {
        Ferrothorn.WeatherType weather = WeatherField.weather.get(AbstractDungeon.getCurrMapNode());
        if (weather != null) {
            AbstractStance stance = getStance(weather);
            AbstractDungeon.actionManager.addToTop(new ChangeStanceAction(stance));
            if (stance.ID.equals(HarshSunlight.STANCE_ID))
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
        }
    }

    private static AbstractStance getStance(Ferrothorn.WeatherType weather) {
        switch (weather) {
            case Rain: return new Rain();
            case Sandstorm: return new Sandstorm();
            case Sun: return new HarshSunlight();
            default: throw new RuntimeException("Unrecognized weather type: " + weather);
        }
    }
}
