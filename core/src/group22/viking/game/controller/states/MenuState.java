package group22.viking.game.controller.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.firebase.FirebaseGameCollection;
import group22.viking.game.controller.firebase.FirebaseProfileCollection;
import group22.viking.game.controller.firebase.OnCollectionUpdatedListener;
import group22.viking.game.controller.firebase.OnGetDataListener;
import group22.viking.game.controller.firebase.Profile;

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
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin(); //Rendering goes below here

        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        tutorialPlayBtn.dispose();
        multiplayerPlayBtn.dispose();
        leaderboardBtn.dispose();
        muteSoundBtn.dispose();
    }

    public void testFirestore() {
        // test Firestore:
        //firebaseGameCollection.setOnValueChangedGameListener("epmFTIiltmEyRenV24Li");
        //firebaseGameCollection.startGame(0, 0);
        //firebaseGameCollection.getGame();
        //firebaseProfileCollection.readProfile("hud1tfhZY3WkUIkl7GnC");
        userSubmitsCreateProfile("Olaf der Tester", 4);
    }

    private void userSubmitsCreateProfile(String name, int avatarId) {

        firebaseProfileCollection.createProfile(name, avatarId, new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(Profile profile) {
                System.out.println("Profile created: " + profile.getId());
            }
            @Override
            public void onFailure() {
                System.out.println("Failure.");
            }
        });

        /*final MenuState that = this;
        Object notifiable = new Object();
        synchronized (this) {
            that.setLoading(true);
            System.out.println("Creating Profile...");
            firebaseProfileCollection.createProfile(name, avatarId, this);

            this.wait();
            System.out.println("Successfully created Profile");

            that.setLoading(false);
        }*/
    }

    private void updateOwnAvatar(int avatarId) {

    }
    
    public void setLoading(boolean isLoading){
        
    }
}
