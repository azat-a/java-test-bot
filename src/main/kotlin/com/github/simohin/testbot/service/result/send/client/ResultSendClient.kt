package com.github.simohin.testbot.service.result.send.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(value = "resultSend", url = "\${result.send.client.url}")
interface ResultSendClient {

    @GetMapping("/external")
    fun send(@RequestParam("event") gameShortName: String, @RequestParam("user_id") userId: Long)
}
