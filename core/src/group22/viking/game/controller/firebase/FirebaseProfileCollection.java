package group22.viking.game.controller.firebase;

import java.util.HashMap;
import java.util.Map;

public class FirebaseProfileCollection extends FirebaseCollection{

    private Map<String, Profile> profiles;

    private String hostId;
    private String guestId;

    public FirebaseProfileCollection(FirebaseInterface firebaseInterface) {
        super(firebaseInterface);
        super.name = "profile";
    }

    /**
     * Creates profile entry in database and returns unique profile-id
     *
     * @param {String} name
     * @param {int} avatarId
     */
    public void createProfile(String name, int avatarId) {
        Map<String, Object> profileValues = new HashMap<String, Object>();

        profileValues.put(Profile.KEY_NAME, name);
        profileValues.put(Profile.KEY_GAMES_WON, 0);
        profileValues.put(Profile.KEY_GAMES_LOST, 0);
        profileValues.put(Profile.KEY_AVATAR_ID, avatarId);

        this.firebaseInterface.addDocumentWithGeneratedId(this.name, profileValues);
    }

    /**
     * Increases the win or lost_game-field in the database by one.
     *
     * @param {Profile} Profile_profile
     * @param {boolean} win                 false if lost game
     */
    public void addWonLostGameStats(Profile profile, boolean win) {
        int newCountSum = 0;
        try {
            newCountSum = win ? profile.getWonGames() + 1: profile.getLostGames() + 1;
        } catch (NotLoadedException e) {
            e.printStackTrace();
            return;
        }
        Map<String, Object> profileValues = new HashMap<String, Object>();
        profileValues.put(win ? Profile.KEY_GAMES_WON : Profile.KEY_GAMES_LOST, newCountSum);
        this.firebaseInterface.update(this.name, profile.getId(), profileValues);
    }

    /**
     * Read profile values from Database.
     *
     * @param {Profile} profileId
     */
    public void readProfile(String profileId) {
        // add profile with unloaded status if profile is not existing yet
        if(!profiles.containsKey(profileId)) {
            this.profiles.put(profileId, new Profile(profileId));
        } else {
            this.profiles.get(profileId).setIsLoading(true);
        }

        this.firebaseInterface.get(this.name, profileId, this);
    }

    public void update(String documentId, Map<String, Object> data) {
        Profile profile = this.profiles.get(documentId);

        for(Map.Entry<String, Object> e : data.entrySet()) {
            try {
                profile.set(e.getKey(), e.getValue());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        profile.setIsLoading(false);
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public Profile getHostProfile() {
        return this.profiles.get(hostId);
    }

    public Profile getGuestProfile() {
        return this.profiles.get(guestId);
    }
}
