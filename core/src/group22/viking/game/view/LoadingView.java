package group22.viking.game.view;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;

public class LoadingView extends View {

    private Stage stage;

    private ShapeRenderer shapeRenderer;
    private float progress;

    private Image vikingHeader;
    private Image stopHeader;

    public LoadingView(SpriteBatch batch, Camera camera) {
        super(batch);
        this.stage = new Stage(new FitViewport(VikingGame.SCREEN_WIDTH,VikingGame.SCREEN_HEIGHT, camera));
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void init() {
        System.out.println("LOADING");

        vikingHeader = new Image(new Texture("img/vikingHeader.png"));
        vikingHeader.setPosition(VikingGame.SCREEN_WIDTH/2-430,VikingGame.SCREEN_HEIGHT/2);
        vikingHeader.setWidth(660);
        vikingHeader.setHeight(200);
        stopHeader = new Image(new Texture("img/stopHeader.png"));
        stopHeader.setPosition(VikingGame.SCREEN_WIDTH/2,VikingGame.SCREEN_HEIGHT/2-130);
        stopHeader.setWidth(430);
        stopHeader.setHeight(300);
        stage.addActor(vikingHeader);
        stage.addActor(stopHeader);

        vikingHeader.addAction(sequence(alpha(0.0f), fadeIn(.3f)));
        stopHeader.addAction(sequence(alpha(0.0f),delay(.1f), fadeIn(0.01f)));

        stage.act(0);
        this.progress = 0f;
    }

    @Override
    public void runInitialAnimations() {

    }

    @Override
    void drawElements(float deltaTime) {

        progress = MathUtils.lerp(progress, Assets.getProgress(), 0.1f);

        //shapeRenderer (use it like a batch)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(32,VikingGame.SCREEN_HEIGHT/4-8,
                VikingGame.SCREEN_WIDTH-64,16);
        shapeRenderer.setColor(Color.GRAY);

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(32,VikingGame.SCREEN_HEIGHT/4-8,
                progress*(VikingGame.SCREEN_WIDTH-64),16);

        shapeRenderer.end();

        //animate upwards motion of logo to match main menu position
        vikingHeader.addAction(moveTo(VikingGame.SCREEN_WIDTH/2-430,VikingGame.SCREEN_HEIGHT -250,.3f));
        stopHeader.addAction(moveTo(VikingGame.SCREEN_WIDTH/2,VikingGame.SCREEN_HEIGHT -380,.3f));

        stage.act(deltaTime);
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
        stage.dispose();
    }
}
