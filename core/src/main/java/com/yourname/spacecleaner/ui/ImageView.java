package com.yourname.spacecleaner.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class ImageView extends View {
    private Texture texture;
    private float width, height;

    public ImageView(float x, float y, String texturePath) {
        setX(x);
        setY(y);
        this.texture = new Texture(texturePath);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
    }

    public ImageView(float x, float y, float width, float height, String texturePath) {
        setX(x);
        setY(y);
        this.texture = new Texture(texturePath);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, getX(), getY(), width, height);
    }

    @Override
    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }

    public boolean isHit(float touchX, float touchY) {
        return touchX >= getX() && touchX <= getX() + width && touchY >= getY() && touchY <= getY() + height;
    }
}
