package group22.viking.game.models;

import java.util.ArrayList;

public class Leaderboard {
    private ArrayList<String> players = new ArrayList<String>();
    private ArrayList<Integer> score = new ArrayList<Integer>();
    
    public Leaderboard(ArrayList<String> players, ArrayList<Integer> score) {
        this.players = players;
        this.score = score;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public void addPlayers(String player) {
        this.players.add(player);
    }

    public ArrayList<Integer> getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score.add(score);
    }

    
}
