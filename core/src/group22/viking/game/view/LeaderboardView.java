package group22.viking.game.view;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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

    public LeaderboardView(SpriteBatch batch, Camera camera) {
        super(batch, camera);

        this.init();
    }

    @Override
    public void init() {
        // stage clear to make sure there aren't any further animations
        stage.clear();

        createButtons();
        createLabels();

        // TODO continue...

        runInitialAnimations();

        stage.act(0);
    }

    private void createButtons() {

        //exit button for profile and leaderboard
        Vector2 exitButtonSize = new Vector2(150,VikingGame.SCREEN_HEIGHT-300);

        exitButton = ViewComponentFactory.createTextButton(
                "<",
                new Vector2(50, 150),
                exitButtonSize
        );

        stage.addActor(exitButton);

    }

    private void createLabels() {
        highscoreLabel = ViewComponentFactory.createLabel48(
                "Highscore",
                new Vector2(VikingGame.SCREEN_WIDTH/2-55,VikingGame.SCREEN_HEIGHT-120)
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

        leaderboardTable.setPosition(VikingGame.SCREEN_WIDTH/2,VikingGame.SCREEN_HEIGHT/2);

        stage.addActor(leaderboardTable);
    }

    @Override
    public void runInitialAnimations() {
        exitButton.addAction(ViewComponentFactory.createFadeInAction());
    }

    @Override
    void drawElements(float deltaTime) {
        Assets.FONT48.draw(batch, "Leaderboard State", 20,80);
        stage.act(deltaTime);
        stage.draw();
    }

    public TextButton getExitButton() {
        return exitButton;
    }
}
