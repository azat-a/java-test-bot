package com.github.simohin.testbot.model

data class Game(
    val taskTemplate: String,
    val codeTemplate: String,
    var passed: Boolean? = false
)

fun Game.toMap(): MutableMap<String, Any?> = mutableMapOf(
    "isPassed" to passed,
    "taskTemplate" to taskTemplate
)
