package com.example.pmu_monopol.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GameStep {

    @PrimaryKey(autoGenerate = true)
    private long idStep;
    private long idGame;
    private String namePlayer;
    private int numberOfSteps;
    private int score;


    public GameStep( long idGame, String namePlayer, int numberOfSteps,int score) {
        this.idGame=idGame;
        this.namePlayer = namePlayer;
        this.numberOfSteps = numberOfSteps;
        this.score=score;

    }

    public long getIdGame() {
        return idGame;
    }

    public void setIdGame(long idGame) {
        this.idGame = idGame;
    }

    public long getIdStep() {
        return idStep;
    }

    public void setIdStep(long idStep) {
        this.idStep = idStep;
    }

    public String getNamePlayer() {
        return namePlayer;
    }

    public void setNamePlayer(String namePlayer) {
        this.namePlayer = namePlayer;
    }

    public int getNumberOfSteps() {
        return numberOfSteps;
    }

    public void setNumberOfSteps(int numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
