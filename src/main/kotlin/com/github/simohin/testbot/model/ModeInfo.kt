package com.github.simohin.testbot.model

data class ModeInfo(
    val name: String,
    val mode: String,
    val mimes: List<String>,
    val mime: String,
    val ext: List<String>,
    val alias: List<String>
)
