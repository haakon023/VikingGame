package group22.viking.game.view;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.controller.states.MenuState;
import group22.viking.game.models.Assets;
import group22.viking.game.view.components.CustomTextButton;
import group22.viking.game.view.components.CustomTextField;

public class ProfileSettingsView extends View {

    //Images
    private Image profileImage;

    //buttons
    private TextButton exitButton;
    private TextButton leftButton;
    private TextButton rightButton;
    private TextButton changeNameButton;

    //Text Fields
    private TextField nameField;

    private final ViewComponentFactory viewComponentFactory;

    public ProfileSettingsView(SpriteBatch batch, Camera camera) {
        super(batch);
        this.viewComponentFactory = new ViewComponentFactory();

        this.stage = new Stage(new FitViewport(VikingGame.SCREEN_WIDTH, VikingGame.SCREEN_HEIGHT, camera));

        this.init();
    }

    @Override
    public void init() {
        // stage clear to make sure there aren't any further animations
        stage.clear();

        //todo get img file from db or json log file
        profileImage = new Image(Assets.getTexture("img/WizardSpriteHead.png"));
        profileImage.setWidth(400);
        profileImage.setHeight(400);
        profileImage.setPosition(VikingGame.SCREEN_WIDTH/4,
                VikingGame.SCREEN_HEIGHT-profileImage.getHeight()-150);
        profileImage.addAction(sequence(alpha(0),parallel(fadeIn(0.5f),moveBy(0,-20,.5f, Interpolation.pow5Out))));
        stage.addActor(profileImage);




        createButtons();
        createTextField();
        // TODO continue...

        runInitialAnimations();

        stage.act(0);
    }


    private void createButtons() {
        Vector2 verySmallButtonSize = new Vector2(120, 120);
        Vector2 smallButtonSize = new Vector2(150, 150);
        Vector2 bigButtonSize =  new Vector2(700, 150);
        Vector2 profileImageSize = new Vector2(500, 500);

        //only for profile
        Vector2 carouselButtonSize = new Vector2(80,profileImage.getHeight());

        //for prodile and leaderboard
        Vector2 exitButtonSize = new Vector2(150,VikingGame.SCREEN_HEIGHT-300);

        exitButton = new CustomTextButton(
                "<",
                new Vector2(50, 150),
                exitButtonSize
        );


        leftButton = new CustomTextButton(
                "<",
                new Vector2(profileImage.getX()-carouselButtonSize.x,
                        VikingGame.SCREEN_HEIGHT-profileImage.getHeight()-150),
                carouselButtonSize
        );


        rightButton = new CustomTextButton(
                ">",
                new Vector2(profileImage.getX()+profileImage.getWidth(),
                        VikingGame.SCREEN_HEIGHT-profileImage.getHeight()-150),
                carouselButtonSize
        );

        changeNameButton = new CustomTextButton(
                "Submit",
                new Vector2(profileImage.getX()+profileImage.getWidth()+carouselButtonSize.x+100+600+50,
                        VikingGame.SCREEN_HEIGHT-profileImage.getHeight()-150),
                new Vector2(600/3,150)
        );



        stage.addActor(exitButton);
        stage.addActor(leftButton);
        stage.addActor(rightButton);
        stage.addActor(changeNameButton);

    }

    private void createTextField() {

        nameField = new CustomTextField(
                "",
                new Vector2(profileImage.getX()+profileImage.getWidth()+rightButton.getWidth()+100,
                        VikingGame.SCREEN_HEIGHT-profileImage.getHeight()-150),
                new Vector2(600, 150)
        );

        //todo get name from Database or json log file
        nameField.setText("Caio");

        stage.addActor(nameField);

    }


    @Override
    public void runInitialAnimations() {
        Action fadeInAnimation = sequence(alpha(0),
                parallel(fadeIn(0.5f),
                        moveBy(0,-20,.5f, Interpolation.pow5Out)
                ));

        exitButton.addAction(fadeInAnimation);
        leftButton.addAction(fadeInAnimation);
        rightButton.addAction(fadeInAnimation);
        nameField.addAction(fadeInAnimation);
        changeNameButton.addAction(fadeInAnimation);
        profileImage.addAction(fadeInAnimation);
    }

    @Override
    void drawElements(float deltaTime) {

        Assets.FONT48.draw(batch, "Profile Settings State", 20,80);
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

    public TextButton getLeftButton() {
        return leftButton;
    }

    public TextButton getRightButton() {
        return rightButton;
    }

    public TextButton getChangeNameButton() {
        return changeNameButton;
    }

    public TextField getNameField() {
        return nameField;
    }
}
