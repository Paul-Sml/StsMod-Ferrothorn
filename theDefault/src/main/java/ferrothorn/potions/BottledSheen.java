package ferrothorn.potions;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import ferrothorn.FerrothornMod;
import ferrothorn.OnDamageBerryInterfaceFerro;

import static ferrothorn.FerrothornMod.FERROTHORN_GREEN;

public class BottledSheen extends AbstractPotion implements OnDamageBerryInterfaceFerro {


    public static final String POTION_ID = FerrothornMod.makeID("BottledSheen");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public BottledSheen() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.FAIRY, PotionColor.FAIRY);
        this.labOutlineColor = FERROTHORN_GREEN;
        potency = getPotency();

        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];

        isThrown = false;

        tips.add(new PowerTip(name, description));

    }

    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
        }
    }

    @Override
    public void DamageActivation(int damage) {
        AbstractPlayer p = AbstractDungeon.player;
        //int i = p.decrementBlock;
        //if (damage){
            this.use(AbstractDungeon.player);
        //}
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BottledSheen();
    }

    @Override
    public int getPotency(final int potency) {
        return 15;
    }

    public boolean canUse() {
        return false;
    }

    public void upgradePotion()
    {
        potency += 5;
        tips.clear();
        tips.add(new PowerTip(name, description));
    }
}