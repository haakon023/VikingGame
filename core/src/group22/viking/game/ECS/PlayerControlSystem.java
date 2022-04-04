package group22.viking.game.ECS;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import group22.viking.game.ECS.InputController;
import group22.viking.game.ECS.components.PlayerComponent;
import group22.viking.game.ECS.components.TransformComponent;

public class PlayerControlSystem extends IteratingSystem {

    private ComponentMapper<PlayerComponent> cmPlayerComponent;
    private ComponentMapper<TransformComponent> cmTransformComponent;

    private InputController input;
    
    public PlayerControlSystem(InputController controller) {
        super(Family.all(PlayerComponent.class, TransformComponent.class).get());
        
        cmPlayerComponent = ComponentMapper.getFor(PlayerComponent.class);
        cmTransformComponent = ComponentMapper.getFor(TransformComponent.class);
        input = controller;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerComponent pComp = cmPlayerComponent.get(entity);
        TransformComponent tComp = cmTransformComponent.get(entity);
        
        if(input.isMouse1Down) {
            Vector2 pos = input.mouseLocation;
            tComp.rotation  = calculateAngle(pos, new Vector2(tComp.position.x, tComp.position.y));
            
            //add shooting here as well
            shootBullet();
        }
    }

    private void shootBullet()
    {

    }
    
    private float calculateAngle(Vector2 mousePos, Vector2 playerPos)
    {
        float radians = (float)Math.atan2(mousePos.x - playerPos.x, mousePos.y - playerPos.y);
        //add 90 degrees offsett to correct the angle
        return radians * MathUtils.radiansToDegrees + 90;
    }
    
}
