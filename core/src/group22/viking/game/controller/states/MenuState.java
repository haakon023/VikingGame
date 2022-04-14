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
import group22.viking.game.view.MenuView;


public class MenuState extends State {

    private final LobbyCollection lobbyCollection;
    private final Profile localPlayerProfile;

    public MenuState(VikingGame game) {
        super(new MenuView(game.getBatch(), game.getCamera()), game);

        this.lobbyCollection = game.getLobbyCollection();

        localPlayerProfile = game.getProfileCollection().getLocalPlayerProfile();
        refreshAvatar();

        Gdx.input.setInputProcessor(view.getStage());
        addListenersToButtons();
        initTextFieldLogic();

        System.out.println("MENU STATE CREATED");
    }

    @Override
    public void reinitialize() {
        super.reinitialize();
        refreshAvatar();
        getView().resetTextField();
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
                GameStateManager.getInstance().push(new PlayState(game, PlayState.Type.TUTORIAL));
            }
        });

        getView().getPracticeButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                GameStateManager.getInstance().push(new PlayState(game, PlayState.Type.PRACTICE));
            }
        });

        getView().getHostButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                userHostsGame();
            }
        });

        getView().getProfileButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("Profile Pushed");
                GameStateManager.getInstance().push(new ProfileSettingsState(game));
            }
        });

        getView().getLeaderboardButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("Leaderboard Pushed");
                GameStateManager.getInstance().push(new LeaderboardState(game));

            }
        });

        getView().getExitButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });

        getView().getMuteButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //todo
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
