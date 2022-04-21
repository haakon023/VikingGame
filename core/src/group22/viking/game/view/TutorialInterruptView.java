package group22.viking.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.controller.states.PlayState;
import group22.viking.game.models.Assets;

public class TutorialInterruptView extends View {

    private TextButton confirmButton;
    private Label heading;
    private Label content;
    private Image teacher;

    public TutorialInterruptView(SpriteBatch batch, Camera camera) {
        super(batch, camera);
        System.out.println("CONSTRUCTOR Tutorial View");
        this.init();
    }

    @Override
    public void init() {
        stage.clear();
        createBackground();
        createButtons();
        createLabels(Assets.t("tutorial_header" + PlayState.popUpCount), Assets.t("tutorial_content" + PlayState.popUpCount));
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
                "Understood !",
                new Vector2(VikingGame.SCREEN_WIDTH/2-100, 100),
                new Vector2(500,150)
        );

        stage.addActor(confirmButton);

    }

    private void createLabels(String headingText, String contentText) {
        heading = ViewComponentFactory.createLabel100(
                headingText,
                new Vector2(VikingGame.SCREEN_WIDTH/2-100,VikingGame.SCREEN_HEIGHT-300)
        );
        heading.setColor(Color.WHITE);
        heading.setAlignment(Align.topLeft);

        heading.setWrap(true);

        content = ViewComponentFactory.createLabel48(
                contentText,
                new Vector2(VikingGame.SCREEN_WIDTH/2-100,300)
        );
        content.setColor(Color.WHITE);

        content.setSize(VikingGame.SCREEN_WIDTH/2, VikingGame.SCREEN_HEIGHT-650);
        content.setAlignment(Align.topLeft);
        content.setWrap(true);

        stage.addActor(heading);
        stage.addActor(content);
    }

    @Override
    public void runInitialAnimations() {
        confirmButton.addAction(ViewComponentFactory.createFadeInAction());
        heading.addAction(ViewComponentFactory.createFadeInAction());
        content.addAction(ViewComponentFactory.createFadeInAction());
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


    public Label getContent() {
        return content;
    }

    public Image getTeacher() {
        return teacher;
    }
}
