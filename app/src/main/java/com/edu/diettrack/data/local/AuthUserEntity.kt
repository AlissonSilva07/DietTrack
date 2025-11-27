package com.edu.diettrack.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auth_user")
data class AuthUserEntity(
    @PrimaryKey val uid: String,
    val name: String,
    val email: String,
    val photoUrl: String
)
