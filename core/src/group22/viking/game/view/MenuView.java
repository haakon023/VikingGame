package group22.viking.game.view;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.firebase.documents.Lobby;
import group22.viking.game.models.Assets;

public class MenuView extends View {

    private TextButton tutorialButton;
    private TextButton hostButton;
    private TextButton exitButton;
    private TextButton practiceButton;

    private ImageButton profileButton;
    private ImageButton muteButton;
    private ImageButton leaderboardButton;


    private TextField joinTextField;

    private Animation[] animations;

    private TextureRegion profileTextureRegion;

    /**
     * Simple storage class for animation info.
     */
    abstract static class Animation {
        Image image;
        public float status;
        public float step;
        public float max;
        boolean animateX;

        public Animation(Image image, float x, float y, float step, float max, boolean animateX) {
            this.image = image;
            this.image.setPosition(x, y);
            this.status = 0F;
            this.step = step;
            this.max = max;
            this.animateX = animateX;
        }

        public void makeStep(float deltaTime) {
            status += step * deltaTime;
            if(animateX) image.setX(image.getX() + step * deltaTime);
            else image.setY(image.getY() + step * deltaTime);

            this.stepChanges();
        }

        abstract void stepChanges();
    }

    static class LoopAnimation extends Animation {
        public LoopAnimation(Image image, float x, float y, float step, float max, boolean animateX) {
            super(image, x, y, step, max, animateX);
        }

        public void stepChanges() {
            if (status > max) {
                status -= max;
                if(animateX) image.setX(image.getX() - max);
                else image.setY(image.getY() - max);
            }
        }
    }

    static class BouncingAnimation extends Animation {
        public BouncingAnimation(Image image, float x, float y, float step, float max, boolean animateX) {
            super(image, x, y, step, max, animateX);
        }

        public void stepChanges() {
            if (Math.abs(status) > max) {
                step *= (status < 0) ^ (step < 0) ? 1 : -1;
            }
        }
    }

    public MenuView(SpriteBatch batch, Camera camera) {
        super(batch, camera);
        this.init();
    }

    @Override
    public void init() {
        // stage clear to make sure there aren't any further animations
        stage.clear();

        createBackground();
        createHeader();
        createButtons();
        createTextField();

        // TODO continue...

        runInitialAnimations();

        stage.act(0);
    }

    private void createButtons() {
        //todo init the texture to the user specific profile image
        tutorialButton = ViewComponentFactory.createTextButton(
                Assets.t("menu_button_tutorial"),
                new Vector2(150, VikingGame.getInstance().SCREEN_HEIGHT/2+80-50),
                ViewComponentFactory.BIG_BUTTON_SIZE
        );

        practiceButton = ViewComponentFactory.createTextButton(
                Assets.t("menu_button_practice"),
                new Vector2(150, VikingGame.getInstance().SCREEN_HEIGHT / 2 - 80 - 150 - 50),
                ViewComponentFactory.BIG_BUTTON_SIZE);


        hostButton = ViewComponentFactory.createTextButton(
                Assets.t("menu_button_host"),
                new Vector2(VikingGame.getInstance().SCREEN_WIDTH/2+(VikingGame.getInstance().SCREEN_WIDTH/2-700-150),VikingGame.getInstance().SCREEN_HEIGHT/2-80-150-50),
                ViewComponentFactory.BIG_BUTTON_SIZE
        );

        exitButton = ViewComponentFactory.createTextButton(
                Assets.t("menu_button_exit"),
                new Vector2(150, VikingGame.getInstance().SCREEN_HEIGHT - 200),
                ViewComponentFactory.SMALL_RECT_BUTTON_SIZE);

        muteButton = ViewComponentFactory.createCheckedImageButton(
                new TextureRegionDrawable(Assets.getTexture(Assets.UNMUTED_BUTTON_IMAGE)),
                new TextureRegionDrawable(Assets.getTexture(Assets.UNMUTED_BUTTON_IMAGE)),
                new TextureRegionDrawable(Assets.getTexture(Assets.MUTED_BUTTON_IMAGE)),
                new Vector2(VikingGame.getInstance().SCREEN_WIDTH - 120 - 60, 50),
                ViewComponentFactory.VERY_SMALL_BUTTON_SIZE);

        TextureRegion leaderboardTextureRegion = new TextureRegion(Assets.getTexture(Assets.LEADERBOARD_BUTTON_IMAGE));
        leaderboardButton = ViewComponentFactory.createImageButton(
                new TextureRegionDrawable(leaderboardTextureRegion),
                new Vector2(VikingGame.getInstance().SCREEN_WIDTH - 120 - 60 - 120 - 60, 50),
                ViewComponentFactory.VERY_SMALL_BUTTON_SIZE);

        profileTextureRegion = new TextureRegion(Assets.getTexture(Assets.getAvatarHead(1)));
        profileButton = ViewComponentFactory.createImageButton(
                new TextureRegionDrawable(profileTextureRegion),
                // new Vector2(VikingGame.SCREEN_WIDTH/2-profileButton.getWidth()/2,
                new Vector2(VikingGame.getInstance().SCREEN_WIDTH/2-500F/2,
                        //VikingGame.SCREEN_HEIGHT/2-profileButton.getHeight()/2-80),
                        VikingGame.getInstance().SCREEN_HEIGHT/2-500F/2-80),
                ViewComponentFactory.PROFILE_IMAGE_SIZE);


        stage.addActor(tutorialButton);
        stage.addActor(practiceButton);
        stage.addActor(hostButton);
        stage.addActor(profileButton);
        stage.addActor(leaderboardButton);
        stage.addActor(exitButton);
        stage.addActor(muteButton);
    }

