package group22.viking.game.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.controller.firebase.FirebaseDocument;
import group22.viking.game.controller.firebase.Lobby;
import group22.viking.game.controller.firebase.LobbyCollection;
import group22.viking.game.controller.firebase.OnCollectionUpdatedListener;
import group22.viking.game.controller.firebase.Profile;
import group22.viking.game.controller.firebase.ProfileCollection;
import group22.viking.game.view.LobbyView;


public class LobbyState extends State {

    ProfileCollection profileCollection;
    LobbyCollection lobbyCollection;

    private boolean isHost = false;

    /**
     * Host lobby constructor.
     *
     * @param game
     */
    public LobbyState(final VikingGame game) {
        super(new LobbyView(game.getBatch(), game.getCamera()), game);

        profileCollection = game.getProfileCollection();
        lobbyCollection = game.getLobbyCollection();

        Gdx.input.setInputProcessor(view.getStage());

        this.isHost = true;
        createLobbyOnServer();
        displayHost(profileCollection.getLocalPlayerProfile());

        addListenersToButtons();
        getView().disablePlayButton();

        System.out.println("HOST LOBBY STATE CREATED");
    }

    /**
     * Join lobby constructor.
     *
     * @param game
     * @param joinLobbyId
     */
    public LobbyState(final VikingGame game, String joinLobbyId) {
        super(new LobbyView(game.getBatch(), game.getCamera()), game);

        profileCollection = game.getProfileCollection();
        lobbyCollection = game.getLobbyCollection();

        Gdx.input.setInputProcessor(view.getStage());

        this.isHost = false;
        tryJoinLobby(joinLobbyId);

        addListenersToButtons();
        getView().disablePlayButton();
        getView().hidePlayButton();

        System.out.println("GUEST LOBBY STATE CREATED");
    }

    private void createLobbyOnServer() {
        lobbyCollection.createLobby(
                profileCollection.getLocalPlayerProfile(),
                // gotIdListener
                new OnCollectionUpdatedListener() {
                    @Override
                    public void onSuccess(FirebaseDocument document) {
                        Lobby lobby = (Lobby) document;
                        System.out.println(lobby.getId());
                        // TODO print lobby ID
                    }

                    @Override
                    public void onFailure() {
                        // TODO Notify that problems with server. No lobby created
                    }
                },
                // guestJoinedListener
                new OnCollectionUpdatedListener() {
                    @Override
                    public void onSuccess(FirebaseDocument document) {
                        Lobby lobby = (Lobby) document;
                        getOpponentInformationAndDisplay(lobby.getGuestId());
                    }

                    @Override
                    public void onFailure() {
                        // TODO Notify that problems with server. No listener
                    }
                }
        );
    }

    private void getOpponentInformationAndDisplay (String guestId) {
        profileCollection.readProfile(guestId, new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                if(isHost) {
                    displayGuest((Profile) document);
                    getView().enablePlayButton();
                } else {
                    displayHost((Profile) document);
                }
            }

            @Override
            public void onFailure() {
                // TODO display server broken warning or sth.
            }
        });
    }

    private void tryJoinLobby(String id){
        lobbyCollection.tryToJoinLobbyById(
                id,
                profileCollection.getLocalPlayerProfile(),
                // Got Lobby Listener
                new OnCollectionUpdatedListener() {
                    @Override
                    public void onSuccess(FirebaseDocument document) {
                        Lobby lobby = (Lobby) document;
                        getOpponentInformationAndDisplay(lobby.getHostId());
                    }

                    @Override
                    public void onFailure() {
                        // TODO Lobby not found. Return to Main Menu.
                    }
                },
                // Start Game Listener
                new OnCollectionUpdatedListener() {
                    @Override
                    public void onSuccess(FirebaseDocument document) {
                        // TODO: BACKEND start game





                    }

                    @Override
                    public void onFailure() {
                        // TODO network error message
                    }
                });
    }

    private void displayHost(Profile profile) {
        getView().displayHostName(profile.getName());
        getView().updateShownHost((int) profile.getAvatarId());
    }

    private void displayGuest(Profile profile) {
        getView().displayGuestName(profile.getName());
        getView().updateShownGuest((int) profile.getAvatarId());
    }

    private void addListenersToButtons() {
        getView().getPlayButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                dispose();
                System.out.println("PLAY BUTTON CLICKED");
                GameStateManager.getInstance().push(new PlayState(game, PlayState.Type.ONLINE));
            }
        });

        getView().getExitButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                dispose();
                System.out.println("EXIT BUTTON CLICKED");
                GameStateManager.getInstance().pop();
            }
        });

    }


    @Override
    protected void handleInput() {

    }

    @Override
    public void dispose() {

    }

    public void update(float delta){

    }

    private LobbyView getView() {
        return (LobbyView) view;
    }



}
