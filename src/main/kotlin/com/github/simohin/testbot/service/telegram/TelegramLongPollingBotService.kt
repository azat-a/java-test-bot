package com.github.simohin.testbot.service.telegram

import com.github.simohin.testbot.config.TelegramBotProperties
import org.springframework.beans.factory.annotation.Autowired
import org.telegram.telegrambots.bots.TelegramLongPollingBot

abstract class TelegramLongPollingBotService : TelegramLongPollingBot() {

    @Autowired
    protected lateinit var properties: TelegramBotProperties

    override fun getBotToken() = properties.token

    override fun getBotUsername() = properties.userName
}
