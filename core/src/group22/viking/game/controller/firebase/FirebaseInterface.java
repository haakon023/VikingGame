package group22.viking.game.controller.firebase;

/*
*       Tutorial: https://www.youtube.com/watch?v=WhuWqWVJ-_Y
*
 */

public interface FirebaseInterface {

    public void createGame(Integer p1_health,
                           Boolean p1_wins,
                           Integer p2_health,
                           Boolean p2_wins,
                           Boolean playing);

    public void getGame();

    public void setOnValueChangedGameListener(String game_id);
}
