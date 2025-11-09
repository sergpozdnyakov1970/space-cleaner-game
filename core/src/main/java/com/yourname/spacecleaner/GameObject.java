package com.yourname.spacecleaner;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public abstract class GameObject {
    protected Body body;
    protected float width;
    protected float height;
    protected Texture texture;

    public GameObject(String texturePath, float x, float y, float width, float height, World world) {
        this.width = width;
        this.height = height;
        this.texture = new Texture(texturePath);
        this.body = createBody(x, y, world);
    }

    private Body createBody(float x, float y, World world) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        Body body = world.createBody(def);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(Math.max(width, height) * GameSettings.SCALE / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 1f;

        body.createFixture(fixtureDef);
        circleShape.dispose();

        body.setTransform(x * GameSettings.SCALE, y * GameSettings.SCALE, 0);
        return body;
    }

    // Геттеры с преобразованием метров в пиксели
    public int getX() {
        return (int) (body.getPosition().x / GameSettings.SCALE);
    }

    public int getY() {
        return (int) (body.getPosition().y / GameSettings.SCALE);
    }

    // Сеттеры с преобразованием пикселей в метры
    public void setX(int x) {
        body.setTransform(x * GameSettings.SCALE, body.getPosition().y, 0);
    }

    public void setY(int y) {
        body.setTransform(body.getPosition().x, y * GameSettings.SCALE, 0);
    }

    // Геттеры размеров
    public float getWidth() { return width; }
    public float getHeight() { return height; }

    // Метод для отрисовки (с учетом центра коллайдера)
    public void draw(SpriteBatch batch) {
        // Получаем координаты центра коллайдера в пикселях
        int centerX = getX();
        int centerY = getY();

        // Пересчитываем в левый нижний угол для отрисовки текстуры
        float drawX = centerX - width / 2;
        float drawY = centerY - height / 2;

        batch.draw(texture, drawX, drawY, width, height);
    }

    // Очистка ресурсов
    public void dispose() {
        texture.dispose();
    }
}
