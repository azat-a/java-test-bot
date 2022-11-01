package com.github.simohin.testbot.service.compile

import com.github.simohin.testbot.service.GameService
import com.github.simohin.testbot.service.compile.client.OnlineCompileClient
import com.github.simohin.testbot.service.compile.dto.CompileTask
import com.github.simohin.testbot.service.compile.dto.CompileTaskBuffers
import org.springframework.stereotype.Service

@Service
class OnlineCompileService(
    private val client: OnlineCompileClient,
    private val gameService: GameService
) {

    fun checkCode(code: String) = client.compile(CompileTask(CompileTaskBuffers(code)))
}
