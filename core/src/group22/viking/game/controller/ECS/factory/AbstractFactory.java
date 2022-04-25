package group22.viking.game.controller.ECS.factory;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;

import group22.viking.game.models.ECS.components.TypeComponent;


public abstract class AbstractFactory {

    protected PooledEngine engine;

    AbstractFactory(PooledEngine engine){
        this.engine = engine;
    }

    Entity createEntity(TypeComponent.EntityType type) {
        TypeComponent tyc = engine.createComponent(TypeComponent.class).init(type);
        return engine.createEntity().add(tyc);
    }
}
