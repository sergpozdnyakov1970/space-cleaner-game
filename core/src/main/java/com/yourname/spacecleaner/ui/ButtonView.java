package com.yourname.spacecleaner.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class ButtonView extends View {
    private Texture background;
    private BitmapFont font;
    private String text;
    private float width, height;

    public ButtonView(float x, float y, float width, float height, BitmapFont font, String texturePath, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.background = new Texture(texturePath);
        this.font = font;
        this.text = text;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(background, x, y, width, height);

        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text);
        float textX = x + (width - layout.width) / 2;
        float textY = y + (height + layout.height) / 2;
        font.draw(batch, text, textX, textY);
    }

    @Override
    public void dispose() {
        if (background != null) {
            background.dispose();
        }
    }

    public boolean isHit(float touchX, float touchY) {
        return touchX >= x && touchX <= x + width && touchY >= y && touchY <= y + height;
    }

    public void setText(String text) {
        this.text = text;
    }
}
