package com.pratikvelani.minesweeper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.pratikvelani.minesweeper.GdxGame;
import com.pratikvelani.minesweeper.actors.BaseActor;
import com.pratikvelani.minesweeper.actors.TileActor;
import com.pratikvelani.minesweeper.g3d.ClickAdapter;

import java.util.ArrayList;


public class GameScreen extends ClickAdapter implements Screen {
    private static final float FOV = 67F;
    private static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;

    public static final String TAG = GameScreen.class.getName();

    private GdxGame game;

    private SpriteBatch batch;

    private Environment environment;
    private ModelBatch modelBatch;
    private PerspectiveCamera cam;

    private CameraInputController camController;
    private FirstPersonCameraController firstPersonCameraController;

    //private ArrayList<BaseActor> models = new ArrayList<BaseActor>();
    private ArrayList<TileActor> tiles = new ArrayList<TileActor>();
    public ModelInstance ground;

    private ModelInstance debugBall;


    public GameScreen(GdxGame game) {
        super ();
        this.game = game;

        batch = new SpriteBatch();

        init ();
    }

    private void init () {
        setupEnvironment();
    }

    @Override
    public void show() {
        /*ModelBuilder modelBuilder = new ModelBuilder();

        Texture texture = new Texture("floorTile.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Material material = new Material(TextureAttribute.createDiffuse(texture)*//*, ColorAttribute.createSpecular(1, 1, 1, 1), FloatAttribute.createShininess(16f)*//*);
        long attributes = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates| VertexAttributes.Usage.Generic;

        model = modelBuilder.createBox(distX / Constants.THREED_SCALE + Constants.FLOOR_OFFSET, 1, distY / Constants.THREED_SCALE + Constants.FLOOR_OFFSET,
                material, attributes);
        model.manageDisposable(texture);

        ground = new ModelInstance(model, new Matrix4().setToTranslation((centerX / Constants.THREED_SCALE)/2, 0, (centerY / Constants.THREED_SCALE)/2));

        //floorInstance.transform.setTranslation(distX / Constants.THREED_SCALE, 0, distY / Constants.THREED_SCALE);
        ground.calculateTransforms();*/

        setupTiles ();
    }

    @Override
    public void render(float delta) {
        camController.update();
        firstPersonCameraController.update(delta);

        /*Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(Assets.getInstance().getTexture(Assets.TEXTURE_BADLOGIC), 0, 0);
        batch.end();*/

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(BACKGROUND_COLOR.r, BACKGROUND_COLOR.g, BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(cam);
        //modelBatch.render(ground, environment);

        for (TileActor actor: tiles) {
            modelBatch.render(actor, environment);
        }

        modelBatch.render(debugBall, environment);

        modelBatch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged (int screenX, int screenY, int pointer) {
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Gdx.app.log(TAG, "mouseMoved ::" + screenX +"::"+ screenY);
        return super.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public void clicked(float x, float y) {
        super.clicked(x, y);
    }

    private void setupEnvironment () {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        //environment.add(new SpotLight().set(Color.RED, new Vector3(0, 0, -10), new Vector3(0, 0, 0), 100.0f, 50.0f, 50.0f));
        //environment.add(new PointLight().set(0.8f, 0.8f, 0.8f, 0f, 0f, 10f, 100f));

        modelBatch = new ModelBatch();

        cam = new PerspectiveCamera(FOV, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0f, 0f, 50f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        camController = new CameraInputController(cam);
        camController.pinchZoomFactor = 100f;

        firstPersonCameraController = new FirstPersonCameraController(cam);
        firstPersonCameraController.setDegreesPerPixel(0.1f);
        firstPersonCameraController.setVelocity(10);

        //Gdx.input.setInputProcessor(new InputMultiplexer(this, camController));
        Gdx.input.setInputProcessor(new InputMultiplexer(firstPersonCameraController));

        // DRAWING DEBUG BALL
        ModelBuilder modelBuilder = new ModelBuilder();
        Model model = modelBuilder.createSphere(2, 2, 2, 10, 10,
                new Material(ColorAttribute.createDiffuse(com.badlogic.gdx.graphics.Color.RED)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        debugBall = new ModelInstance(model);
        debugBall.calculateTransforms();
    }

    private void setupTiles () {

        int x=0, y=0;
        int mrows = 9;
        int mcols = 9;

        int tileW = 10;
        int offset = 5;

        for (x=0; x<mrows; x++) {
            for (y=0; y<mcols; y++) {
                float posX = tileW*x+offset*x;
                float posY = tileW*y+offset*y;
                float length = tileW;
                float breadth = tileW;
                float height = tileW;

                createTile(new Vector3(posX, posY, 0), length, breadth, height);
            }
        }
    }

    private void createTile (Vector3 p1, float length, float breadth, float height) {
        TileActor stackActor = TileActor.Factory.createStack(p1, length, breadth, height);
        tiles.add(stackActor);

        //models.add(stackActor);
    }
}
