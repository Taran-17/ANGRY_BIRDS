package com.mygdx.angrybirdgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pig {
    private Sprite pigSprite;
    private float x, y;
    private int health;

    // Constructor to initialize the pig sprite, its position, and health
    public Pig(float x, float y) {
        this.x = x;
        this.y = y;
        this.health = 100;  // Default health, can be adjusted
        pigSprite = new Sprite(new Texture("pig.png"));  // Load the pig texture
        pigSprite.setSize(35, 35);  // Set pig size
        pigSprite.setPosition(x, y);  // Set initial position
    }

    // Getter for the pig sprite
    public Sprite getPigSprite() {
        return pigSprite;
    }

    // Getter for pig's X position
    public float getX() {
        return x;
    }

    // Getter for pig's Y position
    public float getY() {
        return y;
    }

    // Setter for pig's position
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        pigSprite.setPosition(x, y);  // Update sprite position
    }

    // Method to reduce health (simulate damage when hit)
    public void takeDamage(int damage) {
        this.health -= damage;
        if (health <= 0) {
            die();  // Handle pig death when health reaches zero
        }
    }

    // Method to handle pig death (could trigger an animation or removal)
    private void die() {
        System.out.println("Pig is dead!");
        // Logic for when the pig dies (could be removed from the game world or trigger an effect)
    }

    // Method to render the pig sprite
    public void render(SpriteBatch batch) {
        pigSprite.draw(batch);
    }

    // Optional: Method to check for collision with the bird or other game objects
    public boolean checkCollision(Sprite other) {
        return pigSprite.getBoundingRectangle().overlaps(other.getBoundingRectangle());
    }
}
