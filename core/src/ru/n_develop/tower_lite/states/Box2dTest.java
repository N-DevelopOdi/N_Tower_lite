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
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import ru.n_develop.tower_lite.sprintes.Blox2d;

/**
 * Created by Dima on 18.10.2016.
 */

public class Box2dTest extends State
{
    private Array<Blox2d> bloxArray;

    private World world;
    private Box2DDebugRenderer rend;
    private OrthographicCamera camera;

    private Vector2 velosity;
    private Vector2 position;
    private Vector2 position_new_blox;

    long lastDropTime; // время между палением
    private int countBlox = 0; // кол-во блоков
    private int startY = 25; // началная позиция квадрата по Y
    Boolean timer = false;

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

        bloxArray = new Array<Blox2d>();
        bloxArray.add(new Blox2d(10,startY + 4 * countBlox,world,Blox2d.STATIC));
    }

    @Override
    protected void handlerInput()
    {

        if (Gdx.input.justTouched())
        {
            // если произошло нажатие и блок не падает, подменяем его на динамический и отпускаем вниз
            if (bloxArray.peek().getStatus() != Blox2d.MOVE)
            {
                position_new_blox.x = bloxArray.peek().getPosition1().x;
                position_new_blox.y = bloxArray.peek().getPosition1().y;
                world.destroyBody(bloxArray.peek().getRect());
                bloxArray.add(new Blox2d(position_new_blox.x, position_new_blox.y, world, Blox2d.DINAMIC));
                bloxArray.peek().setStatus(Blox2d.MOVE);
                // Фиксируем время и включаем таймер
                lastDropTime = TimeUtils.millis();
                timer = true;
            }
        }
    }

    @Override
    protected void update(float dt)
    {
        // Проверяем касания
        handlerInput();

        // если таймер включен и прошло время поднимаем камеру и создаем новый блок и выключаем таймер
        if (timer && TimeUtils.millis() - lastDropTime > 2000)
        {
            position_new_blox.y += 4;
            camera.position.y = position_new_blox.y - 10;
            camera.update();
            creatRectStatic(10);
            timer = false;
        }

        for (Blox2d blox : bloxArray )
        {
            blox.update(dt);
        }

        // Падения не для box2d
//        if (getStatus() == MOVE)
//        {
//            // что такое scl
//            // добавляем скорость падения
//            velosity.add(0, 0);
//
//            // и умножаем ее на скаляр времени
//            velosity.scl(dt);
//            // добавляем новое значение
//            position.add(0, velosity.y);
////            position.add(velosity.x, MOVEMENT * dt);
//
//            velosity.scl(1 / dt);
//        }

    }

    @Override
    protected void render(SpriteBatch sb)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        rend.render(world,camera.combined);
        // вроде как говорим что 60 кадров в секунду и еще что-то
        world.step(1/60f, 4, 4);
    }

    @Override
    protected void dispose()
    {

    }

    private void creatRectStatic(float x)
    {
        countBlox++;
        bloxArray.add(new Blox2d(x,startY + 4 * countBlox,world,Blox2d.STATIC));
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
