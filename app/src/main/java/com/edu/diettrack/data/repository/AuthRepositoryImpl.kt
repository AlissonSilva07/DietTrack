package com.edu.diettrack.data.repository

import android.util.Log
import com.edu.diettrack.data.local.AuthUserDao
import com.edu.diettrack.data.mapper.toDomain
import com.edu.diettrack.data.mapper.toEntity
import com.edu.diettrack.data.storage.UserStorage
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
    private val userStorage: UserStorage,
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
                    return Resource.Success(null)
                }

                return Resource.Error("Sem internet e nenhum usuário local encontrado")
            }

            val result = auth
                .signInWithEmailAndPassword(email, password)
                .await()

            val user = result.user ?: return Resource.Error("Credenciais inválidas.")

            authUserDao.saveUser(user.toEntity())
            userStorage.saveUid(user.uid)

            val hasFinished = fetchOnboardingStatus(user.uid)
            userStorage.setOnboarded(hasFinished)

            Resource.Success(user)

        } catch (e: Exception) {
            Resource.Error("Algo inesperado aconteceu! Tente novamente mais tarde.", e)
        }
    }


    override suspend fun signUp(
        email: String,
        password: String
    ): Resource<FirebaseUser?> {
        return try {
            val isConnected = networkChecker.isConnected.first()

            if (!isConnected) {
                val localUser = authUserDao.getUser()

                if (localUser != null && localUser.email == email) {
                    return Resource.Success(null)
                }

                return Resource.Error("Sem internet e nenhum usuário local encontrado")
            }

            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user ?: return Resource.Error("Erro ao criar usuário")

            val userSettingsDoc = mapOf(
                "has_finished_onboarding" to false,
                "water_goal" to null,
                "weight_goal" to null
            )

            firestore.collection("user_settings")
                .document(user.uid)
                .set(userSettingsDoc)
                .await()

            return Resource.Success(result.user)
        } catch (e: Exception) {
            Resource.Error("Algo inesperado aconteceu:", e)
        }

    }

    override suspend fun getCurrentUser(): AuthUser? {
        val uid = userStorage.uidFlow.first()

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
        userStorage.clearUid()
    }

    override suspend fun fetchOnboardingStatus(uid: String): Boolean {
        return try {
            val doc = firestore.collection("user_settings")
                .document(uid)
                .get()
                .await()

            doc.getBoolean("has_finished_onboarding") ?: false
        } catch (e: Exception) {
            false
        }
    }
}