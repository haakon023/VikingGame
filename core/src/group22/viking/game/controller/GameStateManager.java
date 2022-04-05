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
        // VikingGame.instance.setScreen(state);        // probably not necessary, as we have the updateScreen() method below
        states.push(state);
        //updateScreen();
    }

    public void pop(){
        states.pop().dispose();
    }

    public State get(){
        return states.peek();
    }

    public void set(State state){
        states.pop().dispose();
        states.push(state);
        //updateScreen();
    }

    //TODO: adapt the update and render functions as they are not contained in the states anymore
    /*public void update(float dt){
        states.peek().update(dt);
    }*/

    // not used as of now:
    /*public void render(SpriteBatch sb){
        states.peek().render(sb);
    }*/

    /*public void updateScreen() {
        game.setScreen(states.peek().getScreen());
    }*/
}
