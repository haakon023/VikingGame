package group22.viking.game.view;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;

public class InformationOverlayView extends View {

    private TextButton confirmButton;
    private Label headingLabel;
    private Label contentLabel;
    private Image teacher;

    public InformationOverlayView(SpriteBatch batch, Camera camera) {
        super(batch, camera);
        System.out.println("CONSTRUCTOR Tutorial View");
        this.init();
    }

    @Override
    public void init() {
        stage.clear();
        createBackground();
        createButtons();
        createLabels();
        runInitialAnimations();
        stage.act(0);
    }

    private void createBackground(){
        teacher = new Image(Assets.getTexture(Assets.WIZARD_SPRITE));
        teacher.setSize(900,900);
        teacher.setPosition(250,-1200);
        teacher.rotateBy(20f);

        stage.addActor(teacher);
    }

    private void createButtons() {
        confirmButton = ViewComponentFactory.createTextButton(
                Assets.t("overlay_button_okay"),
                new Vector2(VikingGame.SCREEN_WIDTH/2-100, 100),
                new Vector2(500,150)
        );

        stage.addActor(confirmButton);

    }


    private void createLabels() {
        headingLabel = ViewComponentFactory.createLabel100(
                "",
                new Vector2(VikingGame.SCREEN_WIDTH/2-100,VikingGame.SCREEN_HEIGHT-200)
        );
        headingLabel.setColor(Color.WHITE);
        headingLabel.setAlignment(Align.topLeft);
        headingLabel.setWidth(1000);
        headingLabel.setWrap(true);

        contentLabel = ViewComponentFactory.createLabel48(
                "",
                new Vector2(VikingGame.SCREEN_WIDTH/2-100,300)
        );
        contentLabel.setColor(Color.WHITE);
        contentLabel.setSize(VikingGame.SCREEN_WIDTH/2, VikingGame.SCREEN_HEIGHT-650);
        contentLabel.setAlignment(Align.topLeft);
        contentLabel.setWrap(true);

        stage.addActor(headingLabel);
        stage.addActor(contentLabel);
    }

    public void setTexts(String headingText, String contentText) {
        headingLabel.setText(headingText);
        contentLabel.setText(contentText);
    }

    @Override
    public void runInitialAnimations() {
        confirmButton.addAction(ViewComponentFactory.createFadeInAction());
        headingLabel.addAction(ViewComponentFactory.createFadeInAction());
        contentLabel.addAction(ViewComponentFactory.createFadeInAction());
        teacher.addAction(ViewComponentFactory.createAvatarSwooshAnimation(
                new Vector2(0,1),
                new Vector2(0,900)
        ));
    }

    @Override
    void drawElements(float deltaTime) {
        stage.act(deltaTime);
        stage.draw();
    }

    public TextButton getConfirmButton() {
        return confirmButton;
    }

    public Label getHeadingLabel() {
        return headingLabel;
    }

    public Label getContent() {
        return contentLabel;
    }

    public Image getTeacher() {
        return teacher;
    }
}
