package com.yourname.spacecleaner.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.yourname.spacecleaner.GameSettings;
import java.util.ArrayList;

public class RecordsListView extends TextView {

    public RecordsListView(BitmapFont font, float y) {
        super(font, 0, y, "");
    }

    public void setRecords(ArrayList<Integer> recordsList) {
        if (recordsList == null || recordsList.isEmpty()) {
            setText("No records yet!");
            return;
        }

        StringBuilder sb = new StringBuilder();
        int countOfRows = Math.min(recordsList.size(), 5);
        for (int i = 0; i < countOfRows; i++) {
            sb.append(i + 1).append(". - ").append(recordsList.get(i));
            if (i < countOfRows - 1) {
                sb.append("\n");
            }
        }
        setText(sb.toString());

        // Центрирование
        GlyphLayout glyphLayout = new GlyphLayout(font, getText());
        setX((GameSettings.SCREEN_WIDTH - glyphLayout.width) / 2);
    }
}
