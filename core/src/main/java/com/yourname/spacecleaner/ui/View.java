package com.yourname.spacecleaner.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class View {
    protected float x, y;

    public abstract void draw(SpriteBatch batch);
    public abstract void dispose();

    public float getX() { return x; }
    public float getY() { return y; }
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
}
