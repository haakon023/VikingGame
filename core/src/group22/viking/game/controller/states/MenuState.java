package group22.viking.game.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import group22.viking.game.controller.VikingGame;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.firebase.FirebaseDocument;
import group22.viking.game.controller.firebase.GameCollection;
import group22.viking.game.controller.firebase.Lobby;
import group22.viking.game.controller.firebase.LobbyCollection;
import group22.viking.game.controller.firebase.ProfileCollection;
import group22.viking.game.controller.firebase.OnCollectionUpdatedListener;
import group22.viking.game.controller.firebase.Profile;
import group22.viking.game.view.MenuView;


public class MenuState extends State {

    private ProfileCollection profileCollection;
    private LobbyCollection lobbyCollection;

    public MenuState(VikingGame game) {
        super(new MenuView(game.getBatch(), game.getCamera()), game);

        this.profileCollection = game.getProfileCollection();
        this.lobbyCollection = game.getLobbyCollection();

        Gdx.input.setInputProcessor(view.getStage());
        addListenersToButtons();

        System.out.println("MENU STATE CREATED");
    }

    @Override
    public void reinitialize() {
        super.reinitialize();
    }

    @Override
    protected void handleInput() {

    }

    public void update(float delta){

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


    private void addListenersToButtons() {
        ((MenuView) view).getTutorialButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                dispose();
                GameStateManager.getInstance().push(new PlayState(game, PlayState.Type.TUTORIAL));
            }
        });

        ((MenuView) view).getPracticeButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                GameStateManager.getInstance().push(new PlayState(game, PlayState.Type.PRACTICE));
            }
        });

        ((MenuView) view).getHostButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                GameStateManager.getInstance().push(new LobbyState(game));
            }
        });


        ((MenuView) view).getJoinButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                GameStateManager.getInstance().push(new LobbyState(game));
            }
        });

        ((MenuView) view).getProfileButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("Profile Pushed");
                GameStateManager.getInstance().push(new ProfileSettingsState(game));
            }
        });

        ((MenuView) view).getLeaderboardButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("Leaderboard Pushed");
                GameStateManager.getInstance().push(new LeaderboardState(game));
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
