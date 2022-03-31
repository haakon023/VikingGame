package group22.viking.game.ECS;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import group22.viking.game.ECS.components.AnimationComponent;
import group22.viking.game.ECS.components.TextureComponent;

public class AnimationSystem extends IteratingSystem {

    private ComponentMapper<AnimationComponent> cmAnimation;
    private ComponentMapper<TextureComponent> cmTexture;
    //private ComponentMapper<StateComponent> cmState;

    public AnimationSystem() {
        super(Family.all().get());

        cmAnimation = ComponentMapper.getFor(AnimationComponent.class);
        cmTexture = ComponentMapper.getFor(TextureComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationComponent aComp = cmAnimation.get(entity);

        if(aComp.animations.containsKey(state.get()))
            TextureComponent tComp = cmTexture.get(entity);

    }
}
