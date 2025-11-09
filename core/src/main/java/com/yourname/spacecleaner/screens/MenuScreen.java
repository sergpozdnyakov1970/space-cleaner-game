package com.yourname.spacecleaner.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.yourname.spacecleaner.SpaceCleanerGame;
import com.yourname.spacecleaner.GameResources;
import com.yourname.spacecleaner.GameSettings; // Добавляем этот импорт
import com.yourname.spacecleaner.ui.*;
import com.yourname.spacecleaner.GameSession;

public class MenuScreen extends ScreenAdapter {
    SpaceCleanerGame myGdxGame;

    MovingBackgroundView backgroundView;
    TextView titleTextView;
    ButtonView playButtonView;
    ButtonView settingsButtonView;
    ButtonView exitButtonView;

    public MenuScreen(SpaceCleanerGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        titleTextView = new TextView(myGdxGame.largeWhiteFont, 256, 956, "Space Cleaner");

        // Временная кнопка на весь экран
        playButtonView = new ButtonView(0, 0, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT,
            myGdxGame.commonBlackFont, GameResources.BUTTON_SHORT_BG_IMG_PATH, "TAP TO PLAY");

        settingsButtonView = new ButtonView(280, 617, 160, 70, myGdxGame.commonBlackFont,
            GameResources.BUTTON_SHORT_BG_IMG_PATH, "Settings");
        exitButtonView = new ButtonView(280, 517, 160, 70, myGdxGame.commonBlackFont,
            GameResources.BUTTON_SHORT_BG_IMG_PATH, "Exit");
    }

    @Override
    public void render(float delta) {
        handleInput();

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();
        backgroundView.draw(myGdxGame.batch);
        titleTextView.draw(myGdxGame.batch);
        playButtonView.draw(myGdxGame.batch);
        settingsButtonView.draw(myGdxGame.batch);
        exitButtonView.draw(myGdxGame.batch);
        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            myGdxGame.camera.unproject(myGdxGame.touch);

            System.out.println("Touch at: " + myGdxGame.touch.x + ", " + myGdxGame.touch.y);
            System.out.println("Play button hit: " + playButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y));

            if (playButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                System.out.println("Creating GameSession...");
                myGdxGame.gameSession = new GameSession(myGdxGame);
                myGdxGame.setScreen(myGdxGame.gameScreen);
            }
            if (settingsButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.settingsScreen);
            }
            if (exitButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void dispose() {
        backgroundView.dispose();
        titleTextView.dispose();
        playButtonView.dispose();
        settingsButtonView.dispose();
        exitButtonView.dispose();
    }
}
