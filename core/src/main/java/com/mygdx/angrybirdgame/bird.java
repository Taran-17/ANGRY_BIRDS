package com.mygdx.angrybirdgame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class bird {
    private final Sprite birdSprite;

    public bird(Sprite birdSprite) {
        this.birdSprite = birdSprite;
    }

    // Set the position of the bird
    public void setPosition(float x, float y) {
        birdSprite.setPosition(x, y);
    }

    // Get the position of the bird
    public float getX() {
        return birdSprite.getX();
    }

    public float getY() {
        return birdSprite.getY();
    }

    // Set the size of the bird
    public void setSize(float width, float height) {
        birdSprite.setSize(width, height);
    }

    // Draw the bird
    public void draw(SpriteBatch batch) {
        birdSprite.draw(batch);
    }

    // Get the bird sprite (optional)
    public Sprite getSprite() {
        return birdSprite;
    }
}
