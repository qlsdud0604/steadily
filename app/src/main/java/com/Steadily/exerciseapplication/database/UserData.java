package com.Steadily.exerciseapplication.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 데이터베이스에 저장할 데이터를 정의한 클래스
 */
@Entity
public class UserData {

    @PrimaryKey
    private int date;
    private int time;


    public UserData(int date, int time) {
        this.date = date;
        this.time = time;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "date=" + this.date +
                ", time=" + this.time +
                '}' + "\n";
    }
}
