package group22.viking.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

import group22.viking.game.view.LeaderboardView;
import group22.viking.game.view.LobbyView;
import group22.viking.game.view.MenuView;
import group22.viking.game.view.PlayView;
import group22.viking.game.view.ProfileSettingsView;
import group22.viking.game.view.ViewComponentFactory;

public class Assets {

    public static AssetManager assetManager = new AssetManager();

    /*
    View need to be created here, that all GUI operations can also be called from server listeners,
    which are not part of the main-thread.
     */
    public static LeaderboardView leaderboardView;
    public static PlayView playView;
    public static LobbyView lobbyView;
    public static MenuView menuView;
    public static ProfileSettingsView profileSettingsView;

    // language
    @SuppressWarnings("ConstantLocale")
    private static final I18NBundle TRANSLATION = I18NBundle.createBundle(Gdx.files.internal("i18n/app"), Locale.getDefault());

    // fonts
    public static final BitmapFont FONT28 = ViewComponentFactory.generateFont(28, 5);
    public static final BitmapFont FONT48 = ViewComponentFactory.generateFont(48, 5);
    public static final BitmapFont FONT100 = ViewComponentFactory.generateFont(100, 10);

    // background
    public static final String CASTLE = "img/castle.png";
    public static final String GOATICON = "img/GoatIcon.png";
    public static final String STOPHEADER = "img/stopHeader.png";
    public static final String VIKINGHEADER = "img/vikingHeader.png";
    public static final String VIKINGSHIP = "img/vikingShip.png";
    public static final String WAVEDARK = "img/waveDark.png";
    public static final String WAVEMEDIUM = "img/waveMedium.png";
    public static final String WAVELIGHT = "img/waveLight.png";
    public static final String WAVEVERYLIGHT = "img/waveVeryLight.png";

    // character sprites
    public static final String KNIGHTSPRITE = "img/KnightSprite.png";
    public static final String KNIGHTSPRITEHEAD = "img/KnightSpriteHead.png";
    public static final String WARRIORWOMANSPRITE = "img/WarriorWomanSprite.png";
    public static final String WARRIORWOMANSPRITEHEAD = "img/WarriorWomanSpriteHead.png";
    public static final String WIZARDSPRITE = "img/WizardSprite.png";
    public static final String WIZARDSPRITEHEAD = "img/WizardSpriteHead.png";
    public static final String QUESTIONMARK = "img/Questionmark.png";
    public static final int NUMBER_OF_AVATARS = 3;

    //Projectile sprites
    public static final String ARROW_SPRITE = "img/arrow-dummy.png";


    // main game background
    public static final String OCEANBACK = "img/OceanBack.png";
    public static final String OCEANTOP = "img/OceanTop.png";
    public static final String WAVEBOTTOM = "img/WaveBottom.png";
    public static final String ISLAND = "img/Island.png";
    public static final String WAVETOP = "img/WaveTop.png";
    public static final String MONASTERY = "img/Monastery.png";

    // ui files
    public static String UI_SKIN = "ui/uiskin.atlas";

    public static void load() {

        // ?
        assetManager.load("ui/uiskin.atlas", TextureAtlas.class);

        //background
        assetManager.load(CASTLE, Texture.class);
        assetManager.load(GOATICON, Texture.class);
        assetManager.load(STOPHEADER, Texture.class);
        assetManager.load(VIKINGHEADER, Texture.class);
        assetManager.load(VIKINGSHIP, Texture.class);
        assetManager.load(WAVEDARK, Texture.class);
        assetManager.load(WAVEMEDIUM, Texture.class);
        assetManager.load(WAVELIGHT, Texture.class);
        assetManager.load(WAVEVERYLIGHT, Texture.class);

        //character sprites
        assetManager.load(KNIGHTSPRITE, Texture.class);
        assetManager.load(KNIGHTSPRITEHEAD, Texture.class);
        assetManager.load(WARRIORWOMANSPRITE, Texture.class);
        assetManager.load(WARRIORWOMANSPRITEHEAD, Texture.class);
        assetManager.load(WIZARDSPRITE, Texture.class);
        assetManager.load(WIZARDSPRITEHEAD, Texture.class);
        assetManager.load(QUESTIONMARK, Texture.class);

        //projectile sprites
        assetManager.load(ARROW_SPRITE, Texture.class);


        //main game background
        assetManager.load(OCEANBACK, Texture.class);
        assetManager.load(OCEANTOP, Texture.class);
        assetManager.load(WAVEBOTTOM, Texture.class);
        assetManager.load(ISLAND, Texture.class);
        assetManager.load(WAVETOP, Texture.class);
        assetManager.load(MONASTERY, Texture.class);

        //ui files
        // assetManager.load("ui/uiskin.atlas", TextureAtlas.class);

    }

    public static void createViews(SpriteBatch spriteBatch, OrthographicCamera camera) {
        leaderboardView = new LeaderboardView(spriteBatch, camera);
        playView = new PlayView(spriteBatch, camera);
        lobbyView = new LobbyView(spriteBatch, camera);
        menuView = new MenuView(spriteBatch, camera);
        profileSettingsView = new ProfileSettingsView(spriteBatch, camera);
    }

    public static void dispose(){ assetManager.dispose(); }

    //TODO: implement as necessary
    public static boolean update(){
        return assetManager.update();
    }
    //TODO: implement as necessary, e.g. for loading screen
    public static float getProgress(){
        return assetManager.getProgress();
    }

    public static Texture getTexture(String texture){
        return assetManager.get(texture, Texture.class);
    }
    public static TextureAtlas getTextureAtlas(String textureAtlas) {
        return assetManager.get(textureAtlas, TextureAtlas.class);
    }

    //TODO: implement as necessary
    public static Music getMusic(String path) { return assetManager.get(path, Music.class); }
    //TODO: implement as necessary
    public static Sound getSound(String path) {
        return assetManager.get(path, Sound.class);
    }

    public static String getAvatar(int index) {
        return new String[]{
                Assets.KNIGHTSPRITE,
                Assets.WIZARDSPRITE,
                Assets.WARRIORWOMANSPRITE
        }[index];
    }

    public static String getAvatarHead(int index) {
        return new String[]{
                Assets.KNIGHTSPRITEHEAD,
                Assets.WIZARDSPRITEHEAD,
                Assets.WARRIORWOMANSPRITEHEAD
        }[index];
    }

    public static String translate(String key) {
        return TRANSLATION.get(key);
    }

    public static String t(String key) {
        return translate(key);
    }

}
