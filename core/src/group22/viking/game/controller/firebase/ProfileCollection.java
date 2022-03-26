package group22.viking.game.controller.firebase;

import java.util.HashMap;
import java.util.Map;

public class ProfileCollection extends FirebaseCollection{

    private String hostId;
    private String guestId;



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
    public void createProfile(String name, int avatarId, final OnCollectionUpdatedListener listener) {
        Map<String, Object> profileValues = new HashMap<>();

        profileValues.put(Profile.KEY_NAME, name);
        profileValues.put(Profile.KEY_GAMES_WON, 0);
        profileValues.put(Profile.KEY_GAMES_LOST, 0);
        profileValues.put(Profile.KEY_AVATAR_ID, avatarId);

        final ProfileCollection that = this;

        this.firebaseInterface.addDocumentWithGeneratedId(
                this.name,
                profileValues,
                new OnPostDataListener() {
                    @Override
                    public void onSuccess(String documentId) {
                        Profile profile = new Profile(documentId);
                        that.add(documentId, new Profile(documentId));
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
    public void addFinishedGame(Profile profile,
                                boolean win,
                                final OnCollectionUpdatedListener listener)
    {
        profile.setIsLoaded(false);
        profile.addFinishedGame(win);

        Map<String, Object> profileValues = new HashMap<>();
        profileValues.put(Profile.KEY_GAMES_WON, profile.getWonGames());
        profileValues.put(Profile.KEY_GAMES_LOST, profile.getLostGames());

        final ProfileCollection that = this;

        this.firebaseInterface.update(this.name, profile.getId(), profileValues, new OnPostDataListener() {
            @Override
            public void onSuccess(String documentId) {
                that.readProfile(documentId, listener);
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

        final ProfileCollection that = this;

        this.firebaseInterface.get(
                this.name,
                this.hostId,
                new OnGetDataListener() {
                    @Override
                    public void onSuccess(String documentId, Map<String, Object> data) {
                        Profile profile = (Profile) that.get(documentId);
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

}
