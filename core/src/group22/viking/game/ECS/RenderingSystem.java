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
import com.sun.org.apache.xerces.internal.impl.dv.xs.YearDV;

import java.util.Comparator;

import group22.viking.game.ECS.components.TextureComponent;
import group22.viking.game.ECS.components.TransformComponent;

public class RenderingSystem extends SortedIteratingSystem {

    //Pixels per meter
    static final float PPM = 1f;
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
    
    private ComponentMapper<TransformComponent> cmTransformComp;
    private ComponentMapper<TextureComponent> cmTextureComp;

    public RenderingSystem(SpriteBatch spriteBatch, ZComparator comparator) {
        super(Family.all(TransformComponent.class).get(), comparator);
        this.comparator = comparator;

        this.spriteBatch = spriteBatch;

        cmTransformComp = ComponentMapper.getFor(TransformComponent.class);
        cmTextureComp = ComponentMapper.getFor(TextureComponent.class);

        renderQueue = new Array<Entity>();
        
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
            TextureComponent texComp = cmTextureComp.get(entity);
            TransformComponent transComp = cmTransformComp.get(entity);

            if(texComp.region == null || transComp.isHidden)
                continue;

            float width = texComp.region.getRegionWidth();
            float height = texComp.region.getRegionHeight();

            float originX = width / 2;
            float originY = height / 2;

            spriteBatch.draw(texComp.region,
                    transComp.position.x - originX,
                    transComp.position.y - originY,
                    originX, originY,
                    width, height,
                    PixelsToMeters(transComp.scale.x), PixelsToMeters(transComp.scale.y),
                    transComp.rotation
                    );
        }
        
        spriteBatch.end();
        renderQueue.clear();
        
        
    }
    
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    public OrthographicCamera getCamera()
    {
        return camera;
    }
}
