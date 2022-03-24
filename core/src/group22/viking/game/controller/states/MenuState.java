package group22.viking.game.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.JViewport;

import group22.viking.game.controller.GameStateManager;

public class MenuState extends State {

    // TODO: 3/24/2022  replace with singleton
    private float screenWidth = Gdx.graphics.getWidth();
    private float screenHeight = Gdx.graphics.getHeight();

    //header
    private Texture vikingHeader;
    private Texture stopHeader;
    private float counter = 0;
    private boolean toBeStamped = false;

    //for background scene
    private Texture waveDark;
    private Texture waveMedium;
    private Texture waveLight;
    private Texture waveVeryLight;
    private Texture vikingShip;
    private Texture castle;

    //for wave movement
    private float x1 = 0;
    private float x2 = 0;

    //for boat movement
    private float x3 = 0;
    private float y3 = 0;
    boolean movingRight = true;

    //for castle movement
    private float y4 = 0;
    private boolean movingUp = true;




    private Texture title;

    //main Buttons
    private Texture tutorialBtn;
    private Texture practiceBtn;
    private Texture joinBtn;
    private Texture hostBtn;
    private Texture profileBtn;

    //small buttons
    private Texture leaderboardBtn;
    private Texture unmutedBtn;
    private Texture mutedBtn;


    public MenuState(GameStateManager gsm) {
        super(gsm);

        //header
        vikingHeader = new Texture("vikingHeader.png");
        stopHeader = new Texture("stopHeader.png");

        //background
        waveDark = new Texture("waveDark.png");
        castle = new Texture("castle.png");
        waveMedium = new Texture("waveMedium.png");
        vikingShip = new Texture("vikingShip.png");
        waveLight = new Texture("waveLight.png");
        waveVeryLight = new Texture("waveVeryLight.png");

        //main buttons
        tutorialBtn = new Texture("button.png");
        practiceBtn = new Texture("button.png");
        hostBtn = new Texture("button.png");
        joinBtn = new Texture("button.png");
        profileBtn = new Texture("button.png");

        //small buttons
        leaderboardBtn = new Texture("leaderboardButton.png");
        unmutedBtn = new Texture("unmutedButton.png");
        mutedBtn = new Texture("mutedButton.png");




    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();

        //let each imaage move 300PX before resetting the image back to 0, 0



    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin(); //Rendering goes below here




        //draw scene
        spriteBatch.draw(waveDark,-600 +(x1/3),0);
        spriteBatch.draw(castle,1500,290+(y4/10),600,500);
        spriteBatch.draw(waveMedium,-300+(x2/3),0);
        spriteBatch.draw(vikingShip,200+(x3/3),200+(y3/6), 600, 500);
        spriteBatch.draw(waveLight,-600+(x1/3),0);
        spriteBatch.draw(waveVeryLight,-400+(x2/3),0);
        x1++;
        x2 += 2;
        if((x1/3) % 296 == 0){x1 = 0;}
        if((x2/3) % 296 == 0){x2 = 0;}
        //rock ship
        if(movingRight){
            x3++;
            y3++;
            if(x3 == 15){
                movingRight = false;
            }
        }else{
            x3--;
            y3--;
            if(x3 == -15){
                movingRight = true;
            }
        }
        //move island
        if(movingUp){

            y4++;
            if(x3 == 7){
                movingUp = false;
            }
        }else{
            y4--;
            if(x3 == -7){
                movingUp = true;
            }
        }


        //draw header
        spriteBatch.draw(vikingHeader,screenWidth/2-430,screenHeight -250,660,200);
        if (counter == 120) {
            toBeStamped = true;
        }
        if(toBeStamped){
            spriteBatch.draw(stopHeader,screenWidth/2,screenHeight -380,430,300);
        }
        counter++;


        //main buttons
        spriteBatch.draw(tutorialBtn,150,screenHeight/2+80-50,700,150);
        spriteBatch.draw(practiceBtn,150,screenHeight/2-80-150-50,700,150);
        spriteBatch.draw(profileBtn,screenWidth/2-150,screenHeight/2-150-50,300,300);
        spriteBatch.draw(joinBtn,screenWidth/2+(screenWidth/2-700-150),screenHeight/2+80-50,700,150);
        spriteBatch.draw(hostBtn,screenWidth/2+(screenWidth/2-700-150),screenHeight/2-80-150-50,700,150);


        //mini buttons
        spriteBatch.draw(leaderboardBtn,screenWidth-120-60-120-60,50,120,120);
        spriteBatch.draw(unmutedBtn,screenWidth-120-60,50,120,120);



        spriteBatch.end();
    }

    @Override
    public void dispose() {

        //background.dispose();
        title.dispose();

        tutorialBtn.dispose();
        practiceBtn.dispose();
        joinBtn.dispose();
        hostBtn.dispose();
        profileBtn.dispose();

        leaderboardBtn.dispose();
        unmutedBtn.dispose();
        mutedBtn.dispose();
    }
}
