package group22.viking.game.controller.states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.controller.firebase.FirebaseDocument;
import group22.viking.game.controller.firebase.Lobby;
import group22.viking.game.controller.firebase.LobbyCollection;
import group22.viking.game.controller.firebase.OnCollectionUpdatedListener;
import group22.viking.game.controller.firebase.PlayerStatus;
import group22.viking.game.controller.firebase.PlayerStatusCollection;
import group22.viking.game.controller.firebase.Profile;
import group22.viking.game.controller.firebase.ProfileCollection;
import group22.viking.game.models.Assets;
import group22.viking.game.view.LobbyView;
import group22.viking.game.view.SoundManager;
import group22.viking.game.view.ViewComponentFactory;


public class LobbyState extends State {

    ProfileCollection profileCollection;
    LobbyCollection lobbyCollection;

    PlayerStatusCollection playerStatusCollection;

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
        playerStatusCollection = game.getPlayerStatusCollection();

        Gdx.input.setInputProcessor(view.getStage());

        createLobbyOnServer();
        displayHost(profileCollection.getLocalPlayerProfile());
        getView().resetGuest();
        getView().updateScoreLabelHost(null);

        addListenersToButtons();
        getView().disablePlayButton();

        SoundManager.playMusic(this, getGame().getPreferences());

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

        playerStatusCollection = game.getPlayerStatusCollection();

        Gdx.input.setInputProcessor(view.getStage());

        tryJoinLobby(joinLobbyId);

        addListenersToButtons();
        getView().disablePlayButton();
        getView().hidePlayButton();

        displayLobbyId(joinLobbyId);

        displayGuest(profileCollection.getLocalPlayerProfile());

        SoundManager.playMusic(this, getGame().getPreferences());

        System.out.println("GUEST LOBBY STATE CREATED");
    }


    public void reinitialize() {
        Gdx.input.setInputProcessor(view.getStage());
        System.out.println("RE-INIT LOBBY STATE");
        System.out.println(IS_HOST);
        displayHost(profileCollection.getProfile(lobbyCollection.getLobby().getHostId()));
        displayGuest(profileCollection.getProfile(lobbyCollection.getLobby().getGuestId()));

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
                        ViewComponentFactory.createErrorDialog().show(getView().getStage());
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
                        playerStatusCollection.resetGuest();
                        getView().resetGuest();
                        displayHostScore(null);
                        displayGuestScore(null);
                        return;
                    case GUEST_READY:
                    case GUEST_JOINED:
                        if(!IS_HOST) return;
                        getOpponentInformationAndDisplay(lobby.getGuestId());
                        prepareGameStatusDocument(lobby);
                        getDuelStatsAndDisplay();
                        return;
                    case RUNNING:
                        if(IS_HOST) {
                            return; // self started, nothing to do
                        }
                        GameStateManager.getInstance().push(new OnlinePlayState(game, lobby));
                        return;
                }
            }

            @Override
            public void onFailure() {
                // lobby deleted
                handleHostClosedLobby();
                ViewComponentFactory.createErrorDialog().show(getView().getStage());
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
                Profile profile = (Profile) document;
                if(IS_HOST) {
                    displayGuest(profile);
                    getView().enablePlayButton();
                } else {
                    displayHost(profile);
                }
            }

            @Override
            public void onFailure() {
                ViewComponentFactory.createErrorDialog().show(getView().getStage());
            }
        });
    }

    private void getDuelStatsAndDisplay() {
        playerStatusCollection.loadDuelStats(
                profileCollection.getLocalPlayerProfile().getId(),
                lobbyCollection.getLobby().getOpponentId(),
                new OnCollectionUpdatedListener() {
                    @Override
                    public void onSuccess(FirebaseDocument document) {
                        displayHostScore(playerStatusCollection.getHostOrGuestPlayerStatus(IS_HOST, true));
                        displayGuestScore(playerStatusCollection.getHostOrGuestPlayerStatus(IS_HOST, false));
                    }

                    @Override
                    public void onFailure() {
                        // TODO server issues
                    }
                }
        );
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
                        prepareGameStatusDocument(lobby);
                        getDuelStatsAndDisplay();
                    }

                    @Override
                    public void onFailure() {
                        ViewComponentFactory.createErrorDialog().show(getView().getStage());
                    }
                });
    }

    private void prepareGameStatusDocument(Lobby lobby) {
        playerStatusCollection.createOwnStatus(
                lobby.getOwnId(),
                lobby.getOpponentId(),
                new OnCollectionUpdatedListener() {
                    @Override
                    public void onSuccess(FirebaseDocument document) {
                        // nothing
                    }

                    @Override
                    public void onFailure() {
                        ViewComponentFactory.createErrorDialog().show(getView().getStage());
                    }
                }
        );
    }

    private void displayHost(Profile profile) {
        getView().updateNameLabelHost(profile.getName());
        getView().updateAvatarHost((int) profile.getAvatarId());
        getView().getAvatarHost().addAction(ViewComponentFactory.createAvatarSwooshAnimation(
                new Vector2(1,0),
                new Vector2(1000,0)
        ));
        SoundManager.avatarSwooshSound(getGame().getPreferences());
    }

    private void displayHostScore(PlayerStatus playerStatus) {
        getView().updateScoreLabelHost(playerStatus);
    }

    private void displayGuest(Profile profile) {
        getView().updateNameLabelGuest(profile.getName());
        getView().updateAvatarGuest((int) profile.getAvatarId());
        getView().updateScoreLabelGuest(playerStatusCollection.getHostOrGuestPlayerStatus(IS_HOST, false));
        getView().getNameLabelGuest().setVisible(true);
        getView().getScoreLabelGuest().setVisible(true);
        getView().getAvatarGuest().addAction(ViewComponentFactory.createAvatarSwooshAnimation(
                new Vector2(1,0),
                new Vector2(-1000,0)
        ));
        SoundManager.avatarSwooshSound(getGame().getPreferences());
    }

    private void displayGuestScore(PlayerStatus playerStatus) {
        getView().updateScoreLabelGuest(playerStatus);
    }

    private void addListenersToButtons() {
        getView().getPlayButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                SoundManager.buttonClickSound(getGame().getPreferences());
                hostConfirmsStart();
            }
        });

        getView().getExitButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                SoundManager.buttonClickSound(getGame().getPreferences());
                userExits();
            }
        });

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
                ViewComponentFactory.createErrorDialog().show(getView().getStage());
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
                GameStateManager.getInstance().push(new OnlinePlayState(game, lobbyCollection.getLobby()));
            }

            @Override
            public void onFailure() {
                ViewComponentFactory.createErrorDialog().show(getView().getStage());
            }
        });
    }

    private void displayLobbyId(String lobbyId){
        getView().printLobbyId(lobbyId);
    }

}
