package group22.viking.game.view;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;

public class LeaderboardView extends View {

    // buttons
    private TextButton exitButton;

    // labels
    private Label highscoreLabel;

    // tables
    private Table leaderboardTable;

    //images
    Image wizardSurprised;
    Image medal;

    public LeaderboardView(SpriteBatch batch, Camera camera) {
        super(batch, camera);
        this.init();
    }

    private void createBackground(){
        medal = new Image(Assets.getTexture(Assets.MEDAL));
        medal.setSize(400,400);
        medal.setPosition(VikingGame.SCREEN_WIDTH-medal.getWidth()-50,50);
        medal.rotateBy(30f);

        wizardSurprised = new Image(Assets.getTexture(Assets.WIZARD_SPRITE_SURPRISED));
        wizardSurprised.setSize(900,900);
        wizardSurprised.setPosition(30,-1200);
        wizardSurprised.rotateBy(20f);

        stage.addActor(medal);
        stage.addActor(wizardSurprised);
    }

    @Override
    public void init() {
        // stage clear to make sure there aren't any further animations
        stage.clear();

        createBackground();
        createButtons();
        createLabels();

        // TODO continue...

        runInitialAnimations();

        stage.act(0);
    }

    private void createButtons() {

        exitButton = ViewComponentFactory.createTextButton(
                "Exit",
                new Vector2(150, VikingGame.SCREEN_HEIGHT - 200),
                ViewComponentFactory.VERY_SMALL_BUTTON_SIZE
        );

        stage.addActor(exitButton);

    }

    private void createLabels() {
        highscoreLabel = ViewComponentFactory.createLabel100(
                "Highscore",
                new Vector2(VikingGame.SCREEN_WIDTH/2-260,VikingGame.SCREEN_HEIGHT - 200)
        );
        highscoreLabel.setColor(Color.WHITE);
        highscoreLabel.setAlignment(Align.center);

        stage.addActor(highscoreLabel);
    }

    public void createLeaderboardTable(Array<String> names, Array<String> highscores, int localPlayerPosition) {
        float paddingBottom = 40;
        float paddingLeft = 50;

        leaderboardTable = ViewComponentFactory.createTable();

        leaderboardTable.add(new Label("Rank", ViewComponentFactory.createSkin48())).padBottom(paddingBottom);
        leaderboardTable.add(new Label("Name", ViewComponentFactory.createSkin48())).width(500).padLeft(paddingLeft).padBottom(paddingBottom);
        leaderboardTable.add(new Label("Score", ViewComponentFactory.createSkin48())).padBottom(paddingBottom);

        for(int i = 0; i < names.size; i++) {
            leaderboardTable.row();
            leaderboardTable.add(new Label("" + (i + 1), ViewComponentFactory.createSkin48()));
            if(i == localPlayerPosition) {
                Label localPlayerLabel = new Label(names.get(i), ViewComponentFactory.createSkin48());
                localPlayerLabel.setColor(new Color(128f/255f, 170f/255f, 85f/255f, 1f));
                leaderboardTable.add(localPlayerLabel).width(500).padLeft(paddingLeft);
            } else {
                leaderboardTable.add(new Label(names.get(i), ViewComponentFactory.createSkin48())).width(500).padLeft(paddingLeft);
            }
            leaderboardTable.add(new Label(highscores.get(i), ViewComponentFactory.createSkin48()));
        }

        leaderboardTable.setPosition(VikingGame.SCREEN_WIDTH/2,VikingGame.SCREEN_HEIGHT/2-70);

        stage.addActor(leaderboardTable);
    }

    @Override
    public void runInitialAnimations() {
        exitButton.addAction(ViewComponentFactory.createFadeInAction());
        medal.addAction(ViewComponentFactory.createFadeInAction());
        wizardSurprised.addAction(ViewComponentFactory.createAvatarSwooshAnimation(
                new Vector2(0,1),
                new Vector2(0,900)
        ));

    }

    @Override
    void drawElements(float deltaTime) {
        stage.act(deltaTime);
        stage.draw();
    }

    public TextButton getExitButton() {
        return exitButton;
    }

    public Table getLeaderboardTable() {
        return leaderboardTable;
    }
}
