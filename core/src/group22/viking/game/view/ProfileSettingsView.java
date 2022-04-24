package group22.viking.game.view;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;


public class ProfileSettingsView extends View {

    //Images
    private Image profileImage;

    //buttons
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
        profileImage.setPosition(VikingGame.getInstance().SCREEN_WIDTH/4,
                VikingGame.getInstance().SCREEN_HEIGHT-profileImage.getHeight()-250);
        profileImage.addAction(sequence(alpha(0),parallel(fadeIn(0.5f),moveBy(0,-20,.5f, Interpolation.pow5Out))));
        stage.addActor(profileImage);

        createLabels();
        createButtons();
        createTextField();

        // TODO continue...

        runInitialAnimations();

        stage.act(0);
    }


    private void createButtons() {
        //only for profile
        Vector2 carouselButtonSize = new Vector2(80,profileImage.getHeight());

        leftButton = ViewComponentFactory.createTextButton(
                "<",
                new Vector2(profileImage.getX() - carouselButtonSize.x,
                        VikingGame.getInstance().SCREEN_HEIGHT - profileImage.getHeight() - 250),
                carouselButtonSize
        );


        rightButton = ViewComponentFactory.createTextButton(
                ">",
                new Vector2(profileImage.getX() + profileImage.getWidth(),
                        VikingGame.getInstance().SCREEN_HEIGHT - profileImage.getHeight() - 250),
                carouselButtonSize
        );

        submitChangesButton = ViewComponentFactory.createTextButton(
                Assets.t("profile_button_saveAndExit"),
                new Vector2(leftButton.getX(),
                        VikingGame.getInstance().SCREEN_HEIGHT - profileImage.getHeight() - 500),
                new Vector2(2*leftButton.getWidth()+profileImage.getWidth()+100+600, 150)
        );

        stage.addActor(leftButton);
        stage.addActor(rightButton);
        stage.addActor(submitChangesButton);

    }

    private void createTextField() {

        nameTextField = ViewComponentFactory.createTextField(
                "",
                new Vector2(profileImage.getX() + profileImage.getWidth() + rightButton.getWidth() + 100,
                        VikingGame.getInstance().SCREEN_HEIGHT - profileImage.getHeight() - 250),
                new Vector2(600, 150)
        );
        stage.addActor(nameTextField);
    }

    private void createLabels() {
        //labels
        Label profileLabel = ViewComponentFactory.createLabel100(
                Assets.t("profile_label_profile"),
                new Vector2(VikingGame.getInstance().SCREEN_WIDTH / 2 - 150, VikingGame.getInstance().SCREEN_HEIGHT - 200)
        );
        profileLabel.setColor(Color.WHITE);
        profileLabel.setAlignment(Align.center);

        stage.addActor(profileLabel);
    }


    @Override
    public void runInitialAnimations() {
        submitChangesButton.addAction(ViewComponentFactory.createFadeInAction());
        leftButton.addAction(ViewComponentFactory.createFadeInAction());
        rightButton.addAction(ViewComponentFactory.createFadeInAction());
        nameTextField.addAction(ViewComponentFactory.createFadeInAction());
    }

    @Override
    void drawElements(float deltaTime) {
        stage.act(deltaTime);
        stage.draw();
    }

    public void updateShownAvatarId(int avatarId) {
        profileImage.setDrawable(new TextureRegionDrawable(
                Assets.getTexture(Assets.getAvatarHead(avatarId))
        ));
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
