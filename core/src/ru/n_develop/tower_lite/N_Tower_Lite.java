package ru.n_develop.tower_lite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import ru.n_develop.tower_lite.screens.GameScreen;
import ru.n_develop.tower_lite.screens.MainMenu;

public class N_Tower_Lite extends Game
{

	private OrthographicCamera camera;
	public SpriteBatch batch;
	private Stage stage; //** stage holds the Button **//
	private TextureAtlas buttonsAtlas; //** image of buttons **//
	private Skin buttonSkin; //** images are used as skins of the button **//
	private TextButton button; //** the button - the only actor in program


	// Объявляем наш шрифт и символы для него (чтобы нормально читались русские буковки)
	public BitmapFont font, levels;
	private static final String FONT_CHARACTERS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";

	@Override
	public void create () {


		camera = new OrthographicCamera();
		camera.setToOrtho(false, 720, 1080);
//		camera.setToOrtho(false, 400, 400);

		batch = new SpriteBatch();
//
//		buttonsAtlas = new TextureAtlas("buttons.pack"); //** button atlas image **//
//		buttonSkin = new Skin();
//		buttonSkin.addRegions(buttonsAtlas); //** skins for on and off **//
//		font = new BitmapFont(Gdx.files.internal("fonts/16.fnt"),false); //** font **//
//
//		stage = new Stage();        //** window is stage **//
//		stage.clear();
//		Gdx.input.setInputProcessor(stage); //** stage is responsive **//
//
//		TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(); //** Button properties **//
//		style.up = buttonSkin.getDrawable("but_about");
//		style.down = buttonSkin.getDrawable("but_order");
//		style.font = font;
//
//		button = new TextButton("", style); //** Button text and style **//
//		button.setPosition(100, 100); //** Button location **//
//		button.setHeight(300); //** Button Height **//
//		button.setWidth(600); //** Button Width **//
//		button.addListener(new InputListener() {
//			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//				Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//
//				return true;
//			}
//			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//				Gdx.app.log("my app", "Released");
//			}
//		});
//
//		stage.addActor(button);

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/russoone.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
		param.size = Gdx.graphics.getHeight() / 18; // Размер шрифта. Я сделал его исходя из размеров экрана. Правда коряво, но вы сами можете поиграться, как вам угодно.
		param.characters = FONT_CHARACTERS; // Наши символы
		font = generator.generateFont(param); // Генерируем шрифт
		param.size = Gdx.graphics.getHeight() / 20;
		levels = generator.generateFont(param);
		font.setColor(Color.WHITE); // Цвет белый
		levels.setColor(Color.WHITE);
		generator.dispose(); // Уничтожаем наш генератор за ненадобностью.

		this.setScreen(new MainMenu(this));

	}

	@Override
	public void dispose()
	{
	}

	@Override
	public void render ()
	{
		super.render();
	}
}
