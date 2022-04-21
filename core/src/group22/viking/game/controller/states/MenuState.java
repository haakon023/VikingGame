package group22.viking.game.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.Locale;

import group22.viking.game.controller.VikingGame;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.firebase.Lobby;
import group22.viking.game.controller.firebase.LobbyCollection;
import group22.viking.game.controller.firebase.Profile;
import group22.viking.game.models.Assets;
import group22.viking.game.view.MenuView;
import group22.viking.game.view.SoundManager;


public class MenuState extends State {

    private LobbyCollection lobbyCollection;
    private Profile localPlayerProfile;

    public MenuState(VikingGame game) {
        super(Assets.menuView, game);

        this.lobbyCollection = game.getLobbyCollection();


        localPlayerProfile = game.getProfileCollection().getLocalPlayerProfile();
        refreshAvatar();

        Gdx.input.setInputProcessor(view.getStage());
        addListenersToButtons();
        initTextFieldLogic();

        SoundManager.playMusic(this, getGame().getPreferences());
        System.out.println("SET BUTTON TO: " + getGame().getPreferences().getBoolean(VikingGame.PREFERENCES_SOUND_KEY));
        getView().getMuteButton().setChecked(
                !getGame().getPreferences().getBoolean(VikingGame.PREFERENCES_SOUND_KEY)
        );

        System.out.println("MENU STATE CREATED");
    }

    @Override
    public void reinitialize() {
        super.reinitialize();
        refreshAvatar();
        getView().resetTextField();

        SoundManager.playMusic(this, getGame().getPreferences());
    }

    private void refreshAvatar() {
        getView().setAvatar((int) localPlayerProfile.getAvatarId());
    }

    @Override
    protected void handleInput() {

    }

    private void addListenersToButtons() {
        getView().getTutorialButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                SoundManager.buttonClickSound(getGame().getPreferences());
                GameStateManager.getInstance().push(new PlayState(game, PlayState.Type.TUTORIAL));
                GameStateManager.getInstance().push(new TutorialInterruptState(game));
            }
        });

        getView().getPracticeButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                SoundManager.buttonClickSound(getGame().getPreferences());
                GameStateManager.getInstance().push(new PlayState(game, PlayState.Type.PRACTICE));
            }
        });

        getView().getHostButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                SoundManager.buttonClickSound(getGame().getPreferences());
                userHostsGame();
            }
        });

        getView().getProfileButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("Profile Clicked");
                SoundManager.buttonClickSound(getGame().getPreferences());
                GameStateManager.getInstance().push(new ProfileSettingsState(game));
            }
        });

        getView().getLeaderboardButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("Leaderboard Clicked");
                SoundManager.buttonClickSound(getGame().getPreferences());
                GameStateManager.getInstance().push(new LeaderboardState(game));

            }
        });

        getView().getExitButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                SoundManager.buttonClickSound(getGame().getPreferences());
                Gdx.app.exit();
            }
        });

        getView().getMuteButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (getGame().getPreferences().getBoolean("sound_preference")){
                    Assets.MENU_MUSIC.pause();
                    getGame().getPreferences().putBoolean(VikingGame.PREFERENCES_SOUND_KEY, false);
                } else {
                    Assets.MENU_MUSIC.play();
                    getGame().getPreferences().putBoolean(VikingGame.PREFERENCES_SOUND_KEY, true);
                }
                getGame().getPreferences().flush();
            }
        });
    }

    private void initTextFieldLogic() {
        final TextField textField = getView().getJoinTextField();

        textField.setTextFieldFilter(new TextField.TextFieldFilter() {
            @Override
            public boolean acceptChar(TextField textField, char c) {
                return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
            }
        });

        textField.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                if(c >= 'a' && c <= 'z') {
                    int cursorPosition = textField.getCursorPosition();
                    textField.setText(textField.getText().toUpperCase(Locale.ROOT));
                    textField.setCursorPosition(cursorPosition);
                }
                if(textField.getText().length() == Lobby.ID_LENGTH) {
                    userSubmitsJoinLobbyId(textField);
                }
            }
        });

        textField.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(textField.getText().length() > Lobby.ID_LENGTH) {
                    textField.setText("");
                }
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
                if(textField.getText().isEmpty()) {
                    getView().resetTextField();
                }
            }
        });
    }

    private void userSubmitsJoinLobbyId(TextField textField) {
        String id = getView().getJoinTextField().getText();
        if (!lobbyCollection.validateId(id)) {
            // id is wrong
            System.out.println("Misspelling in ID");
            getView().getJoinTextField().setText("");
            getView().makeErrorShakeOnTextField();
            SoundManager.errorSound(getGame().getPreferences());
            return;
        }
        textField.getOnscreenKeyboard().show(false);
        GameStateManager.getInstance().push(new LobbyState(game, id));
    }

    private void userHostsGame() {
        GameStateManager.getInstance().push(new LobbyState(game));
    }

    private MenuView getView() {
        return (MenuView) view;
    }

}
