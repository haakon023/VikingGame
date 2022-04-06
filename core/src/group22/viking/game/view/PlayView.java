package group22.viking.game.view;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

import group22.viking.game.ECS.EntityFactory;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.controller.states.PlayState;
import group22.viking.game.models.Assets;
import group22.viking.game.view.components.CustomTextButton;

public class PlayView extends View{

    private Stage stage;


    public PlayView(SpriteBatch batch, Camera camera) {
        super(batch);
        this.stage = new Stage(new FitViewport(VikingGame.SCREEN_WIDTH, VikingGame.SCREEN_HEIGHT, camera));
    }

    public void buildBackground(EntityFactory factory) {
        factory.createTexture(
                Assets.OCEANBACK,
                new Vector3(0,0,-1),
                new Vector2(VikingGame.SCREEN_HEIGHT, VikingGame.SCREEN_WIDTH)
        );
        // ...


    }

    @Override
    void show() {

    }

    @Override
    void drawElements(float deltaTime) {

    }
}
