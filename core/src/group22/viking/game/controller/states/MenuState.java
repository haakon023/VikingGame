package group22.viking.game.controller.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.firebase.FirebaseDocument;
import group22.viking.game.controller.firebase.GameCollection;
import group22.viking.game.controller.firebase.ProfileCollection;
import group22.viking.game.controller.firebase.OnCollectionUpdatedListener;
import group22.viking.game.controller.firebase.Profile;

public class MenuState extends State {
    private Texture background;
    private Texture tutorialPlayBtn;
    private Texture multiplayerPlayBtn;
    private Texture leaderboardBtn;
    private Texture muteSoundBtn;

    private ProfileCollection profileCollection;
    private GameCollection gameCollection;

    public MenuState(GameStateManager gsm,
                     ProfileCollection profileCollection,
                     GameCollection gameCollection) {
        super(gsm);
        this.profileCollection = profileCollection;
        this.gameCollection = gameCollection;
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
        final MenuState that = this;

        profileCollection.createProfile(name, avatarId, new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument profile) {
                System.out.println("Profile created: " + profile.getId());
                that.getHostProfileData();
            }
            @Override
            public void onFailure() {
                System.out.println("Failure.");
            }
        });
    }

    private void getHostProfileData() {
        Profile host = profileCollection.getHostProfile();
        System.out.println("The Host is " + host.getName());
    }

    private void updateOwnAvatar(int avatarId) {

    }
    
    public void setLoading(boolean isLoading){
        
    }
}
