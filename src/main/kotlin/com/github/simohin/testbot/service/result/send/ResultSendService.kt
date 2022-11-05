package com.github.simohin.testbot.service.result.send

import com.github.simohin.testbot.service.result.send.client.ResultSendClient
import org.springframework.stereotype.Service

@Service
class ResultSendService(
    private val resultSendClient: ResultSendClient
) {

    fun send(gameShortName: String, userId: Long) = try {
        resultSendClient.send(gameShortName, userId)
        true
    } catch (e: Exception) {
        false
    }
}