    public void setAvatar(int avatarId) {
        profileTextureRegion.setRegion(Assets.getTexture(Assets.getAvatarHead(avatarId)));
    }

    private void createTextField() {

        joinTextField = ViewComponentFactory.createTextField(
                Assets.t("menu_text_field_join"),
                new Vector2(VikingGame.getInstance().SCREEN_WIDTH / 2 + (VikingGame.getInstance().SCREEN_WIDTH / 2 - 700 - 150),
                        VikingGame.getInstance().SCREEN_HEIGHT / 2 + 80 - 50),
                ViewComponentFactory.BIG_BUTTON_SIZE
        );


        joinTextField.setMaxLength(4);
        joinTextField.setAlignment(Align.center);
        stage.addActor(joinTextField);
    }

    private void createBackground() {
        Image waveDark = new Image(Assets.getTexture(Assets.WAVE_DARK));
        stage.addActor(waveDark);

        Image castle = new Image(Assets.getTexture(Assets.CASTLE));
        castle.setWidth(600);
        castle.setHeight(500);
        stage.addActor(castle);

        Image waveMedium = new Image(Assets.getTexture(Assets.WAVE_MEDIUM));
        stage.addActor(waveMedium);

        Image vikingShip = new Image(Assets.getTexture(Assets.VIKING_SHIP));
        vikingShip.setWidth(600);
        vikingShip.setHeight(500);
        stage.addActor(vikingShip);

        Image waveLight = new Image(Assets.getTexture(Assets.WAVE_LIGHT));
        stage.addActor(waveLight);

        Image waveVeryLight = new Image(Assets.getTexture(Assets.WAVE_VERY_LIGHT));
        stage.addActor(waveVeryLight);

        this.animations = new Animation[]{
                new LoopAnimation(waveDark, -600, 0, 8, 296, true),
                new BouncingAnimation(castle, 1500, 290, 10, 7, false),
                new LoopAnimation(waveMedium, -300, 0, 16, 296, true),
                new BouncingAnimation(vikingShip, 200, 200, 10, 15, true),
                new BouncingAnimation(vikingShip, 200, 200, 30, 15, false),
                new LoopAnimation(waveLight, -600, 0, 32, 296, true),
                new LoopAnimation(waveVeryLight, -400, 0, 64, 296, true)
        };

    }

    private void createHeader() {
        //header
        Image vikingHeader = new Image(Assets.getTexture(Assets.VIKING_HEADER));
        vikingHeader.setPosition(VikingGame.getInstance().SCREEN_WIDTH/2-430,VikingGame.getInstance().SCREEN_HEIGHT -250);
        vikingHeader.setWidth(660);
        vikingHeader.setHeight(200);
        Image stopHeader = new Image(Assets.getTexture(Assets.STOP_HEADER));
        stopHeader.setPosition(VikingGame.getInstance().SCREEN_WIDTH/2,VikingGame.getInstance().SCREEN_HEIGHT -380);
        stopHeader.setWidth(430);
        stopHeader.setHeight(300);
        stage.addActor(vikingHeader);
        stage.addActor(stopHeader);
    }

    @Override
    public void runInitialAnimations() {
        tutorialButton.addAction(ViewComponentFactory.createFadeInAction());
        practiceButton.addAction(ViewComponentFactory.createFadeInAction());
        hostButton.addAction(ViewComponentFactory.createFadeInAction());
        joinTextField.addAction(ViewComponentFactory.createFadeInAction());
        profileButton.addAction(ViewComponentFactory.createFadeInAction());
        leaderboardButton.addAction(ViewComponentFactory.createFadeInAction());
        exitButton.addAction(ViewComponentFactory.createFadeInAction());
        muteButton.addAction(ViewComponentFactory.createFadeInAction());
    }

    public void makeErrorShakeOnTextField() {
        joinTextField.addAction(sequence(
                moveBy(-30,0,.1f, Interpolation.circle),
                moveBy(60,0,.1f, Interpolation.circle),
                moveBy(-60,0,.1f, Interpolation.circle),
                moveBy(60,0,.1f, Interpolation.circle),
                moveBy(-60,0,.1f, Interpolation.circle),
                moveBy(30,0,.1f, Interpolation.circle)
        ));
    }

    void drawElements(float deltaTime) {

        for (Animation animation : animations) {
            animation.makeStep(deltaTime);
        }

        stage.act(deltaTime);
        stage.draw();
    }

    public TextButton getTutorialButton() {
        return tutorialButton;
    }

    public TextButton getHostButton() {
        return hostButton;
    }

    public TextButton getExitButton() {
        return exitButton;
    }

    public ImageButton getLeaderboardButton() {
        return leaderboardButton;
    }

    public ImageButton getMuteButton() {
        return muteButton;
    }

    public TextButton getPracticeButton() {
        return practiceButton;
    }

    public TextField getJoinTextField() {
        return joinTextField;
    }

    public ImageButton getProfileButton() {
        return profileButton;
    }


    public void resetTextField() {
        joinTextField.setMaxLength(1000);
        TextField.TextFieldFilter textFieldFilter = joinTextField.getTextFieldFilter();
        joinTextField.setTextFieldFilter(null);

        joinTextField.setText(Assets.t("menu_text_field_join"));

        joinTextField.setTextFieldFilter(textFieldFilter);
        joinTextField.setMaxLength(Lobby.ID_LENGTH);
    }
}
