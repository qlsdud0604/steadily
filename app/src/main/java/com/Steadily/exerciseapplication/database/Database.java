package com.Steadily.exerciseapplication.database;

import androidx.room.RoomDatabase;

/** class to create database */
@androidx.room.Database(entities = {UserData.class}, version = 1)
public abstract class Database extends RoomDatabase {
    public abstract UserDataDao userDataDao();
}
