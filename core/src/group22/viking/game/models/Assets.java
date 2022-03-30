package group22.viking.game.models;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import group22.viking.game.view.components.Font;

public class Assets {

    public static AssetManager assetManager = new AssetManager();

    // fonts
    public static final Font FONT48 = new Font(48, 5);
    public static final Font FONT100 = new Font(100, 10);

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
        assetManager.load("img/GoatIcon.png", Texture.class);
        assetManager.load("img/stopHeader.png", Texture.class);
        assetManager.load("img/vikingHeader.png", Texture.class);
        assetManager.load("img/vikingShip.png", Texture.class);
        assetManager.load("img/waveDark.png", Texture.class);
        assetManager.load("img/waveMedium.png", Texture.class);
        assetManager.load("img/waveLight.png", Texture.class);
        assetManager.load("img/waveVeryLight.png", Texture.class);

        //character sprites
        assetManager.load("img/KnightSprite.png", Texture.class);
        assetManager.load("img/KnightSpriteHead.png", Texture.class);
        assetManager.load("img/WarriorWomanSprite.png", Texture.class);
        assetManager.load("img/WarriorWomanSpriteHead.png", Texture.class);
        assetManager.load("img/WizardSprite.png", Texture.class);
        assetManager.load("img/WizardSpriteHead.png", Texture.class);

        //main game background
        assetManager.load("img/OceanBack.png", Texture.class);
        assetManager.load("img/OceanTop.png", Texture.class);
        assetManager.load("img/WaveBottom.png", Texture.class);
        assetManager.load("img/Island.png", Texture.class);
        assetManager.load("img/WaveTop.png", Texture.class);
        assetManager.load("img/Monastery.png", Texture.class);

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

}
