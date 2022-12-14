package com.github.simohin.testbot.service.compile

import com.github.simohin.testbot.service.compile.client.OnlineCompileClient
import com.github.simohin.testbot.service.compile.dto.CompileTask
import com.github.simohin.testbot.service.compile.dto.CompileTaskBuffers
import org.springframework.stereotype.Service

@Service
class OnlineCompileService(
    private val client: OnlineCompileClient
) {

    fun checkCode(code: String, args: List<String>? = emptyList()) =
        client.compile(CompileTask(CompileTaskBuffers(code), args!!))
}
