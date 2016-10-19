package ru.n_develop.tower_lite.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import ru.n_develop.tower_lite.N_Tower_Lite;

/**
 * Created by Dima on 22.08.2016.
 */
public class MainMenu extends State
{
//    final N_Tower_Lite game;

    // Объявим все необходимые объекты
    private Stage stage;
    private TextButton play, exit;
    private Table table;
    private Label.LabelStyle labelStyle;

    int width;

    private TextureAtlas buttonsAtlas; //** image of buttons **//

    Rectangle bucket;
    Texture bucketImage;
    OrthographicCamera camera;

    SpriteBatch batch;



    public MainMenu (final GameStageManager gsm)
    {
        super(gsm);

        Gdx.app.log("MainMenu","MainMenu");

        // Сцена -- она поможет существенно уменьшить количество кода и упростить нам жизнь
        stage = new Stage(new ScreenViewport());

        batch = new SpriteBatch();

//        camera = new OrthographicCamera();
//        camera.setToOrtho(false, N_Tower_Lite.WIDHT / 2, N_Tower_Lite.HEIGTH / 2);// задаем размер показываюмего окна

        // Скин для кнопок. Изображения вы найдете по ссылке внизу статьи
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("images/buttons.pack"));
        skin.addRegions(buttonAtlas);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = gsm.font;
        textButtonStyle.up = skin.getDrawable("but_about");
        textButtonStyle.down = skin.getDrawable("but_der");
        textButtonStyle.checked = skin.getDrawable("but_about");

        labelStyle = new Label.LabelStyle();
        labelStyle.font = gsm.font;
        table = new Table();
        table.setFillParent(true);

        // Кнопка играть. Добавляем новый listener, чтобы слушать события касания. После касания, выбрирует и переключает на экран выбора уровней, а этот экран уничтожается
        play = new TextButton("", textButtonStyle);
        play.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                Gdx.input.vibrate(20);
                return true;
            };
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("asd","asd");
                gsm.set(new Box2dTest(gsm));

//                gsm.set(new GameScreen(gsm,200));
                dispose();
            };
        });

        play.setX(Gdx.graphics.getWidth() / 2 - play.getWidth() / 2);
        play.setY(Gdx.graphics.getHeight() / 2 - play.getHeight() / 2 + 50);


        stage.addActor(play);
        //stage.addActor(exit);

        Gdx.input.setInputProcessor(stage);  // Устанавливаем нашу сцену основным процессором для ввода (нажатия, касания, клавиатура etc.)
        Gdx.input.setCatchBackKey(true); // Это нужно для того, чтобы пользователь возвращался назад, в случае нажатия на кнопку Назад на своем устройстве

    }

    @Override
    protected void handlerInput()
    {
        if (Gdx.input.justTouched())
        {
//            gsm.set(new PlayState(gsm));
        }

    }

    @Override
    protected void update(float dt)
    {
        handlerInput();
    }

    @Override
    public void render(SpriteBatch sb)
    {
//        Gdx.app.log("ww", String.valueOf(play.getX()));

        // Очищаем экран и устанавливаем цвет фона черным
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        sb.end();

        // Рисуем сцену
//        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose()
    {
        // Уничтожаем сцену и объект game.
//        stage.dispose();
//        play.clear();
//        game.dispose();
    }
}
