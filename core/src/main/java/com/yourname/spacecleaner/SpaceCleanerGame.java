package com.yourname.spacecleaner;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.Vector2;
import com.yourname.spacecleaner.managers.AudioManager;
import com.yourname.spacecleaner.managers.ContactManager;
import com.yourname.spacecleaner.screens.*;
import com.yourname.spacecleaner.GameSession;
import com.yourname.spacecleaner.GameSettings;
import com.yourname.spacecleaner.GameResources;
import com.yourname.spacecleaner.ui.FontBuilder;

public class SpaceCleanerGame extends Game {
    public SpriteBatch batch;
    public OrthographicCamera camera;
    public World world;
    public Vector3 touch;

    public GameScreen gameScreen;
    public MenuScreen menuScreen;
    public SettingsScreen settingsScreen;
    public GameOverScreen gameOverScreen;

    public AudioManager audioManager;
    public GameSession gameSession;

    public BitmapFont commonWhiteFont;
    public BitmapFont largeWhiteFont;
    public BitmapFont commonBlackFont;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
        world = new World(new Vector2(0, 0), true);
        touch = new Vector3();

        world.setContactListener(new ContactManager());

        // 1. Сначала инициализируем шрифты
        commonWhiteFont = FontBuilder.generate(24, com.badlogic.gdx.graphics.Color.WHITE, GameResources.FONT_PATH);
        largeWhiteFont = FontBuilder.generate(48, com.badlogic.gdx.graphics.Color.WHITE, GameResources.FONT_PATH);
        commonBlackFont = FontBuilder.generate(24, com.badlogic.gdx.graphics.Color.BLACK, GameResources.FONT_PATH);

        // 2. Затем создаем AudioManager
        audioManager = new AudioManager();

        // 3. Только потом создаем экраны (они используют audioManager)
        gameScreen = new GameScreen(this);
        menuScreen = new MenuScreen(this);
        settingsScreen = new SettingsScreen(this);
        gameOverScreen = new GameOverScreen(this);

        setScreen(menuScreen);
    }

    public void stepWorld() {
        world.step(GameSettings.STEP_TIME, GameSettings.VELOCITY_ITERATIONS, GameSettings.POSITION_ITERATIONS);
    }

    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
        commonWhiteFont.dispose();
        largeWhiteFont.dispose();
        commonBlackFont.dispose();
        if (audioManager != null) {
            audioManager.dispose();
        }
    }
}
