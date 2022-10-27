package com.github.simohin.testbot.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class GameEntity(
    val templateName: String,
    @Id
    @GeneratedValue
    val id: Long? = null
)
