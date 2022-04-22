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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import com.badlogic.gdx.graphics.GL20;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.controller.firebase.PlayerStatus;
import group22.viking.game.models.Assets;

public class LobbyView extends View {

    private Image avatarHost;
    private Image avatarGuest;

    private TextButton playButton;
    private TextButton exitButton;

    private final ShapeRenderer shapeRenderer;
    private final ShapeRenderer shapePlayerName;

    private Label idLabel;

    private Label nameLabelHost;
    private Label nameLabelGuest;
    private Label scoreLabelHost;
    private Label scoreLabelGuest;



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

        avatarHost = new Image(Assets.getTexture(Assets.QUESTIONMARK));
        avatarHost.setPosition(0, 120);
        avatarHost.setWidth(VikingGame.SCREEN_WIDTH / 2);
        avatarHost.setHeight(VikingGame.SCREEN_HEIGHT-120);

        avatarGuest = new Image(Assets.getTexture(Assets.QUESTIONMARK));
        avatarGuest.setPosition(VikingGame.SCREEN_WIDTH / 2, 120);
        avatarGuest.setWidth(VikingGame.SCREEN_WIDTH / 2);
        avatarGuest.setHeight(VikingGame.SCREEN_HEIGHT-120);

        stage.addActor(avatarHost);
        stage.addActor(avatarGuest);

        createButtons();
        createLabel();

        runInitialAnimations();

        stage.act(0);
    }

    @Override
    public void runInitialAnimations() {
        exitButton.addAction(ViewComponentFactory.createFadeInAction());
        playButton.addAction(ViewComponentFactory.createFadeInAction());
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
                Assets.t("lobby_button_play"),
                new Vector2(VikingGame.SCREEN_WIDTH / 2 - 700F / 2, VikingGame.SCREEN_HEIGHT / 2 - 150F / 2),
                ViewComponentFactory.BIG_BUTTON_SIZE
        );

        exitButton = ViewComponentFactory.createTextButton(
                Assets.t("all_button_exit"),
                new Vector2(150, VikingGame.SCREEN_HEIGHT - 200),
                ViewComponentFactory.VERY_SMALL_RECT_BUTTON_SIZE
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
        nameLabelHost = ViewComponentFactory.createLabel48(
                "...",
                new Vector2(50,20)
        );
        nameLabelHost.setColor(Color.WHITE);

        scoreLabelHost = ViewComponentFactory.createLabel100(
                "-",
                new Vector2(VikingGame.SCREEN_WIDTH/2-100-100,20)
        );
        scoreLabelHost.setColor(Color.WHITE);



        //player 2
        nameLabelGuest = ViewComponentFactory.createLabel48(
                "...",
                new Vector2(VikingGame.SCREEN_WIDTH-400-20,20)
        );

        nameLabelGuest.setColor(Color.WHITE);
        nameLabelGuest.setVisible(false);

        scoreLabelGuest = ViewComponentFactory.createLabel100(
                "-",
                new Vector2(VikingGame.SCREEN_WIDTH/2+100,20)
        );
        scoreLabelGuest.setColor(Color.WHITE);
        scoreLabelGuest.setVisible(false);

        stage.addActor(idLabel);
        stage.addActor(nameLabelHost);
        stage.addActor(scoreLabelHost);
        stage.addActor(nameLabelGuest);
        stage.addActor(scoreLabelGuest);
    }


    public TextButton getPlayButton() {
        return playButton;
    }

    public TextButton getExitButton() {
        return exitButton;
    }

    public void updateNameLabelGuest(String name) {
        nameLabelGuest.setText(name);
    }

    public void updateNameLabelHost(String name) {
        nameLabelHost.setText(name);
    }

    public void updateScoreLabelGuest(PlayerStatus playerStatus) {
        if(playerStatus == null) {
            scoreLabelGuest.setText("-");
            return;
        }
        scoreLabelGuest.setText(String.valueOf(playerStatus.getWonGames()));
    }

    public void updateScoreLabelHost(PlayerStatus playerStatus) {
        if(playerStatus == null) {
            scoreLabelHost.setText("-");
            return;
        }
        scoreLabelHost.setText(String.valueOf(playerStatus.getWonGames()));
    }

    public void updateAvatarHost(int avatarId) {
        System.out.println("host updated");
        avatarHost.setDrawable(new TextureRegionDrawable(
                Assets.getTexture(Assets.getAvatar(avatarId))
        ));
        avatarHost.setPosition(-1000,0);
    }

    public void updateAvatarGuest(int avatarId) {
        System.out.println("guest updated: " + avatarId);
        avatarGuest.setDrawable(new TextureRegionDrawable(
                Assets.getTexture(Assets.getAvatar(avatarId))
        ));
        avatarGuest.setPosition(VikingGame.SCREEN_WIDTH,0);
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

    public Label getNameLabelGuest() {
        return nameLabelGuest;
    }

    public Label getScoreLabelGuest() {
        return scoreLabelGuest;
    }

    public void resetGuest() {
        this.avatarGuest.setDrawable(new TextureRegionDrawable(Assets.getTexture(Assets.QUESTIONMARK)));
        nameLabelGuest.setVisible(false);
        scoreLabelGuest.setVisible(false);
    }

    public Image getAvatarHost() {
        return avatarHost;
    }

    public Image getAvatarGuest() {
        return avatarGuest;
    }
}