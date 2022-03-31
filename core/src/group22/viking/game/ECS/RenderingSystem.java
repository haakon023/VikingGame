package group22.viking.game.ECS;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Comparator;

public class RenderingSystem extends SortedIteratingSystem {

    //Pixels per meter
    static final float PPM = 32.0f;
    public static final float PIXELS_TO_METRES = 1.0f / PPM;
    
    static final float FRUSTUM_WIDTH = Gdx.graphics.getWidth() / PPM;
    static final float FRUSTUM_HEIGHT = Gdx.graphics.getHeight() / PPM;

    private static Vector2 meterDimensions = new Vector2();
    private static Vector2 pixelDimensions = new Vector2();

    
    public static Vector2 getScreenSizeInMeters(){
        meterDimensions.set(Gdx.graphics.getWidth()*PIXELS_TO_METRES,
                Gdx.graphics.getHeight()*PIXELS_TO_METRES);
        return meterDimensions;
    }

    public static float PixelsToMeters(float pixelValue){
        return pixelValue * PIXELS_TO_METRES;
    }
    
    private SpriteBatch spriteBatch;
    private Comparator<Entity> comparator;
    private Array<Entity> renderQueue;
    private OrthographicCamera camera;
    
    //Todo needs mapper for PositionComponent and TextureComponent or whatever
    //Dependency on Git Component issues/tasks
    private ComponentMapper<tempComponentToBeRemoved> tempMapper;

    public RenderingSystem(SpriteBatch spriteBatch) {
        super(Family.all(tempComponentToBeRemoved.class).get(), new ZComparator());
        this.spriteBatch = spriteBatch;

        //For testing purposes
        //needs proper components
        tempMapper = ComponentMapper.getFor(tempComponentToBeRemoved.class);
        
        renderQueue = new Array<Entity>();
        this.spriteBatch = spriteBatch;
        
        camera = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        camera.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
    }

    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);
        renderQueue.sort(comparator);
        
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.enableBlending();
        spriteBatch.begin();
        
        
        for(Entity entity : renderQueue) {

        }
        
        spriteBatch.end();
        renderQueue.clear();
        
        
    }
    
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }
}
