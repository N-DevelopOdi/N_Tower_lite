package ru.n_develop.tower_lite.sprintes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
/**
 * Created by Dima on 17.10.2016.
 */

public class Blox
{
    private static final int GRAVITY = -15;
    private static final int MOVEMENT = 100;

    private static final int NEW = 0;
    private static final int MOVE = 1;
    private static final int BLOCK = 2;

    private Vector2 position;
    private Vector2 velosity;

    private Texture blox;

    private int status = 0;


    public Blox (int x, int y)
    {
        position = new Vector2(x, y);
        velosity = new Vector2(0, 0);
        blox = new Texture("images/bloxx/blox.png");
    }

    public int getStatus()
    {
        return status;
    }

    public int setStatus(int new_status)
    {
        return status = new_status;
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public Texture getBlox()
    {
        return blox;
    }

    public void update (float dt)
    {

        Gdx.app.log("status", String.valueOf(status));

        if (getStatus() == MOVE)
        {
            // что такое scl
            // добавляем скорость падения
            velosity.add(0, GRAVITY);

            // и умножаем ее на скаляр времени
            velosity.scl(dt);
            // добавляем новое значение
            position.add(0, velosity.y);
//            position.add(velosity.x, MOVEMENT * dt);

            velosity.scl(1 / dt);

            if (position.y < 0)
            {
                this.setStatus(BLOCK);
            }
        }


//        if(Gdx.input.isTouched())
//        {
//            drop = true;
//            block[numberBloxx] = 1; // 1 - ачинает падать
//        }
//
//        if (block[numberBloxx] == 1)
//        {
//            Y[numberBloxx] -= 1500 * Gdx.graphics.getDeltaTime();
//        }
//
//        if (numberBloxx > 1)
//        {
//            if ((Y[numberBloxx-1] > camera.position.y ) && !moveCamera )
//            {
////                Gdx.app.log("CAMera ", String.valueOf(Y[numberBloxx-1]));
//                upCamera();
////                float i = 0;
////                while (i < UpCamera)
////                {
////                    i += 0.1f;
////                    camera.translate(0, 0.001f, 0);
////
////                    Gdx.app.log("CAMera ", String.valueOf(i));
////
////                }
////
////                moveCamera = true;
//            }
//        }
//
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
//            } else if (Y[numberBloxx] < Y[numberBloxx - 1] + bloxx1.getHeight() && block[numberBloxx] == 1)
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
//        }







//        if (position.y > 0)
//        {
//            // что такое scl
//            // добавляем скорость падения
//            velosity.add(0, GRAVITY, 0);
//        }
//        // и умножаем ее на скаляр времени
//        velosity.scl(dt);
//        // добавляем новое значение
//        position.add(MOVEMENT * dt, velosity.y, 0);
//
//        velosity.scl(1 / dt);
//
//        if (position.y < 0 )
//        {
//            position.y = 0;
//        }

    }

    public void jump()
    {
        velosity.y = 250;
    }


}
