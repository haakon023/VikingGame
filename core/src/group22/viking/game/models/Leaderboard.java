package group22.viking.game.models;

import java.util.ArrayList;

import group22.viking.game.controller.firebase.Profile;

public class Leaderboard {
    private ArrayList<Profile> players = new ArrayList<Profile>();
    private ArrayList<Integer> score = new ArrayList<Integer>();

    public Leaderboard(ArrayList<Profile> players, ArrayList<Integer> score) {
        this.players = players;
        this.score = score;
    }

    public ArrayList<Profile> getPlayers() {
        return players;
    }

    public void addPlayers(Profile player) {
        this.players.add(player);
    }

    public ArrayList<Integer> getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score.add(score);
    }

    
}
