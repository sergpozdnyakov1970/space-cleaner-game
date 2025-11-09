package com.yourname.spacecleaner.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.yourname.spacecleaner.SpaceCleanerGame;
import com.yourname.spacecleaner.GameResources;
import com.yourname.spacecleaner.ui.*;
import com.yourname.spacecleaner.managers.MemoryManager;
import java.util.ArrayList;

public class SettingsScreen extends ScreenAdapter {
    SpaceCleanerGame myGdxGame;

    MovingBackgroundView backgroundView;
    TextView titleTextView;
    ImageView blackoutImageView;
    ButtonView returnButton;
    TextView musicSettingView;
    TextView soundSettingView;
    TextView clearSettingView;

    public SettingsScreen(SpaceCleanerGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        titleTextView = new TextView(myGdxGame.largeWhiteFont, 256, 956, "Settings");
        blackoutImageView = new ImageView(85, 365, GameResources.BLACKOUT_MIDDLE_IMG_PATH);

        // Проверяем, что audioManager не null
        boolean isMusicOn = myGdxGame.audioManager != null && myGdxGame.audioManager.isMusicOn;
        boolean isSoundOn = myGdxGame.audioManager != null && myGdxGame.audioManager.isSoundOn;

        musicSettingView = new TextView(myGdxGame.commonWhiteFont, 173, 717,
            "music: " + translateStateToText(isMusicOn));
        soundSettingView = new TextView(myGdxGame.commonWhiteFont, 173, 658,
            "sound: " + translateStateToText(isSoundOn));
        clearSettingView = new TextView(myGdxGame.commonWhiteFont, 173, 599, "clear records");
        returnButton = new ButtonView(280, 447, 160, 70, myGdxGame.commonBlackFont,
            GameResources.BUTTON_SHORT_BG_IMG_PATH, "return");
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
        blackoutImageView.draw(myGdxGame.batch);
        returnButton.draw(myGdxGame.batch);
        musicSettingView.draw(myGdxGame.batch);
        soundSettingView.draw(myGdxGame.batch);
        clearSettingView.draw(myGdxGame.batch);
        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            myGdxGame.camera.unproject(myGdxGame.touch);

            if (returnButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.menuScreen);
            }
            if (clearSettingView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                MemoryManager.saveTableOfRecords(new ArrayList<>());
                clearSettingView.setText("records cleared!");
            }
            if (musicSettingView.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && myGdxGame.audioManager != null) {
                MemoryManager.saveSoundSettings(!MemoryManager.loadIsMusicOn(), myGdxGame.audioManager.isSoundOn);
                myGdxGame.audioManager.updateMusicFlag();
                musicSettingView.setText("music: " + translateStateToText(myGdxGame.audioManager.isMusicOn));
            }
            if (soundSettingView.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && myGdxGame.audioManager != null) {
                MemoryManager.saveSoundSettings(myGdxGame.audioManager.isMusicOn, !MemoryManager.loadIsSoundOn());
                myGdxGame.audioManager.updateSoundFlag();
                soundSettingView.setText("sound: " + translateStateToText(myGdxGame.audioManager.isSoundOn));
            }
        }
    }

    private String translateStateToText(boolean state) {
        return state ? "ON" : "OFF";
    }

    @Override
    public void dispose() {
        backgroundView.dispose();
        blackoutImageView.dispose();
        returnButton.dispose();
        titleTextView.dispose();
        musicSettingView.dispose();
        soundSettingView.dispose();
        clearSettingView.dispose();
    }
}
