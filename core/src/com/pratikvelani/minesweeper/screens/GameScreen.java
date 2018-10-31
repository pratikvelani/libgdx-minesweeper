package com.pratikvelani.minesweeper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pratikvelani.minesweeper.GdxGame;
import com.pratikvelani.minesweeper.actors.TileActor;
import com.pratikvelani.minesweeper.g3d.ClickAdapter;
import com.pratikvelani.minesweeper.g3d.EnvironmentCubemap;
import com.pratikvelani.minesweeper.toolbox.Assets;

import java.util.ArrayList;
import java.util.Arrays;

public class GameScreen extends ClickAdapter implements Screen {
    private static final float FOV = 67F;
    private static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;

    public static final String TAG = GameScreen.class.getName();

    private final int STAGE_WIDTH;
    private final int STAGE_HEIGHT;

    private GdxGame game;
    private Stage stage2d;

    private SpriteBatch spriteBatch;

    private Environment environment;
    private ModelBatch modelBatch;
    private PerspectiveCamera cam;

    private CameraInputController camController;
    private FirstPersonCameraController firstPersonCameraController;

    //private ArrayList<BaseActor> models = new ArrayList<BaseActor>();
    private ArrayList<TileActor> tilesModels = new ArrayList<TileActor>();
    private float bombFactor = 0.1f;

    private int cols = 10;
    private int rows = 5;
    private int[][] tileMap = new int[cols][rows];
    private int[][] bombMap = new int[cols][rows];
    private TileActor[][] tiles = new TileActor[cols][rows];

    public ModelInstance ground;

    private ModelInstance debugBall;

    private int selected = -1, selecting = -1;
    private TileActor selectedTile;

    private DirectionalLight directionalLight;

    EnvironmentCubemap envCubemap;


    public GameScreen(GdxGame game) {
        super ();
        this.game = game;

        STAGE_WIDTH = Gdx.graphics.getWidth();
        STAGE_HEIGHT = Gdx.graphics.getHeight();

        Gdx.input.setCursorCatched(true);
        Gdx.input.setCursorPosition(STAGE_WIDTH/2, STAGE_HEIGHT/2);

        Gdx.app.log("STG", STAGE_WIDTH + ":" + STAGE_HEIGHT);

        init ();
    }

    private void init () {
        spriteBatch = new SpriteBatch();
        setupEnvironment();
    }

    @Override
    public void show() {
        /*if(Gdx.app.getType() == Application.ApplicationType.iOS) {
            //Do awesome stuff for iOS here
        }*/
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

        /*if (selected > 0) {
            selectedTile = tilesModels.get(selected);
            tiles[selectedTile.index.y-1][selectedTile.index.x-1].transform.rotate(Vector3.Y, 5);
            tiles[selectedTile.index.y+1][selectedTile.index.x-1].transform.rotate(Vector3.Y, 5);
            tiles[selectedTile.index.y+1][selectedTile.index.x+1].transform.rotate(Vector3.Y, 5);
            tiles[selectedTile.index.y-1][selectedTile.index.x+1].transform.rotate(Vector3.Y, 5);
        }*/

        /*Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(Assets.getInstance().getTexture(Assets.TEXTURE_BADLOGIC), 0, 0);
        batch.end();*/

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(BACKGROUND_COLOR.r, BACKGROUND_COLOR.g, BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        envCubemap.render(cam);

        modelBatch.begin(cam);
        //modelBatch.render(ground, environment);

        for (TileActor actor: tilesModels) {
            //actor.transform.setFromEulerAngles(0, 0, 1);
            modelBatch.render(actor, environment);
        }

        /*modelBatch.render(debugBall, environment);*/

        modelBatch.end();

        /*spriteBatch.begin();
        spriteBatch.draw(Assets.getInstance().getTexture(Assets.TEXTURE_CROSSHAIR), STAGE_WIDTH/2-25, STAGE_HEIGHT/2-25, 50, 50);
        spriteBatch.end();*/

        stage2d.act(Gdx.graphics.getDeltaTime());
        stage2d.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage2d.getViewport().update(width, height, true);
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
        spriteBatch.dispose();
        stage2d.dispose();
    }

    @Override
    public boolean keyTyped(char character) {
        Gdx.app.log("Key", "" + character);
        if (character == 'x') {
            Gdx.app.exit();
        }
        return super.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //selecting = getObject(screenX, screenY);
        selecting = getObject(STAGE_WIDTH/2, STAGE_HEIGHT/2);
        /*if (selecting >= 0) {
            return true;
        }*/
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        /*if (selecting >= 0) {
            return true;
        }*/
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        //Gdx.app.log(TAG, "mouseMoved ::" + screenX +"::"+ screenY);
        return firstPersonCameraController.touchDragged(screenX, screenY, 0);
        //return super.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (selecting >= 0) {
            //if (selecting == getObject(screenX, screenY)){
            if (selecting == getObject(STAGE_WIDTH/2, STAGE_HEIGHT/2)){
                //setSelected(selecting);
                Gdx.app.log("SELECT", "" + selecting);
                setSelected (selecting);
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
        stage2d = new Stage();

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
        InputMultiplexer multiplexer = new InputMultiplexer(stage2d, firstPersonCameraController, this);

        Gdx.input.setInputProcessor(multiplexer);

        Texture texture = Assets.getInstance().getTexture(Assets.TEXTURE_CROSSHAIR);
        Image crosshair = new Image(texture);
        crosshair.setHeight(25);
        crosshair.setWidth(25);
        crosshair.setPosition(STAGE_WIDTH/2-crosshair.getWidth()/2,STAGE_HEIGHT/2-crosshair.getHeight()/2);
        stage2d.addActor(crosshair);

        TextButton button = new TextButton("Exit", Assets.getInstance().getUISkin());
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                super.clicked(event, x, y);
            }
        });
        stage2d.addActor(button);

