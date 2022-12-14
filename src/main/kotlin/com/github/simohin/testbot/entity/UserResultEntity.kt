package com.github.simohin.testbot.entity

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

@Entity
@IdClass(UserResultId::class)
data class UserResultEntity(
    @Id
    val userId: Long,
    @Id
    val gameId: Long,
    val success: Boolean,
    val userName: String,
    @Column(columnDefinition = "TEXT")
    val solution: String,
    var sent: Boolean? = false
)

data class UserResultId(
    val userId: Long? = null,
    val gameId: Long? = null,
) : Serializable
