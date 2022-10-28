package com.github.simohin.testbot.service.compile.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CompileTask(
    val buffers: CompileTaskBuffers,
    val cliargs: List<String> = emptyList(),
    val language: String = "java"
)

data class CompileTaskBuffers(
    @field:JsonProperty("main.java")
    val mainJava: String
)
