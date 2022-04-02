package group22.viking.game.controller.firebase;

import java.util.HashMap;
import java.util.Map;

/**
 * The ProfileCollection follows the concept: First write data in the database, and save it to the
 * local collection AFTER a confirmation of the server.
 */
public class ProfileCollection extends FirebaseCollection{

    private String hostId;
    private String guestId;
    private String localPlayerId;

    public ProfileCollection(FirebaseInterface firebaseInterface) {
        super(firebaseInterface, new Profile(null));
        super.name = "profile";
    }

    /**
     * Creates profile entry in database and returns unique profile-id
     *
     * @param name {String}
     * @param avatarId {int}
     */
    public void createProfile(String name, long avatarId, final OnCollectionUpdatedListener listener) {
        Map<String, Object> profileValues = new HashMap<>();

        profileValues.put(Profile.KEY_NAME, name);
        profileValues.put(Profile.KEY_GAMES_WON, 0);
        profileValues.put(Profile.KEY_GAMES_LOST, 0);
        profileValues.put(Profile.KEY_AVATAR_ID, avatarId);

        this.firebaseInterface.addDocumentWithGeneratedId(
                this.name,
                profileValues,
                new OnPostDataListener() {
                    @Override
                    public void onSuccess(String documentId) {
                        Profile profile = new Profile(documentId);
                        add(documentId, new Profile(documentId));
                        hostId = documentId;
                        System.out.println("ProfileCollection: Host is: " + documentId);
                        readProfile(documentId, listener);
                    }
                    @Override
                    public void onFailure() {
                        System.out.println("ProfileCollection: Saving profile failed.");
                        listener.onFailure();
                    }
                });
    }

    /**
     * Increases the win or lost_game-field in the database by one.
     *
     * @param profile {Profile}
     * @param win {boolean}                 false if lost game
     */
    public void addFinishedGame(Profile profile,
                                boolean win,
                                final OnCollectionUpdatedListener listener)
    {
        profile.setIsLoaded(false);
        profile.addFinishedGame(win);

        Map<String, Object> profileValues = new HashMap<>();
        profileValues.put(Profile.KEY_GAMES_WON, profile.getWonGames());
        profileValues.put(Profile.KEY_GAMES_LOST, profile.getLostGames());

        this.firebaseInterface.addOrUpdateDocument(this.name, profile.getId(), profileValues, new OnPostDataListener() {
            @Override
            public void onSuccess(String documentId) {
                readProfile(documentId, listener);
            }

            @Override
            public void onFailure() {
                listener.onFailure();
            }
        });
    }

    /**
     * Read profile values from Database.
     *
     * @param profileId {String}
     */
    public void readProfile(String profileId, final OnCollectionUpdatedListener listener) {
        // add profile with unloaded status if profile is not existing yet
        if(!isKeyLocallyExisting(profileId)) {
            this.add(profileId, new Profile(profileId));
        } else {
            this.get(profileId).setIsLoaded(false);
        }

        this.firebaseInterface.get(
                this.name,
                this.hostId,
                new OnGetDataListener() {
                    @Override
                    public void onGetData(String documentId, Map<String, Object> data) {
                        Profile profile = (Profile) get(documentId);
                        for(Map.Entry<String, Object> e : data.entrySet()) {
                            try {
                                profile.set(e.getKey(), e.getValue());
                            } catch (FieldKeyUnknownException exception) {
                                exception.printStackTrace();
                            }
                        }
                        profile.setIsLoaded(true);
                        listener.onSuccess(profile);
                    }

                    @Override
                    public void onFailure() {
                        System.out.println("ProfileCollection: Loading profile failed.");
                        listener.onFailure();
                    }
                }
        );
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public Profile getProfileById(String id) {
        if (!isKeyLocallyExisting(id)) {
            return null;
        }
        return (Profile) get(id);
    }

    public Profile getHostProfile() {
        return getProfileById(hostId);
    }

    public Profile getGuestProfile() {
        return getProfileById(guestId);
    }

    public Profile getLocalPlayerProfile() {return getProfileById(localPlayerId);}
}