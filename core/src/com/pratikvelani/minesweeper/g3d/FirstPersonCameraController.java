package com.pratikvelani.minesweeper.g3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntIntMap;

public class FirstPersonCameraController extends InputAdapter {
    private final Camera camera;
    private final IntIntMap keys = new IntIntMap();

    private float velocity = 5;
    private float degreesPerPixel = 0.5f;
    private final Vector3 tmp = new Vector3();

    public FirstPersonCameraController (Camera camera) {
        this.camera = camera;
    }

    /** Sets the velocity in units per second for moving forward, backward and strafing left/right.
     * @param velocity the velocity in units per second */
    public void setVelocity (float velocity) {
        this.velocity = velocity;
    }

    /** Sets how many degrees to rotate per pixel the mouse moved.
     * @param degreesPerPixel */
    public void setDegreesPerPixel (float degreesPerPixel) {
        this.degreesPerPixel = degreesPerPixel;
    }

    @Override
    public boolean touchDragged (int screenX, int screenY, int pointer) {
        float deltaX = -Gdx.input.getDeltaX() * degreesPerPixel;
        float deltaY = -Gdx.input.getDeltaY() * degreesPerPixel;

        camera.direction.rotate(camera.up, deltaX);
        tmp.set(camera.direction).crs(camera.up).nor();
        camera.direction.rotate(tmp, deltaY);


        /*Gdx.app.log("", "" + camera.direction);
        Gdx.app.log("", deltaX + ":" + deltaY);

        if (camera.direction.x < -0.9 && deltaX > 0) {
            deltaX = 0;
        } else if (camera.direction.x > 0.9 && deltaX < 0) {
            deltaX = 0;
        }

        if (camera.direction.y > 0.9 && deltaY > 0) {
            deltaY = 0;
        } else if (camera.direction.y < -0.9 && deltaY < 0) {
            deltaY = 0;
        }

        if (camera.direction.z > -0.25 && camera.direction.x < -0.9 && deltaX > 0) {
            deltaX = 0;
            deltaY = 0;
        } else if (camera.direction.z > -0.25 && camera.direction.x > 0.9 && deltaX < 0) {
            deltaX = 0;
            deltaY = 0;
        }*/

        return true;
    }

    public void update () {
        update(Gdx.graphics.getDeltaTime());
    }

    public void update (float deltaTime) {
        camera.update(true);
    }
}
