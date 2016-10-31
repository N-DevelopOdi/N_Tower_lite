package ru.n_develop.tower_lite.sprintes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Dima on 17.10.2016.
 */

public class Blox2d
{
    private static final float GRAVITY = -10.15f;
    private static final int MOVEMENT = 100;

    public static final int NEW = 0;
    public static final int MOVE = 1;
    public static final int BLOCK = 2;

    public static final String DINAMIC = "dinamic";
    public static final String STATIC = "static";

    private Vector2 position;
    private Vector2 startPosition; // точки где начинаются квадраты
    private Vector2 velosity;

    private static float fi = 0f; // для движение п одуге
    private static float speedFi = 0.03f; // для движение п одуге

    private Texture blox;

    private int status = 0;

    private Body rect;

    private Boolean back = false;

    public Blox2d(float x, float y, World world, String type)
    {
        startPosition = new Vector2(x, y);
        position = new Vector2(x, y);
        velosity = new Vector2(0, 0);
        blox = new Texture("images/bloxx/blox.png");
        if (type == STATIC)
        {
            creatRectStatic(position.x, position.y, world);
        }
        else
        {
            creatRectDinamic(position.x, position.y, world);
        }
    }

    public int getStatus()
    {
        return status;
    }

    public int setStatus(int new_status)
    {

        return status = new_status;
    }

    public Vector2 getPosition1()
    {
        return position;
    }

    public Texture getBlox()
    {
        return blox;
    }

    public void update (float dt)
    {


        // Проверяем что блок новый и статический, тогда двигаем его по дуге
        if (getStatus() == NEW && String.valueOf(rect.getType()) == "StaticBody")
        {
            position.x = (float) (startPosition.x + 5 * Math.cos(fi));// центр окружности + 100 радиус * на косинус
            position.y = (float) (startPosition.y - 2*Math.sin(fi));

            rect.setTransform(position.x,position.y,0);

            // Движение по дуге
            if (fi > MathUtils.PI)
            {
                back = true;
            }
            if (fi < 0)
            {
                back = false;
            }

            if (back)
            {
                fi -= speedFi;
            }
            else
            {
                fi += speedFi;
            }

        }
        if (getStatus() == MOVE)
        {

            if (position.y < 0)
            {
//                Gdx.app.log("BLOCK", String.valueOf(status));
//                this.setStatus(BLOCK);
            }
        }
    }

    public Body getRect()
    {
        return rect;
    }

    private void creatRectDinamic(float x, float y, World world)
    {
        BodyDef bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.DynamicBody;
        bDef.position.set(x,y);

        rect = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(2,2);

        fDef.shape = shape;
        // кг
        fDef.density = 2;
        fDef.restitution = 0.01f;

        rect.createFixture(fDef);
    }
    private void creatRectStatic(float x, float y, World world)
    {
        BodyDef bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.StaticBody;
        bDef.position.set(x,y);

        rect = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(2,2);

        fDef.shape = shape;
        // кг
        fDef.density = 0;
        fDef.restitution = 0.01f;

        rect.createFixture(fDef);
    }

}
