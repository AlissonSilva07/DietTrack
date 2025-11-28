package com.edu.diettrack.data.repository

import android.util.Log
import com.edu.diettrack.data.local.AuthUserDao
import com.edu.diettrack.data.mapper.toDomain
import com.edu.diettrack.data.mapper.toEntity
import com.edu.diettrack.data.storage.AuthStorage
import com.edu.diettrack.data.utils.NetworkChecker
import com.edu.diettrack.data.utils.Resource
import com.edu.diettrack.domain.model.AuthUser
import com.edu.diettrack.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val authUserDao: AuthUserDao,
    private val authStorage: AuthStorage,
    private val networkChecker: NetworkChecker
) : AuthRepository {
    override val authState = MutableStateFlow<FirebaseUser?>(null)
    private val repositoryScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        auth.addAuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser

            if (firebaseUser != null) {
                Log.i("AUTH", "Firebase auth listener: user logged in")

                repositoryScope.launch {
                    val local = authUserDao.getUser()

                    if (local != null) {
                        authState.value = firebaseUser
                    }
                }

            } else {
                Log.i("AUTH", "Firebase auth listener: user logged out")

                repositoryScope.launch {
                    authState.value = null
                }
            }
        }
    }


    override suspend fun signIn(
        email: String,
        password: String
    ): Resource<FirebaseUser?> {
        return try {
            val isConnected = networkChecker.isConnected.first()

            if (!isConnected) {
                val localUser = authUserDao.getUser()

                if (localUser != null && localUser.email == email) {
                    Log.i("AUTH", "Offline login success (local user)")
                    return Resource.Success(null)
                }

                return Resource.Error("Sem internet e nenhum usuário local encontrado")
            }

            Log.i("AUTH", "Calling Firebase signInWithEmailAndPassword...")
            val result = auth
                .signInWithEmailAndPassword(email, password)
                .await()

            val user = result.user ?: return Resource.Error("Erro ao fazer login")

            Log.i("AUTH", "Firebase login SUCCESS: ${user.uid}")

            authUserDao.saveUser(user.toEntity())
            authStorage.saveUid(user.uid)

            Resource.Success(user)

        } catch (e: Exception) {
            Log.e("AUTH", "Firebase login ERROR: ${e.message}")
            Resource.Error("Algo inesperado aconteceu: ${e.message}", e)
        }
    }


    override suspend fun signUp(
        email: String,
        password: String
    ): Resource<FirebaseUser?> {
        return try {
            val isConnected = networkChecker.isConnected.first()
            if (!isConnected) {
                return Resource.Error("Sem internet")
            }

            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: throw Exception("Falha ao fazer login.")

            val userRef = firestore.collection("users").document(uid)
            val userData = hashMapOf(
                "name" to result.user?.displayName,
                "email" to result.user?.email
            )
            userRef.set(userData).await()
            return Resource.Success(result.user)
        } catch (e: Exception) {
            Resource.Error("Algo inesperado aconteceu:", e)
        }

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