package group22.viking.game.controller.states;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.logging.Level;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.firebase.FirebaseDocument;
import group22.viking.game.firebase.listeners.OnCollectionUpdatedListener;
import group22.viking.game.models.firebase.documents.Profile;
import group22.viking.game.firebase.collections.ProfileCollection;
import group22.viking.game.models.Assets;
import group22.viking.game.view.ProfileSettingsView;
import group22.viking.game.view.SoundManager;
import group22.viking.game.view.ViewComponentFactory;


public class ProfileSettingsState extends State {

    private final ProfileCollection profileCollection;

    private int currentShownAvatarId;

    public ProfileSettingsState(final VikingGame game) {
        super(Assets.profileSettingsView, game);

        addListenersToButtons();
        initTextFieldLogic();

        this.profileCollection = game.getProfileCollection();
        Profile profile = profileCollection.getLocalPlayerProfile();

        this.currentShownAvatarId = (int) profile.getAvatarId();
        updateShownAvatar();

        ((ProfileSettingsView) view).getNameTextField().setText(profile.getName());

        VikingGame.LOG.log(Level.INFO, "PROFILE STATE CREATED");
    }

    private void addListenersToButtons() {
        getView().getLeftButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundManager.buttonClickSound();
                currentShownAvatarId--;
                updateShownAvatar();
            }
        });
        getView().getRightButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundManager.buttonClickSound();
                currentShownAvatarId++;
                updateShownAvatar();
            }
        });
        getView().getSubmitChangesButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundManager.buttonClickSound();
                userSubmitsChanges(getView());
            }
        });
    }

    private void initTextFieldLogic() {
        final TextField textField = getView().getNameTextField();

        textField.setMaxLength(Profile.NAME_MAX_CHAR);

        // only allow certain symbols
        textField.setTextFieldFilter(new TextField.TextFieldFilter() {
            @Override
            public boolean acceptChar(TextField textField, char c) {
                return (c >= 'a' && c <= 'z') ||
                        (c >= 'A' && c <= 'Z') ||
                        (c >= '0' && c <= '9');
            }
        });

        // get clicks outside text field to close keyboard
        getView().getStage().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if ((x > textField.getX() &&
                        x < textField.getX() + textField.getWidth() &&
                        y > textField.getY() &&
                        y < textField.getY() + textField.getHeight())
                ) return;
                textField.getOnscreenKeyboard().show(false);
            }
        });
    }

    private void updateShownAvatar() {
        currentShownAvatarId = (currentShownAvatarId + Assets.NUMBER_OF_AVATARS) % Assets.NUMBER_OF_AVATARS;
        ((ProfileSettingsView) view).updateShownAvatarId(currentShownAvatarId);
    }

    private void userSubmitsChanges(ProfileSettingsView view) {
        profileCollection.updateLocalProfile(
                view.getNameTextField().getText(),
                currentShownAvatarId,
                new OnCollectionUpdatedListener(){
                    @Override
                    public void onSuccess(FirebaseDocument document) {
                        goBackToMenu();
                    }

                    @Override
                    public void onFailure() {
                        ViewComponentFactory.createErrorDialog(Assets.t("profile_cannot_be_updated_error")).show(getView().getStage());
                    }
                }
        );
    }

    private void goBackToMenu() {
        GameStateManager.getInstance().pop();
    }

    private ProfileSettingsView getView() {
        return (ProfileSettingsView) view;
    }

    @Override
    public void dispose() {
        removeAllNonDefaultListeners(getView().getLeftButton());
        removeAllNonDefaultListeners(getView().getRightButton());
        removeAllNonDefaultListeners(getView().getSubmitChangesButton());
    }
}
