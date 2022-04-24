package group22.viking.game.ECS.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Comparator;

import group22.viking.game.ECS.utils.ZComparator;
import group22.viking.game.ECS.components.TextureComponent;
import group22.viking.game.ECS.components.TransformComponent;

public class RenderingSystem extends SortedIteratingSystem {

    //Pixels per meter
    static final float PPM = 1f;
    public static final float PIXELS_TO_METRES = 1.0f / PPM;
    
    static final float FRUSTUM_WIDTH = Gdx.graphics.getWidth() / PPM;
    static final float FRUSTUM_HEIGHT = Gdx.graphics.getHeight() / PPM;

    // might be of use in future:
    private final static Vector2 meterDimensions = new Vector2();
    //private static Vector2 pixelDimensions = new Vector2();

    public static Vector2 getScreenSizeInMeters(){
        meterDimensions.set(Gdx.graphics.getWidth()*PIXELS_TO_METRES,
                Gdx.graphics.getHeight()*PIXELS_TO_METRES);
        return meterDimensions;
    }

    public static float pixelsToMeters(float pixelValue){
        return pixelValue * PIXELS_TO_METRES;
    }
    
    private final SpriteBatch spriteBatch;
    private final Comparator<com.badlogic.ashley.core.Entity> comparator;
    private final Array<com.badlogic.ashley.core.Entity> renderQueue;
    private final OrthographicCamera camera;

    private final ComponentMapper<TransformComponent> cmTransformComp;
    private final ComponentMapper<TextureComponent> cmTextureComp;

    public RenderingSystem(SpriteBatch spriteBatch, ZComparator comparator) {
        super(Family.all(TransformComponent.class).get(), comparator);
        this.comparator = comparator;

        this.spriteBatch = spriteBatch;

        cmTransformComp = ComponentMapper.getFor(TransformComponent.class);
        cmTextureComp = ComponentMapper.getFor(TextureComponent.class);

        renderQueue = new Array<>();
        
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
        
        
        for(com.badlogic.ashley.core.Entity entity : renderQueue) {
            TextureComponent texComp = cmTextureComp.get(entity);
            TransformComponent transComp = cmTransformComp.get(entity);

            if(texComp.textureRegion == null || transComp.isHidden)
                continue;

            float width = texComp.textureRegion.getRegionWidth() * TextureComponent.RENDER_SCALE;
            float height = texComp.textureRegion.getRegionHeight() * TextureComponent.RENDER_SCALE;

            float originX = width / 2;
            float originY = height / 2;

            spriteBatch.draw(texComp.textureRegion,
                    transComp.position.x - originX,
                    transComp.position.y - originY,
                    originX, originY,
                    width, height,
                    pixelsToMeters(transComp.scale.x), pixelsToMeters(transComp.scale.y),
                    transComp.rotation
                    );
        }
        
        spriteBatch.end();
        renderQueue.clear();
        
        
    }
    
    @Override
    protected void processEntity(com.badlogic.ashley.core.Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    public OrthographicCamera getCamera()
    {
        return camera;
    }
}
