package group22.viking.game.controller;

import java.util.Stack;

import group22.viking.game.controller.states.State;

public class GameStateManager {

    private static GameStateManager instance;

    public static GameStateManager getInstance() {
        if(instance == null) {
            instance = new GameStateManager();
        }
        return instance;
    }

    private Stack<State> states;

    private GameStateManager(){
        states = new Stack<>();
    }

    public void push(State state){
        if(!states.isEmpty()) states.peek().pause();
        states.push(state);
    }

    public void pop(){
        System.out.println("POP");
        if(states.size() <= 1) return;
        State popped = states.pop();
        states.peek().reinitialize();
        popped.dispose();
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
