package com.yourname.spacecleaner.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.graphics.Texture;
import com.yourname.spacecleaner.GameSettings;
import com.yourname.spacecleaner.GameResources;

public class ShipObject extends GameObject {
    private Texture texture;
    private int lives;
    private long lastHitTime;

    public ShipObject(World world) {
        this.texture = new Texture(GameResources.SHIP_IMG_PATH);
        this.alive = true;
        this.lives = 3;
        this.lastHitTime = 0;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(GameSettings.SCREEN_WIDTH / 2 * GameSettings.SCALE,
            100 * GameSettings.SCALE);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(GameSettings.SHIP_WIDTH / 2 * GameSettings.SCALE,
            GameSettings.SHIP_HEIGHT / 2 * GameSettings.SCALE);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.filter.categoryBits = GameSettings.SHIP_BIT;
        fixtureDef.filter.maskBits = GameSettings.TRASH_BIT;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    @Override
    public void update() {
        // Корабль управляется через физику
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (alive) {
            batch.draw(texture,
                body.getPosition().x / GameSettings.SCALE - GameSettings.SHIP_WIDTH / 2,
                body.getPosition().y / GameSettings.SCALE - GameSettings.SHIP_HEIGHT / 2,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT);
        }
    }

    public int getLives() {
        return lives;
    }

    public void hit() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastHitTime > 1000) { // Защита от спама
            lives--;
            lastHitTime = currentTime;
            if (lives <= 0) {
                alive = false;
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        if (texture != null) {
            texture.dispose();
        }
    }
    public void moveTo(float targetX, float targetY) {
        float currentX = body.getPosition().x / GameSettings.SCALE;
        float currentY = body.getPosition().y / GameSettings.SCALE;

        float directionX = targetX - currentX;
        float directionY = targetY - currentY;

        // Нормализуем направление и применяем силу
        float length = (float)Math.sqrt(directionX * directionX + directionY * directionY);
        if (length > 0) {
            directionX /= length;
            directionY /= length;

            body.applyForceToCenter(
                directionX * GameSettings.SHIP_FORCE_RATIO * GameSettings.SCALE,
                directionY * GameSettings.SHIP_FORCE_RATIO * GameSettings.SCALE,
                true
            );
        }
    }
}
