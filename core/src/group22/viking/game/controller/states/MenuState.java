package group22.viking.game.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import group22.viking.game.controller.VikingGame;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.firebase.FirebaseDocument;
import group22.viking.game.controller.firebase.LobbyCollection;
import group22.viking.game.controller.firebase.OnCollectionUpdatedListener;
import group22.viking.game.controller.firebase.ProfileCollection;
import group22.viking.game.controller.firebase.Profile;
import group22.viking.game.view.MenuView;


public class MenuState extends State {

    private ProfileCollection profileCollection;
    private LobbyCollection lobbyCollection;

    private Profile localPlayerProfile;

    public MenuState(VikingGame game) {
        super(new MenuView(game.getBatch(), game.getCamera()), game);

        this.profileCollection = game.getProfileCollection();
        this.lobbyCollection = game.getLobbyCollection();

        localPlayerProfile = profileCollection.getLocalPlayerProfile();
        refreshAvatar();

        Gdx.input.setInputProcessor(view.getStage());
        addListenersToButtons();

        System.out.println("MENU STATE CREATED");
    }

    @Override
    public void reinitialize() {
        super.reinitialize();
        refreshAvatar();
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
                dispose();
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


        getView().getJoinButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                userSubmitsJoinLobbyId();
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

    private void userSubmitsJoinLobbyId() {
        String id = getView().getJoinTextField().getText();
        if (!lobbyCollection.validateId(id)) {
            // id is wrong
            System.out.println("Misspelling in ID");
            // TODO make something.
            return;
        }
        GameStateManager.getInstance().push(new LobbyState(game, id));
    }

    private void userHostsGame() {
        GameStateManager.getInstance().push(new LobbyState(game));
    }

    private MenuView getView() {
        return (MenuView) view;
    }

}
