package group22.viking.game.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.controller.firebase.FirebaseDocument;
import group22.viking.game.controller.firebase.OnCollectionUpdatedListener;
import group22.viking.game.controller.firebase.Profile;
import group22.viking.game.controller.firebase.ProfileCollection;
import group22.viking.game.models.Assets;
import group22.viking.game.view.LeaderboardView;
import group22.viking.game.view.LobbyView;


public class LeaderboardState extends State {

    private final ProfileCollection profileCollection;
    private ArrayList<Profile> leaderboard;

    public LeaderboardState(VikingGame game) {
        super(Assets.leaderboardView, game);
        Gdx.input.setInputProcessor(view.getStage());
        addListenersToButtons();


        this.profileCollection = game.getProfileCollection();

        loadLeaderboard(10);
        System.out.println("LEADERBOARD STATE CREATED");
    }

    @Override
    protected void handleInput() {

    }


    public void update(float dt) {

    }

    private LeaderboardView getView() {
        return (LeaderboardView) view;
    }




    private void addListenersToButtons() {
        ((LeaderboardView) view).getExitButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                dispose();
                System.out.println("EXIT BUTTON CLICKED");
                GameStateManager.getInstance().pop();
            }
        });


    }


    private void loadLeaderboard(int topPlaces) {
        profileCollection.loadLeaderboard(
                topPlaces,
                new OnCollectionUpdatedListener(){
                    @Override
                    public void onSuccess(FirebaseDocument document) {
                        leaderboard = profileCollection.getLeaderboard();
                        Array<String> names = new Array<>();
                        Array<String> highscores = new Array<>();
                        for(Profile profile: leaderboard) {
                            names.add(profile.getName());
                            highscores.add("" + profile.getHighscore());

                            System.out.println(profile.getName() + profile.getHighscore());
                        }
                        displayLeaderboard(names, highscores);
                    }

                    @Override
                    public void onFailure() {
                        // TODO error message
                    }
                }
        );
    }

    private void displayLeaderboard(Array<String> names, Array<String> highscores){
        getView().printLeaderboard(names, highscores);
    }

    
}
