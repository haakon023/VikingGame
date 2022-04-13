package group22.viking.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import com.badlogic.gdx.graphics.GL20;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;

public class LobbyView extends View {

    private Image player1Image;
    private Image player2Image;

    private TextButton playButton;
    private TextButton exitButton;

    private final ShapeRenderer shapeRenderer;
    private final ShapeRenderer shapePlayerName;

    private Label idLabel;

    private Label player1NameLabel;
    private Label player2NameLabel;
    private Label player1ScoreLabel;
    private Label player2ScoreLabel;



    public LobbyView(SpriteBatch batch, Camera camera) {
        super(batch, camera);
        this.shapeRenderer = new ShapeRenderer();
        this.shapePlayerName = new ShapeRenderer();
        this.init();
    }


    @Override
    public void init() {
        //stage clear to make sure there aren't any further animations
        stage.clear();

        player1Image = new Image(Assets.getTexture(Assets.QUESTIONMARK));
        player1Image.setPosition(0, 120);
        player1Image.setWidth(VikingGame.SCREEN_WIDTH / 2);
        player1Image.setHeight(VikingGame.SCREEN_HEIGHT-120);

        player2Image = new Image(Assets.getTexture(Assets.QUESTIONMARK));
        player2Image.setPosition(VikingGame.SCREEN_WIDTH / 2, 120);
        player2Image.setWidth(VikingGame.SCREEN_WIDTH / 2);
        player2Image.setHeight(VikingGame.SCREEN_HEIGHT-120);




        stage.addActor(player1Image);
        stage.addActor(player2Image);

        createButtons();
        createLabel();

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
        shapeRenderer.rect(VikingGame.SCREEN_WIDTH / 2 - 30, 0,
                30, VikingGame.SCREEN_HEIGHT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.setColor(new Color(1,1,1,1f));
        shapeRenderer.rect(VikingGame.SCREEN_WIDTH/2-125,VikingGame.SCREEN_HEIGHT-250,220,100);

        shapeRenderer.end();

        stage.act(deltaTime);
        stage.draw();
    }


    private void createButtons() {

        playButton = ViewComponentFactory.createTextButton(
                "Play",
                new Vector2(VikingGame.SCREEN_WIDTH / 2 - 700F / 2, VikingGame.SCREEN_HEIGHT / 2 - 150F / 2),
                ViewComponentFactory.BIG_BUTTON_SIZE
        );

        exitButton = ViewComponentFactory.createTextButton(
                "Exit",
                new Vector2(150, VikingGame.SCREEN_HEIGHT - 200),
                ViewComponentFactory.VERY_SMALL_BUTTON_SIZE
        );


        stage.addActor(playButton);
        stage.addActor(exitButton);
    }

    private void createLabel(){
        idLabel = ViewComponentFactory.createLabel48(
                ". . .",
                new Vector2(VikingGame.SCREEN_WIDTH/2-55,VikingGame.SCREEN_HEIGHT-220)
        );
        idLabel.setColor(Color.BLACK);
        idLabel.setAlignment(Align.center);


        //player 1
        player1NameLabel = ViewComponentFactory.createLabel48(
                "Player 1",
                new Vector2(50,20)
        );
        player1NameLabel.setColor(Color.WHITE);


        player1ScoreLabel = ViewComponentFactory.createLabel100(
                "0",
                new Vector2(VikingGame.SCREEN_WIDTH/2-100-100,20)
        );
        player1ScoreLabel.setColor(Color.WHITE);



        //player 2
        player2NameLabel = ViewComponentFactory.createLabel48(
                "Player 2",
                new Vector2(VikingGame.SCREEN_WIDTH-400-20,20)
        );

        player2NameLabel.setColor(Color.WHITE);
        player2NameLabel.setVisible(false);


        player2ScoreLabel = ViewComponentFactory.createLabel100(
                "0",
                new Vector2(VikingGame.SCREEN_WIDTH/2+100,20)
        );
        player2ScoreLabel.setColor(Color.WHITE);
        player2ScoreLabel.setVisible(false);






        stage.addActor(idLabel);
        stage.addActor(player1NameLabel);
        stage.addActor(player1ScoreLabel);
        stage.addActor(player2NameLabel);
        stage.addActor(player2ScoreLabel);
    }


    public TextButton getPlayButton() {
        return playButton;
    }

    public TextButton getExitButton() {
        return exitButton;
    }

    public void displayGuestName(String name) {
        player2NameLabel.setText(name);
    }

    public void displayHostName(String name) {
        player1NameLabel.setText(name);
    }

    public void updateShownHost(int avatarId) {
        System.out.println("host updated");
        player1Image.setDrawable(new TextureRegionDrawable(
                Assets.getTexture(Assets.getAvatar(avatarId))
        ));
    }

    public void updateShownGuest(int avatarId) {
        System.out.println("guest updated: " + avatarId);
        player2Image.setDrawable(new TextureRegionDrawable(
                Assets.getTexture(Assets.getAvatar(avatarId))
        ));
    }

    public void enablePlayButton() {
        playButton.setDisabled(false);
    }

    public void disablePlayButton() {
        playButton.setDisabled(true);
    }

    public void hidePlayButton() {
        playButton.setVisible(false);
    }

    public void showPlayButton() {
        playButton.setVisible(true);
    }

    //
    public void printLobbyId(String lobbyId){
        idLabel.setText(lobbyId);
    }

    public Image getPlayer2Image() {
        return player2Image;
    }

    public Label getPlayer2NameLabel() {
        return player2NameLabel;
    }

    public Label getPlayer2ScoreLabel() {
        return player2ScoreLabel;
    }

    public void resetGuest() {
        this.player2Image.setDrawable(new TextureRegionDrawable(Assets.getTexture(Assets.QUESTIONMARK)));
        player2NameLabel.setVisible(false);
        player2ScoreLabel.setVisible(false);
    }

    public void setPlayer2NameLabel(Label player2NameLabel) {
        this.player2NameLabel = player2NameLabel;
    }

    public void setPlayer2ScoreLabel(Label player2ScoreLabel) {
        this.player2ScoreLabel = player2ScoreLabel;
    }
}