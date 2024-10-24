package com.mygdx.angrybirdgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.Texture;

public class LevelsScreen implements Screen {
    private final MyAngryBirds game;
    private Stage stage;
    private Skin skin;
    private Texture levelTexture;
    private Image levelImage;

    public LevelsScreen(final MyAngryBirds game) {
        this.game = game;
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Load the level image
        levelTexture = new Texture(Gdx.files.internal("level.png"));
        levelImage = new Image(levelTexture);

        // Set the image to fill the screen
        levelImage.setFillParent(true);

        // Add the image to the stage
        stage.addActor(levelImage);

        Table table = new Table();
        table.setFillParent(true);

        // Add a button for each level
        for (int i = 1; i <= 5; i++) {
            final int level = i;
            TextButton levelButton;

            // Unlock only the first level, others are disabled
            if (level == 1) {
                levelButton = new TextButton("Level " + level, skin);
                levelButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.setScreen(new GameScreen(game)); // Switch to GameScreen
                    }
                });
            } else {
                levelButton = new TextButton("Level " + level, skin, "default");
                levelButton.setDisabled(true); // Lock other levels
            }

            table.add(levelButton).pad(10).row();
        }

        // Add Back button to return to HomeScreen
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HomeScreen(game)); // Go back to HomeScreen
            }
        });

        table.add(backButton).pad(10);

        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
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
        levelTexture.dispose();
    }
}
