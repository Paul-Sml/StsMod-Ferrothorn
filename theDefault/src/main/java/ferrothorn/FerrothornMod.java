package ferrothorn;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import ferrothorn.cards.*;
import ferrothorn.characters.Ferrothorn;
import ferrothorn.potions.BottledSheen;
import ferrothorn.potions.EquilibriumPotion;
import ferrothorn.potions.ResonatingPotion;
import ferrothorn.relics.*;
import ferrothorn.subscribers.FerroWeatherSubscriber;
import ferrothorn.util.IDCheckDontTouchPls;
import ferrothorn.util.TextureLoader;
import ferrothorn.variables.DefaultCustomVariable;
import ferrothorn.variables.DefaultSecondMagicNumber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
// Please don't just mass replace "theDefault" with "yourMod" everywhere.
// It'll be a bigger pain for you. You only need to replace it in 3 places.
// I comment those places below, under the place where you set your ID.

//TODO: FIRST THINGS FIRST: RENAME YOUR PACKAGE AND ID NAMES FIRST-THING!!!
// Right click the package (Open the project pane on the left. Folder with black dot on it. The name's at the very top) -> Refactor -> Rename, and name it whatever you wanna call your mod.
// Scroll down in this file. Change the ID from "theDefault:" to "yourModName:" or whatever your heart desires (don't use spaces). Dw, you'll see it.
// In the JSON strings (resources>localization>eng>[all them files] make sure they all go "yourModName:" rather than "theDefault". You can ctrl+R to replace in 1 file, or ctrl+shift+r to mass replace in specific files/directories (Be careful.).
// Start with the DefaultCommon cards - they are the most commented cards since I don't feel it's necessary to put identical comments on every card.
// After you sorta get the hang of how to make cards, check out the card template which will make your life easier

/*
 * With that out of the way:
 * Welcome to this super over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, couple of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. MIT licence applies. Happy modding!
 *
 * And pls. Read the comments.
 */

@SpireInitializer
public class FerrothornMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        OnStartBattleSubscriber,
        PostInitializeSubscriber {
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(FerrothornMod.class.getName());
    public static boolean lastTurnAttacked;
    public static Object hpAtTurnStart;
    public static boolean gainedStrDexThisTurn;
    public static int gainedStrDexThisTurnAmt = 0;
    private static String modID;
    public static boolean ResonanceCheck;
    public static int checkEnergy = 0;
    public static int usedEnergy = 0;
    public static boolean exhaustedThisTurn;
    public void receiveOnBattleStart (AbstractRoom room) {
        FerrothornMod.lastTurnAttacked = false;
        FerrothornMod.gainedStrDexThisTurn = false;
        FerrothornMod.gainedStrDexThisTurnAmt = 0;
        FerrothornMod.hpAtTurnStart = AbstractDungeon.player.currentHealth;
        FerrothornMod.ResonanceCheck = false;
        FerrothornMod.checkEnergy = 0;
        FerrothornMod.usedEnergy = 0;
        FerrothornMod.exhaustedThisTurn = false;
    }

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties ferrothornDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = false; // The boolean we'll be setting on/off (true/false)

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Ferrothorn";
    private static final String AUTHOR = "Diamsword"; // And pretty soon - You!
    private static final String DESCRIPTION = "A mod inspired by Ferrothorn from Pokemon";
    
    // =============== INPUT TEXTURE LOCATION =================
    
    // Colors (RGB)
    // Character Color
    public static final Color FERROTHORN_GREEN = CardHelper.getColor(103.0f, 193.0f, 140.0f).cpy();

    // Potion Colors in RGB
//    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
//    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
//    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown

    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
  
    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_DEFAULT_GRAY = "ferrothornResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY = "ferrothornResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY = "ferrothornResources/images/512/bg_power_default_gray.png";
    
    private static final String ENERGY_ORB_DEFAULT_GRAY = "ferrothornResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "ferrothornResources/images/512/card_small_orb.png";
    
    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "ferrothornResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "ferrothornResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = "ferrothornResources/images/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "ferrothornResources/images/1024/card_default_gray_orb.png";
    
    // Character assets
    private static final String THE_DEFAULT_BUTTON = "ferrothornResources/images/charSelect/DefaultCharacterButton.png";
    private static final String THE_DEFAULT_PORTRAIT = "ferrothornResources/images/charSelect/DefaultCharacterPortraitBG.png";
    public static final String THE_DEFAULT_SHOULDER_1 = "ferrothornResources/images/char/defaultCharacter/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "ferrothornResources/images/char/defaultCharacter/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "ferrothornResources/images/char/defaultCharacter/corpse.png";
    
    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "ferrothornResources/images/Badge.png";
    
    // Atlas and JSON files for the Animations
    public static final String THE_DEFAULT_SKELETON_ATLAS = "ferrothornResources/images/char/defaultCharacter/skeleton.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = "ferrothornResources/images/char/defaultCharacter/skeleton.json";
    
    // =============== MAKE IMAGE PATHS =================
    
    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }
    
    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }
    
