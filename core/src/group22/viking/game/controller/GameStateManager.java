package group22.viking.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

import group22.viking.game.controller.states.State;

public class GameStateManager {

    private static GameStateManager instance;
    private VikingGame game;

    public static GameStateManager getInstance(VikingGame game) {
        if(instance == null) {
            instance = new GameStateManager(game);
        }
        return instance;
    }

    private Stack<State> states;

    private GameStateManager(VikingGame game){
        states = new Stack<State>();
        this.game = game;
    }

    public void push(State state){
        if(!states.isEmpty()) states.peek().pause();
        states.push(state);
    }

    public void pop(){
        states.pop().dispose();
        states.peek().reinitialize();
    }

    public State get(){
        return states.peek();
    }

    public void set(State state){
        states.pop().dispose();
        states.push(state);
    }

    public void render(float deltaTime){
        states.peek().render(deltaTime);
    }
}
