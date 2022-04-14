package group22.viking.game.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.controller.firebase.FirebaseDocument;
import group22.viking.game.controller.firebase.OnCollectionUpdatedListener;
import group22.viking.game.controller.firebase.Profile;
import group22.viking.game.controller.firebase.ProfileCollection;
import group22.viking.game.models.Assets;
import group22.viking.game.view.ProfileSettingsView;


public class ProfileSettingsState extends State {

    private final ProfileCollection profileCollection;

    private int currentShownAvatarId;

    public ProfileSettingsState(final VikingGame game) {
        super(new ProfileSettingsView(game.getBatch(), game.getCamera()), game);
        Gdx.input.setInputProcessor(view.getStage());
        addListenersToButtons();

        this.profileCollection = game.getProfileCollection();
        Profile profile = profileCollection.getLocalPlayerProfile();

        this.currentShownAvatarId = (int) profile.getAvatarId();
        updateShownAvatar();

        ((ProfileSettingsView) view).getNameField().setText(profile.getName());

        System.out.println("PROFILE STATE CREATED");
    }


    @Override
    protected void handleInput() {

    }

    public void update(float delta){

    }

    @Override
    public void dispose() {

    }

    private void addListenersToButtons() {
        final ProfileSettingsView view = (ProfileSettingsView) this.view;

        view.getExitButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("EXIT BUTTON CLICKED");
                goBackToMenu();
            }
        });

        view.getLeftButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentShownAvatarId--;
                updateShownAvatar();
            }
        });
        view.getRightButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentShownAvatarId++;
                updateShownAvatar();
            }
        });
        view.getSubmitChangesButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userSubmitsChanges(view);
            }
        });
        /*
        view.getNameField().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        */
    }

    private void updateShownAvatar() {
        currentShownAvatarId = (currentShownAvatarId + Assets.NUMBER_OF_AVATARS) % Assets.NUMBER_OF_AVATARS;
        ((ProfileSettingsView) view).updateShownAvatarId(currentShownAvatarId);
    }

    private void userSubmitsChanges(ProfileSettingsView view) {
        profileCollection.updateLocalProfile(
                view.getNameField().getText(),
                currentShownAvatarId,
                new OnCollectionUpdatedListener(){
                    @Override
                    public void onSuccess(FirebaseDocument document) {
                        goBackToMenu();
                    }

                    @Override
                    public void onFailure() {
                        // TODO error message
                    }
                }
        );
    }

    private void goBackToMenu() {
        dispose();
        GameStateManager.getInstance().pop();
    }
}
