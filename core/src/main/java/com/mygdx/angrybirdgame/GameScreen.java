package com.mygdx.angrybirdgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {
    private final MyAngryBirds game;
    private final SpriteBatch batch;
    private final Texture backgroundTexture, slingshotTexture, baseTexture, slingshotBaseTexture;
    private final Sprite slingshotSprite, bgSprite, baseSprite, slingshotBaseSprite;
    private final bird birdEntity;
    private final Pig[] pigEntities;
    private final Structure structure;
    private final Stage stage;
    private final Skin skin;

    // Box2D World and Physics Variables
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Body birdBody;
    private Body slingshotBody;
    private Array<Body> pigBodies;
    private Array<Body> structureBodies;

    public GameScreen(MyAngryBirds game) {
        this.game = game;
        batch = new SpriteBatch();

        // Initialize Box2D world
        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();

        // Load textures and initialize sprites
        backgroundTexture = new Texture("gameBackground.png");
        slingshotTexture = new Texture("slingshot.png");
        baseTexture = new Texture("base.png");
        slingshotBaseTexture = new Texture("base.png");

        bgSprite = new Sprite(backgroundTexture);
        slingshotSprite = new Sprite(slingshotTexture);
        baseSprite = new Sprite(baseTexture);
        slingshotBaseSprite = new Sprite(slingshotBaseTexture);

        birdEntity = new bird(new Sprite(new Texture("bird.png")));
        birdEntity.setSize(50, 50);
        pigEntities = new Pig[2];
        pigEntities[0] = new Pig(750, 170);
        pigEntities[1] = new Pig(900, 270);
        structure = new Structure(5, 3, 2);

        bgSprite.setSize(1280, 720);
        slingshotSprite.setSize(100, 150);
        baseSprite.setSize(400, 50);
        slingshotBaseSprite.setSize(150, 50);

        initPositions();
        createPhysicsBodies();

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        setupUI();

        Gdx.input.setInputProcessor(stage);
    }

    private void initPositions() {
        slingshotBaseSprite.setPosition(120, 80);
        slingshotSprite.setPosition(145, 130);
        birdEntity.setPosition(160, 200);
        baseSprite.setPosition(700, 100);
        structure.setPositions(850, 130);
    }

    private void createPhysicsBodies() {
        // Create bird body as kinematic initially (stable on slingshot)
        birdBody = createKinematicBody(
                slingshotSprite.getX() + slingshotSprite.getWidth() / 2,
                slingshotSprite.getY() + slingshotSprite.getHeight() - 25, // Place the bird above the slingshot
                25
        );

        // Create slingshot body
        slingshotBody = createStaticBody(
                slingshotSprite.getX() + slingshotSprite.getWidth() / 2,
                slingshotSprite.getY() + slingshotSprite.getHeight() / 2,
                slingshotSprite.getWidth() / 2,
                slingshotSprite.getHeight() / 2
        );

        // Create pig bodies
        pigBodies = new Array<>();
        for (Pig pig : pigEntities) {
            pigBodies.add(createStaticBody(pig.getX(), pig.getY(), 17.5f, 17.5f));
        }

        // Create structure bodies
        structureBodies = new Array<>();
        for (Sprite woodBlock : structure.getWoodBlocks()) {
            structureBodies.add(createStaticBody(
                    woodBlock.getX(), woodBlock.getY(),
                    woodBlock.getWidth() / 2, woodBlock.getHeight() / 2
            ));
        }
        for (Sprite glassBlock : structure.getGlassBlocks()) {
            structureBodies.add(createStaticBody(
                    glassBlock.getX(), glassBlock.getY(),
                    glassBlock.getWidth() / 2, glassBlock.getHeight() / 2
            ));
        }
        for (Sprite glassFBlock : structure.getGlassFBlocks()) {
            structureBodies.add(createStaticBody(
                    glassFBlock.getX(), glassFBlock.getY(),
                    glassFBlock.getWidth() / 2, glassFBlock.getHeight() / 2
            ));
        }
    }

    private Body createKinematicBody(float x, float y, float radius) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(x / 100, y / 100); // Convert pixels to meters

        CircleShape shape = new CircleShape();
        shape.setRadius(radius / 100); // Convert to meters

        Body body = world.createBody(bodyDef);
        body.createFixture(shape, 1.0f);
        shape.dispose();
        return body;
    }



    private Body createDynamicBody(float x, float y, float radius) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / 100, y / 100);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius / 100); // Convert to meters

        Body body = world.createBody(bodyDef);
        body.createFixture(shape, 1.0f);
        shape.dispose();
        return body;
    }

    private Body createStaticBody(float x, float y, float halfWidth, float halfHeight) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x / 100, y / 100); // Convert pixels to meters

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(halfWidth / 100, halfHeight / 100);

        Body body = world.createBody(bodyDef);
        body.createFixture(shape, 0.0f); // Density = 0 for static bodies
        shape.dispose();
        return body;
    }

    private void setupUI() {
        TextButton settingsButton = new TextButton("Settings", skin);
        TextButton pauseButton = new TextButton("Pause", skin);

        pauseButton.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 50);
        settingsButton.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 100);

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingsScreen(game, GameScreen.this));
            }
        });
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PauseScreen(game));
            }
        });

        stage.addActor(settingsButton);
        stage.addActor(pauseButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Check for launch input (space key for example)
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
            birdBody.setType(BodyDef.BodyType.DynamicBody);
            birdBody.applyForceToCenter(new Vector2(5, 10), true); // Simulate a launch force
        }

        // Step the physics simulation
        world.step(1 / 60f, 6, 2);

        // Sync bird position with its body
        birdEntity.setPosition(birdBody.getPosition().x * 100 - 25, birdBody.getPosition().y * 100 - 25);

        batch.begin();
        bgSprite.draw(batch);
        baseSprite.draw(batch);
        slingshotBaseSprite.draw(batch);
        slingshotSprite.draw(batch);
        birdEntity.draw(batch);

        for (Pig pig : pigEntities) {
            pig.render(batch);
        }
        structure.render(batch);
        batch.end();

        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();

        // Optional: Debug renderer
        debugRenderer.render(world, stage.getCamera().combined);
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose();
        slingshotTexture.dispose();
        baseTexture.dispose();
        slingshotBaseTexture.dispose();
        stage.dispose();
        skin.dispose();
        world.dispose();
        debugRenderer.dispose();
    }
}
