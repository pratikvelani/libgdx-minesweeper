package com.pratikvelani.minesweeper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.pratikvelani.minesweeper.GdxGame;
import com.pratikvelani.minesweeper.actors.TileActor;
import com.pratikvelani.minesweeper.g3d.ClickAdapter;
import com.pratikvelani.minesweeper.g3d.EnvironmentCubemap;
//import com.pratikvelani.minesweeper.g3d.FirstPersonCameraController;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.pratikvelani.minesweeper.toolbox.Assets;
import com.pratikvelani.minesweeper.ui.InGameUI;

import java.util.ArrayList;

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

    private FirstPersonCameraController firstPersonCameraController;

    private InGameUI ui;

    //private ArrayList<BaseActor> models = new ArrayList<BaseActor>();
    private ArrayList<TileActor> tilesModels = new ArrayList<TileActor>();
    private float bombFactor = 0.1f;

    private int cols = 11;
    private int rows = 5;
    private int[][] tileMap = new int[cols][rows];
    private int[][] map = new int[cols][rows];
    private TileActor[][] tiles = new TileActor[cols][rows];
    private int tilesCount = 0;

    public ModelInstance ground;

    private ModelInstance debugBall;

    private int selected = -1, selecting = -1;
    private TileActor selectedTile;

    private DirectionalLight directionalLight;

    EnvironmentCubemap envCubemap;

    private Button exitButton;
    private Matrix4 tmpMat4 = new Matrix4();
    private Matrix4 transform = new Matrix4();
    private Vector3 position = new Vector3(0, 0, 50);
    private Vector3 sizeVec = new Vector3(0,0,0);

    private ModelInstance debugBox;

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

        setupTiles ();
    }

    @Override
    public void render(float delta) {

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

        envCubemap.render(cam);

        modelBatch.begin(cam);
        //modelBatch.render(ground, environment);

        for (TileActor actor: tilesModels) {
            //actor.transform.setFromEulerAngles(0, 0, 1);
            modelBatch.render(actor, environment);
        }

        //modelBatch.render(debugBox, environment);

        modelBatch.end();


        ui.render(delta);

        //transform.setToTranslation(position).rotate(Vector3.Z, cam.direction);
        /*spriteBatch.setProjectionMatrix(tmpMat4.set(cam.combined).mul(transform));
        spriteBatch.begin();
        exitButton.draw(spriteBatch, 1.0f);
        spriteBatch.end();*/


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
        cam.near = 1f;
        cam.far = 1000f;
        cam.update();

        stage2d = new Stage(new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera()), spriteBatch);


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

        ui = new InGameUI();
        ui.setCamera(cam);
        ui.transform.translate(0, -250, -400);
        //ui.transform.rotate(Vector3.Y, 180);
        ui.showStart();

        /*transform.translate(0, 0, 300);
        transform.rotate(Vector3.Y, 180);
        exitButton = new TextButton("Exit", Assets.getInstance().getUISkin());
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                super.clicked(event, x, y);
            }
        });
        exitButton.setOrigin(Align.center);

        sizeVec.set(40, 20, 40);

        ModelBuilder modelBuilder = new ModelBuilder();
        Model model = modelBuilder.createBox(sizeVec.x, sizeVec.y, sizeVec.z,
                new Material(ColorAttribute.createDiffuse(com.badlogic.gdx.graphics.Color.RED)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        debugBox = new ModelInstance(model);
        debugBox.calculateTransforms();

        transform.getTranslation(position);
        debugBox.transform.translate(position.x - exitButton.getWidth()/2, position.y + exitButton.getHeight()/2, position.z);*/

        //stage2d.addActor(button);

        envCubemap = new EnvironmentCubemap(Gdx.files.internal("cubemap/px.tga"), Gdx.files.internal("cubemap/nx.tga"),
                Gdx.files.internal("cubemap/py.tga"), Gdx.files.internal("cubemap/ny.tga"),
                Gdx.files.internal("cubemap/pz.tga"), Gdx.files.internal("cubemap/nz.tga"));
    }

    private void setupTiles () {

        int x=0, y=0;

        int offsetY = 9;

        float angleOffset = 5f;
        float startAngle = 245f;
        float startY = -cols*5;
        float radii = 100;

        for (y=0; y<rows; y++) {
            for (x = 0; x < cols; x++) {
                map[x][y] = (Math.random() < bombFactor) ? 5 : 0;
                if (map[x][y] == 0) {
                    tilesCount ++;
                }
            }
        }

        for (x=0; x<cols; x++) {
            for (y = 0; y < rows; y++) {
                if (map[x][y] != 5) {
                    map[x][y] = getNeighbourCount(x, y);
                }
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

                TileActor actor = TileActor.create(mat, map[x][y]);
                actor.index.x = x;
                actor.index.y = y;
                actor.neighborCount = getNeighbourCount(x, y);
                //actor.face = (map[x][y] == 1)? 5 : actor.neighborCount;
                actor.type = map[x][y];
                tilesModels.add(actor);
                tiles[x][y] = actor;
            }
        }

        int centerX = (int) Math.floor(tiles.length/2);
        int centerY = (int) Math.floor(tiles[centerX].length/2);
        TileActor centerTile = tiles[centerX][centerY];

        cam.position.set (centerTile.point.x, centerTile.point.y, -50);
        cam.direction.set(Vector2.Y, -180);
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

                if (map[nx][ny] == 5) {
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

        result = ui.getClickResult(screenX, screenY);
        if (result > -1) {
            return result;
        }

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
        actor.open ();

        tilesCount--;
        Gdx.app.log("TILES", "" + tilesCount);
        if (actor.isBomb() == true) {
            Gdx.app.log("GAME", "OVER");
        } else if (tilesCount == 0) {
            Gdx.app.log("GAME", "COMPLETE");
        } else if (actor.type == 0) {
            floodFill(actor.index.x, actor.index.y);
        }
    }
}
