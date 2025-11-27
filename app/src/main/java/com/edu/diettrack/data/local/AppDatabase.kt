package com.edu.diettrack.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AuthUserEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun authUserDao(): AuthUserDao
}