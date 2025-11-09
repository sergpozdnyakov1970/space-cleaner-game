package com.yourname.spacecleaner;

import com.badlogic.gdx.utils.TimeUtils;
import java.util.ArrayList;
import com.yourname.spacecleaner.managers.MemoryManager;

public class GameSession {
    public GameState state;
    private int score;
    int destructedTrashNumber;
    private long sessionStartTime;
    private SpaceCleanerGame game;

    public GameSession(SpaceCleanerGame game) {
        this.game = game;
        this.state = GameState.PLAYING;
        this.score = 0;
        this.destructedTrashNumber = 0;
        this.sessionStartTime = TimeUtils.millis();
    }

    public void updateScore() {
        score = (int)(TimeUtils.millis() - sessionStartTime) / 100 + destructedTrashNumber * 100;
    }

    public int getScore() {
        return score;
    }

    public void destructionRegistration() {
        destructedTrashNumber += 1;
    }

    public void endGame() {
        updateScore();
        state = GameState.ENDED;
        ArrayList<Integer> recordsTable = MemoryManager.loadRecordsTable();
        if (recordsTable == null) {
            recordsTable = new ArrayList<>();
        }
        int foundIdx = 0;
        for (; foundIdx < recordsTable.size(); foundIdx++) {
            if (recordsTable.get(foundIdx) < getScore()) break;
        }
        recordsTable.add(foundIdx, getScore());
        if (recordsTable.size() > 5) {
            recordsTable = new ArrayList<>(recordsTable.subList(0, 5));
        }
        MemoryManager.saveTableOfRecords(recordsTable);
    }
}
