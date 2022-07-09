package ferrothorn.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;
import com.megacrit.cardcrawl.vfx.FlameAnimationEffect;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;
import ferrothorn.util.TextureLoader;
import javassist.CtBehavior;

import java.util.ArrayList;

public class FerroWeatherPatch {
    private static final Texture RAIN = TextureLoader.getTexture(FerrothornMod.makeUiPath("storm64.png"));
    private static final Texture SAND = TextureLoader.getTexture(FerrothornMod.makeUiPath("sandstorm64.png"));
    private static final Texture SUN = TextureLoader.getTexture(FerrothornMod.makeUiPath("sun64.png"));
    private static final float GLOW_CYCLE = 2.0F;
    private static final float ALPHA_RANGE = 0.20F;

    @SpirePatch(clz = MapRoomNode.class, method = "render", paramtypez = {SpriteBatch.class})
    public static class RenderPatch {
        @SpireInsertPatch(locator = RenderPatch.Locator.class)
        public static void renderWeatherVfx(MapRoomNode __instance, SpriteBatch sb, float ___flameVfxTimer, ArrayList<FlameAnimationEffect> ___fEffects, float ___scale) {
            Ferrothorn.WeatherType weather = WeatherField.weather.get(__instance);
            if (weather != null) {
                Texture weatherImage = getWeatherImage(weather);
                int imgWidth = ReflectionHacks.getPrivate(__instance, MapRoomNode.class, "IMG_WIDTH");
                float scale = ReflectionHacks.getPrivate(__instance, MapRoomNode.class, "scale");
                float offsetX = ReflectionHacks.getPrivateStatic(MapRoomNode.class, "OFFSET_X");
                float offsetY = ReflectionHacks.getPrivateStatic(MapRoomNode.class, "OFFSET_Y");
                float spacingX = ReflectionHacks.getPrivateStatic(MapRoomNode.class, "SPACING_X");

                sb.setColor(Color.WHITE);
                if (!Settings.isMobile) {
                    sb.draw(weatherImage, (float)__instance.x * spacingX + offsetX - 32.0F + __instance.offsetX + imgWidth * scale, (float)__instance.y * Settings.MAP_DST_Y + offsetY + DungeonMapScreen.offsetY - 64.0F + __instance.offsetY, 64.0F, 64.0F, 128.0F, 128.0F, scale * Settings.scale, scale * Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
                } else {
                    sb.draw(weatherImage, (float)__instance.x * spacingX + offsetX - 32.0F + __instance.offsetX + imgWidth * scale, (float)__instance.y * Settings.MAP_DST_Y + offsetY + DungeonMapScreen.offsetY - 64.0F + __instance.offsetY, 64.0F, 64.0F, 128.0F, 128.0F, scale * Settings.scale * 2.0F, scale * Settings.scale * 2.0F, 0.0F, 0, 0, 128, 128, false, false);
                }

                //exp10In interpolation with input range from 0.0 to 0.5 results in output range from 0.0 to 0.12
                //Future experiment can be to linearly map between those numbers instead
                //The value of 0.12F for the maximum additional scale here was reached by empirically examining the
                //interpolation that pulsing relics using, Interpolation.exp10In with an input range from 0.0 to 0.5,
                //and observing that it gives an output range of 0.0 to 0.12. This was then tweaked based on how things
                //looked with the various smoother interpolations (linear, sine).
                float maxAdditionalScale = 0.09F;
                float p = Math.abs((GLOW_CYCLE / 2.0F) - ___flameVfxTimer) / (GLOW_CYCLE / 2.0F);
                //float tmp = Interpolation.exp10In.apply(0.0F, 4.0F, p / 2.0F);
                //float tmp = p * maxAdditionalScale;
                float tmp = (1.0F - MathUtils.sin(___flameVfxTimer / GLOW_CYCLE * MathUtils.PI)) * maxAdditionalScale;
                sb.setBlendFunction(770, 1);
                float alpha = p * ALPHA_RANGE;
                sb.setColor(new Color(1.0F, 1.0F, 1.0F, alpha));
                if (!Settings.isMobile) {
                    sb.draw(weatherImage, (float)__instance.x * spacingX + offsetX - 32.0F + __instance.offsetX + imgWidth * scale, (float)__instance.y * Settings.MAP_DST_Y + offsetY + DungeonMapScreen.offsetY - 64.0F + __instance.offsetY, 64.0F, 64.0F, 128.0F, 128.0F, scale * Settings.scale + tmp, scale * Settings.scale + tmp, 0.0F, 0, 0, 128, 128, false, false);
                    sb.draw(weatherImage, (float)__instance.x * spacingX + offsetX - 32.0F + __instance.offsetX + imgWidth * scale, (float)__instance.y * Settings.MAP_DST_Y + offsetY + DungeonMapScreen.offsetY - 64.0F + __instance.offsetY, 64.0F, 64.0F, 128.0F, 128.0F, scale * Settings.scale + tmp * 0.66F, scale * Settings.scale + tmp * 0.66F, 0.0F, 0, 0, 128, 128, false, false);
                    sb.draw(weatherImage, (float)__instance.x * spacingX + offsetX - 32.0F + __instance.offsetX + imgWidth * scale, (float)__instance.y * Settings.MAP_DST_Y + offsetY + DungeonMapScreen.offsetY - 64.0F + __instance.offsetY, 64.0F, 64.0F, 128.0F, 128.0F, scale * Settings.scale + tmp * 0.33F, scale * Settings.scale + tmp * 0.33F, 0.0F, 0, 0, 128, 128, false, false);
                } else {
                    sb.draw(weatherImage, (float)__instance.x * spacingX + offsetX - 32.0F + __instance.offsetX + imgWidth * scale, (float)__instance.y * Settings.MAP_DST_Y + offsetY + DungeonMapScreen.offsetY - 64.0F + __instance.offsetY, 64.0F, 64.0F, 128.0F, 128.0F, scale * Settings.scale * 2.0F + tmp, scale * Settings.scale * 2.0F + tmp, 0.0F, 0, 0, 128, 128, false, false);
                    sb.draw(weatherImage, (float)__instance.x * spacingX + offsetX - 32.0F + __instance.offsetX + imgWidth * scale, (float)__instance.y * Settings.MAP_DST_Y + offsetY + DungeonMapScreen.offsetY - 64.0F + __instance.offsetY, 64.0F, 64.0F, 128.0F, 128.0F, scale * Settings.scale * 2.0F + tmp * 0.66F, scale * Settings.scale * 2.0F + tmp * 0.66F, 0.0F, 0, 0, 128, 128, false, false);
                    sb.draw(weatherImage, (float)__instance.x * spacingX + offsetX - 32.0F + __instance.offsetX + imgWidth * scale, (float)__instance.y * Settings.MAP_DST_Y + offsetY + DungeonMapScreen.offsetY - 64.0F + __instance.offsetY, 64.0F, 64.0F, 128.0F, 128.0F, scale * Settings.scale * 2.0F + tmp * 0.33F, scale * Settings.scale * 2.0F + tmp * 0.33F, 0.0F, 0, 0, 128, 128, false, false);
                }
                sb.setBlendFunction(770, 771);

                if (__instance.hb.hovered) {
                    String title = getWeatherText(weather);
                    String text = getWeatherTitle(weather);
                    TipHelper.renderGenericTip(__instance.hb.cX + __instance.hb.width / 2.0f + weatherImage.getWidth() / 4.0f, __instance.y * Settings.MAP_DST_Y + offsetY + DungeonMapScreen.offsetY + __instance.offsetY + weatherImage.getWidth() / 4.0f, title, text);
                }

            }
        }

        private static Texture getWeatherImage(Ferrothorn.WeatherType weather) {
            switch (weather) {
                case Rain: return RAIN;
                case Sandstorm: return SAND;
                case Sun: return SUN;
                default: throw new RuntimeException("Unrecognized weather type: " + weather);
            }
        }

        private static String getWeatherTitle(Ferrothorn.WeatherType weather) {
            switch (weather) {
                case Rain: return "Enter the combat in #yRain.";
                case Sandstorm: return "Enter the combat in #ySandstorm.";
                case Sun: return "Enter the combat in #yHarsh #ySunlight.";
                default: throw new RuntimeException("Unrecognized weather type: " + weather);
            }
        }

        private static String getWeatherText(Ferrothorn.WeatherType weather) {
            switch (weather) {
                case Rain: return "Rain";
                case Sandstorm: return "Sandstorm";
                case Sun: return "Harsh Sunlight";
                default: throw new RuntimeException("Unrecognized weather type: " + weather);
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(MapRoomNode.class, "renderEmeraldVfx");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch(clz = MapRoomNode.class, method = "update")
    public static class UpdatePatch {
        @SpireInsertPatch(locator = UpdatePatch.Locator.class)
        public static void updateCorruptedVfx(MapRoomNode __instance, @ByRef float[] ___flameVfxTimer, ArrayList<FlameAnimationEffect> ___fEffects, Hitbox ___hb) {
            if (WeatherField.weather.get(__instance) != null && !__instance.hasEmeraldKey) {
                ___flameVfxTimer[0] -= Gdx.graphics.getDeltaTime();

                if ( ___flameVfxTimer[0] < 0.0F) {
                    ___flameVfxTimer[0] = GLOW_CYCLE;
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(MapRoomNode.class, "updateEmerald");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}