package com.edu.diettrack.data.mapper

import com.edu.diettrack.data.local.AuthUserEntity
import com.edu.diettrack.domain.model.AuthUser
import com.google.firebase.auth.FirebaseUser

fun AuthUser.toEntity() = AuthUserEntity(
    uid = uid,
    name = name,
    email = email,
)

fun AuthUserEntity.toDomain() = AuthUser(
    uid = uid,
    name = name,
    email = email,
)

fun FirebaseUser.toEntity() = AuthUserEntity(
    uid = uid,
    name = displayName ?: "",
    email = email ?: ""
)
