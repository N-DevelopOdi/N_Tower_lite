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
    private World world;
    private Box2DDebugRenderer rend;
    private OrthographicCamera camera;
    private Body rect;

    private Vector2 velosity;
    private Vector2 position;
    private Vector2 position_new_blox;

    public Box2dTest(GameStageManager gsm)
    {
        super(gsm);
        // передаем 0 это отсутстивие гравитации по Х и -10 м/с это по У
        world = new World(new Vector2(0,-10), true);
        rend = new Box2DDebugRenderer();
        camera = new OrthographicCamera(20, 30);
        camera.position.set(new Vector2(10, 15f), 0);

        position = new Vector2(10, 15);
        velosity = new Vector2(0, 0);
        position_new_blox = new Vector2(10, 25);
        // Создаем стены
        createWall();


    }

    @Override
    protected void handlerInput()
    {

        if (Gdx.input.justTouched())
        {
//            position.y = (position_new_blox.y + 8) / 2;
            camera.position.y = position_new_blox.y - 10;
            creatRect(position_new_blox.x, position_new_blox.y);
            position_new_blox.y += 4;
            camera.update();

//            camera.position.set(new Vector2(10,25f), 0);
        }
    }

    @Override
    protected void update(float dt)
    {
        handlerInput();

//        if (getStatus() == MOVE)
//        {
            // что такое scl
            // добавляем скорость падения
            velosity.add(0, 0);

            // и умножаем ее на скаляр времени
            velosity.scl(dt);
            // добавляем новое значение
            position.add(0, velosity.y);
//            position.add(velosity.x, MOVEMENT * dt);

            velosity.scl(1 / dt);
//        }
    }

    @Override
    protected void render(SpriteBatch sb)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        rend.render(world,camera.combined);

//        camera.position.set(position.x, position.y, 0);
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
        fDef.restitution = 0.01f;

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
                new Vector2(0,30), new Vector2(3,5), new Vector2(17, 5), new Vector2(20,30)
        });

        fDef.shape = shape;
        // кг
        fDef.density = 0;

        w.createFixture(fDef);
    }
}
