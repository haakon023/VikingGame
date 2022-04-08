package group22.viking.game.controller.firebase;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Preferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import group22.viking.game.controller.VikingGame;

/**
 * The ProfileCollection follows the concept: First write data in the database, and save it to the
 * local collection AFTER a confirmation of the server.
 */
public class ProfileCollection extends FirebaseCollection{

    private static final String PREFERENCES_PROFILE_KEY = "local-profile-id";

    private String hostId;
    private String guestId;
    private String localPlayerId;
    private final Preferences preferences;

    private ArrayList<String> leaderboard;

    public ProfileCollection(FirebaseInterface firebaseInterface, Preferences preferences) {
        super(firebaseInterface, new Profile(null));
        super.name = "profile";
        this.preferences = preferences;
    }

    public void init(final OnCollectionUpdatedListener listener) {
        if(!preferences.contains(PREFERENCES_PROFILE_KEY) ||
                preferences.getString(PREFERENCES_PROFILE_KEY) == null ||
                preferences.getString(PREFERENCES_PROFILE_KEY).isEmpty()){
            System.out.println("NO LOCAL PROFILE FOUND");
            listener.onSuccess(null);
            return;
        }
        this.localPlayerId = preferences.getString(PREFERENCES_PROFILE_KEY);
        this.readProfile(localPlayerId, new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                System.out.println("PROFILE SUCCESSFULLY LOADED FROM DB");
                listener.onSuccess(document);
            }

            @Override
            public void onFailure() {
                System.out.println("PROFILE DOESN'T EXIST");
                listener.onFailure();
            }
        });
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
                        System.out.println("ProfileCollection: Host is: " + documentId);

                        Profile profile = new Profile(documentId);
                        add(documentId, profile);

                        localPlayerId = documentId;
                        preferences.putString(PREFERENCES_PROFILE_KEY, documentId);

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
                                long score,
                                final OnCollectionUpdatedListener listener)
    {
        profile.setIsLoaded(false);
        profile.addFinishedGame(win, score);

        Map<String, Object> profileValues = new HashMap<>();
        profileValues.put(Profile.KEY_GAMES_WON, profile.getWonGames());
        profileValues.put(Profile.KEY_GAMES_LOST, profile.getLostGames());
        profileValues.put(Profile.KEY_HIGHSCORE, profile.getHighscore());

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

    /**
     * Load the top leaderboard profiles.
     *
     * @param topPlaces {int} top X places
     * @param listener for synchronization
     */
    public void loadLeaderboard(int topPlaces, final OnCollectionUpdatedListener listener) {
        final int[] counter = {topPlaces};
        leaderboard = new ArrayList<>();
        firebaseInterface.getAll(name, Profile.KEY_HIGHSCORE, topPlaces, new OnGetDataListener() {
            @Override
            public void onGetData(String documentId, Map<String, Object> data) {
                Profile profile = new Profile(documentId);
                leaderboard.add(documentId);
                for(Map.Entry<String, Object> e : data.entrySet()) {
                    try {
                        profile.set(e.getKey(), e.getValue());
                    } catch (FieldKeyUnknownException exception) {
                        exception.printStackTrace();
                    }
                }
                profile.setIsLoaded(true);
                counter[0]--;
                if(counter[0] <= 0) {
                    listener.onSuccess(getLocalPlayerProfile());
                }
            }

            @Override
            public void onFailure() {
                listener.onFailure();
            }
        });
    }

    /**
     * Return the leaderboard profiles.
     *
     * @return ArrayList of profiles. Empty if not loaded.
     */
    public ArrayList<Profile> getLeaderboard() {
        ArrayList<Profile> leaderboardProfiles = new ArrayList<>();
        if (leaderboard == null) return leaderboardProfiles;
        for(String profileId : leaderboard) {
            leaderboardProfiles.add((Profile) get(profileId));
        }
        return leaderboardProfiles;
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
