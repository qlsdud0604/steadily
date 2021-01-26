package com.Steadily.exerciseapplication.database;

import androidx.room.RoomDatabase;

/**
 * 데이터베이스를 생성하기 위한 클래스
 */
@androidx.room.Database(entities = {UserData.class}, version = 1)
public abstract class Database extends RoomDatabase {
    public abstract UserDataDao userDataDao();
}
