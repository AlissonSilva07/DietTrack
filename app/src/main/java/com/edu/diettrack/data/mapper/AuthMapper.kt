package com.edu.diettrack.data.mapper

import com.edu.diettrack.data.local.AuthUserEntity
import com.edu.diettrack.domain.model.AuthUser

fun AuthUserEntity.toDomain() = AuthUser(
    uid = uid,
    name = name,
    email = email,
    photoUrl = photoUrl
)

fun AuthUser.toEntity() = AuthUserEntity(
    uid = uid,
    name = name,
    email = email,
    photoUrl = photoUrl
)
