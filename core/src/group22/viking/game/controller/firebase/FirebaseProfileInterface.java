package group22.viking.game.controller.firebase;

public class FirebaseProfileInterface implements FirebaseInterface{

    private final static String COLUMN_LOST_GAMES = "lost_games";
    private final static String COLUMN_WON_GAMES = "won_games";

    /**
     * Creates profile entry in database and returns unique profile-id
     *
     * @param {String} name
     * @return {String} id
     */
    public String createProfile(String name) {

        // TODO

        return null;
    }

    /**
     * Increases the win or lost_game-field in the database by one.
     *
     * @param {Profile} Profile_profile
     */
    public void addWonLostGameStats(String Profile_profile, boolean win) { // TODO use Profile-class later
        String fieldId = win ? COLUMN_WON_GAMES : COLUMN_LOST_GAMES;

        // TODO
    }

    public Profile readProfile(String profileId) {

        return null;
    }

    @Override
    public void someFunction() {

    }

    @Override
    public void FirstFireBaseTest() {

    }

    @Override
    public void SetOnValueChangedListener() {

    }

    @Override
    public void SetValueInDb(String target, String value) {

    }
}
