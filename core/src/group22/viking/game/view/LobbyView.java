package group22.viking.game.view;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;

public class LobbyView extends View {

    private Stage stage;

    private Image player1;
    private Image player2;

    private TextButton playButton;
    private TextButton exitButton;

    private ShapeRenderer shapeRenderer;

    public LobbyView(SpriteBatch batch, Camera camera) {
        super(batch);
        this.stage = new Stage(new FitViewport(VikingGame.SCREEN_WIDTH,VikingGame.SCREEN_HEIGHT, camera));
        this.shapeRenderer = new ShapeRenderer();

        this.init();
    }


    @Override
    public void init() {
        //stage clear to make sure there aren't any further animations
        stage.clear();

        // TODO continue...

        //todo get profile sprite information from user
        //todo set player2 once the second player has joined
        player1 = new Image(Assets.getTexture("img/WizardSprite.png"));
        player1.setPosition(0,0);
        player1.setWidth(VikingGame.SCREEN_WIDTH/2);
        player1.setHeight(VikingGame.SCREEN_HEIGHT);
        player2 = new Image(Assets.getTexture("img/KnightSprite.png"));
        player2.setPosition(VikingGame.SCREEN_WIDTH/2,0);
        player2.setWidth(VikingGame.SCREEN_WIDTH/2);
        player2.setHeight(VikingGame.SCREEN_HEIGHT);

        stage.addActor(player1);
        stage.addActor(player2);

        createButtons();

        runInitialAnimations();

        stage.act(0);
    }

    @Override
    public void runInitialAnimations() {
        
    }


    @Override
    void drawElements(float deltaTime) {

        //shapeRenderer (use it like a batch)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(VikingGame.SCREEN_WIDTH/2-15,0,
                30,VikingGame.SCREEN_HEIGHT);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.end();

        Assets.FONT48.draw(batch, "Lobby State", 20,80);
        Assets.FONT100.draw(batch, "0", VikingGame.SCREEN_WIDTH/2-220,200);
        Assets.FONT100.draw(batch, "0", VikingGame.SCREEN_WIDTH/2+150,200);

        stage.act(deltaTime);
        stage.draw();
    }



    private void createButtons() {
        Vector2 verySmallButtonSize = new Vector2(120, 120);
        Vector2 smallButtonSize = new Vector2(150, 150);
        Vector2 bigButtonSize =  new Vector2(700, 150);
        Vector2 profileImageSize = new Vector2(500, 500);

        playButton = ViewComponentFactory.createTextButton(
                "Play",
                new Vector2(VikingGame.SCREEN_WIDTH/2-600/2,VikingGame.SCREEN_HEIGHT/2-150/2),
                bigButtonSize
        );


        exitButton = ViewComponentFactory.createTextButton(
                "Exit",
                new Vector2(150,VikingGame.SCREEN_HEIGHT-200),
                verySmallButtonSize
        );


        stage.addActor(playButton);
        stage.addActor(exitButton);
    }

    /*
    @Override
    public void dispose() {
        super.dispose();
        //stage.dispose();
    }
    */

    public TextButton getPlayButton() {
        return playButton;
    }

    public TextButton getExitButton() {
        return exitButton;
    }
}
