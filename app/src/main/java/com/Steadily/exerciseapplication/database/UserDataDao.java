package com.Steadily.exerciseapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.Steadily.exerciseapplication.database.UserData;

import java.util.List;

/** interface that defines what to do with the database */
@Dao
public interface UserDataDao {
    /** method that returns all data stored in the database */
    @Query("SELECT * FROM UserData")
    List<UserData> getAll();

    /** method that delete all data stored in the database */
    @Query("DELETE FROM UserData")
    void deleteAll();

    /** methods to store specific data in the database */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void put(UserData userData);

    /** method to update a specific data value in the database */
    @Update
    void update(UserData userDate);

    /** method to delete specific data stored in database */
    @Delete
    void delete(UserData userData);

}
