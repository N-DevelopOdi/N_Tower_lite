package ru.n_develop.tower_lite.screens;

import com.badlogic.gdx.Gdx;
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

    Boolean drop = false;
    int block []  ; // двигается ли текущий блок
    int width;
    TextureAtlas buttonsAtlas; //** image of buttons **/

    public GameScreen(final N_Tower_Lite gam)
    {
        block = new int [20];
        X = new int[20];
        Y = new int[20];
        for (int i = 0; i < block.length; i++)
        {
            block[i] = 0; // 0 - не тронутый готовый падать с верху;
//            testBlox[i].x = (Gdx.graphics.getWidth() / 2 - bloxx.getWidth() / 2);
//            testBlox[i].y = (Gdx.graphics.getHeight() - 50 - bloxx.getHeight() / 2);
//            testBlox[i].width = 64;
//            testBlox[i].height = 64;
        }




        camera = new OrthographicCamera();
        camera.setToOrtho(false, 720, 1280);// задаем размер показываюмего окна
        camera.setToOrtho(false, 600, 600);// задаем размер показываюмего окна

        game = gam;
        // Сцена -- она поможет существенно уменьшить количество кода и упростить нам жизнь
        stage = new Stage(new ScreenViewport());

        batch = new SpriteBatch();


        // Скин для кнопок. Изображения вы найдете по ссылке внизу статьи
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("images/bloxx/bloxx.pack"));
        skin.addRegions(buttonAtlas);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = game.font;
        textButtonStyle.up = skin.getDrawable("bloxxRed");

        bloxx1 = new Texture("images/bloxx/bloxxx.png");

        Gdx.app.log("w", String.valueOf(bloxx1.getWidth()) );
        Gdx.app.log("h", String.valueOf(bloxx1.getHeight()) );

        labelStyle = new Label.LabelStyle();
        labelStyle.font = game.font;
        table = new Table();
        table.setFillParent(true);

        bloxx = new TextButton("", textButtonStyle);

        width = Gdx.graphics.getWidth();

        Gdx.input.setInputProcessor(stage);  // Устанавливаем нашу сцену основным процессором для ввода (нажатия, касания, клавиатура etc.)
        Gdx.input.setCatchBackKey(true); // Это нужно для того, чтобы пользователь возвращался назад, в случае нажатия на кнопку Назад на своем устройстве


        blox = new Array<Rectangle>();

        spawnRaindrop(numberBloxx);
    }


    private void spawnRaindrop(int number)
    {

        X[number] = (int) (Gdx.graphics.getWidth() / 2 - bloxx.getWidth() / 2);
        Y[number] = (int) (Gdx.graphics.getHeight() - 50 - bloxx.getHeight() / 2);

//        Rectangle gameBlox = new Rectangle();
//        gameBlox.x = (Gdx.graphics.getWidth() / 2 - bloxx.getWidth() / 2);
//        gameBlox.y = (Gdx.graphics.getHeight() - 50 - bloxx.getHeight() / 2);
////        raindrop.x = MathUtils.random(0,600);
////        raindrop.y = MathUtils.random(0,400);
//
//        gameBlox.height = 64;
//        gameBlox.width = 64;
//        blox.add(gameBlox);
//        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta)
    {
        // Очищаем экран и устанавливаем цвет фона черным
        Gdx.gl.glClearColor(0, 1, 0x2, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        if(Gdx.input.isTouched()) {
            Gdx.app.log("numberBloxx", String.valueOf(numberBloxx));
            drop = true;
            block[numberBloxx] = 1; // 1 - ачинает падать

        }
//        Gdx.app.log("size", String.valueOf(blox.items));


        if (block[numberBloxx] == 1)
        {
            Y[numberBloxx] -= 200 * Gdx.graphics.getDeltaTime();
        }



        for (int i = 0; i <= numberBloxx; i++)
        {
            if (Y[numberBloxx] < 20 && block[numberBloxx] == 1)
            {
                block[numberBloxx] = 2;// прекратил падать
                numberBloxx++;
                spawnRaindrop(numberBloxx);
            }

            game.batch.draw(bloxx1, X[i], Y[i]);
        }




//        for (Rectangle gameBlox : blox)
//        {
//            if (block[numberBloxx] == 1)
//            {
//                gameBlox.y -= 200 * Gdx.graphics.getDeltaTime();
//            }
//
//            if (gameBlox.getY() < 20 && block[numberBloxx] == 1)
//            {
//                block[numberBloxx] = 2;// прекратил падать
//                numberBloxx++;
//                spawnRaindrop(numberBloxx);
//
//            }
////            gameBlox.y -= 200 * Gdx.graphics.getDeltaTime();
//            game.batch.draw(bloxx1, gameBlox.x, gameBlox.y);
//
//        }

        game.batch.end();

        // Рисуем сцену
        stage.act(delta);
        stage.draw();
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
