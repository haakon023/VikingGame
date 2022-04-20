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
import group22.viking.game.models.Assets;
import group22.viking.game.view.ErrorDialog;
import group22.viking.game.view.LobbyView;
import group22.viking.game.view.ViewComponentFactory;


public class LobbyState extends State {

    ProfileCollection profileCollection;
    LobbyCollection lobbyCollection;

    private final boolean IS_HOST;

    /**
     * Host lobby constructor.
     *
     * @param game {VikingGame}
     */
    public LobbyState(final VikingGame game) {
        super(Assets.lobbyView, game);
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
     * @param game {VikingGame}
     * @param joinLobbyId {String} ID of lobby
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

        displayGuest(profileCollection.getLocalPlayerProfile());

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
                        setLobbyListener(lobby);
                        displayLobbyId(lobby.getId());
                    }

                    @Override
                    public void onFailure() {
                        // TODO Notify that problems with server. No lobby created
                    }
                }
        );
    }

    private void setLobbyListener(Lobby lobby) {
        lobbyCollection.addLobbyListener(lobby, new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                Lobby lobby = (Lobby) document;

                System.out.println(lobby.getState());
                switch (lobby.getState()) {
                    case OPEN:
                    case UNDEFINED:
                        return;
                    case GUEST_LEFT:
                        getView().resetGuest();
                        return;
                    case GUEST_READY:
                    case GUEST_JOINED:
                        if(IS_HOST) getOpponentInformationAndDisplay(lobby.getGuestId());
                        return;
                    case RUNNING:
                        if(IS_HOST) {
                            return; // self started, nothing to do
                        }
                        GameStateManager.getInstance().push(new PlayState(game, lobby));
                        return;
                }
                getOpponentInformationAndDisplay(lobby.getGuestId());
            }

            @Override
            public void onFailure() {
                // lobby deleted
                handleHostClosedLobby();
            }
        });
    }


    /**
     * Get opponent information from server, and call display functions.
     *
     * @param profileId {String} opponent profile ID
     */
    private void getOpponentInformationAndDisplay (String profileId) {
        profileCollection.readProfile(profileId, new OnCollectionUpdatedListener() {
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
     * @param lobbyId {String} ID of lobby
     */
    private void tryJoinLobby(String lobbyId){
        lobbyCollection.tryToJoinLobbyById(
                lobbyId,
                profileCollection.getLocalPlayerProfile(),
                // Got Lobby Listener
                new OnCollectionUpdatedListener() {
                    @Override
                    public void onSuccess(FirebaseDocument document) {
                        Lobby lobby = (Lobby) document;
                        getOpponentInformationAndDisplay(lobby.getHostId());
                        setLobbyListener(lobby);
                    }

                    @Override
                    public void onFailure() {
                        // TODO Lobby not found. Return to Main Menu.
                    }
                });
    }

    private void displayHost(Profile profile) {
        getView().updateNameLabelHost(profile.getName());
        getView().updateAvatarHost((int) profile.getAvatarId());
    }

    private void displayGuest(Profile profile) {
        getView().updateNameLabelGuest(profile.getName());
        getView().updateAvatarGuest((int) profile.getAvatarId());
        getView().getNameLabelGuest().setVisible(true);
        getView().getScoreLabelGuest().setVisible(true);
    }

    private void addListenersToButtons() {
        getView().getPlayButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                hostConfirmsStart();
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

    private LobbyView getView() {
        return (LobbyView) view;
    }

    private void userExits() {
        // after leaving logic
        OnCollectionUpdatedListener whenExited = new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                GameStateManager.getInstance().pop();
                dispose();
            }

            @Override
            public void onFailure() {
                ErrorDialog errorDialog = ViewComponentFactory.createErrorDialog();
                errorDialog.show(getView().getStage());
            }
        };

        if(IS_HOST) {
            lobbyCollection.deleteLobby(whenExited);
        } else {
            lobbyCollection.leaveLobby(whenExited);
        }
    }

    private void handleHostClosedLobby() {
        if(IS_HOST) return; // host has to call exit method by button
        GameStateManager.getInstance().pop();
        dispose();
    }

    private void hostConfirmsStart() {
        System.out.println("PLAY BUTTON CLICKED");
        if(!IS_HOST) return;
        if(!lobbyCollection.getLobby().isFull()) return;
        if(!lobbyCollection.getLobby().isGuestReady()) return;

        lobbyCollection.setLobbyToStarted(new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                GameStateManager.getInstance().push(new PlayState(game, lobbyCollection.getLobby()));
            }

            @Override
            public void onFailure() {
                // TODO Network error
            }
        });
    }

    private void displayLobbyId(String lobbyId){
        getView().printLobbyId(lobbyId);
    }

}
