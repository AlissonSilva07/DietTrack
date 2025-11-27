package com.edu.diettrack.di

import com.edu.diettrack.data.local.AuthUserDao
import com.edu.diettrack.data.repository.AuthRepositoryImpl
import com.edu.diettrack.data.storage.AuthStorage
import com.edu.diettrack.data.utils.NetworkChecker
import com.edu.diettrack.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore,
        userDao: AuthUserDao,
        authStorage: AuthStorage,
        networkChecker: NetworkChecker
    ): AuthRepository = AuthRepositoryImpl(
        auth, firestore, userDao, authStorage, networkChecker
    )
}