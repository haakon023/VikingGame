package group22.viking.game.ECS;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

import java.util.Comparator;


public class ZComparator implements Comparator<Entity> {

    private ComponentMapper<tempComponentToBeRemoved> componentMapperTransform;

    public ZComparator()
    {
        componentMapperTransform = ComponentMapper.getFor(tempComponentToBeRemoved.class);
    }

    @Override
    public int compare(Entity t1, Entity t2) {
        float t1ZPos = componentMapperTransform.get(t1).position.z;
        float t2ZPos = componentMapperTransform.get(t2).position.z;

        if(t1ZPos > t2ZPos)
            return 1;
        else if(t2ZPos > t1ZPos)
            return -1;

        return 0;
    }
}
