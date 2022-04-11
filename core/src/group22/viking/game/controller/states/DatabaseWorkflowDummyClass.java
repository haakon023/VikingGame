package group22.viking.game.controller.states;

import group22.viking.game.controller.firebase.FirebaseDocument;
import group22.viking.game.controller.firebase.PlayerStatus;
import group22.viking.game.controller.firebase.PlayerStatusCollection;
import group22.viking.game.controller.firebase.Lobby;
import group22.viking.game.controller.firebase.LobbyCollection;
import group22.viking.game.controller.firebase.OnCollectionUpdatedListener;
import group22.viking.game.controller.firebase.Profile;
import group22.viking.game.controller.firebase.ProfileCollection;

public class DatabaseWorkflowDummyClass {

    PlayerStatusCollection playerStatusCollection;
    ProfileCollection profileCollection;
    LobbyCollection lobbyCollection;

    public DatabaseWorkflowDummyClass(PlayerStatusCollection playerStatusCollection,
                                      ProfileCollection profileCollection,
                                      LobbyCollection lobbyCollection)
    {
        this.playerStatusCollection = playerStatusCollection;
        this.profileCollection = profileCollection;
        this.lobbyCollection = lobbyCollection;
    }

    /**
     * PROFILE STATE
     */

    private void changeAvatarTo(long avatarId) {
        //TODO maybe no must have
    }

    private void createProfile(String name, long avatarId) {
        profileCollection.createProfile(name, avatarId, new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                Profile profile = (Profile) document;
                // Profile created
                // TODO show
            }

            @Override
            public void onFailure() {
                // TODO
            }
        });
    }

    /**
     * MENU STATE
     */

    private void createLobby(){
        lobbyCollection.createLobby(
                profileCollection.getLocalPlayerProfile(),
                new OnCollectionUpdatedListener() {
                    @Override
                    public void onSuccess(FirebaseDocument document) {
                        Lobby lobby = (Lobby) document;
                        // TODO switch view and show lobby id
                    }

                    @Override
                    public void onFailure() {
                        // TODO
                    }
                },
                new OnCollectionUpdatedListener() {
                    @Override
                    public void onSuccess(FirebaseDocument document) {
                        // User is joined
                        Lobby lobby = (Lobby) document;
                        getGuestProfile(lobby.getGuestId());
                    }

                    @Override
                    public void onFailure() {
                        // TODO
                    }
                });
    }

    private boolean isLobbyIdValidate(String id) {
        return lobbyCollection.validateId(id);
    }

    private void tryJoinLobby(String id) {
        lobbyCollection.tryToJoinLobbyById(
                id,
                profileCollection.getLocalPlayerProfile(),
                new OnCollectionUpdatedListener() {
                    @Override
                    public void onSuccess(FirebaseDocument document) {
                        Lobby lobby = (Lobby) document;
                        getHostProfile(lobby.getHostId());
                        // TODO switch to lobby state
                    }

                    @Override
                    public void onFailure() {
                        // TODO retry
                    }
                },
                new OnCollectionUpdatedListener() {
                    @Override
                    public void onSuccess(FirebaseDocument document) {
                        // TODO start game (in lobby state)
                    }

                    @Override
                    public void onFailure() {
                        // TODO
                    }
                });
    }


    /**
     * LOBBY STATE
     */


    // as a host
    private void getGuestProfile(String guestId) {
        profileCollection.readProfile(guestId, new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                Profile guest = (Profile) document;
                // TODO display guest
            }

            @Override
            public void onFailure() {
                // TODO
            }
        });
    }

    // as a guest
    private void getHostProfile(String hostId) {
        profileCollection.readProfile(hostId, new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                Profile host = (Profile) document;
                // TODO display host
            }

            @Override
            public void onFailure() {
                // TODO
            }
        });
    }

    private void hostStartsGame() {
        lobbyCollection.setLobbyToStarted(new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                // TODO switch to game state
            }

            @Override
            public void onFailure() {
                // TODO
            }
        });
    }

    /**
     * GAME STATE
     */
    /*private void setGameListener() {
        playerStatusCollection.setOpponentListener(new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                PlayerStatus game = (PlayerStatus) document;
                // TODO update health, check if game still running etc
                // game.getHealthGuest();
                // game.getHealthHost();
                // game.isRunning();

                long score = -1; // dummy
                hostEndsGame(true, score); //dummy
            }

            @Override
            public void onFailure() {
                // TODO
            }
        });
    }*/

    private long reduceOwnHealth(long damage) {
        return playerStatusCollection.reduceOwnHealth(damage);
    }

    private void hostEndsGame(boolean hostWin, long score) {
        profileCollection.addFinishedGame(profileCollection.getHostProfile(), hostWin, score, new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                // nothing?
            }

            @Override
            public void onFailure() {
                // TODO
            }
        });
        profileCollection.addFinishedGame(profileCollection.getGuestProfile(), !hostWin, score, new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                // nothing?
            }

            @Override
            public void onFailure() {
                // TODO
            }
        });
        lobbyCollection.setLobbyToGameEnded(new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                Lobby lobby = (Lobby) document;
                // TODO switch to lobby state
            }

            @Override
            public void onFailure() {

            }
        });
    }
}
