package group22.viking.game.models.ECS.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import group22.viking.game.models.ECS.components.AnimationComponent;
import group22.viking.game.models.ECS.components.StateComponent;
import group22.viking.game.models.ECS.components.TextureComponent;

public class AnimationSystem extends IteratingSystem {

    private ComponentMapper<AnimationComponent> cmAnimation;
    private ComponentMapper<TextureComponent> cmTexture;
    private ComponentMapper<StateComponent> cmState;

    public AnimationSystem() {
        super(Family.all().get());

        cmAnimation = ComponentMapper.getFor(AnimationComponent.class);
        cmTexture = ComponentMapper.getFor(TextureComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationComponent anim = cmAnimation.get(entity);
        StateComponent state = cmState.get(entity);

        if(anim.animations.containsKey(state.get())){
            TextureComponent tComp = cmTexture.get(entity);
            tComp.textureRegion = (TextureRegion) anim.animations.get(state.get()).getKeyFrame(state.time, state.isLooping);
        }
        state.time += deltaTime;
    }
}
