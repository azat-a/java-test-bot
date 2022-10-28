package com.github.simohin.testbot.model

data class ModeInfo(
    val name: String? = null,
    val mode: String? = null,
    val mimes: List<String>? = null,
    val mime: String? = null,
    val ext: List<String>? = null,
    val alias: List<String>? = null
)
