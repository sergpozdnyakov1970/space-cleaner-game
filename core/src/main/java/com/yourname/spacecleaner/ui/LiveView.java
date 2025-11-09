package com.yourname.spacecleaner.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.yourname.spacecleaner.GameResources;

public class LiveView extends View {
    private Texture heartTexture;
    private int lives;
    private float spacing;

    public LiveView(float x, float y) {
        this.x = x;
        this.y = y;
        this.heartTexture = new Texture(GameResources.HEART_IMG_PATH);
        this.lives = 3;
        this.spacing = 60f;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    @Override
    public void draw(SpriteBatch batch) {
        for (int i = 0; i < lives; i++) {
            batch.draw(heartTexture, x + i * spacing, y, 50, 50);
        }
    }

    @Override
    public void dispose() {
        if (heartTexture != null) {
            heartTexture.dispose();
        }
    }
}
