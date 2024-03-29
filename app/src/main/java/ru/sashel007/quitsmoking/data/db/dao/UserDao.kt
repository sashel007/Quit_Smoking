package ru.sashel007.quitsmoking.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.sashel007.quitsmoking.data.db.entity.UserData

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userData: UserData): Long // Обычно возвращается ID вставленной строки

    @Query("SELECT * FROM user_data WHERE id = :userId")
    fun getUserById(userId: Int): LiveData<UserData>

    // Новый метод для синхронного получения данных пользователя
    @Query("SELECT * FROM user_data WHERE id = :userId")
    suspend fun getUserByIdAsync(userId: Int): UserData?

    @Update
    suspend fun update(userData: UserData)

    @Delete
    suspend fun delete(userData: UserData)

    @Query("SELECT * from user_data WHERE id = :userId")
    fun getUserData(userId: Int): LiveData<UserData>

    @Query("SELECT * FROM user_data")
    fun getAllUserData(): LiveData<List<UserData>>


}
