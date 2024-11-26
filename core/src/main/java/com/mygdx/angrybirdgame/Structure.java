package com.mygdx.angrybirdgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Structure {
    private Sprite[] woodBlocks;
    private Sprite[] glassBlocks;
    private Sprite[] glassFBlocks;

    public Structure(int woodBlockCount, int glassBlockCount, int glassFBlockCount) {
        // Initialize arrays for the blocks
        woodBlocks = new Sprite[woodBlockCount];
        glassBlocks = new Sprite[glassBlockCount];
        glassFBlocks = new Sprite[glassFBlockCount];

        // Create and initialize the wood blocks
        for (int i = 0; i < woodBlockCount; i++) {
            woodBlocks[i] = new Sprite(new Texture("wood.png"));
            woodBlocks[i].setSize(50, 50); // Set size for the wood blocks
        }

        // Create and initialize the glass blocks
        for (int i = 0; i < glassBlockCount; i++) {
            glassBlocks[i] = new Sprite(new Texture("GlassBlock.png"));
            glassBlocks[i].setSize(40, 40); // Set size for the glass blocks
        }

        // Create and initialize the glass F blocks
        for (int i = 0; i < glassFBlockCount; i++) {
            glassFBlocks[i] = new Sprite(new Texture("GlassFBlock.png"));
            glassFBlocks[i].setSize(50, 50); // Set size for the glass F blocks
        }
    }

    public void setPositions(float startX, float startY) {
        // Position wood blocks (starting from bottom left and stacking upwards)
        for (int i = 0; i < woodBlocks.length; i++) {
            // Evenly space the blocks in the bottom row
            woodBlocks[i].setPosition(startX + (i - 2) * woodBlocks[i].getWidth(), startY); // Offset to center the row
        }

        // Position glass blocks (middle row, stacked above wood blocks)
        for (int i = 0; i < glassBlocks.length; i++) {
            // Evenly space the blocks in the middle row, centered above the wood blocks
            glassBlocks[i].setPosition(startX + (i - 1) * glassBlocks[i].getWidth(), startY + woodBlocks[0].getHeight());
        }

        // Position glass F blocks (top row, stacked above glass blocks)
        for (int i = 0; i < glassFBlocks.length; i++) {
            // Evenly space the blocks in the top row, centered above the glass blocks
            glassFBlocks[i].setPosition(startX + (i) * glassFBlocks[i].getWidth(), startY + woodBlocks[0].getHeight() + glassBlocks[0].getHeight());
        }
    }

    public void render(SpriteBatch batch) {
        // Render wood blocks
        for (Sprite woodBlock : woodBlocks) {
            woodBlock.draw(batch);
        }

        // Render glass blocks
        for (Sprite glassBlock : glassBlocks) {
            glassBlock.draw(batch);
        }

        // Render glass F blocks
        for (Sprite glassFBlock : glassFBlocks) {
            glassFBlock.draw(batch);
        }
    }

    public Sprite[] getWoodBlocks() {
        return woodBlocks;
    }

    public Sprite[] getGlassBlocks() {
        return glassBlocks;
    }

    public Sprite[] getGlassFBlocks() {
        return glassFBlocks;
    }
}
