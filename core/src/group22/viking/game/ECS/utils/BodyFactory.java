package group22.viking.game.ECS.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class BodyFactory {

    private static BodyFactory thisInstance;
    private World world;

    private BodyFactory(World world){
        this.world = world;
    }

    public static BodyFactory getInstance(World world){
        if(thisInstance == null){
            thisInstance = new BodyFactory(world);
        }
        return thisInstance;
    }


    public Body makeCirclePolyBody(float posx, float posy, float radius, BodyDef.BodyType bodyType, boolean fixedRotation){
        // create a definition
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = posx;
        boxBodyDef.position.y = posy;
        boxBodyDef.fixedRotation = fixedRotation;

        //create the body to attach said definition
        Body circleBody = world.createBody(boxBodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius /2);
        circleBody.createFixture(makeFixture(circleShape));
        circleShape.dispose();
        return circleBody;
    }

    static public FixtureDef makeFixture(Shape shape){
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        fixtureDef.density = 1f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.1f;

        return fixtureDef;
    }
}