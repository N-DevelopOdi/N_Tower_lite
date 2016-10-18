package ru.n_develop.tower_lite.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Dima on 15.10.2016.
 */

public abstract class State
{
    protected OrthographicCamera camera;
    protected Vector3 mouse;
    protected GameStageManager gsm;

    public State(GameStageManager gsm)
    {
        this.gsm = gsm;
        camera = new OrthographicCamera();
        mouse = new Vector3();

    }

    protected abstract void handlerInput ();
    protected abstract void update (float dt);
    protected abstract void render (SpriteBatch sb);
    protected abstract void dispose ();
}
