package group22.viking.game.factory;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;

import group22.viking.game.ECS.components.TypeComponent;


public abstract class AbstractFactory {

    protected PooledEngine engine;

    AbstractFactory(PooledEngine engine){
        this.engine = engine;
    }

    Entity createEntity(TypeComponent.EntityType type) {
        TypeComponent tyc = engine.createComponent(TypeComponent.class).set(type);
        return engine.createEntity().add(tyc);
    }
}
