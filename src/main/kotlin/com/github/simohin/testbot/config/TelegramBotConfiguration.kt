package com.github.simohin.testbot.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Configuration
class TelegramBotConfiguration {

    @Bean
    fun botApi() = TelegramBotsApi(DefaultBotSession::class.java)
}