        // DRAWING BALL
        /*ModelBuilder modelBuilder = new ModelBuilder();
        Model model = modelBuilder.createSphere(2, 2, 2, 10, 10,
                new Material(ColorAttribute.createDiffuse(com.badlogic.gdx.graphics.Color.RED)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        debugBall = new ModelInstance(model);
        debugBall.calculateTransforms();*/

        envCubemap = new EnvironmentCubemap(Gdx.files.internal("skybox/pos-x.tga"), Gdx.files.internal("skybox/neg-x.tga"),
                Gdx.files.internal("skybox/pos-y.tga"), Gdx.files.internal("skybox/neg-y.tga"),
                Gdx.files.internal("skybox/pos-z.tga"), Gdx.files.internal("skybox/neg-z.tga"));
    }

    private void setupTiles () {

        int x=0, y=0;

        int offsetY = 9;

        float angleOffset = 5f;
        float startAngle = 240f;
        float startY = -cols*5;
        float radii = 100;

        for (y=0; y<rows; y++) {
            for (x = 0; x < cols; x++) {
                bombMap[x][y] = (Math.random() < bombFactor) ? 1 : 0;
            }
        }

        for (x=0; x<cols; x++) {
            for (y=0; y<rows; y++) {
                tileMap[x][y] = 0;

                /*float posX = (float) (tileW*x+offset*x - totalW*0.5);
                float posY = (float) (tileW*y+offset*y - totalH*0.5);

                float px = rad*sin(xrot*(PI/180))*cos(yrot*(PI/180));
                float py = rad*sin(xrot*(PI/180))*sin(yrot*(PI/180));
                float pz = cos(xrot*(PI/180));*/

                //float radians = (float)Math.toRadians(12*x);

                float px = 0;
                float py = 0;
                float pz = 0;
                float phi = 0;
                float theta = 0;

                phi = (float) Math.toRadians(angleOffset*y);
                theta = (float) Math.toRadians(startAngle + angleOffset*x);

                /*px = (float) (50 * Math.cos(phi) * Math.sin(theta));
                py = (float) (50 * Math.sin(phi) * Math.sin(theta));
                pz = (float) (50 * Math.cos(theta));*/

                px = (float)(Math.cos(theta) * radii);
                py = startY + offsetY * y;
                pz = (float)(Math.sin(theta) * radii);

                //Gdx.app.log("POS", px + ":" + py + ":" + pz);

                Matrix4 mat = new Matrix4();
                mat.translate(px, py, pz);
                mat.rotate(Vector3.X, 0);
                mat.rotate(Vector3.Y, (float)-Math.toDegrees(theta));
                mat.rotate(Vector3.Z, 0);
                mat.scale(4, 4, 4);

                TileActor actor = TileActor.create(mat);
                actor.index.x = x;
                actor.index.y = y;
                actor.neighborCount = getNeighbourCount(x, y);
                actor.face = (bombMap[x][y] == 1)? 5 : actor.neighborCount;
                tilesModels.add(actor);
                tiles[x][y] = actor;
                //actor.showFace(actor.face);
            }
        }
    }

    private int getNeighbourCount (int x, int y) {
        int count = 0;
        for (int i=-1; i<=1; i++) {
            int nx = x+i;
            if (nx<0 || nx>=cols) continue;
            for (int j=-1; j<=1; j++) {
                //if (i == 0 && j == 0) continue;

                int ny = y+j;
                if (ny<0 || ny>=rows) continue;

                if (bombMap[nx][ny] == 1) {
                    count ++;
                }
            }
        }

        return count;
    }



    public void floodFill (int x, int y) {
        int count = 0;
        for (int i=-1; i<=1; i++) {
            int nx = x + i;
            if (nx < 0 || nx >= cols) continue;
            for (int j = -1; j <= 1; j++) {
                //if (i == 0 && j == 0) continue;

                int ny = y + j;
                if (ny < 0 || ny >= rows) continue;

                TileActor actor = tiles[nx][ny];

                if (actor.opened == false) {
                    open(nx, ny);
                }
            }
        }
    }

    public int getObject (int screenX, int screenY) {
        Ray ray = cam.getPickRay(screenX, screenY);
        int result = -1;
        float distance = -1;
        Vector3 position = new Vector3();
        for (int i = 0; i < tilesModels.size(); ++i) {
            final ModelInstance instance = tilesModels.get(i);
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

    public void setSelected (int value) {
        if (selected == value) return;
        if (selected >= 0) {
            // OLD SELECTION
            /*Material mat = instances.get(selected).materials.get(0);
            mat.clear();
            mat.set(originalMaterial);*/
        }
        selected = value;
        if (selected >= 0) {
            /*Material mat = instances.get(selected).materials.get(0);
            originalMaterial.clear();
            originalMaterial.set(mat);
            mat.clear();
            mat.set(selectionMaterial);*/
            // NEW SELECTION

            TileActor actor = tilesModels.get(selecting);
            open (actor.index.x, actor.index.y);
        }
    }

    public void open (int x, int y) {
        TileActor actor = tiles[x][y];
        actor.showFace(actor.face);

        if (actor.face == 0) {
            floodFill(actor.index.x, actor.index.y);
        }
    }
}
