package com.yourname.spacecleaner.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.yourname.spacecleaner.GameResources;

public class AudioManager {
    public boolean isSoundOn;
    public boolean isMusicOn;

    public Music backgroundMusic;
    public Sound shootSound;
    public Sound explosionSound;

    public AudioManager() {
        isMusicOn = MemoryManager.loadIsMusicOn();
        isSoundOn = MemoryManager.loadIsSoundOn();

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(GameResources.BACKGROUND_MUSIC_PATH));
        shootSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.SHOOT_SOUND_PATH));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.DESTROY_SOUND_PATH));

        backgroundMusic.setVolume(0.2f);
        backgroundMusic.setLooping(true);
        updateMusicFlag();
        updateSoundFlag();
    }

    public void updateMusicFlag() {
        isMusicOn = MemoryManager.loadIsMusicOn();
        if (isMusicOn) {
            backgroundMusic.play();
        } else {
            backgroundMusic.stop();
        }
    }

    public void updateSoundFlag() {
        isSoundOn = MemoryManager.loadIsSoundOn();
    }

    public void playShootSound() {
        if (isSoundOn) {
            shootSound.play();
        }
    }

    public void playExplosionSound() {
        if (isSoundOn) {
            explosionSound.play(0.2f);
        }
    }

    public void dispose() {
        backgroundMusic.dispose();
        shootSound.dispose();
        explosionSound.dispose();
    }
}
