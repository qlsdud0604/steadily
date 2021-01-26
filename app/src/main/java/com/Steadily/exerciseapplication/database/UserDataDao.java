package com.Steadily.exerciseapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.Steadily.exerciseapplication.database.UserData;

import java.util.List;

/**
 * 데이터베이스에 접근하여 수행할 작업을 정의한 인터페이스
 */
@Dao
public interface UserDataDao {
    /**
     * 데이터베이스에 저장된 모든 데이터를 반환하는 메소드
     */
    @Query("SELECT * FROM UserData")
    List<UserData> getAll();

    /**
     * 데이터베이스에 저장된 모든 데이터를 삭제하는 메소드
     */
    @Query("DELETE FROM UserData")
    void deleteAll();

    /**
     * 데이터베이스에 특정한 데이터만을 저장하는 메소드
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void put(UserData userData);

    /**
     * 데이터베이스내에 특정한 데이터만을 수정하는 메소드
     */
    @Update
    void update(UserData userDate);

    /**
     * 데이터베이스내에 특정한 데이터만을 삭제하는 메소드
     */
    @Delete
    void delete(UserData userData);
}
