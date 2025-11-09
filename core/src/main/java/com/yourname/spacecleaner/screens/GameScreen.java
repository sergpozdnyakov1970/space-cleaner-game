package com.yourname.spacecleaner.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Array;
import com.yourname.spacecleaner.SpaceCleanerGame;
import com.yourname.spacecleaner.GameResources;
import com.yourname.spacecleaner.GameState;
import com.yourname.spacecleaner.GameSession;
import com.yourname.spacecleaner.GameSettings;
import com.yourname.spacecleaner.objects.*;
import com.yourname.spacecleaner.ui.*;
import com.yourname.spacecleaner.managers.MemoryManager;
import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {
    SpaceCleanerGame myGdxGame;
    GameSession gameSession;

    // Игровые объекты
    ShipObject shipObject;
    Array<BulletObject> bullets;
    Array<TrashObject> trashArray;

    // Время
    long lastShootTime;
    long lastTrashAppearanceTime;
    long trashAppearanceCoolDown;

    // UI элементы
    MovingBackgroundView backgroundView;
    LiveView liveView;
    TextView scoreTextView;

    // PAUSED state UI
    ImageView blackoutView;
    ButtonView resumeButton;
    ButtonView menuButton1;

    // ENDED state UI
    ImageView fullBlackoutView;
    TextView recordsTextView;
    RecordsListView recordsListView;
    ButtonView homeButton2;

    public GameScreen(SpaceCleanerGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        // Инициализация игровых объектов
        shipObject = new ShipObject(myGdxGame.world);
        bullets = new Array<>();
        trashArray = new Array<>();

        // Установка user data для коллизий
        shipObject.getBody().setUserData(shipObject);

        // Время
        lastShootTime = 0;
        lastTrashAppearanceTime = 0;
        trashAppearanceCoolDown = GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN;

        // Основной UI
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        liveView = new LiveView(50, 1000);
        scoreTextView = new TextView(myGdxGame.commonWhiteFont, 50, 950, "Score: 0");

        // PAUSED state UI
        blackoutView = new ImageView(85, 365, GameResources.BLACKOUT_MIDDLE_IMG_PATH);
        resumeButton = new ButtonView(280, 617, 160, 70, myGdxGame.commonBlackFont,
            GameResources.BUTTON_SHORT_BG_IMG_PATH, "Resume");
        menuButton1 = new ButtonView(280, 517, 160, 70, myGdxGame.commonBlackFont,
            GameResources.BUTTON_SHORT_BG_IMG_PATH, "Menu");

        // ENDED state UI
        fullBlackoutView = new ImageView(0, 0, GameResources.BLACKOUT_FULL_IMG_PATH);

        // Создаем шрифт для RecordsListView
        BitmapFont recordsFont = FontBuilder.generate(24, Color.WHITE, GameResources.FONT_PATH);
        recordsListView = new RecordsListView(recordsFont, 690);

        recordsTextView = new TextView(myGdxGame.largeWhiteFont, 206, 842, "Last records");
        homeButton2 = new ButtonView(280, 365, 160, 70, myGdxGame.commonBlackFont,
            GameResources.BUTTON_SHORT_BG_IMG_PATH, "Home");
    }

    @Override
    public void render(float delta) {
        handleInput();
        myGdxGame.stepWorld();

        System.out.println("Game state: " + (gameSession != null ? gameSession.state : "null"));
        System.out.println("Ship alive: " + shipObject.isAlive());
        System.out.println("Bullets count: " + bullets.size);
        System.out.println("Trash count: " + trashArray.size);


        if (gameSession != null && gameSession.state == GameState.PLAYING) {
            updateGame();
        }

        draw();
    }

    private void updateGame() {
        // Обновление объектов
        shipObject.update();
        updateBullets();
        updateTrash();
        spawnTrash();

        // Обновление жизней и счета
        liveView.setLives(shipObject.getLives());
        gameSession.updateScore();
        scoreTextView.setText("Score: " + gameSession.getScore());

        // Проверка конца игры
        if (!shipObject.isAlive()) {
            gameSession.endGame();
            recordsListView.setRecords(MemoryManager.loadRecordsTable());
        }
    }

    private void updateBullets() {
        for (int i = 0; i < bullets.size; i++) {
            BulletObject bullet = bullets.get(i);
            bullet.update();
            if (!bullet.isAlive()) {
                bullet.dispose();
                bullets.removeIndex(i);
                i--;
            }
        }
    }

    private void updateTrash() {
        for (int i = 0; i < trashArray.size; i++) {
            TrashObject trash = trashArray.get(i);
            trash.update();
            if (!trash.isAlive()) {
                if (trash.getBody().getUserData() != null) {
                    gameSession.destructionRegistration();
                    if (myGdxGame.audioManager.isSoundOn) {
                        myGdxGame.audioManager.explosionSound.play(0.2f);
                    }
                }
                trash.dispose();
                trashArray.removeIndex(i);
                i--;
            }
        }
    }

    private void spawnTrash() {
        long currentTime = TimeUtils.millis();
        if (currentTime - lastTrashAppearanceTime > trashAppearanceCoolDown) {
            TrashObject trash = new TrashObject(myGdxGame.world);
            trash.getBody().setUserData(trash);
            trashArray.add(trash);
            lastTrashAppearanceTime = currentTime;

            System.out.println("Spawned trash at: " + currentTime);

            // Уменьшаем кд для увеличения сложности
            if (trashAppearanceCoolDown > 500) {
                trashAppearanceCoolDown -= 10;
            }
        }
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            myGdxGame.camera.unproject(myGdxGame.touch);

            if (gameSession == null) return;

            switch (gameSession.state) {
                case PLAYING:
                    // Движение корабля к точке касания
                    shipObject.moveTo(myGdxGame.touch.x, myGdxGame.touch.y);

                    // Стрельба
                    long currentTime = TimeUtils.millis();
                    if (currentTime - lastShootTime > GameSettings.SHOOTING_COOL_DOWN) {
                        BulletObject bullet = new BulletObject(myGdxGame.world, myGdxGame.touch.x, myGdxGame.touch.y);
                        bullet.getBody().setUserData(bullet);
                        bullets.add(bullet);
                        myGdxGame.audioManager.playShootSound();
                        lastShootTime = currentTime;
                    }
                    break;

                case PAUSED:
                    if (resumeButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        gameSession.state = GameState.PLAYING;
                    }
                    if (menuButton1.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        myGdxGame.setScreen(myGdxGame.menuScreen);
                    }
                    break;

                case ENDED:
                    if (homeButton2.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        myGdxGame.setScreen(myGdxGame.menuScreen);
                    }
                    break;
            }
        }
    }

    private void draw() {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();
        backgroundView.draw(myGdxGame.batch);

        // Отрисовка игровых объектов
        shipObject.draw(myGdxGame.batch);
        for (BulletObject bullet : bullets) {
            bullet.draw(myGdxGame.batch);
        }
        for (TrashObject trash : trashArray) {
            trash.draw(myGdxGame.batch);
        }

        // Основной UI
        liveView.draw(myGdxGame.batch);
        scoreTextView.draw(myGdxGame.batch);

        // Состояния PAUSED и ENDED
        if (gameSession != null) {
            if (gameSession.state == GameState.PAUSED) {
                blackoutView.draw(myGdxGame.batch);
                resumeButton.draw(myGdxGame.batch);
                menuButton1.draw(myGdxGame.batch);
            } else if (gameSession.state == GameState.ENDED) {
                fullBlackoutView.draw(myGdxGame.batch);
                recordsTextView.draw(myGdxGame.batch);
                recordsListView.draw(myGdxGame.batch);
                homeButton2.draw(myGdxGame.batch);
            }
        }

        myGdxGame.batch.end();
    }

    @Override
    public void dispose() {
        backgroundView.dispose();
        liveView.dispose();
        scoreTextView.dispose();
        blackoutView.dispose();
        resumeButton.dispose();
        menuButton1.dispose();
        fullBlackoutView.dispose();
        recordsTextView.dispose();
        recordsListView.dispose();
        homeButton2.dispose();

        // Очистка игровых объектов
        shipObject.dispose();
        for (BulletObject bullet : bullets) {
            bullet.dispose();
        }
        for (TrashObject trash : trashArray) {
            trash.dispose();
        }
    }
}
