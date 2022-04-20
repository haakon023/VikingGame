package group22.viking.game.controller.states;

import com.badlogic.gdx.math.MathUtils;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.controller.firebase.FirebaseDocument;
import group22.viking.game.controller.firebase.OnCollectionUpdatedListener;
import group22.viking.game.models.Assets;
import group22.viking.game.view.LoadingView;
import group22.viking.game.view.SoundManager;


public class LoadingState extends State {

    private float progress;
    private boolean profileLoaded;

    public LoadingState(final VikingGame game){
        super(new LoadingView(game.getBatch(), game.getCamera()), game);
        this.profileLoaded = false;
        Assets.load();

        game.getProfileCollection().init(new OnCollectionUpdatedListener() {
            @Override
            public void onSuccess(FirebaseDocument document) {
                profileLoaded = true;
            }

            @Override
            public void onFailure() {
                // TODO throw some warning (no internet etc.)
            }
        });

        view.init();
    }

    @Override
    protected void handleInput() {

    }

    public void render(float deltaTime) {
        //TODO Needs to make progressbar smooth (lerp function does NOT work somehow)
        progress = MathUtils.lerp(progress, Assets.getProgress(), 0.1f);

        //once done loading all assets, go to menu screen
        if(Assets.update() && progress <= Assets.getProgress()-.001f && profileLoaded){
            Assets.createViews(game.getBatch(), game.getCamera());
            GameStateManager.getInstance().push(new MenuState(game));
        }

        view.render(deltaTime);
    }


    @Override
    public void dispose() {

    }

    public float updateProgressBar() {
        return Assets.getProgress();
    }

}