//    public static String makeOrbPath(String resourcePath) {
//        return getModID() + "Resources/orbs/" + resourcePath;
//    }
    
    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    public static String makeUiPath(String resourcePath) {
        return getModID() + "Resources/images/ui/" + resourcePath;
    }
    
    // =============== /MAKE IMAGE PATHS/ =================
    
    // =============== /INPUT TEXTURE LOCATION/ =================
    
    
    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================

    public FerrothornMod() {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);
        
      /*
           (   ( /(  (     ( /( (            (  `   ( /( )\ )    )\ ))\ )
           )\  )\()) )\    )\()))\ )   (     )\))(  )\()|()/(   (()/(()/(
         (((_)((_)((((_)( ((_)\(()/(   )\   ((_)()\((_)\ /(_))   /(_))(_))
         )\___ _((_)\ _ )\ _((_)/(_))_((_)  (_()((_) ((_|_))_  _(_))(_))_
        ((/ __| || (_)_\(_) \| |/ __| __| |  \/  |/ _ \|   \  |_ _||   (_)
         | (__| __ |/ _ \ | .` | (_ | _|  | |\/| | (_) | |) |  | | | |) |
          \___|_||_/_/ \_\|_|\_|\___|___| |_|  |_|\___/|___/  |___||___(_)
      */
      
        setModID("ferrothorn");
        // cool
        // TODO: NOW READ THIS!!!!!!!!!!!!!!!:
        
        // 1. Go to your resources folder in the project panel, and refactor> rename theDefaultResources to
        // yourModIDResources.
        
        // 2. Click on the localization > eng folder and press ctrl+shift+r, then select "Directory" (rather than in Project)
        // replace all instances of theDefault with yourModID.
        // Because your mod ID isn't the default. Your cards (and everything else) should have Your mod id. Not mine.
        
        // 3. FINALLY and most importantly: Scroll up a bit. You may have noticed the image locations above don't use getModID()
        // Change their locations to reflect your actual ID rather than theDefault. They get loaded before getID is a thing.
        
        logger.info("Done subscribing");
        
        logger.info("Creating the color " + Ferrothorn.Enums.COLOR_FERROTHORN.toString());
        
        BaseMod.addColor(Ferrothorn.Enums.COLOR_FERROTHORN, FERROTHORN_GREEN, FERROTHORN_GREEN, FERROTHORN_GREEN,
                FERROTHORN_GREEN, FERROTHORN_GREEN, FERROTHORN_GREEN, FERROTHORN_GREEN,
                ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);
        
        logger.info("Done creating the color");
        
        
        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        ferrothornDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", ferrothornDefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
        
    }
    
    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP
    
    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = FerrothornMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO
    
    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH
    
    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = FerrothornMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = FerrothornMod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
    
    // ====== YOU CAN EDIT AGAIN ======
    
    
    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        FerrothornMod defaultmod = new FerrothornMod();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }
    
    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================
    
    
    // =============== LOAD THE CHARACTER =================
    
    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + Ferrothorn.Enums.FERROTHORN.toString());
        
        BaseMod.addCharacter(new Ferrothorn("Ferrothorn", Ferrothorn.Enums.FERROTHORN),
                THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, Ferrothorn.Enums.FERROTHORN);
        
        receiveEditPotions();
        logger.info("Added " + Ferrothorn.Enums.FERROTHORN.toString());
    }
    
    // =============== /LOAD THE CHARACTER/ =================
    
    
    // =============== POST-INITIALIZE =================

    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        
        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("Enable skin based Boss relics (Z-Drive <skin>)",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enablePlaceholder, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:
            
            enablePlaceholder = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", ferrothornDefaultSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        BaseMod.subscribe(new FerroWeatherSubscriber());
        
        // =============== EVENTS =================
        
        // This event will be exclusive to the City (act 2). If you want an event that's present at any
        // part of the game, simply don't include the dungeon ID
        // If you want to have a character-specific event, look at slimebound (CityRemoveEventPatch).
        // Essentially, you need to patch the game and say "if a player is not playing my character class, remove the event from the pool"
        //BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);
        
        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");
    }
    
    // =============== / POST-INITIALIZE/ =================
    
    
    // ================ ADD POTIONS ===================
    
    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");
        
        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.THE_DEFAULT".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        /*BaseMod.addPotion(BottledSheen.class, Color.GOLD.cpy(), Color.CYAN.cpy(), null, BottledSheen.POTION_ID, Ferrothorn.Enums.FERROTHORN);
        BaseMod.addPotion(ResonatingPotion.class, Color.valueOf("0d429dff").cpy(), null, Color.CYAN.cpy(), ResonatingPotion.POTION_ID, Ferrothorn.Enums.FERROTHORN);
        BaseMod.addPotion(EquilibriumPotion.class, Color.DARK_GRAY.cpy(), Color.CHARTREUSE.cpy(), Color.CORAL.cpy(), EquilibriumPotion.POTION_ID, Ferrothorn.Enums.FERROTHORN);
