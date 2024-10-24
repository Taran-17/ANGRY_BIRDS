package com.mygdx.angrybirdgame;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingsScreen implements Screen {
    private MyAngryBirds game;
    private Stage stage;
    private Skin skin;
    private Screen previousScreen;  // To track where we came from

    public SettingsScreen(MyAngryBirds game, Screen previousScreen) {
        this.game = game;
        this.previousScreen = previousScreen;  // Store reference to the previous screen
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));  // Use LibGDX default skin

        // Load settings.png texture
        Texture settingsTexture = new Texture(Gdx.files.internal("settings.png"));
        Image settingsImage = new Image(settingsTexture);// Create an Image actor

        // Scale the image to fill the screen
        settingsImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Label for Settings (just for display)
        Label titleLabel = new Label("Settings", skin);
        titleLabel.setPosition(Gdx.graphics.getWidth() / 2f - 50, Gdx.graphics.getHeight() - 100);

        // Example buttons (for now they are placeholders for options like sound/music)
        TextButton soundButton = new TextButton("Sound: On", skin);
        TextButton musicButton = new TextButton("Music: On", skin);

        // Back button to return to previous screen
        TextButton backButton = new TextButton("Back", skin);

        // Position the buttons
        soundButton.setPosition(Gdx.graphics.getWidth() / 2f - 100, 300);
        musicButton.setPosition(Gdx.graphics.getWidth() / 2f - 100, 200);
        backButton.setPosition(Gdx.graphics.getWidth() / 2f - 100, 100);

        // Add click listener for Back button
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(previousScreen);  // Go back to the previous screen
            }
        });

        // Add components to the stage
        stage.addActor(settingsImage);  // Add the settings image (fills screen)
        stage.addActor(titleLabel);     // Add the label
        stage.addActor(soundButton);
        stage.addActor(musicButton);
        stage.addActor(backButton);  // Add the sound image to the stage

        Gdx.input.setInputProcessor(stage);  // Set the stage to handle input
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
        stage.dispose();
        skin.dispose();
    }
}
