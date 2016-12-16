package ru.n_develop.tower_lite.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

import ru.n_develop.tower_lite.N_Tower_Lite;
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
    private long time ;
    private  BitmapFont font;

    private int rotationSpeed = 5;

    public Box2dTest(GameStageManager gsm)
    {
        super(gsm);
//        font = new BitmapFont(Gdx.files.internal("fonts/calibri.ttf"), false);
        font = new BitmapFont();
        startY = N_Tower_Lite.HEIGTH - Blox2d.BLOCKY - 10;
        // передаем 0 это отсутстивие гравитации по Х и -10 м/с это по У
        world = new World(new Vector2(0,-10), true);
        world.setGravity(new Vector2(0,-15));
        rend = new Box2DDebugRenderer();
        camera = new OrthographicCamera(N_Tower_Lite.WIDHT, N_Tower_Lite.HEIGTH);
        camera.position.set(new Vector2(N_Tower_Lite.WIDHT / 2, N_Tower_Lite.HEIGTH / 2), 0);

        position = new Vector2(10, 15);
        velosity = new Vector2(0, 0);
        position_new_blox = new Vector2(10, 25);
        // Создаем стены
        createWall();

        Gdx.app.log("camera Y start = ", String.valueOf(camera.position.y));
        Gdx.app.log("blox Y start = ", String.valueOf(startY + Blox2d.BLOCKY  * countBlox));

        bloxArray = new Array<Blox2d>();
        bloxArray.add(new Blox2d((N_Tower_Lite.WIDHT / 2) - Blox2d.BLOCKY  / 2,startY + Blox2d.BLOCKY  * countBlox,world,Blox2d.STATIC));
    }

    @Override
    protected void handlerInput()
    {

        if (Gdx.input.justTouched())
        {
            // если произошло нажатие и блок не падает, подменяем его на динамический и отпускаем вниз
            if (bloxArray.peek().getStatus() != Blox2d.MOVE)
            {
                time = TimeUtils.millis();
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

        // вижение камеры
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.zoom += 0.02;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom -= 0.02;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (camera.position.x > 0)
                camera.translate(-3, 0, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (camera.position.x < 1024)
                camera.translate(3, 0, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if (camera.position.y > 0)
                camera.translate(0, -3, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (camera.position.y < 1024)
                camera.translate(0, 3, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.rotate(-rotationSpeed, 0, 0, 1);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.E)) {
            camera.rotate(rotationSpeed, 0, 0, 1);
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
            Float X = bloxArray.peek().getPosition1().x;
            Float Y = bloxArray.peek().getPosition1().y;

//            bloxArray.items

            Gdx.app.log("new block OLD Y = ", String.valueOf(position_new_blox.y));

            position_new_blox.y += Blox2d.BLOCKY  * 2 ;
            Gdx.app.log("new block NEW Y = ", String.valueOf(position_new_blox.y));

            Gdx.app.log("camera OLD Y = ", String.valueOf(camera.position.y));
//            camera.position.y = position_new_blox.y /2 - 10 *2;
            camera.position.y += Blox2d.BLOCKY  * 2  ;
            camera.update();
            Gdx.app.log("camera New Y = ", String.valueOf(camera.position.y));

            creatRectStatic();
            timer = false;
            Gdx.app.log("Count", String.valueOf(countBlox));
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

        sb.setProjectionMatrix(camera.combined);
        sb.begin();

//        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        font.getData().setScale(.25f,.25f);
//        font.draw(sb,String.valueOf(countBlox),camera.position.x + 20,camera.position.y + 35);

        // нужно будет сделать определения кол-ва символов и в зависимости от это го отодвигать от края по X
        gsm.font.getData().setScale(.20f,.20f);
        gsm.font.draw(sb, String.valueOf(countBlox),camera.position.x + 20,camera.position.y + 35);
        sb.end();
    }

    @Override
    protected void dispose()
    {

    }

    private void creatRectStatic()
    {
        countBlox++;
        bloxArray.add(new Blox2d((N_Tower_Lite.WIDHT / 2) - Blox2d.BLOCKX / 2,
                startY + Blox2d.BLOCKY * 2  * countBlox,world,Blox2d.STATIC));
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
                new Vector2(0,N_Tower_Lite.HEIGTH),
                new Vector2(3,10),
                new Vector2(N_Tower_Lite.WIDHT - 3, 10),
                new Vector2(N_Tower_Lite.WIDHT,N_Tower_Lite.HEIGTH)
        });

        fDef.shape = shape;
        // кг
        fDef.density = 0;

        w.createFixture(fDef);
    }
}
