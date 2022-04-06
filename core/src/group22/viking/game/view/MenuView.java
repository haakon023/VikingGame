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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.controller.states.PlayState;
import group22.viking.game.view.components.CustomTextButton;

public class MenuView extends View {

    private TextButton tutorialButton;
    private final ViewComponentFactory viewComponentFactory;

    public MenuView(SpriteBatch batch, Camera camera) {
        super(batch);
        this.viewComponentFactory = new ViewComponentFactory();

        this.stage = new Stage(new FitViewport(VikingGame.SCREEN_WIDTH, VikingGame.SCREEN_HEIGHT, camera));

        this.init();
    }

    @Override
    public void init() {
        //stage clear to make sure there aren't any further animations
        stage.clear();

        createButtons();
        // TODO continue...


        stage.act(0);
        runInitialAnimations();
    }

    private void createButtons() {
        Vector2 verySmallButtonSize = new Vector2(120, 120);
        Vector2 smallButtonSize = new Vector2(150, 150);
        Vector2 bigButtonSize =  new Vector2(700, 150);
        Vector2 profileImageSize = new Vector2(500, 500);

        tutorialButton = viewComponentFactory.createTextButton(
                "Tutorial",
                new Vector2(150, VikingGame.SCREEN_HEIGHT/2+80-50),
                bigButtonSize
        );


        stage.addActor(tutorialButton);
    }

    @Override
    public void runInitialAnimations() {
        // TODO
        Action fadeInAnimation = sequence(alpha(0),
                parallel(fadeIn(0.5f),
                        moveBy(0,-20,.5f, Interpolation.pow5Out)
                ));

        tutorialButton.addAction(fadeInAnimation);
    }

    @Override
    public void render(float deltaTime) {
        super.render(deltaTime);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    void drawElements(float deltaTime) {
        System.out.println("DRAW ELEMENTS");
        stage.act(deltaTime);
        stage.draw();
    }

    public TextButton getTutorialButton() {
        return tutorialButton;
    }
}
