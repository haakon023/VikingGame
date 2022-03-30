package group22.viking.game.view.components;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import group22.viking.game.controller.VikingGame;

public class CustomImageButton extends ImageButton {
    public CustomImageButton(TextureRegionDrawable profileTextureRegionDrawable, Vector2 position, Vector2 size) {
        super(profileTextureRegionDrawable);
        this.setSize(size.x, size.y);
        this.setPosition(position.x, position.y);
        this.addAction(
            sequence(alpha(0),
            parallel(fadeIn(0.5f) //,
                    //moveBy(0,-20,.5f, Interpolation.pow5Out)
            )));
    }
}
