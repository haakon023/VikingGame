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

    private final boolean IS_HOST;

    /**
     * Host lobby constructor.
     *
     * @param game
     */
    public LobbyState(final VikingGame game) {
        super(new LobbyView(game.getBatch(), game.getCamera()), game);
        this.IS_HOST = true;

        profileCollection = game.getProfileCollection();
        lobbyCollection = game.getLobbyCollection();

        Gdx.input.setInputProcessor(view.getStage());

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
        this.IS_HOST = false;

        profileCollection = game.getProfileCollection();
        lobbyCollection = game.getLobbyCollection();

        Gdx.input.setInputProcessor(view.getStage());

        tryJoinLobby(joinLobbyId);

        addListenersToButtons();
        getView().disablePlayButton();
        getView().hidePlayButton();

        displayLobbyId(joinLobbyId);

        System.out.println("GUEST LOBBY STATE CREATED");
    }

    /**
     * Create lobby on server (ONLY HOST)
     */
    private void createLobbyOnServer() {
        lobbyCollection.createLobby(
                profileCollection.getLocalPlayerProfile(),
                // gotIdListener
                new OnCollectionUpdatedListener() {
                    @Override
                    public void onSuccess(FirebaseDocument document) {
                        Lobby lobby = (Lobby) document;
                        System.out.println(lobby.getId());
                        getView().printLobbyId(lobby.getId());
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
                        displayLobbyId(lobby.getId());
                    }

                    @Override
                    public void onFailure() {
                        // TODO Notify that problems with server. No listener
                    }
                }
        );
    }

    /**
     * Get opponent information from server, and call display functions.
     *
     * @param opponent
     */
    private void getOpponentInformationAndDisplay (String opponent) {
        profileCollection.readProfile(opponent, new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                if(IS_HOST) {
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

    /**
     * Try to join Lobby. (ONLY GUEST)
     *
     * @param id
     */
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
                        System.out.println("TODO: Received info: Host starts game");




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
                userConfirmsStart();
            }
        });

        getView().getExitButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                userExits();
            }
        });

    }

    @Override
    protected void handleInput() {

    }

    public void update(float delta){

    }

    private LobbyView getView() {
        return (LobbyView) view;
    }

    private void userExits() {
        OnCollectionUpdatedListener whenExited = new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                GameStateManager.getInstance().pop();
                dispose();
            }

            @Override
            public void onFailure() {
                // TODO
            }
        };

        if(IS_HOST) {
            lobbyCollection.deleteLobby(whenExited);
        } else {
            lobbyCollection.leaveLobby(whenExited);
        }
    }

    private void userConfirmsStart() {
        System.out.println("PLAY BUTTON CLICKED");
        if(!IS_HOST) return;
        if(!lobbyCollection.getLobby().isFull()) return;
        if(!lobbyCollection.getLobby().isGuestReady()) return;

        GameStateManager.getInstance().push(new PlayState(game, PlayState.Type.ONLINE));
    }

    private void displayLobbyId(String lobbyId){
        getOpponentInformationAndDisplay(lobbyId);
    }

}
