package ferrothorn.cards;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractDynamicCard extends AbstractDefaultCard {

    // "How come DefaultCommonAttack extends CustomCard and not DynamicCard like all the rest?"

    // Well every card, at the end of the day, extends CustomCard.
    // Abstract Default Card extends CustomCard and builds up on it, adding a second magic number. Your card can extend it and
    // bam - you can have a second magic number in that card (Learn Java inheritance if you want to know how that works).
    // Abstract Dynamic Card builds up on Abstract Default Card even more and makes it so that you don't need to add
    // the NAME and the DESCRIPTION into your card - it'll get it automatically. Of course, this functionality could have easily
    // Been added to the default card rather than creating a new Dynamic one, but was done so to deliberately.

    public AbstractDynamicCard(final String id,
                               final String img,
                               final int cost,
                               final CardType type,
                               final CardColor color,
                               final CardRarity rarity,
                               final CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);

        String path;
        switch(this.rarity) {
            case RARE:
                path = "bg_rare.png";
                break;
            case UNCOMMON:
                path = "bg_unco.png";
                break;
            default:
                path = "bg_co.png";
        }
        setBackgroundTexture("ferrothornResources/images/512/" + path, "ferrothornResources/images/1024/" + path);
    }

    /*@Override
    public Texture getBackgroundSmallTexture() {
        if (this.textureBackgroundSmallImg == null) {
            switch(this.type) {
                case ATTACK:
                    return BaseMod.getAttackBgTexture(this.color);
                case POWER:
                    return BaseMod.getPowerBgTexture(this.color);
                default:
                    return BaseMod.getSkillBgTexture(this.color);
            }
        } else {
            return super.getBackgroundSmallTexture();
        }
    }

    @Override
    public Texture getBackgroundLargeTexture() {
        if (this.textureBackgroundLargeImg == null) {
            switch(this.rarity) {
                case RARE:
                    return ImageMaster.loadImage;
                case UNCOMMON:
                    return BaseMod.getPowerBgPortraitTexture(this.color);
                default:
                    return BaseMod.getSkillBgPortraitTexture(this.color);
            }
        } else {
            return super.getBackgroundLargeTexture();
        }
    }*/



    public void strAndDexGains(int total) {}

}