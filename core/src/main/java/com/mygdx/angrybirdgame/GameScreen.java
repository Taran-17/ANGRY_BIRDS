package com.mygdx.angrybirdgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {
    private Sprite[] woodBlocks;
    private Sprite[] glassBlocks;
    private Sprite[] glassFBlocks;

    private final MyAngryBirds game;
    private final SpriteBatch batch;
    private final Texture backgroundTexture, slingshotTexture, baseTexture, slingshotBaseTexture;
    private final Sprite slingshotSprite, bgSprite, baseSprite, slingshotBaseSprite;
    private final bird birdEntity;
    private final Pig[] pigEntities;
    private final Stage stage;
    private final Skin skin;

    // Box2D World and Physics Variables
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Body birdBody;
    private Body slingshotBody;
    private Array<Body> pigBodies;
    private Array<Body> structureBodies;
    private ObjectMap<Sprite, Integer> blockHealthMap;

    public GameScreen(MyAngryBirds game) {
        this.game = game;
        batch = new SpriteBatch();

        // Initialize block health map
        blockHealthMap = new ObjectMap<>();

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

        initStructure(5, 3, 2);

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

    private void initStructure(int woodBlockCount, int glassBlockCount, int glassFBlockCount) {
        woodBlocks = new Sprite[woodBlockCount];
        glassBlocks = new Sprite[glassBlockCount];
        glassFBlocks = new Sprite[glassFBlockCount];

        for (int i = 0; i < woodBlockCount; i++) {
            woodBlocks[i] = new Sprite(new Texture("wood.png"));
            woodBlocks[i].setSize(50, 50);
            blockHealthMap.put(woodBlocks[i], 100); // Wood block health: 100
        }

        for (int i = 0; i < glassBlockCount; i++) {
            glassBlocks[i] = new Sprite(new Texture("GlassBlock.png"));
            glassBlocks[i].setSize(40, 40);
            blockHealthMap.put(glassBlocks[i], 50); // Glass block health: 50
        }

        for (int i = 0; i < glassFBlockCount; i++) {
            glassFBlocks[i] = new Sprite(new Texture("GlassFBlock.png"));
            glassFBlocks[i].setSize(50, 50);
            blockHealthMap.put(glassFBlocks[i], 75); // Glass F block health: 75
        }
    }

    private void updateBlockHealth() {
        // Simulate block damage (e.g., via collision detection or random decrement)
        for (ObjectMap.Entry<Sprite, Integer> entry : blockHealthMap.entries()) {
            Sprite block = entry.key;
            int health = entry.value;

            // For demonstration: decrement health over time
            health -= 1;

            if (health <= 0) {
                blockHealthMap.remove(block); // Remove block when health is zero
            } else {
                blockHealthMap.put(block, health); // Update block health
            }
        }
    }


    private void initPositions() {
        slingshotBaseSprite.setPosition(120, 90);
        slingshotSprite.setPosition(145, 130);
        birdEntity.setPosition(180, 230);
        baseSprite.setPosition(700, 100);

        float startX = 850, startY = 130;

        // Position wood blocks
        for (int i = 0; i < woodBlocks.length; i++) {
            woodBlocks[i].setPosition(startX + (i - 2) * woodBlocks[i].getWidth(), startY);
        }

        // Position glass blocks
        for (int i = 0; i < glassBlocks.length; i++) {
            glassBlocks[i].setPosition(startX + (i - 1) * glassBlocks[i].getWidth(),
                    startY + woodBlocks[0].getHeight());
        }

        // Position glass F blocks
        for (int i = 0; i < glassFBlocks.length; i++) {
            glassFBlocks[i].setPosition(startX + (i) * glassFBlocks[i].getWidth(),
                    startY + woodBlocks[0].getHeight() + glassBlocks[0].getHeight());
        }
    }

    private void createPhysicsBodies() {
        birdBody = createKinematicBody(
                slingshotSprite.getX() + slingshotSprite.getWidth() / 2,
                slingshotSprite.getY() + slingshotSprite.getHeight() - 25,
                25
        );

        slingshotBody = createStaticBody(
                slingshotSprite.getX() + slingshotSprite.getWidth() / 2,
                slingshotSprite.getY() + slingshotSprite.getHeight() / 2,
                slingshotSprite.getWidth() / 2,
                slingshotSprite.getHeight() / 2
        );

        pigBodies = new Array<>();
        for (Pig pig : pigEntities) {
            pigBodies.add(createStaticBody(pig.getX(), pig.getY(), 17.5f, 17.5f));
        }

        structureBodies = new Array<>();
        for (Sprite woodBlock : woodBlocks) {
            structureBodies.add(createStaticBody(
                    woodBlock.getX(), woodBlock.getY(),
                    woodBlock.getWidth() / 2, woodBlock.getHeight() / 2
            ));
        }
        for (Sprite glassBlock : glassBlocks) {
            structureBodies.add(createStaticBody(
                    glassBlock.getX(), glassBlock.getY(),
                    glassBlock.getWidth() / 2, glassBlock.getHeight() / 2
            ));
        }
        for (Sprite glassFBlock : glassFBlocks) {
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

    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update world physics
        world.step(1 / 60f, 6, 2);

        // Update block health
        updateBlockHealth();

        batch.begin();
        bgSprite.draw(batch);
        baseSprite.draw(batch);
        slingshotBaseSprite.draw(batch);
        slingshotSprite.draw(batch);
        birdEntity.draw(batch);

        for (Pig pig : pigEntities) {
            pig.render(batch);
        }

        // Draw blocks with remaining health
        for (Sprite woodBlock : woodBlocks) {
            if (blockHealthMap.containsKey(woodBlock)) {
                woodBlock.draw(batch);
            }
        }
        for (Sprite glassBlock : glassBlocks) {
            if (blockHealthMap.containsKey(glassBlock)) {
                glassBlock.draw(batch);
            }
        }
        for (Sprite glassFBlock : glassFBlocks) {
            if (blockHealthMap.containsKey(glassFBlock)) {
                glassFBlock.draw(batch);
            }
        }
        batch.end();

        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();
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
        // Dispose of all resources explicitly
        batch.dispose();
        backgroundTexture.dispose();
        slingshotTexture.dispose();
        baseTexture.dispose();
        slingshotBaseTexture.dispose();
        stage.dispose();
        skin.dispose();
        world.dispose();
        debugRenderer.dispose();

        // Clear block health map
        blockHealthMap.clear();
    }

}
