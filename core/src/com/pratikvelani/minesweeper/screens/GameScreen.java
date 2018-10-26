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
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.environment.SpotLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
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

    private int selected = -1, selecting = -1;

    private DirectionalLight directionalLight;


    public GameScreen(GdxGame game) {
        super ();
        this.game = game;

        init ();
    }

    private void init () {
        batch = new SpriteBatch();
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

        directionalLight.setDirection(cam.direction);

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
        selecting = getObject(screenX, screenY);
        /*if (selecting >= 0) {
            return true;
        }*/
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged (int screenX, int screenY, int pointer) {
        /*if (selecting >= 0) {
            return true;
        }*/
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        //Gdx.app.log(TAG, "mouseMoved ::" + screenX +"::"+ screenY);
        //return firstPersonCameraController.touchDragged(screenX, screenY, 0);
        return super.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        if (selecting >= 0) {
            if (selecting == getObject(screenX, screenY)){
                //setSelected(selecting);
                Gdx.app.log("SELECT", "" + selecting);
            }
            selecting = -1;
            //return true;
        }
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public void clicked(float x, float y) {
        super.clicked(x, y);
    }

    private void setupEnvironment () {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        //environment.add(new SpotLight().set(Color.RED, new Vector3(0, 0, 20), new Vector3(0, 0, 0), 100.0f, 50.0f, 50.0f));
        //environment.add(new PointLight().set(1.0f, 0.0f, 0.0f, 0f, 0f, 0f, 1000f));

        directionalLight = new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f);
        environment.add(directionalLight);

        modelBatch = new ModelBatch();

        cam = new PerspectiveCamera(FOV, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //cam.position.set(0f, 0f, 50f);
        cam.position.set(0f, 0f, 0f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        camController = new CameraInputController(cam);
        camController.pinchZoomFactor = 100f;

        firstPersonCameraController = new FirstPersonCameraController(cam);
        firstPersonCameraController.setDegreesPerPixel(0.5f);
        firstPersonCameraController.setVelocity(10);

        //Gdx.input.setInputProcessor(new InputMultiplexer(this, camController));
        Gdx.input.setInputProcessor(new InputMultiplexer(this, firstPersonCameraController));

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
        int mrows = 4;
        int mcols = 4;

        int tileW = 10;
        int offset = 1;

        float totalW = tileW * mcols + offset * mcols - offset;
        float totalH = tileW * mrows + offset * mrows - offset;

        /*for (x=0; x<mcols; x++) {
            for (y=0; y<mrows; y++) {
                float posX = (float) (tileW*x+offset*x - totalW*0.5);
                float posY = (float) (tileW*y+offset*y - totalH*0.5);
                float length = tileW;
                float breadth = tileW;
                float height = tileW;



                float px = rad*sin(xrot*(PI/180))*cos(yrot*(PI/180));
                float py = rad*sin(xrot*(PI/180))*sin(yrot*(PI/180));
                float pz = cos(xrot*(PI/180));


                createTile(new Vector3(posX, posY, 0), length, breadth, height);
            }
        }*/

        float length = tileW;
        float breadth = tileW;
        float height = tileW;

        int num = 10;
        float angleOffset = 12f;
        float startAngle = (float)Math.toRadians(180);
        for (x=0; x<num; x++) {
            /*float posX = (float) (tileW*x+offset*x - totalW*0.5);
                float posY = (float) (tileW*y+offset*y - totalH*0.5);

                float px = rad*sin(xrot*(PI/180))*cos(yrot*(PI/180));
                float py = rad*sin(xrot*(PI/180))*sin(yrot*(PI/180));
                float pz = cos(xrot*(PI/180));*/

            //float radians = (float)Math.toRadians(12*x);
            float radians = startAngle + (float)Math.toRadians(12*x);

            float px = (float)(Math.cos(radians) * 50);
            float py = (float)(Math.sin(radians) * 50);

            TileActor actor = createTile(new Vector3(px, 0, py), length, breadth, height);
            actor.viewAngle.x = 0;
            actor.viewAngle.y = -12*x;
            actor.viewAngle.z = 0;
            actor.transform.rotate(Vector3.X, actor.viewAngle.x);
            actor.transform.rotate(Vector3.Y, actor.viewAngle.y);
            actor.transform.rotate(Vector3.Z, actor.viewAngle.z);
            actor.calculateTransforms();
        }
    }

    private TileActor createTile (Vector3 p1, float length, float breadth, float height) {
        TileActor actor = TileActor.create(p1, length, breadth, height);
        tiles.add(actor);

        //models.add(stackActor);

        return actor;
    }


    public int getObject (int screenX, int screenY) {
        Ray ray = cam.getPickRay(screenX, screenY);
        int result = -1;
        float distance = -1;
        Vector3 position = new Vector3();
        for (int i = 0; i < tiles.size(); ++i) {
            final ModelInstance instance = tiles.get(i);
            instance.transform.getTranslation(position);
            position.add(((TileActor) instance).center);
            float dist2 = ray.origin.dst2(position);
            if (distance >= 0f && dist2 > distance) continue;
            if (Intersector.intersectRayBounds(ray, ((TileActor) instance).bounds, null)) {
                result = i;
                distance = dist2;
            }
            /*if (Intersector.intersectRaySphere(ray, position, ((TileActor) instance).radius, null)) {
                result = i;
                distance = dist2;
            }*/
        }
        return result;
    }

    /*public void setSelected (int value) {
        if (selected == value) return;
        if (selected >= 0) {
            Material mat = instances.get(selected).materials.get(0);
            mat.clear();
            mat.set(originalMaterial);
        }
        selected = value;
        if (selected >= 0) {
            Material mat = instances.get(selected).materials.get(0);
            originalMaterial.clear();
            originalMaterial.set(mat);
            mat.clear();
            mat.set(selectionMaterial);
        }
    }*/
}
