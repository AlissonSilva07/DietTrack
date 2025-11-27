package com.edu.diettrack.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AuthUserDao {
    @Query("SELECT * FROM auth_user LIMIT 1")
    suspend fun getUser(): AuthUserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: AuthUserEntity)

    @Query("DELETE FROM auth_user")
    suspend fun clear()
}