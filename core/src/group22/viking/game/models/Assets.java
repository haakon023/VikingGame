package group22.viking.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

import group22.viking.game.view.ViewComponentFactory;

public class Assets {

    public static AssetManager assetManager = new AssetManager();

    // language
    @SuppressWarnings("ConstantLocale")
    private static final I18NBundle TRANSLATION = I18NBundle.createBundle(Gdx.files.internal("i18n/app"), Locale.getDefault());

    // fonts
    public static final BitmapFont FONT28 = ViewComponentFactory.generateFont(28, 5);
    public static final BitmapFont FONT48 = ViewComponentFactory.generateFont(48, 5);
    public static final BitmapFont FONT100 = ViewComponentFactory.generateFont(100, 10);

    // background
    public static final String CASTLE = "img/castle.png";
    public static final String GOAT_ICON = "img/GoatIcon.png";
    public static final String STOP_HEADER = "img/stopHeader.png";
    public static final String VIKING_HEADER = "img/vikingHeader.png";
    public static final String VIKING_SHIP = "img/vikingShip.png";
    public static final String WAVE_DARK = "img/waveDark.png";
    public static final String WAVE_MEDIUM = "img/waveMedium.png";
    public static final String WAVE_LIGHT = "img/waveLight.png";
    public static final String WAVE_VERY_LIGHT = "img/waveVeryLight.png";

    // character sprites
    public static final String KNIGHT_SPRITE = "img/KnightSprite.png";
    public static final String KNIGHT_SPRITE_HEAD = "img/KnightSpriteHead.png";

    public static final String WARRIOR_WOMAN_SPRITE = "img/WarriorWomanSprite.png";
    public static final String WARRIOR_WOMAN_SPRITE_HEAD = "img/WarriorWomanSpriteHead.png";

    public static final String WIZARD_SPRITE = "img/WizardSprite.png";
    public static final String WIZARD_SPRITE_HEAD = "img/WizardSpriteHead.png";

    public static final String ROBIN_HOOD_SPRITE = "img/RobinHoodSprite.png";
    public static final String ROBIN_HOOD_SPRITE_HEAD = "img/RobinHoodSpriteHead.png";

    public static final String LEGOLAS_SPRITE = "img/legolas.png";
    public static final String LEGOLAS_SPRITE_HEAD = "img/LegolasHead.png";

    public static final String QUESTIONMARK = "img/Questionmark.png";
    public static final int NUMBER_OF_AVATARS = 5;

    //Projectile sprites
    public static final String ARROW_SPRITE = "img/arrow-dummy.png";


    // main game background
    public static final String OCEAN_BACK = "img/OceanBack.png";
    public static final String OCEAN_TOP = "img/OceanTop.png";
    public static final String WAVE_BOTTOM = "img/WaveBottom.png";
    public static final String ISLAND = "img/Island.png";
    public static final String WAVE_TOP = "img/WaveTop.png";
    public static final String MONASTERY = "img/Monastery.png";

    // ui files
    public static String UI_SKIN = "ui/uiskin.atlas";

    public static void load() {

        // ?
        assetManager.load("ui/uiskin.atlas", TextureAtlas.class);

        //background
        assetManager.load(CASTLE, Texture.class);
        assetManager.load(GOAT_ICON, Texture.class);
        assetManager.load(STOP_HEADER, Texture.class);
        assetManager.load(VIKING_HEADER, Texture.class);
        assetManager.load(VIKING_SHIP, Texture.class);
        assetManager.load(WAVE_DARK, Texture.class);
        assetManager.load(WAVE_MEDIUM, Texture.class);
        assetManager.load(WAVE_LIGHT, Texture.class);
        assetManager.load(WAVE_VERY_LIGHT, Texture.class);

        //character sprites
        assetManager.load(KNIGHT_SPRITE, Texture.class);
        assetManager.load(KNIGHT_SPRITE_HEAD, Texture.class);
        assetManager.load(WARRIOR_WOMAN_SPRITE, Texture.class);
        assetManager.load(WARRIOR_WOMAN_SPRITE_HEAD, Texture.class);
        assetManager.load(WIZARD_SPRITE, Texture.class);
        assetManager.load(WIZARD_SPRITE_HEAD, Texture.class);
        assetManager.load(ROBIN_HOOD_SPRITE, Texture.class);
        assetManager.load(ROBIN_HOOD_SPRITE_HEAD, Texture.class);
        assetManager.load(LEGOLAS_SPRITE, Texture.class);
        assetManager.load(LEGOLAS_SPRITE_HEAD, Texture.class);

        assetManager.load(QUESTIONMARK, Texture.class);

        //projectile sprites
        assetManager.load(ARROW_SPRITE, Texture.class);


        //main game background
        assetManager.load(OCEAN_BACK, Texture.class);
        assetManager.load(OCEAN_TOP, Texture.class);
        assetManager.load(WAVE_BOTTOM, Texture.class);
        assetManager.load(ISLAND, Texture.class);
        assetManager.load(WAVE_TOP, Texture.class);
        assetManager.load(MONASTERY, Texture.class);

        //ui files
        // assetManager.load("ui/uiskin.atlas", TextureAtlas.class);

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
                Assets.KNIGHT_SPRITE,
                Assets.WIZARD_SPRITE,
                Assets.WARRIOR_WOMAN_SPRITE,
                Assets.ROBIN_HOOD_SPRITE,
                Assets.LEGOLAS_SPRITE
        }[index];
    }

    public static String getAvatarHead(int index) {
        return new String[]{
                Assets.KNIGHT_SPRITE_HEAD,
                Assets.WIZARD_SPRITE_HEAD,
                Assets.WARRIOR_WOMAN_SPRITE_HEAD,
                Assets.ROBIN_HOOD_SPRITE_HEAD,
                Assets.LEGOLAS_SPRITE_HEAD
        }[index];
    }

    public static String translate(String key) {
        return TRANSLATION.get(key);
    }

    public static String t(String key) {
        return translate(key);
    }

}
