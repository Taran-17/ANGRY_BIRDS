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
    private final Texture backgroundTexture, birdTexture, pigTexture, slingshotTexture, woodBlockTexture, glassBlockTexture, glassFBlockTexture;
    private final Sprite birdSprite, pigSprite, slingshotSprite, bgSprite;
    private final Sprite[] woodBlocks, glassBlocks, glassFBlocks;
    private final Stage stage;
    private final Skin skin;

    public GameScreen(MyAngryBirds game) {
        this.game = game;
        batch = new SpriteBatch();

        // Load textures
        backgroundTexture = new Texture("gameBackground.png");
        birdTexture = new Texture("bird.png");
        pigTexture = new Texture("pig.png");
        slingshotTexture = new Texture("slingshot.png");
        woodBlockTexture = new Texture("wood.png");
        glassBlockTexture = new Texture("GlassBlock.png");
        glassFBlockTexture = new Texture("GlassFBlock.png");

        // Initialize sprites
        bgSprite = new Sprite(backgroundTexture);
        birdSprite = new Sprite(birdTexture);
        pigSprite = new Sprite(pigTexture);
        slingshotSprite = new Sprite(slingshotTexture);

        // Create an array for multiple wood, glass, and glassF blocks
        int numberOfWoodBlocks = 5;
        int numberOfGlassBlocks = 3;
        int numberOfGlassFBlocks = 2;

        woodBlocks = new Sprite[numberOfWoodBlocks];
        for (int i = 0; i < numberOfWoodBlocks; i++) {
            woodBlocks[i] = new Sprite(woodBlockTexture);
        }

        glassBlocks = new Sprite[numberOfGlassBlocks];
        for (int i = 0; i < numberOfGlassBlocks; i++) {
            glassBlocks[i] = new Sprite(glassBlockTexture);
        }

        glassFBlocks = new Sprite[numberOfGlassFBlocks];
        for (int i = 0; i < numberOfGlassFBlocks; i++) {
            glassFBlocks[i] = new Sprite(glassFBlockTexture);
        }

        // Set sizes
        bgSprite.setSize(1280, 720);
        birdSprite.setSize(50, 50);
        pigSprite.setSize(55, 55);
        slingshotSprite.setSize(100, 150);
        for (Sprite woodBlock : woodBlocks) {
            woodBlock.setSize(50, 50);
        }
        for (Sprite glassBlock : glassBlocks) {
            glassBlock.setSize(40, 40);
        }
        for (Sprite glassFBlock : glassFBlocks) {
            glassFBlock.setSize(50, 50);
        }

        initPositions();

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        setupUI();

        Gdx.input.setInputProcessor(stage);
    }

    private void initPositions() {
        // Position slingshot
        slingshotSprite.setPosition(100, 50);

        // Position bird on slingshot
        birdSprite.setPosition(slingshotSprite.getX() + slingshotSprite.getWidth() / 2 - birdSprite.getWidth() / 2,
                slingshotSprite.getY() + slingshotSprite.getHeight() - birdSprite.getHeight() / 2);

        // Base position for the structure
        float baseX = 800;
        float baseY = 100;

        // Position the bottom wood blocks as a base
        for (int i = 0; i < 3; i++) {
            woodBlocks[i].setPosition(baseX + i * woodBlocks[i].getWidth(), baseY);
        }

        // Place the pigs on top of the bottom wood blocks
        for (int i = 0; i < 2; i++) {
            pigSprite.setPosition(baseX + woodBlocks[i].getWidth() / 2 + i * woodBlocks[i].getWidth() - pigSprite.getWidth() / 2,
                    baseY + woodBlocks[i].getHeight());
        }

        // Add glass blocks above the pigs for reinforcement
        for (int i = 0; i < 2; i++) {
            glassBlocks[i].setPosition(baseX + i * glassBlocks[i].getWidth() + woodBlocks[i].getWidth() / 2,
                    baseY + woodBlocks[0].getHeight() + pigSprite.getHeight());
        }

        // Create vertical wood blocks as support pillars
        for (int i = 0; i < 2; i++) {
            woodBlocks[i + 3].setPosition(baseX + i * 2 * woodBlocks[i].getWidth(),
                    baseY + glassBlocks[i].getHeight() + woodBlocks[0].getHeight() + pigSprite.getHeight());
        }

        // Add a top wood block across the two vertical supports
        woodBlocks[4].setPosition(baseX + woodBlocks[0].getWidth(),
                baseY + 2 * woodBlocks[0].getHeight() + pigSprite.getHeight() + glassBlocks[0].getHeight());

        // Add glass F blocks as decorative roofs
        for (int i = 0; i < glassFBlocks.length; i++) {
            glassFBlocks[i].setPosition(baseX + i * glassFBlocks[i].getWidth(),
                    baseY + 2 * woodBlocks[0].getHeight() + glassBlocks[0].getHeight() + pigSprite.getHeight());
        }
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
                game.setScreen(new SettingsScreen(game, GameScreen.this));
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
        for (Sprite woodBlock : woodBlocks) {
            woodBlock.draw(batch);
        }
        for (Sprite glassBlock : glassBlocks) {
            glassBlock.draw(batch);
        }
        for (Sprite glassFBlock : glassFBlocks) {
            glassFBlock.draw(batch);
        }
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
        glassBlockTexture.dispose();
        glassFBlockTexture.dispose();
        stage.dispose();
        skin.dispose();
        batch.dispose();
    }
}
