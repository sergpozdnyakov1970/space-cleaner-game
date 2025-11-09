package com.yourname.spacecleaner.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class TextView extends View {
    protected BitmapFont font;
    protected String text;

    public TextView(BitmapFont font, float x, float y, String text) {
        this.font = font;
        this.x = x;
        this.y = y;
        this.text = text;
    }

    @Override
    public void draw(SpriteBatch batch) {
        font.draw(batch, text, x, y);
    }

    @Override
    public void dispose() {
        // Шрифт удаляется в главном классе
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public boolean isHit(float touchX, float touchY) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text);
        return touchX >= x && touchX <= x + layout.width && touchY >= y - layout.height && touchY <= y;
    }
}
