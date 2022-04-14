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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;


public class ProfileSettingsView extends View {

    //Images
    private Image profileImage;

    //buttons
    private TextButton exitButton;
    private TextButton leftButton;
    private TextButton rightButton;
    private TextButton submitChangesButton;

    //Text Fields
    private TextField nameTextField;

    public ProfileSettingsView(SpriteBatch batch, Camera camera) {
        super(batch, camera);
        this.init();
    }

    @Override
    public void init() {
        // stage clear to make sure there aren't any further animations
        stage.clear();

        //todo get img file from db or json log file
        profileImage = new Image(Assets.getTexture(Assets.getAvatarHead(0)));
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
        //only for profile
        Vector2 carouselButtonSize = new Vector2(80,profileImage.getHeight());

        //for profile and leaderboard
        Vector2 exitButtonSize = new Vector2(150,VikingGame.SCREEN_HEIGHT-300);

        exitButton = ViewComponentFactory.createTextButton(
                "<",
                new Vector2(50, 150),
                exitButtonSize
        );


        leftButton = ViewComponentFactory.createTextButton(
                "<",
                new Vector2(profileImage.getX() - carouselButtonSize.x,
                        VikingGame.SCREEN_HEIGHT - profileImage.getHeight() - 150),
                carouselButtonSize
        );


        rightButton = ViewComponentFactory.createTextButton(
                ">",
                new Vector2(profileImage.getX() + profileImage.getWidth(),
                        VikingGame.SCREEN_HEIGHT - profileImage.getHeight() - 150),
                carouselButtonSize
        );

        submitChangesButton = ViewComponentFactory.createTextButton(
                "Submit",
                new Vector2(profileImage.getX() + profileImage.getWidth() + carouselButtonSize.x + 100 + 600 + 50,
                        VikingGame.SCREEN_HEIGHT - profileImage.getHeight() - 150),
                new Vector2(600F / 3, 150)
        );

        stage.addActor(exitButton);
        stage.addActor(leftButton);
        stage.addActor(rightButton);
        stage.addActor(submitChangesButton);

    }

    private void createTextField() {

        nameTextField = ViewComponentFactory.createTextField(
                "",
                new Vector2(profileImage.getX() + profileImage.getWidth() + rightButton.getWidth() + 100,
                        VikingGame.SCREEN_HEIGHT - profileImage.getHeight() - 150),
                new Vector2(600, 150)
        );
        stage.addActor(nameTextField);
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
        nameTextField.addAction(fadeInAnimation);
        submitChangesButton.addAction(fadeInAnimation);
        profileImage.addAction(fadeInAnimation);
    }

    @Override
    void drawElements(float deltaTime) {

        Assets.FONT48.draw(batch, "Profile Settings State", 20,80);
        stage.act(deltaTime);
        stage.draw();
    }

    public void updateShownAvatarId(int avatarId) {
        profileImage.setDrawable(new TextureRegionDrawable(
                Assets.getTexture(Assets.getAvatarHead(avatarId))
        ));
    }

    public TextButton getExitButton() {
        return exitButton;
    }

    public TextButton getLeftButton() {
        return leftButton;
    }

    public TextButton getRightButton() {
        return rightButton;
    }

    public TextButton getSubmitChangesButton() {
        return submitChangesButton;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }
}
