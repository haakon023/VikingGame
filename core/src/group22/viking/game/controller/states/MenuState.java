package group22.viking.game.controller.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.view.MenuScreen;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.firebase.FirebaseGameCollection;
import group22.viking.game.controller.firebase.FirebaseProfileCollection;

public class MenuState extends State {
    private Texture background;
    private Texture tutorialPlayBtn;
    private Texture multiplayerPlayBtn;
    private Texture leaderboardBtn;
    private Texture muteSoundBtn;

    private FirebaseProfileCollection firebaseProfileCollection;
    private FirebaseGameCollection firebaseGameCollection;

    public MenuState(GameStateManager gsm,
                     FirebaseProfileCollection firebaseProfileCollection,
                     FirebaseGameCollection firebaseGameCollection) {
        super(gsm);
        this.firebaseProfileCollection = firebaseProfileCollection;
        this.firebaseGameCollection = firebaseGameCollection;
    }


    @Override
    protected void handleInput() {

    }

    public void update(float delta){

    }

    @Override
    public void render(SpriteBatch sb) {

    }


    @Override
    public void show() {
        
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

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
