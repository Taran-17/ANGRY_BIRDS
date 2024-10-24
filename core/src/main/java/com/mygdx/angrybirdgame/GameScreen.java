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
    private Texture backgroundTexture, birdTexture, pigTexture, slingshotTexture, woodBlockTexture;
    private Sprite birdSprite, pigSprite, slingshotSprite, woodBlock1Sprite, woodBlock2Sprite, woodBlock3Sprite, bgSprite;
    private Stage stage;
    private Skin skin;

    public GameScreen(MyAngryBirds game) {
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
        pigSprite.setSize(100, 100);
        slingshotSprite.setSize(150, 250);
        woodBlock1Sprite.setSize(150, 150);
        woodBlock2Sprite.setSize(150, 150);
        woodBlock3Sprite.setSize(150, 100);

        slingshotSprite.setPosition(100, 0);
        birdSprite.setPosition(slingshotSprite.getX() + slingshotSprite.getWidth() / 2 - birdSprite.getWidth() / 2,
                slingshotSprite.getY() + slingshotSprite.getHeight() - birdSprite.getHeight() / 2);

        woodBlock1Sprite.setPosition(800, 100);
        woodBlock2Sprite.setPosition(800, 200 );
        woodBlock3Sprite.setPosition(800, 200 );

        pigSprite.setPosition(800 + woodBlock1Sprite.getWidth() / 2 - pigSprite.getWidth() / 2,
                woodBlock3Sprite.getY() + woodBlock3Sprite.getHeight()); 

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));

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

        Gdx.input.setInputProcessor(stage);  
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
