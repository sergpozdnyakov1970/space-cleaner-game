package com.yourname.spacecleaner.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.graphics.Texture;
import com.yourname.spacecleaner.GameSettings;
import com.yourname.spacecleaner.GameResources;

public class BulletObject extends GameObject {
    private Texture texture;

    public BulletObject(World world, float x, float y) {
        this.texture = new Texture(GameResources.BULLET_IMG_PATH);
        this.alive = true;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x * GameSettings.SCALE, y * GameSettings.SCALE);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(GameSettings.BULLET_WIDTH / 2 * GameSettings.SCALE,
            GameSettings.BULLET_HEIGHT / 2 * GameSettings.SCALE);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.filter.categoryBits = GameSettings.BULLET_BIT;
        fixtureDef.filter.maskBits = GameSettings.TRASH_BIT;

        body.createFixture(fixtureDef);
        shape.dispose();

        // Пуля летит вверх
        body.setLinearVelocity(0, GameSettings.BULLET_VELOCITY * GameSettings.SCALE);
    }

    @Override
    public void update() {
        // Уничтожаем пулю, если она вышла за экран
        if (body.getPosition().y / GameSettings.SCALE > GameSettings.SCREEN_HEIGHT) {
            alive = false;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (alive) {
            batch.draw(texture,
                body.getPosition().x / GameSettings.SCALE - GameSettings.BULLET_WIDTH / 2,
                body.getPosition().y / GameSettings.SCALE - GameSettings.BULLET_HEIGHT / 2,
                GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        if (texture != null) {
            texture.dispose();
        }
    }
}
