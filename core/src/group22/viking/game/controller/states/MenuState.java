package group22.viking.game.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.awt.Menu;

import group22.viking.game.ECS.InputController;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.view.MenuScreen;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.firebase.FirebaseGameCollection;
import group22.viking.game.controller.firebase.FirebaseProfileCollection;
import group22.viking.game.view.MenuView;

public class MenuState extends State {
    private Texture background;
    private Texture tutorialPlayBtn;
    private Texture multiplayerPlayBtn;
    private Texture leaderboardBtn;
    private Texture muteSoundBtn;

    private FirebaseProfileCollection firebaseProfileCollection;
    private FirebaseGameCollection firebaseGameCollection;

    public MenuState(VikingGame game,               // GameStateManager gsm,
                     FirebaseProfileCollection firebaseProfileCollection,
                     FirebaseGameCollection firebaseGameCollection) {
        // super(gsm);
        super(new MenuView(game.getBatch(), game.getCamera()), game);
        this.firebaseProfileCollection = firebaseProfileCollection;
        this.firebaseGameCollection = firebaseGameCollection;
    }

    // alternative constructor w/o Firebase for now:
    public MenuState(VikingGame game) {
        super(new MenuView(game.getBatch(), game.getCamera()), game);
        Gdx.input.setInputProcessor(view.getStage());
        addListenersToButtons();

        System.out.println("MENU STATE CREATED");
    }


    @Override
    protected void handleInput() {

    }

    public void update(float delta){

    }

    public void testFirestore() {
        // test Firestore:
        //firebaseGameCollection.setOnValueChangedGameListener("epmFTIiltmEyRenV24Li");
        firebaseGameCollection.startGame(0, 0);
        //firebaseGameCollection.getGame();
    }


    private void addListenersToButtons() {
        ((MenuView) view).getTutorialButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                dispose();
                GameStateManager.getInstance(game).push(new PlayState(game, PlayState.Type.TUTORIAL));
            }
        });

        ((MenuView) view).getPracticeButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                GameStateManager.getInstance(game).push(new PlayState(game, PlayState.Type.PRACTICE));
            }
        });

        ((MenuView) view).getHostButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                GameStateManager.getInstance(game).push(new LobbyState(game));
            }
        });


        ((MenuView) view).getJoinButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                GameStateManager.getInstance(game).push(new LobbyState(game));
            }
        });

        ((MenuView) view).getProfileButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                GameStateManager.getInstance(game).push(new ProfileSettingsState(game));
            }
        });

        ((MenuView) view).getLeaderboardButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                GameStateManager.getInstance(game).push(new LeaderboardState(game));
            }
        });

        ((MenuView) view).getExitButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });

        ((MenuView) view).getMuteButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //todo
            }
        });


    }
}
