package com.edu.diettrack.di

import android.content.Context
import androidx.room.Room
import com.edu.diettrack.data.local.AuthUserDao
import com.edu.diettrack.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "diettrack_db"
        ).fallbackToDestructiveMigration() // Only for development/testing
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthUserDao(db: AppDatabase): AuthUserDao {
        return db.authUserDao()
    }
}