package com.mygdx.angrybirdgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class WinScreen implements Screen {
    private MyAngryBirds game;
    private Stage stage;
    private Skin skin;
    private Texture backgroundTexture;
    private SpriteBatch batch;

    public WinScreen(MyAngryBirds game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));  // Load default skin
        batch = new SpriteBatch();  // Initialize SpriteBatch

        // Load background texture for win screen
        backgroundTexture = new Texture(Gdx.files.internal("win_background.png"));

        // Create a label for "You Win!"
        Label winLabel = new Label("You Win!", skin);
        winLabel.setFontScale(3);  // Scale the font size
        winLabel.setPosition(Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() - 100);  // Center the label

        // Create a back to home button
        TextButton backButton = new TextButton("Back to Home", skin);
        backButton.setPosition(Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() / 2f - 50);

        // Add click listener for back button
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HomeScreen(game));  // Replace with your main menu screen
            }
        });

        // Add components to the stage
        stage.addActor(winLabel);
        stage.addActor(backButton);

        Gdx.input.setInputProcessor(stage);  // Set input processor to handle buttons
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the background
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();  // Draw the UI
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
        backgroundTexture.dispose();
        batch.dispose();  // Dispose of the SpriteBatch
    }
}
