package ru.n_develop.tower_lite.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.Stack;

/**
 * Created by Dima on 15.10.2016.
 */

public class GameStageManager
{
    private Stack<State> states;

    public BitmapFont font, levels;
    private static final String FONT_CHARACTERS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";


    /**
     * Создает пустой стэк
     */
    public GameStageManager()
    {
        states = new Stack<State>();

//        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/russoone.ttf"));
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/calibri.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        param.size = Gdx.graphics.getHeight() / 100; // Размер шрифта. Я сделал его исходя из размеров экрана. Правда коряво, но вы сами можете поиграться, как вам угодно.
        param.size = 15; // Размер шрифта. Я сделал его исходя из размеров экрана. Правда коряво, но вы сами можете поиграться, как вам угодно.
        param.characters = FONT_CHARACTERS; // Наши символы
        font = generator.generateFont(param); // Генерируем шрифт
        param.size = Gdx.graphics.getHeight() / 20;
        levels = generator.generateFont(param);
        font.setColor(Color.WHITE); // Цвет белый
        levels.setColor(Color.WHITE);
        generator.dispose(); // Уничтожаем наш генератор за ненадобностью.

    }

    /**
     * Помещает элемнгт в верину стэка
     * @param state
     */
    public void push(State state)
    {
        states.push(state);
    }

    /**
     * Удаляет верхний элемент
     */
    public void pop ()
    {
        states.pop().dispose();
    }

    /**
     * удалет из тека верхний экран и помещает следуюший экран в вершину стека
     * @param state
     */
    public void set (State state)
    {
        states.pop().dispose();
        states.push(state);
    }

    /**
     * Возвращает верхний элемент не удаляя его из стека
     * Обновляет игру обновляет только элемент на вершине стека
     */
    public void update (float dt)
    {
        states.peek().update(dt);
    }

    /**
     *Принимает соостояние из верха оставляет и отрисовывает
     * @param sb
     */
    public void render (SpriteBatch sb)
    {
        states.peek().render(sb);
    }
}
