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

import java.util.Random;

public class GameScreen2 implements Screen {
    private final MyAngryBirds game;
    private final SpriteBatch batch;
    private final Texture backgroundTexture, birdTexture, pigTexture, slingshotTexture, woodBlockTexture;
    private final Sprite birdSprite, pigSprite, slingshotSprite, woodBlock1Sprite, woodBlock2Sprite, woodBlock3Sprite, bgSprite;
    private final Stage stage;
    private final Skin skin;

    public GameScreen2(MyAngryBirds game) {
        this.game = game;
        batch = new SpriteBatch();

        // Load textures
        backgroundTexture = new Texture("gameBackground.png");
        birdTexture = new Texture("bird.png");
        pigTexture = new Texture("pig.png");
        slingshotTexture = new Texture("slingshot.png");
        woodBlockTexture = new Texture("woodBlock.png");

        // Initialize sprites
        bgSprite = new Sprite(backgroundTexture);
        birdSprite = new Sprite(birdTexture);
        pigSprite = new Sprite(pigTexture);
        slingshotSprite = new Sprite(slingshotTexture);
        woodBlock1Sprite = new Sprite(woodBlockTexture);
        woodBlock2Sprite = new Sprite(woodBlockTexture);
        woodBlock3Sprite = new Sprite(woodBlockTexture);

        // Set sizes
        bgSprite.setSize(1280, 720);
        birdSprite.setSize(80, 80);
        pigSprite.setSize(150, 100);
        slingshotSprite.setSize(150, 250);
        woodBlock1Sprite.setSize(150, 150);
        woodBlock2Sprite.setSize(150, 150);
        woodBlock3Sprite.setSize(150, 100);

        initPositions();

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        setupUI();

        Gdx.input.setInputProcessor(stage);
    }

    private void initPositions() {
        // Position slingshot
        slingshotSprite.setPosition(100, 0);

        // Position bird on slingshot
        birdSprite.setPosition(slingshotSprite.getX() + slingshotSprite.getWidth() / 2 - birdSprite.getWidth() / 2,
                slingshotSprite.getY() + slingshotSprite.getHeight() - birdSprite.getHeight() / 2);

        // Fixed positions for wood blocks with overlap
        float baseX = 800;
        float baseY = 100;
        woodBlock1Sprite.setPosition(baseX, baseY);
        woodBlock2Sprite.setPosition(baseX, baseY + woodBlock1Sprite.getHeight() - 50);  // Overlap by 50 pixels
        woodBlock3Sprite.setPosition(baseX + woodBlock1Sprite.getWidth() - 50, baseY + woodBlock1Sprite.getHeight() - 50);  // Horizontal overlap by 50 pixels

        // Calculate pig position on top of the wooden block
        pigSprite.setPosition(900 + woodBlock1Sprite.getWidth() / 2 - pigSprite.getWidth() / 2,
                -17 + woodBlock2Sprite.getY() + woodBlock2Sprite.getHeight() - 50);  // Adjust based on overlap
    }

    private void setupUI() {
        TextButton settingsButton = new TextButton("Settings", skin);
        TextButton pauseButton = new TextButton("Pause", skin);
        TextButton triggerWinButton = new TextButton("Win", skin);
        TextButton triggerLoseButton = new TextButton("Lose", skin);

        pauseButton.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 50);
        settingsButton.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 100);
        triggerWinButton.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 150);
        triggerLoseButton.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 200);

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingsScreen(game, GameScreen2.this));
            }
        });
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PauseScreen(game));
            }
        });
        triggerWinButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new WinScreen(game));
            }
        });
        triggerLoseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LoseScreen(game));
            }
        });

        stage.addActor(pauseButton);
        stage.addActor(settingsButton);
        stage.addActor(triggerWinButton);
        stage.addActor(triggerLoseButton);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        bgSprite.draw(batch);
        slingshotSprite.draw(batch);
        woodBlock1Sprite.draw(batch);
        woodBlock2Sprite.draw(batch);
        woodBlock3Sprite.draw(batch);
        pigSprite.draw(batch);
        birdSprite.draw(batch);
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();  // Draw the UI
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        backgroundTexture.dispose();
        birdTexture.dispose();
        pigTexture.dispose();
        slingshotTexture.dispose();
        woodBlockTexture.dispose();
        stage.dispose();
        skin.dispose();
        batch.dispose();
    }
}
