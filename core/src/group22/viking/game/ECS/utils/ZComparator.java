package group22.viking.game.ECS.utils;

import com.badlogic.ashley.core.ComponentMapper;

import java.util.Comparator;

import group22.viking.game.ECS.components.TransformComponent;


public class ZComparator implements Comparator<com.badlogic.ashley.core.Entity> {

    private ComponentMapper<TransformComponent> componentMapperTransform;

    public ZComparator()
    {
        componentMapperTransform = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    public int compare(com.badlogic.ashley.core.Entity t1, com.badlogic.ashley.core.Entity t2) {
        float t1ZPos = componentMapperTransform.get(t1).position.z;
        float t2ZPos = componentMapperTransform.get(t2).position.z;

        if(t1ZPos > t2ZPos)
            return 1;
        else if(t2ZPos > t1ZPos)
            return -1;

        return 0;
    }
}
