package group22.viking.game.controller.firebase;

public class FirebaseProfileCollection {

    private FirebaseInterface firebaseInterface;

    private final static String COLUMN_LOST_GAMES = "lost_games";
    private final static String COLUMN_WON_GAMES = "won_games";

    public FirebaseProfileCollection(FirebaseInterface firebaseInterface) {
        this.firebaseInterface = firebaseInterface;
    }

    /**
     * Creates profile entry in database and returns unique profile-id
     *
     * @param {String} name
     * @param {int} avatarId
     *
     * @return {Profile} new profile
     */
    public Profile createProfile(String name, int avatarId) {

        // TODO

        // return profile
        return new Profile(null, null, 0, 0, 0);
    }

    /**
     * Increases the win or lost_game-field in the database by one.
     *
     * @param {Profile} Profile_profile
     * @param {boolean} win                 false if lost game
     *
     * @return {Profile} updated profile
     */
    public Profile addWonLostGameStats(Profile profile, boolean win) { // TODO use Profile-class later
        String fieldId = win ? COLUMN_WON_GAMES : COLUMN_LOST_GAMES;

        // TODO

        // return updated profile
        return new Profile(null, null, 0, 0, 0);
    }

    /**
     * Read profile values from Database.
     *
     * @param profileId
     * @return {Profile} profile
     */
    public Profile readProfile(String profileId) {

        return new Profile(null, null, 0, 0, 0);;
    }
}
