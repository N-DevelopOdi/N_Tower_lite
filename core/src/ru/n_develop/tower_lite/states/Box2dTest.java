package ru.n_develop.tower_lite.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import ru.n_develop.tower_lite.N_Tower_Lite;

/**
 * Created by Dima on 18.10.2016.
 */

public class Box2dTest extends State
{
    World world;
    Box2DDebugRenderer rend;
    OrthographicCamera camera;
    Body rect;

    public Box2dTest(GameStageManager gsm)
    {
        super(gsm);
        // передаем 0 это отсутстивие гравитации по Х и -10 м/с это по У
        world = new World(new Vector2(0,-10), true);
        rend = new Box2DDebugRenderer();
        camera = new OrthographicCamera(20, 30);
        camera.position.set(new Vector2(10, 15f), 0);

        creatRect(10, 2);
        createWall();
        creatRect(9, 7);
        creatRect(10.66f, 15);
        creatRect(10.5f, 25);
        creatRect(10.5f, 35);

    }

    @Override
    protected void handlerInput()
    {

    }

    @Override
    protected void update(float dt)
    {

    }

    @Override
    protected void render(SpriteBatch sb)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        rend.render(world,camera.combined);

        //
        world.step(1/60f, 4, 4);

    }

    @Override
    protected void dispose()
    {

    }

    private void creatRect(float x, float y)
    {
        BodyDef bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.DynamicBody;
        bDef.position.set(x,y);

        rect = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(2,2);

        fDef.shape = shape;
        // кг
        fDef.density = 2;
//        fDef.restitution = 1f;

        rect.createFixture(fDef);
    }

    private void createWall()
    {
        BodyDef bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.StaticBody;
        bDef.position.set(0,0);

        Body w = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();

        ChainShape shape = new ChainShape();
        shape.createChain(new Vector2[]{
                new Vector2(0,30), new Vector2(3,0), new Vector2(17, 0), new Vector2(20,30)
        });

        fDef.shape = shape;
        // кг
        fDef.density = 0;

        w.createFixture(fDef);
    }
}
