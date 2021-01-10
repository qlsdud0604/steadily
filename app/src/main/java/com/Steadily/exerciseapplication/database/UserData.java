package com.Steadily.exerciseapplication.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/** class that defines the type of data to be stored in the database */
@Entity
public class UserData {
    @PrimaryKey
    private int date;
    private int time;

    /** parameter constructor */
    public UserData(int date, int time) {
        this.date = date;
        this.time = time;
    }

    /** method to get date */
    public int getDate() {
        return date;
    }

    /** method to set date */
    public void setDate(int date) {
        this.date = date;
    }

    /**  method to get time */
    public int getTime() {
        return time;
    }

    /**  method to set time */
    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "date=" + this.date +
                ", time=" + this.time+
                '}'+"\n";
    }
}
