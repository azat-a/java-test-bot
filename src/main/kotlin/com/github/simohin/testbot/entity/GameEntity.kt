package com.github.simohin.testbot.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class GameEntity(
    @Column(columnDefinition = "TEXT")
    val codeExecution: String,
    @Column(columnDefinition = "TEXT")
    val codeTemplate: String,
    val templateName: String,
    @Id
    @GeneratedValue
    val id: Long? = null
)
