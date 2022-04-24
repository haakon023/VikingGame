package group22.viking.game.controller.states;

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
import group22.viking.game.view.SoundManager;
import group22.viking.game.view.ViewComponentFactory;


public class LeaderboardState extends State {

    private static final int NUMBER_PLACES = 10;
    private final ProfileCollection profileCollection;

    public LeaderboardState(VikingGame game) {
        super(Assets.leaderboardView, game);
        addListenersToButtons();


        this.profileCollection = game.getProfileCollection();

        loadLeaderboard();
        System.out.println("LEADERBOARD STATE CREATED");
    }


    private LeaderboardView getView() {
        return (LeaderboardView) view;
    }

    private void addListenersToButtons() {
        getView().getExitButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                dispose();
                SoundManager.buttonClickSound();
                GameStateManager.getInstance().pop();
            }
        });


    }


    private void loadLeaderboard() {
        profileCollection.loadLeaderboard(
                NUMBER_PLACES,
                new OnCollectionUpdatedListener(){
                    @Override
                    public void onSuccess(FirebaseDocument document) {
                        displayLeaderboard();
                    }

                    @Override
                    public void onFailure() {
                        ViewComponentFactory.createErrorDialog(Assets.t("server_error")).show(getView().getStage());
                    }
                }
        );
    }

    private void displayLeaderboard(){
        ArrayList<Profile> leaderboard = profileCollection.getLeaderboard();
        Array<String> names = new Array<>();
        Array<String> highscores = new Array<>();
        int localPlayerPosition = -1;
        for(int i = 0; i < leaderboard.size(); i++) {
            names.add(leaderboard.get(i).getName());
            highscores.add(String.valueOf(leaderboard.get(i).getHighscore()));
            if(leaderboard.get(i) == profileCollection.getLocalPlayerProfile()) {
                localPlayerPosition = i;
            }
        }
        getView().createLeaderboardTable(names, highscores, localPlayerPosition);
        getView().getLeaderboardTable().addAction(ViewComponentFactory.createFadeInAction());
    }


    @Override
    public void dispose() {
        getView().getExitButton().removeListener(
                getView().getExitButton().getClickListener()
        );
    }
}
