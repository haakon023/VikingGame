package group22.viking.game.controller.firebase;

/**
 * Refers to data in database.
 */
public class Profile {

    private String id;
    private String name;
    private int avatarId;
    private int wonGames;
    private int lostGames;

    public Profile(String id, String name, int avatarId, int wonGames, int lostGames) {
        this.id = id;
        this.name = name;
        this.avatarId = avatarId;
        this.wonGames = wonGames;
        this.lostGames = lostGames;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public int getWonGames() {
        return wonGames;
    }

    public int getLostGames() {
        return lostGames;
    }

    public double getScore() {
        // TODO invent a more elaborated formula :)
        return wonGames - lostGames;
    }
}
