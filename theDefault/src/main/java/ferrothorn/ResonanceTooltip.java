package ferrothorn;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

public class ResonanceTooltip extends TooltipInfo {
    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ferrothorn:Resonance");
    public ResonanceTooltip(){
        super(uiStrings.TEXT[0], uiStrings.TEXT[1]);
    }

    public ResonanceTooltip(String title, String description) {
        super(title, description);
    }
}
