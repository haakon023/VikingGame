package group22.viking.game.controller.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.Menu;

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
        System.out.println("MENU STATE CREATED");
    }


    @Override
    protected void handleInput() {

    }

    public void update(float delta){

    }

    public void render(float deltaTime) {

    }

    @Override
    public void dispose() {

    }

    public void testFirestore() {
        // test Firestore:
        //firebaseGameCollection.setOnValueChangedGameListener("epmFTIiltmEyRenV24Li");
        firebaseGameCollection.startGame(0, 0);
        //firebaseGameCollection.getGame();
    }
}
