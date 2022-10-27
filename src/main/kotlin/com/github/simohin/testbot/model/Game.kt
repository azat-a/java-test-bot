package com.github.simohin.testbot.model

data class Game(
    val taskTemplate: String,
    var passed: Boolean? = false
)

fun Game.toMap() = mapOf(
    "isPassed" to passed,
    "taskTemplate" to taskTemplate
)
