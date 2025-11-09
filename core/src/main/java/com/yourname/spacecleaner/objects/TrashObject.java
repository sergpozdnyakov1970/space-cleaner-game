package com.yourname.spacecleaner.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.graphics.Texture;
import com.yourname.spacecleaner.GameSettings;
import com.yourname.spacecleaner.GameResources;
import java.util.Random;

public class TrashObject extends GameObject {
    private Texture texture;
    private Random random;

    public TrashObject(World world) {
        this.texture = new Texture(GameResources.TRASH_IMG_PATH);
        this.alive = true;
        this.random = new Random();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        float x = random.nextInt(GameSettings.SCREEN_WIDTH - GameSettings.TRASH_WIDTH) + GameSettings.TRASH_WIDTH / 2;
        bodyDef.position.set(x * GameSettings.SCALE, GameSettings.SCREEN_HEIGHT * GameSettings.SCALE);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(GameSettings.TRASH_WIDTH / 2 * GameSettings.SCALE,
            GameSettings.TRASH_HEIGHT / 2 * GameSettings.SCALE);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.filter.categoryBits = GameSettings.TRASH_BIT;
        fixtureDef.filter.maskBits = GameSettings.SHIP_BIT | GameSettings.BULLET_BIT;

        body.createFixture(fixtureDef);
        shape.dispose();

        // Мусор падает вниз
        body.setLinearVelocity(0, -GameSettings.TRASH_VELOCITY * GameSettings.SCALE);
    }

    @Override
    public void update() {
        // Уничтожаем мусор, если он упал за экран
        if (body.getPosition().y / GameSettings.SCALE < -GameSettings.TRASH_HEIGHT) {
            alive = false;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (alive) {
            batch.draw(texture,
                body.getPosition().x / GameSettings.SCALE - GameSettings.TRASH_WIDTH / 2,
                body.getPosition().y / GameSettings.SCALE - GameSettings.TRASH_HEIGHT / 2,
                GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT);
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
