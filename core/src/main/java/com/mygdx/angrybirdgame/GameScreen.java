package com.mygdx.angrybirdgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {
    private final MyAngryBirds game;
    private final SpriteBatch batch;
    private final Texture backgroundTexture, slingshotTexture, baseTexture, slingshotBaseTexture;
    private final Sprite slingshotSprite, bgSprite, baseSprite, slingshotBaseSprite;
    private final bird birdEntity;
    private final Pig[] pigEntities;
    private final Structure structure; // Structure object to manage blocks
    private final Stage stage;
    private final Skin skin;

    public GameScreen(MyAngryBirds game) {
        this.game = game;
        batch = new SpriteBatch();

        // Load textures
        backgroundTexture = new Texture("gameBackground.png");
        slingshotTexture = new Texture("slingshot.png");
        baseTexture = new Texture("base.png");
        slingshotBaseTexture = new Texture("base.png");  // Slingshot base texture

        // Initialize sprites
        bgSprite = new Sprite(backgroundTexture);
        slingshotSprite = new Sprite(slingshotTexture);
        baseSprite = new Sprite(baseTexture);
        slingshotBaseSprite = new Sprite(slingshotBaseTexture);  // Slingshot base sprite

        // Initialize bird entity
        birdEntity = new bird(new Sprite(new Texture("bird.png")));
        birdEntity.setSize(50, 50);

        // Initialize pig entities
        pigEntities = new Pig[2];
        pigEntities[0] = new Pig(750, 170);
        pigEntities[1] = new Pig(900, 270);

        // Initialize the Structure (with 5 wood blocks, 3 glass blocks, and 2 glass F blocks)
        structure = new Structure(5, 3, 2);

        // Set sizes for sprites
        bgSprite.setSize(1280, 720);
        slingshotSprite.setSize(100, 150);
        baseSprite.setSize(400, 50);
        slingshotBaseSprite.setSize(150, 50); // Adjust size of slingshot base as needed

        // Initialize positions
        initPositions();

        // Stage and UI setup
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        setupUI();

        Gdx.input.setInputProcessor(stage);
    }

    private void initPositions() {
        // Position slingshot base (on the ground)
        slingshotBaseSprite.setPosition(120, 80);  // Adjust position as needed

        int base_x = 100;
        int base_y = 70;

        // Position slingshot (above the base)
        slingshotSprite.setPosition(base_x + slingshotBaseSprite.getWidth() / 2 - slingshotSprite.getWidth() / 2,
                base_y + slingshotBaseSprite.getHeight());

        // Position bird on the slingshot
        birdEntity.setPosition(slingshotSprite.getX() + slingshotSprite.getWidth() / 2 - birdEntity.getSprite().getWidth() / 2,
                slingshotSprite.getY() + slingshotSprite.getHeight() - birdEntity.getSprite().getHeight() / 2);

        // Position base (platform)
        baseSprite.setPosition(700, 100); // Adjust X and Y coordinates as needed
        structure.setPositions(850, 130); // Set structure's blocks starting position
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
    public void show() {
        // Initialize screen elements, if needed
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        bgSprite.draw(batch);
        baseSprite.draw(batch);
        slingshotBaseSprite.draw(batch);  // Draw the slingshot base
        slingshotSprite.draw(batch);
        birdEntity.draw(batch);

        // Render pigs
        for (Pig pig : pigEntities) {
            pig.render(batch);
        }

        // Render the structure blocks
        structure.render(batch);

        batch.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        // Clean up resources if needed
    }

    @Override
    public void pause() {
        // Handle pause behavior if needed
    }

    @Override
    public void resume() {
        // Handle resume behavior if needed
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose();
        slingshotTexture.dispose();
        baseTexture.dispose();
        slingshotBaseTexture.dispose();  // Dispose of slingshot base texture
        stage.dispose();
        skin.dispose();
    }
}
