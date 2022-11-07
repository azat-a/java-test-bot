package com.github.simohin.testbot.controller

import com.github.simohin.testbot.service.telegram.TelegramBot
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/integration")
class IntegrationGameController(
    private val telegramBot: TelegramBot
) {

    @GetMapping("/gameUrl")
    @ResponseBody
    fun get(
        @RequestParam userId: Long,
        @RequestParam userName: String,
        @RequestParam gameShortName: String
    ): String = telegramBot.buildUrl(userId, userName, gameShortName)
}
