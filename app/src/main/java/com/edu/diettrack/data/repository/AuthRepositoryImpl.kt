package com.edu.diettrack.data.repository

import com.edu.diettrack.data.local.AuthUserDao
import com.edu.diettrack.data.mapper.toDomain
import com.edu.diettrack.data.mapper.toEntity
import com.edu.diettrack.data.storage.AuthStorage
import com.edu.diettrack.data.utils.NetworkChecker
import com.edu.diettrack.domain.model.AuthUser
import com.edu.diettrack.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val authUserDao: AuthUserDao,
    private val authStorage: AuthStorage,
    private val networkChecker: NetworkChecker
) : AuthRepository {
    override suspend fun signIn(
        email: String,
        password: String
    ): AuthUser {
        val isConnected = networkChecker.isConnected.first()
        if (!isConnected) {
            val localUser = authUserDao.getUser()
            if (localUser != null && localUser.email == email) {
                return localUser.toDomain()
            }
            throw Exception("Sem conexão com a internet.")
        }

        val result = auth.signInWithEmailAndPassword(email, password).await()
        val uid = result.user?.uid ?: throw Exception("Falha ao fazer login.")

        val document = firestore.collection("users").document(uid).get().await()

        val user = AuthUser(
            uid = uid,
            name = document.getString("name") ?: "",
            email = document.getString("email") ?: "",
            photoUrl = document.getString("photoUrl") ?: ""
        )

        authUserDao.saveUser(user.toEntity())

        authStorage.saveUid(uid)

        return user
    }

    override suspend fun signUp(
        email: String,
        password: String
    ): AuthUser? {
        val isConnected = networkChecker.isConnected.first()
        if (!isConnected) {
            throw Exception("Sem conexão com a internet.")
        }

        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val uid = result.user?.uid ?: throw Exception("Falha ao fazer login.")

        val document = firestore.collection("users").document(uid).get().await()

        if (document.exists()) {
            val user = AuthUser(
                uid = uid,
                name = document.getString("name") ?: "",
                email = document.getString("email") ?: "",
                photoUrl = document.getString("photoUrl") ?: ""
            )

            authUserDao.saveUser(user.toEntity())

            authStorage.saveUid(uid)

            return user
        }

        return null
    }

    override suspend fun getCurrentUser(): AuthUser? {
        val uid = authStorage.uidFlow.first()

        val local = authUserDao.getUser()
        if (local != null) return local.toDomain()

        val isConnected = networkChecker.isConnected.first()
        if (isConnected && uid != null) {
            val doc = firestore.collection("users").document(uid).get().await()
            if (doc.exists()) {
                val user = AuthUser(
                    uid = uid,
                    name = doc.getString("name") ?: "",
                    email = doc.getString("email") ?: "",
                    photoUrl = doc.getString("photoUrl") ?: ""
                )
                authUserDao.saveUser(user.toEntity())
                return user
            }
        }
        return null
    }

    override suspend fun signOut() {
        auth.signOut()
        authUserDao.clear()
        authStorage.clearUid()
    }


}