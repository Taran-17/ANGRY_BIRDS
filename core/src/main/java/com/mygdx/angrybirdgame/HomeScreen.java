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

public class HomeScreen implements Screen {
    private MyAngryBirds game;
    private Stage stage;
    private Skin skin;
    private TextButton levelsButton;
    private SpriteBatch batch;
    private Sprite bg;  // Declare bg as a class variable
    private Texture bgt;  // Declare bgt as a class variable

    public HomeScreen(MyAngryBirds game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));  // Use LibGDX's default skin

        // Create Buttons
        TextButton playButton = new TextButton("Play", skin);
        TextButton settingsButton = new TextButton("Settings", skin);
        TextButton exitButton = new TextButton("Exit", skin);
        TextButton levelsButton = new TextButton("Levels", skin);
        // Set button positions
        playButton.setPosition(Gdx.graphics.getWidth() / 2f - 100, 300);
        settingsButton.setPosition(Gdx.graphics.getWidth() / 2f - 100, 200);
        exitButton.setPosition(Gdx.graphics.getWidth() / 2f - 100, 100);
        levelsButton.setPosition(Gdx.graphics.getWidth() / 2f - 100, 0);
        // Add click listeners for screen transitions
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));  // Transition to GameScreen
            }
        });

        levelsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelsScreen(game)); // Switch to LevelsScreen
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();  // Exit the application
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingsScreen(game, HomeScreen.this));  // Go to SettingsScreen
            }
        });

        // Initialize the background texture and sprite
        bgt = new Texture("img_1.png");
        bg = new Sprite(bgt);
        bg.setSize(1280, 720);
        bg.setPosition(0, 0);

        // Add buttons to the stage
        stage.addActor(playButton);
        stage.addActor(settingsButton);
        stage.addActor(exitButton);
        stage.addActor(levelsButton);

        // Create a SpriteBatch for rendering
        batch = new SpriteBatch();

        // Set the stage as input processor
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();  // Begin SpriteBatch
        bg.draw(batch);  // Draw background sprite
        batch.end();  // End SpriteBatch

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));  // Update stage actors
        stage.draw();  // Draw the stage (which includes buttons)
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();  // Dispose the SpriteBatch
        bgt.dispose();  // Dispose the background texture
        stage.dispose();
        skin.dispose();
    }
}
