package group22.viking.game.controller.firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseProfileCollection extends FirebaseCollection{

    private final Map<String, Profile> profiles;

    private String hostId;
    private String guestId;

    public FirebaseProfileCollection(FirebaseInterface firebaseInterface) {
        super(firebaseInterface);
        super.name = "profile";
        this.profiles = new HashMap<>();
    }

    /**
     * Creates profile entry in database and returns unique profile-id
     *
     * @param name {String}
     * @param avatarId {int}
     */
    public void createProfile(String name, int avatarId, final OnCollectionUpdatedListener listener) {
        Map<String, Object> profileValues = new HashMap<>();

        profileValues.put(Profile.KEY_NAME, name);
        profileValues.put(Profile.KEY_GAMES_WON, 0);
        profileValues.put(Profile.KEY_GAMES_LOST, 0);
        profileValues.put(Profile.KEY_AVATAR_ID, avatarId);

        final FirebaseProfileCollection that = this;

        this.firebaseInterface.addDocumentWithGeneratedId(
                this.name,
                profileValues,
                new OnPostDataListener() {
                    @Override
                    public void onSuccess(String documentId) {
                        Profile profile = new Profile(documentId);
                        that.profiles.put(documentId, new Profile(documentId));
                        that.hostId = documentId;
                        System.out.println("Host is: " + documentId);
                        that.readProfile(documentId, listener);
                    }
                    @Override
                    public void onFailure() {
                        System.out.println("Saving profile failed.");
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
    public void addWonLostGameStats(Profile profile, boolean win) {
        long newCountSum = win ? profile.getWonGames() + 1: profile.getLostGames() + 1;
        Map<String, Object> profileValues = new HashMap<>();
        profileValues.put(win ? Profile.KEY_GAMES_WON : Profile.KEY_GAMES_LOST, newCountSum);
        this.firebaseInterface.update(this.name, profile.getId(), profileValues);
    }

    /**
     * Read profile values from Database.
     *
     * @param profileId {String}
     */
    public void readProfile(String profileId, final OnCollectionUpdatedListener listener) {
        // add profile with unloaded status if profile is not existing yet
        if(!profiles.containsKey(profileId)) {
            this.profiles.put(profileId, new Profile(profileId));
        } else {
            this.profiles.get(profileId).setIsLoaded(false);
        }

        final FirebaseProfileCollection that = this;

        this.firebaseInterface.get(
                this.name,
                this.hostId,
                new OnGetDataListener() {
                    @Override
                    public void onSuccess(String documentId, Map<String, Object> data) {
                        Profile profile = that.profiles.get(documentId);
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
                        System.out.println("Loading profile failed.");
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
        if (!profiles.containsKey(id)) {
            return null;
        }
        return profiles.get(id);
    }

    public Profile getHostProfile() {
        return getProfileById(hostId);
    }

    public Profile getGuestProfile() {
        return getProfileById(guestId);
    }

}
