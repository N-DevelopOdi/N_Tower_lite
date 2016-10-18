package ru.n_develop.tower_lite.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import ru.n_develop.tower_lite.N_Tower_Lite;
import ru.n_develop.tower_lite.sprintes.Blox;

/**
 * Created by Dima on 22.08.2016.
 */
public class GameScreen extends State
{

    private float    rotationSpeed;
    private float    UpCamera;

    private Blox bloxclass;
    private Array<Blox> bloxArray;

    // Объявим все необходимые объекты
    Stage stage;
    TextButton play, exit;
    Texture bloxx1;
    SpriteBatch batch ;
    OrthographicCamera camera;
    int numberBloxx = 0;
    Table table;
    Label.LabelStyle labelStyle;

    int X [];
    int Y [];

    int test = 0;

    Boolean drop = false;
    int block []  ; // двигается ли текущий блок
    Boolean moveCamera; // Передвинули мы уже камеру

    int width = 480;
    int height = 800;


    public GameScreen(GameStageManager gsm, int count)
    {
        super(gsm);

        bloxArray = new Array<Blox>();


        camera = new OrthographicCamera();
        camera.setToOrtho(false, N_Tower_Lite.WIDHT / 2, N_Tower_Lite.HEIGTH / 2);// задаем размер показываюмего окна

        rotationSpeed = 0.5f;
        UpCamera = 64;
        block = new int [count];
        X = new int[count];
        Y = new int[count];
        for (int i = 0; i < block.length; i++)
        {
            block[i] = 0; // 0 - не тронутый готовый падать с верху;
        }

//       Сцена -- она поможет существенно уменьшить количество кода и упростить нам жизнь
        stage = new Stage(new ScreenViewport());

        batch = new SpriteBatch();

//         Скин для кнопок. Изображения вы найдете по ссылке внизу статьи
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("images/bloxx/bloxx.pack"));
        skin.addRegions(buttonAtlas);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("bloxxRed");

        bloxx1 = new Texture("images/bloxx/blox.png");

        labelStyle = new Label.LabelStyle();
        table = new Table();
        table.setFillParent(true);

//        width = Gdx.graphics.getWidth();

        Gdx.input.setInputProcessor(stage);  // Устанавливаем нашу сцену основным процессором для ввода (нажатия, касания, клавиатура etc.)
        Gdx.input.setCatchBackKey(true); // Это нужно для того, чтобы пользователь возвращался назад, в случае нажатия на кнопку Назад на своем устройстве


//        blox = new Array<Rectangle>();

        spawnRaindrop();
    }


    private void spawnRaindrop()
    {
        bloxArray.add(new Blox(50,300));

        Gdx.app.log("numberBloxx", String.valueOf(bloxArray.size));

//        X[number] = (int) (width / 2 - bloxx1.getWidth() / 2);
//        Y[number] = (int) (height * 0.8 + UpCamera * number+1 - bloxx.getHeight() / 2);
//        Y[number] = (int) (camera.position.y * 2 - bloxx1.getHeight() / 2);

//        Gdx.app.log("Y - ", String.valueOf(Y[number]) + " number - " + String.valueOf(number) + " camera.y - "+ String.valueOf(camera.viewportHeight));
//        Gdx.app.log("X - ", String.valueOf(X[number]) + " number - " + String.valueOf(number) + " camera.y - "+ String.valueOf(camera.viewportWidth));
//        Gdx.app.log("numberBloxx", String.valueOf(numberBloxx));

    }

    @Override
    protected void handlerInput()
    {
        if (Gdx.input.justTouched())
        {
            if (bloxArray.peek().getStatus() != 1)
            bloxArray.peek().setStatus(1);

            int status = bloxArray.peek().getStatus();
            Gdx.app.log("status", String.valueOf(status));

        }
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
            Gdx.app.log("camera.position.y - ", String.valueOf(camera.position.y));

        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (camera.position.y < 1024)
                camera.translate(0, 3, 0);
            Gdx.app.log("camera.position.y - ", String.valueOf(camera.position.y));

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
        handlerInput();

        for (Blox blox : bloxArray)
        {
            blox.update(dt);
        }

        if (bloxArray.peek().getStatus() == 2)
        {
            spawnRaindrop();
        }

//        camera.position.y = bloxArray.peek().getPosition().y + 20;

//        bloxclass.update(dt);
//        bird.update(dt);
//        camera.position.x = bird.getPosition().x + 80;
//
//        for (Tube tube : tubes)
//        {
//            if (camera.position.x - (camera.viewportWidth / 2) >
//                    tube.getPosTopTube().x + tube.getTopTube().getWidth())
//            {
//                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDHT + TUBE_SPACING) * TUBE_COUNT));
//            }
//        }
//        camera.update();

    }


    @Override
    public void render(SpriteBatch sb)
    {
        handlerInput();

        // Очищаем экран и устанавливаем цвет фона черным
        Gdx.gl.glClearColor(0, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        if(Gdx.input.isTouched())
        {
            drop = true;
            block[numberBloxx] = 1; // 1 - ачинает падать
        }

        if (block[numberBloxx] == 1)
        {
            Y[numberBloxx] -= 1500 * Gdx.graphics.getDeltaTime();
        }

        if (numberBloxx > 1)
        {
            if ((Y[numberBloxx-1] > camera.position.y ) && !moveCamera )
            {
//                Gdx.app.log("CAMera ", String.valueOf(Y[numberBloxx-1]));
                upCamera();
//                float i = 0;
//                while (i < UpCamera)
//                {
//                    i += 0.1f;
//                    camera.translate(0, 0.001f, 0);
//
//                    Gdx.app.log("CAMera ", String.valueOf(i));
//
//                }
//
//                moveCamera = true;
            }
        }

//        for (int i = 0; i <= numberBloxx; i++)
//        {
//            if (numberBloxx == 0)
//            {
//                if (Y[numberBloxx] < 20 && block[numberBloxx] == 1)
//                {
//                    block[numberBloxx] = 2;// прекратил падать
//                    numberBloxx++;
//                    spawnRaindrop(numberBloxx);
//                }
//            }
//            else if (Y[numberBloxx] < Y[numberBloxx-1]+bloxx1.getHeight() && block[numberBloxx] == 1)
//            {
//                block[numberBloxx] = 2;// прекратил падать
//                moveCamera = false;
//                numberBloxx++;
//                if (camera.position.y < 1024)
//                {
//                    // нужно сделать функцию с анимацией опускания
////                    upCamera();
//
//                }
//                spawnRaindrop(numberBloxx);
//            }
//
////            sb.draw(bloxx1, X[i], Y[i]);
//        }
//        sb.draw(bloxclass.getBlox(), bloxclass.getPosition().x, bloxclass.getPosition().y);

        for (Blox blox : bloxArray)
        {
            sb.draw(blox.getBlox(), blox.getPosition().x, blox.getPosition().y);
        }
        sb.end();

//         Рисуем сцену
//        stage.act(delta);
        stage.draw();





    }

    private void upCamera()
    {
        camera.translate(0, UpCamera, 0);
        moveCamera = true;
        Gdx.app.log("camera.position.y - ", String.valueOf(camera.position.y));
        Gdx.app.log("camera.position.x - ", String.valueOf(camera.position.x));
        Gdx.app.log("Y - ", String.valueOf(Y[numberBloxx]));
    }



    @Override
    public void dispose()
    {
        // Уничтожаем сцену и объект game.
        stage.dispose();
//        game.dispose();
    }
}
