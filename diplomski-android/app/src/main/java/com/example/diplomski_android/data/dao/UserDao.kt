package com.example.diplomski_android.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diplomski_android.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    @Query("select * from users where id=:id")
    fun getById(id: Long): User

    @Query("select * from users where email=:email")
    fun getByEmail(email: String): User

    @Query("select admin from users where id=:id")
    fun getIsAdmin(id: Long): Boolean

    @Query("delete from users")
    suspend fun deleteAll()
}