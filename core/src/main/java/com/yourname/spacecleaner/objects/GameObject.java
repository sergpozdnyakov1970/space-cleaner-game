package com.yourname.spacecleaner.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class GameObject {
    protected Body body;
    protected boolean alive;

    public abstract void update();
    public abstract void draw(SpriteBatch batch);

    public Body getBody() {
        return body;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void dispose() {
        if (body != null) {
            body.getWorld().destroyBody(body);
        }
    }
}
