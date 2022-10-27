package com.github.simohin.testbot.config

import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.generics.LongPollingBot
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import java.util.logging.Logger
import javax.annotation.PostConstruct

@Configuration
class TelegramBotConfiguration(
    private val longPollingBots: List<LongPollingBot>
) {

    companion object {
        private val log = Logger.getLogger(TelegramBotConfiguration::class.simpleName)
        private val botsApi = TelegramBotsApi(DefaultBotSession::class.java)
    }

    @PostConstruct
    fun init() =
        longPollingBots.onEach {
            log.info("Registering ${it.javaClass.simpleName}")
        }.forEach(botsApi::registerBot)
}
