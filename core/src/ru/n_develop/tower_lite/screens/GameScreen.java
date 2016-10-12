package ru.n_develop.tower_lite.screens;

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

/**
 * Created by Dima on 22.08.2016.
 */
public class GameScreen implements Screen
{
    final N_Tower_Lite game;

    private float    rotationSpeed;
    private float    UpCamera;

    // Объявим все необходимые объекты
    Stage stage;
    TextButton play, exit, bloxx;
    Texture bloxx1;
    SpriteBatch batch ;
    OrthographicCamera camera;
    int numberBloxx = 0;
    Table table;
    Label.LabelStyle labelStyle;

    Array<Rectangle> blox;

    int X [];
    int Y [];

    int test = 0;

    Boolean drop = false;
    int block []  ; // двигается ли текущий блок
    Boolean moveCamera; // Передвинули мы уже камеру
//    int width;
    TextureAtlas buttonsAtlas; //** image of buttons **/

    int width = 480;
    int height = 800;


    public GameScreen(final N_Tower_Lite gam, int count)
    {
        this.game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);// задаем размер показываюмего окна

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
        textButtonStyle.font = game.font;
        textButtonStyle.up = skin.getDrawable("bloxxRed");

        bloxx1 = new Texture("images/bloxx/blox.png");

        labelStyle = new Label.LabelStyle();
        labelStyle.font = game.font;
        table = new Table();
        table.setFillParent(true);

        bloxx = new TextButton("", textButtonStyle);

//        width = Gdx.graphics.getWidth();

        Gdx.input.setInputProcessor(stage);  // Устанавливаем нашу сцену основным процессором для ввода (нажатия, касания, клавиатура etc.)
        Gdx.input.setCatchBackKey(true); // Это нужно для того, чтобы пользователь возвращался назад, в случае нажатия на кнопку Назад на своем устройстве


        blox = new Array<Rectangle>();

        spawnRaindrop(numberBloxx);
    }


    private void spawnRaindrop(int number)
    {
        X[number] = (int) (width / 2 - bloxx.getWidth() / 2);
//        Y[number] = (int) (height * 0.8 + UpCamera * number+1 - bloxx.getHeight() / 2);
        Y[number] = (int) (camera.position.y * 2 - bloxx.getHeight() / 2);

        Gdx.app.log("Y - ", String.valueOf(Y[number]) + " number - " + String.valueOf(number) + " camera.y - "+ String.valueOf(camera.viewportHeight));
        Gdx.app.log("X - ", String.valueOf(X[number]) + " number - " + String.valueOf(number) + " camera.y - "+ String.valueOf(camera.viewportWidth));
//        Gdx.app.log("numberBloxx", String.valueOf(numberBloxx));

    }

    @Override
    public void render(float delta)
    {
        handleInput();
        // Очищаем экран и устанавливаем цвет фона черным
        Gdx.gl.glClearColor(0, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

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

        for (int i = 0; i <= numberBloxx; i++)
        {
            if (numberBloxx == 0)
            {
                if (Y[numberBloxx] < 20 && block[numberBloxx] == 1)
                {
                    block[numberBloxx] = 2;// прекратил падать
                    numberBloxx++;
                    spawnRaindrop(numberBloxx);
                }
            }
            else if (Y[numberBloxx] < Y[numberBloxx-1]+bloxx1.getHeight() && block[numberBloxx] == 1)
            {
                block[numberBloxx] = 2;// прекратил падать
                moveCamera = false;
                numberBloxx++;
                if (camera.position.y < 1024)
                {
                    // нужно сделать функцию с анимацией опускания
//                    upCamera();

                }
                spawnRaindrop(numberBloxx);
            }

            game.batch.draw(bloxx1, X[i], Y[i]);
        }
        game.batch.end();

//         Рисуем сцену
        stage.act(delta);
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


    private void handleInput() {
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
    public void dispose()
    {
        // Уничтожаем сцену и объект game.
        stage.dispose();
        game.dispose();
    }

    @Override
    public void hide()
    {
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resize(int width, int height)
    {
    }
    @Override
    public void show()
    {
    }
}
