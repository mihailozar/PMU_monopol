package com.example.pmu_monopol.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MyData {

    @PrimaryKey(autoGenerate = true)
    private long idGame;
    private String date;
    private String namePlayers;
    private String winner;


    public MyData( long idGame, String date, String namePlayers,String winner) {
        this.idGame=idGame;
        this.date = date;
        this.namePlayers = namePlayers;
        this.winner=winner;

    }

    public long getIdGame() {
        return idGame;
    }

    public void setIdGame(long idGame) {
        this.idGame = idGame;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNamePlayers() {
        return namePlayers;
    }

    public void setNamePlayers(String namePlayers) {
        this.namePlayers = namePlayers;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