*/
        logger.info("Done editing potions");
    }
    
    // ================ /ADD POTIONS/ ===================
    
    
    // ================ ADD RELICS ===================
    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");
        BaseMod.addRelicToCustomPool(new IronBarbs(), Ferrothorn.Enums.COLOR_FERROTHORN);
        UnlockTracker.markRelicAsSeen(IronBarbs.ID);
        BaseMod.addRelicToCustomPool(new RockyHelmet(), Ferrothorn.Enums.COLOR_FERROTHORN);
        UnlockTracker.markRelicAsSeen(RockyHelmet.ID);
        BaseMod.addRelicToCustomPool(new RareCandy(), Ferrothorn.Enums.COLOR_FERROTHORN);
        UnlockTracker.markRelicAsSeen(RareCandy.ID);
        BaseMod.addRelicToCustomPool(new DrySkin(), Ferrothorn.Enums.COLOR_FERROTHORN);
        UnlockTracker.markRelicAsSeen(DrySkin.ID);
        BaseMod.addRelicToCustomPool(new SandVeil(), Ferrothorn.Enums.COLOR_FERROTHORN);
        UnlockTracker.markRelicAsSeen(SandVeil.ID);
        BaseMod.addRelicToCustomPool(new SolarPower(), Ferrothorn.Enums.COLOR_FERROTHORN);
        UnlockTracker.markRelicAsSeen(SolarPower.ID);
        BaseMod.addRelicToCustomPool(new Moxie(), Ferrothorn.Enums.COLOR_FERROTHORN);
        UnlockTracker.markRelicAsSeen(Moxie.ID);

        logger.info("Done adding relics!");
    }
    
    // ================ /ADD RELICS/ ===================


    // ================ ADD CARDS ===================
    
    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());
        
        logger.info("Adding cards");
        // Add the cards
        // Don't comment out/delete these cards (yet). You need 1 of each type and rarity (technically) for your game not to crash
        // when generating card rewards/shop screen items.

        BaseMod.addCard(new Strike());
        BaseMod.addCard(new Defend());
        BaseMod.addCard(new LeechSeed());

        BaseMod.addCard(new AncientPower());
        UnlockTracker.unlockCard(AncientPower.ID);
        BaseMod.addCard(new BodyPress());
        UnlockTracker.unlockCard(BodyPress.ID);
        BaseMod.addCard(new BulletSeed());
        UnlockTracker.unlockCard(BulletSeed.ID);
        BaseMod.addCard(new CottonGuard());
        UnlockTracker.unlockCard(CottonGuard .ID);
        BaseMod.addCard(new Curse());
        UnlockTracker.unlockCard(Curse.ID);
        BaseMod.addCard(new Endure());
        UnlockTracker.unlockCard(Endure.ID);
        BaseMod.addCard(new Explosion());
        UnlockTracker.unlockCard(Explosion.ID);
        BaseMod.addCard(new GrassKnot());
        UnlockTracker.unlockCard(GrassKnot.ID);
        BaseMod.addCard(new GyroBall());
        UnlockTracker.unlockCard(GyroBall.ID);
        BaseMod.addCard(new Harden());
        UnlockTracker.unlockCard(Harden.ID);
        BaseMod.addCard(new HeavySlam());
        UnlockTracker.unlockCard(HeavySlam.ID);
        BaseMod.addCard(new HyperBeam());
        UnlockTracker.unlockCard(HyperBeam.ID);
        BaseMod.addCard(new Ingrain());
        UnlockTracker.unlockCard(Ingrain.ID);
        BaseMod.addCard(new IronDefense());
        UnlockTracker.unlockCard(IronDefense.ID);
        BaseMod.addCard(new IronHead());
        UnlockTracker.unlockCard(IronHead.ID);
        BaseMod.addCard(new KnockOff());
        UnlockTracker.unlockCard(KnockOff.ID);
        BaseMod.addCard(new PowerWhip());
        UnlockTracker.unlockCard(PowerWhip.ID);
        BaseMod.addCard(new Protect());
        UnlockTracker.unlockCard(Protect.ID);
        BaseMod.addCard(new RainDance());
        UnlockTracker.unlockCard(RainDance.ID);
        BaseMod.addCard(new Revenge());
        UnlockTracker.unlockCard(Revenge.ID);
        BaseMod.addCard(new RockPolish());
        UnlockTracker.unlockCard(RockPolish.ID);
        BaseMod.addCard(new Rollout());
        UnlockTracker.unlockCard(Rollout.ID);
        BaseMod.addCard(new Sandstorm());
        UnlockTracker.unlockCard(Sandstorm.ID);
        BaseMod.addCard(new SappySeed());
        UnlockTracker.unlockCard(SappySeed.ID);
        BaseMod.addCard(new Seed());
        UnlockTracker.unlockCard(Seed.ID);
        BaseMod.addCard(new SeedBomb());
        UnlockTracker.unlockCard(SeedBomb.ID);
        BaseMod.addCard(new ShoreUp());
        UnlockTracker.unlockCard(ShoreUp.ID);
        BaseMod.addCard(new SolarBeam());
        UnlockTracker.unlockCard(SolarBeam.ID);
        BaseMod.addCard(new SolarBeamAttack());
        UnlockTracker.unlockCard(SolarBeamAttack.ID);
        BaseMod.addCard(new Spikes());
        UnlockTracker.unlockCard(Spikes.ID);
        BaseMod.addCard(new SpikyShield());
        UnlockTracker.unlockCard(SpikyShield.ID);
        BaseMod.addCard(new StealthRock());
        UnlockTracker.unlockCard(StealthRock.ID);
        BaseMod.addCard(new StrengthSap());
        UnlockTracker.unlockCard(StrengthSap.ID);
        BaseMod.addCard(new Substitute());
        UnlockTracker.unlockCard(Substitute.ID);
        BaseMod.addCard(new SunnyDay());
        UnlockTracker.unlockCard(SunnyDay.ID);
        BaseMod.addCard(new Swagger());
        UnlockTracker.unlockCard(Swagger.ID);
