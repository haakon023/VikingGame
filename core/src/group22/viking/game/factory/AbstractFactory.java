package group22.viking.game.factory;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;


public abstract class AbstractFactory {
    protected PooledEngine engine;

    AbstractFactory(PooledEngine engine){
        this.engine = engine;
    }
    abstract Entity createEntity(float x, float y, float z, Texture texture);
}
