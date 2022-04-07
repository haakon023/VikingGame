package group22.viking.game.view;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;

public class LeaderboardView extends View {

    //buttons
    private TextButton exitButton;

    public LeaderboardView(SpriteBatch batch, Camera camera) {
        super(batch);

        this.stage = new Stage(new FitViewport(VikingGame.SCREEN_WIDTH, VikingGame.SCREEN_HEIGHT, camera));

        this.init();
    }

    @Override
    public void init() {
        // stage clear to make sure there aren't any further animations
        stage.clear();

        createButtons();

        // TODO continue...

        runInitialAnimations();

        stage.act(0);
    }

    private void createButtons() {
        Vector2 verySmallButtonSize = new Vector2(120, 120);
        Vector2 smallButtonSize = new Vector2(150, 150);
        Vector2 bigButtonSize =  new Vector2(700, 150);
        Vector2 profileImageSize = new Vector2(500, 500);

        //exit button for prodile and leaderboard
        Vector2 exitButtonSize = new Vector2(150,VikingGame.SCREEN_HEIGHT-300);

        exitButton = factory.createTextButton(
                "<",
                new Vector2(50, 150),
                exitButtonSize
        );

        stage.addActor(exitButton);

    }

    @Override
    public void runInitialAnimations() {
        exitButton.addAction(factory.FADE_IN_ANIMATION);
    }

    @Override
    void drawElements(float deltaTime) {
        Assets.FONT48.draw(batch, "Leaderboard State", 20,80);
        stage.act(deltaTime);
        stage.draw();
    }

    /*
    @Override
    public void dispose() {
        super.dispose();
        //stage.dispose();
    }
    */


    public TextButton getExitButton() {
        return exitButton;
    }


}