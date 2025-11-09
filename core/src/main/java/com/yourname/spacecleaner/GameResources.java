package com.yourname.spacecleaner;

public class GameResources {
    // Текстуры - обновляем пути согласно вашим файлам
    public static final String SHIP_IMG_PATH = "textures/ship.png";           // был spaceship.png
    public static final String TRASH_IMG_PATH = "textures/trash.png";         // ✓ совпадает
    public static final String BULLET_IMG_PATH = "textures/bullet.png";       // ✓ совпадает
    public static final String BACKGROUND_IMG_PATH = "textures/background.png"; // был space_bg.png
    public static final String UI_BACKGROUND_IMG_PATH = "textures/background.png"; // временно используем background.png
    public static final String BLACKOUT_TOP_IMG_PATH = "textures/blackout_top.png";
    public static final String BLACKOUT_FULL_IMG_PATH = "textures/blackout_full.png";
    public static final String BLACKOUT_MIDDLE_IMG_PATH = "textures/blackout_middle.png";
    public static final String PAUSE_IMG_PATH = "textures/pausejcon.png";     // был pause_button.png
    public static final String RESUME_BUTTON_PATH = "textures/button_background_short.png"; // временно
    public static final String MENU_BUTTON_PATH = "textures/button_background_short.png";   // временно
    public static final String BACK_BUTTON_PATH = "textures/button_background_short.png";   // временно
    public static final String BUTTON_LONG_BG_IMG_PATH = "textures/button_background_long.png";
    public static final String BUTTON_SHORT_BG_IMG_PATH = "textures/button_background_short.png";
    public static final String HEART_IMG_PATH = "textures/life.png";          // был heart.png

    // Звуки - обновляем пути
    public static final String BACKGROUND_MUSIC_PATH = "sounds/background_music.mp3"; // ✓ совпадает
    public static final String SHOOT_SOUND_PATH = "sounds/shoot.mp3";         // был shoot_sound.wav
    public static final String DESTROY_SOUND_PATH = "sounds/destroy.mp3";     // был explosion_sound.wav

    // Шрифты
    public static final String FONT_PATH = "fonts/Montserrat-Bold.ttf";       // был game_font.ttf
}
