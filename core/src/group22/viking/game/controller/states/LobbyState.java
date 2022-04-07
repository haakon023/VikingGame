package group22.viking.game.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.view.LobbyView;


public class LobbyState extends State {


    public LobbyState(final VikingGame game) {
        super(new LobbyView(game.getBatch(), game.getCamera()), game);
        //System.out.println("STAGE IS: " + view.getStage().toString());
        Gdx.input.setInputProcessor(view.getStage());
        addListenersToButtons();

        System.out.println("LOBBY STATE CREATED");
    }


    @Override
    protected void handleInput() {

    }

    @Override
    public void dispose() {

    }

    public void update(float delta){

    }

    private void addListenersToButtons() {
        ((LobbyView) view).getPlayButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                dispose();
                System.out.println("PLAY BUTTON CLICKED");
                GameStateManager.getInstance().push(new PlayState(game, PlayState.Type.ONLINE));
            }
        });

        ((LobbyView) view).getExitButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                dispose();
                System.out.println("EXIT BUTTON CLICKED");
                GameStateManager.getInstance().pop();
            }
        });

    }

}
