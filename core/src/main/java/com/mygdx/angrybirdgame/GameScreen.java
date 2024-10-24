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
    private MyAngryBirds game;
    private SpriteBatch batch;
    private Texture backgroundTexture, birdTexture, pigTexture, slingshotTexture;
    private Sprite birdSprite, pigSprite, slingshotSprite,bgSprite;
    private Stage stage;
    private Skin skin;

    public GameScreen(MyAngryBirds game) {
        this.game = game;
        batch = new SpriteBatch();

        // Load textures for static game elements
        backgroundTexture = new Texture("gameBackground.png");
        birdTexture = new Texture("bird.png");
        pigTexture = new Texture("pig.png");
        slingshotTexture = new Texture("slingshot.png");

        // Initialize sprites
        bgSprite = new Sprite(backgroundTexture);
        birdSprite = new Sprite(birdTexture);
        pigSprite = new Sprite(pigTexture);
        slingshotSprite = new Sprite(slingshotTexture);

        // Set initial position for the bird
        bgSprite.setSize(1280, 720);
        birdSprite.setSize(50, 50);
        pigSprite.setSize(50, 50);
        slingshotSprite.setSize(100, 300);
        birdSprite.setPosition(1, 0);
        pigSprite.setPosition(1200, 0);
        slingshotSprite.setPosition(100, 0);

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));  // Load default skin

        // Create UI buttons
        TextButton settingsButton = new TextButton("Settings", skin);
        TextButton pauseButton = new TextButton("Pause", skin);
        pauseButton.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 50);
        settingsButton.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 100);

        // Add click listeners
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

        stage.addActor(pauseButton);
        stage.addActor(settingsButton);

        Gdx.input.setInputProcessor(stage);  // Set input processor to handle buttons
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        bgSprite.draw(batch);  // Draw background
        slingshotSprite.draw(batch);  // Draw slingshot
        birdSprite.draw(batch);  // Draw bird
        pigSprite.draw(batch);  // Draw pig
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());  // Handle stage updates
        stage.draw();  // Draw the UI (Pause button)

        handleInput();  // Handle input for bird interaction
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Invert Y coordinate

            // Check if the bird is clicked
            if (birdSprite.getBoundingRectangle().contains(touchX, touchY)) {
                // Move bird to touch position
                birdSprite.setPosition(touchX - birdSprite.getWidth() / 2, touchY - birdSprite.getHeight() / 2);
            }
        }
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
        backgroundTexture.dispose();
        birdTexture.dispose();
        pigTexture.dispose();
        slingshotTexture.dispose();
        stage.dispose();
        skin.dispose();
        batch.dispose();
    }
}
