package group22.viking.game.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.controller.firebase.Profile;
import group22.viking.game.controller.firebase.ProfileCollection;
import group22.viking.game.view.LeaderboardView;


public class LeaderboardState extends State {

    private final ProfileCollection profileCollection;
    private ArrayList<Profile> leaderboard;

    public LeaderboardState(VikingGame game) {
        super(new LeaderboardView(game.getBatch(), game.getCamera()), game);
        Gdx.input.setInputProcessor(view.getStage());
        addListenersToButtons();

        this.profileCollection = game.getProfileCollection();
        leaderboard = profileCollection.getLeaderboard();

        System.out.println("LEADERBOARD STATE CREATED");
        System.out.println(leaderboard);        //TODO
    }

    @Override
    protected void handleInput() {

    }


    public void update(float dt) {

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



}
