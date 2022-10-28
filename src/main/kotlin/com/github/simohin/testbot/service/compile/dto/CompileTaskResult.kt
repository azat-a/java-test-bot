package com.github.simohin.testbot.service.compile.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CompileTaskResult(
    @field:JsonProperty("make_ret")
    val makeRet: Int? = null,
    @field:JsonProperty("make_log")
    val makeLog: String? = null,
    @field:JsonProperty("exec_ret")
    val execRet: Int? = null,
    @field:JsonProperty("exec_log")
    val execLog: String? = null
) {
    fun isCompileSuccess() = 0 == makeRet
    fun isExecutionSuccess() = 0 == execRet
}
