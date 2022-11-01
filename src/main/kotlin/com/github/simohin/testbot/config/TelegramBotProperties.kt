package com.github.simohin.testbot.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "telegram.bot")
@Component
class TelegramBotProperties {
    lateinit var token: String
    lateinit var userName: String
    lateinit var baseUrl: String
}
