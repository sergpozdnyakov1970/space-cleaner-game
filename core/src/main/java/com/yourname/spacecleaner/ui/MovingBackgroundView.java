package com.yourname.spacecleaner.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.yourname.spacecleaner.GameSettings;

public class MovingBackgroundView extends View {
    private Texture texture;
    private float y1, y2;
    private float speed;

    public MovingBackgroundView(String texturePath) {
        this.texture = new Texture(texturePath);
        this.y1 = 0;
        this.y2 = GameSettings.SCREEN_HEIGHT;
        this.speed = 1.0f;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, 0, y1, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
        batch.draw(texture, 0, y2, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);

        y1 -= speed;
        y2 -= speed;

        if (y1 + GameSettings.SCREEN_HEIGHT <= 0) {
            y1 = y2 + GameSettings.SCREEN_HEIGHT;
        }
        if (y2 + GameSettings.SCREEN_HEIGHT <= 0) {
            y2 = y1 + GameSettings.SCREEN_HEIGHT;
        }
    }

    @Override
    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}