//        BaseMod.addCard(new SwordDance());
//        UnlockTracker.unlockCard(SwordDance.ID);
        BaseMod.addCard(new Taunt());
        UnlockTracker.unlockCard(Taunt.ID);
        BaseMod.addCard(new Thunder());
        UnlockTracker.unlockCard(Thunder.ID);
        BaseMod.addCard(new Toxic());
        UnlockTracker.unlockCard(Toxic.ID);
        BaseMod.addCard(new ToxicSpikes());
        UnlockTracker.unlockCard(ToxicSpikes.ID);
        BaseMod.addCard(new WeatherBall());
        UnlockTracker.unlockCard(WeatherBall.ID);
        BaseMod.addCard(new WorrySeed());
        UnlockTracker.unlockCard(WorrySeed.ID);
        BaseMod.addCard(new DoubleTeam());
        UnlockTracker.unlockCard(DoubleTeam.ID);
        BaseMod.addCard(new AcidSpray());
        UnlockTracker.unlockCard(AcidSpray.ID);
        BaseMod.addCard(new Confide());
        UnlockTracker.unlockCard(Confide.ID);
        BaseMod.addCard(new Synthesis());
        UnlockTracker.unlockCard(Synthesis.ID);
        BaseMod.addCard(new KingsShield());
        UnlockTracker.unlockCard(KingsShield.ID);
        BaseMod.addCard(new WideGuard());
        UnlockTracker.unlockCard(WideGuard.ID);
        /*BaseMod.addCard(new Stockpile());
        UnlockTracker.unlockCard(Stockpile.ID);*/
        BaseMod.addCard(new MetalBurst());
        UnlockTracker.unlockCard(MetalBurst.ID);
        BaseMod.addCard(new Swallow());
        UnlockTracker.unlockCard(Swallow.ID);
        /*BaseMod.addCard(new SpitUp());
        UnlockTracker.unlockCard(SpitUp.ID);
        BaseMod.addCard(new SpitUpSwallow());
        UnlockTracker.unlockCard(SpitUpSwallow.ID);*/
        BaseMod.addCard(new ShiftGear());
        UnlockTracker.unlockCard(ShiftGear.ID);
        BaseMod.addCard(new FollowMe());
        UnlockTracker.unlockCard(FollowMe.ID);
        BaseMod.addCard(new PoisonSting());
        UnlockTracker.unlockCard(PoisonSting.ID);
        BaseMod.addCard(new StickyWeb());
        UnlockTracker.unlockCard(StickyWeb.ID);
        BaseMod.addCard(new DiamondStorm());
        UnlockTracker.unlockCard(DiamondStorm.ID);
