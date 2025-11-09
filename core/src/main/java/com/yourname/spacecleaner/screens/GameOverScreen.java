package com.yourname.spacecleaner.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.yourname.spacecleaner.SpaceCleanerGame;
import com.yourname.spacecleaner.GameResources;
import com.yourname.spacecleaner.ui.*;
import com.yourname.spacecleaner.managers.MemoryManager;
import java.util.ArrayList;

public class GameOverScreen extends ScreenAdapter {
    SpaceCleanerGame game;

    MovingBackgroundView backgroundView;
    ImageView blackoutImageView;
    TextView titleTextView;
    RecordsListView recordsListView;
    ButtonView menuButton;

    public GameOverScreen(SpaceCleanerGame game) {
        this.game = game;

        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        blackoutImageView = new ImageView(85, 365, GameResources.BLACKOUT_MIDDLE_IMG_PATH);

        // Создаем шрифт для RecordsListView
        BitmapFont fontForRecords = FontBuilder.generate(24, Color.WHITE, GameResources.FONT_PATH);
        recordsListView = new RecordsListView(fontForRecords, 690);

        // Для остальных элементов также создаем шрифты
        BitmapFont titleFont = FontBuilder.generate(48, Color.WHITE, GameResources.FONT_PATH);
        BitmapFont buttonFont = FontBuilder.generate(24, Color.BLACK, GameResources.FONT_PATH);

        titleTextView = new TextView(titleFont, 206, 842, "Game Over");
        menuButton = new ButtonView(280, 365, 160, 70, buttonFont,
            GameResources.BUTTON_SHORT_BG_IMG_PATH, "Menu");

        // Загружаем рекорды
        ArrayList<Integer> records = MemoryManager.loadRecordsTable();
        recordsListView.setRecords(records);
    }

    @Override
    public void render(float delta) {
        handleInput();

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        game.batch.begin();
        backgroundView.draw(game.batch);
        blackoutImageView.draw(game.batch);
        titleTextView.draw(game.batch);
        recordsListView.draw(game.batch);
        menuButton.draw(game.batch);
        game.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            game.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.camera.unproject(game.touch);

            if (menuButton.isHit(game.touch.x, game.touch.y)) {
                game.setScreen(game.menuScreen);
            }
        }
    }

    @Override
    public void dispose() {
        backgroundView.dispose();
        blackoutImageView.dispose();
        titleTextView.dispose();
        recordsListView.dispose();
        menuButton.dispose();
    }
}
