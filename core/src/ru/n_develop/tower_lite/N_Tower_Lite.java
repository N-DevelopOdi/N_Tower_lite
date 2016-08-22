package ru.n_develop.tower_lite;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class N_Tower_Lite extends Game
{

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Stage stage; //** stage holds the Button **//
	private BitmapFont font; //** same as that used in Tut 7 **//
	private TextureAtlas buttonsAtlas; //** image of buttons **//
	private Skin buttonSkin; //** images are used as skins of the button **//
	private TextButton button; //** the button - the only actor in program



	@Override
	public void create () {

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480); //** w/h ratio = 1.66 **//

		batch = new SpriteBatch();

		buttonsAtlas = new TextureAtlas("buttons.pack"); //** button atlas image **//
		buttonSkin = new Skin();
		buttonSkin.addRegions(buttonsAtlas); //** skins for on and off **//
		font = new BitmapFont(Gdx.files.internal("fonts/16.fnt"),false); //** font **//

		stage = new Stage();        //** window is stage **//
		stage.clear();
		Gdx.input.setInputProcessor(stage); //** stage is responsive **//

		TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(); //** Button properties **//
		style.up = buttonSkin.getDrawable("but_about");
		style.down = buttonSkin.getDrawable("but_order");
		style.font = font;

		button = new TextButton("", style); //** Button text and style **//
		button.setPosition(100, 100); //** Button location **//
		button.setHeight(300); //** Button Height **//
		button.setWidth(600); //** Button Width **//
		button.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("my app", "Released");
			}
		});

		stage.addActor(button);
	}

	@Override
	public void dispose() {
		batch.dispose();
		buttonsAtlas.dispose();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		stage.draw();
		batch.end();

	}
}