//        BaseMod.addCard(new AcidArmor());
//        UnlockTracker.unlockCard(AcidArmor.ID);
        BaseMod.addCard(new BanefulBunker());
        UnlockTracker.unlockCard(BanefulBunker.ID);
        BaseMod.addCard(new Superpower());
        UnlockTracker.unlockCard(Superpower.ID);
        BaseMod.addCard(new SeedFlare());
        UnlockTracker.unlockCard(SeedFlare.ID);
        BaseMod.addCard(new DefenseCurl());
        UnlockTracker.unlockCard(DefenseCurl.ID);
        BaseMod.addCard(new Payback());
        UnlockTracker.unlockCard(Payback.ID);
        BaseMod.addCard(new FoulPlay());
        UnlockTracker.unlockCard(FoulPlay.ID);
        BaseMod.addCard(new QuickGuard());
        UnlockTracker.unlockCard(QuickGuard.ID);
        BaseMod.addCard(new ShellTrap());
        UnlockTracker.unlockCard(ShellTrap.ID);
        BaseMod.addCard(new TeeterDance());
        UnlockTracker.unlockCard(TeeterDance.ID);
        BaseMod.addCard(new Wish());
        UnlockTracker.unlockCard(Wish.ID);
        BaseMod.addCard(new AerialAce());
        UnlockTracker.unlockCard(AerialAce.ID);
        BaseMod.addCard(new RagePowder());
        UnlockTracker.unlockCard(RagePowder.ID);
        BaseMod.addCard(new Moonlight());
        UnlockTracker.unlockCard(Moonlight.ID);
        BaseMod.addCard(new Flatter());
        UnlockTracker.unlockCard(Flatter.ID);
        BaseMod.addCard(new GrassyTerrain());
        UnlockTracker.unlockCard(GrassyTerrain.ID);
        BaseMod.addCard(new Assist());
        UnlockTracker.unlockCard(Assist.ID);
        BaseMod.addCard(new SuckerPunch());
        UnlockTracker.unlockCard(SuckerPunch.ID);
        BaseMod.addCard(new SuckerPunch());
        UnlockTracker.unlockCard(SuckerPunch.ID);
        BaseMod.addCard(new MagnetRise());
        UnlockTracker.unlockCard(MagnetRise.ID);
        BaseMod.addCard(new Defog());
        UnlockTracker.unlockCard(Defog.ID);
        BaseMod.addCard(new HelpingHand());
        UnlockTracker.unlockCard(HelpingHand.ID);
        BaseMod.addCard(new MorningSun());
        UnlockTracker.unlockCard(MorningSun.ID);
        BaseMod.addCard(new AfterYou());
        UnlockTracker.unlockCard(AfterYou.ID);


        logger.info("Done adding cards!");
    }



    // There are better ways to do this than listing every single individual card, but I do not want to complicate things
    // in a "tutorial" mod. This will do and it's completely ok to use. If you ever want to clean up and
    // shorten all the imports, go look take a look at other mods, such as Hubris.
    
    // ================ /ADD CARDS/ ===================

    @SpireEnum
    public static AbstractCard.CardTags SEED;

    // ================ LOAD THE TEXT ===================

    private static String getLanguageString() {
//        switch (Settings.language) {
//            case FRA:
//                return "fra";
//            case ZHS:
//                return "zhs";
//            default:
                return "eng";
        //}
    }

    @Override
    public void receiveEditStrings() {
        String language = getLanguageString();
        String l10nPath = getModID() + "Resources/localization/";
        BaseMod.loadCustomStringsFile(RelicStrings.class, l10nPath + language + "/Relic-Strings.json");
        BaseMod.loadCustomStringsFile(CardStrings.class, l10nPath + language + "/Card-Strings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, l10nPath + language + "/Power-Strings.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, l10nPath + language + "/Character-Strings.json");
        BaseMod.loadCustomStringsFile(PotionStrings.class, l10nPath + language + "/Potion-Strings.json");
        BaseMod.loadCustomStringsFile(StanceStrings.class, l10nPath + language + "/Stance-Strings.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, l10nPath + language + "/Ui-String.json");
    }
    
    // ================ /LOAD THE TEXT/ ===================

    /*public static ArrayList<TooltipInfo> resonanceTooltip; //define static list of tooltip info
    static { //static code block, is executed the first time the class is used
        resonanceTooltip = new ArrayList<TooltipInfo>(); //create new instance
        if (Settings.language == Settings.GameLanguage.FRA)
            resonanceTooltip.add(new TooltipInfo("Resonance", "A 3 effets, piochez deux cartes, infligez des dégâts de la valeur de votre Force et gagnez 4 Armure."));
        if (Settings.language == Settings.GameLanguage.ZHS)
            resonanceTooltip.add(new TooltipInfo("共振","当累计 3 层时，抽两张牌, 以力量打击敌人一次并获得4点格挡, 每回合每个敌人只能触发一次."));
        if (Settings.language != Settings.GameLanguage.ENG && Settings.language != Settings.GameLanguage.FRA && Settings.language != Settings.GameLanguage.ZHS)
            resonanceTooltip.add(new TooltipInfo("Resonance", "When at 3 stacks draw two cards, deal damage equal to your Strength and gain 4 Block, can be activated only once per turn per enemy.")); //Add a tooltip to the list
        if (Settings.language == Settings.GameLanguage.ENG)
            resonanceTooltip.add(new TooltipInfo("Resonance", "When at 3 stacks draw two cards, deal damage equal to your Strength and gain 4 Block, can be activated only once per turn per enemy.")); //Add a tooltip to the list
    }*/
    /*public static ArrayList<TooltipInfo> itemTooltip; //define static list of tooltip info
    static { //static code block, is executed the first time the class is used
        itemTooltip = new ArrayList<TooltipInfo>(); //create new instance
        itemTooltip.add(new TooltipInfo("Items affected", "Banshee's veil NL Guinsoo's rageblade NL Haunting guise NL Hextech protobelt NL Iceborn gauntlet NL Lich bane NL Nashor's tooth NL Needlessly large rod NL Trinity force NL Zhonyas hourglass")); //Add a tooltip to the list
    }
    public static ArrayList<TooltipInfo> dupTooltip; //define static list of tooltip info
    static { //static code block, is executed the first time the class is used
        dupTooltip = new ArrayList<TooltipInfo>(); //create new instance
        dupTooltip.add(new TooltipInfo("Powers affected", "Common effects : NL Energized NL Draw more cards on next turn NL Next turn block NL Next turn lose strength NL Next turn lose dexterity NL Next turn gain strength NL Delayed resonance NL Delayed damage" +
                " NL NL Rare effects : NL Keeping momentum NL Next turn death NL Gain an extra turn NL Phantasmal NL Nightmare NL Draw less cards on next turn")); //Add a tooltip to the list
    }*/
    
    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {
        String language = getLanguageString();
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/"+language+"/Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }
    
    // ================ /LOAD THE KEYWORDS/ ===================    
    
    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}
