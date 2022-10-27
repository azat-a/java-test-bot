package config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.generics.LongPollingBot
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import java.util.logging.Logger

@Configuration
class TelegramBotConfiguration(
    private val longPollingBots: List<LongPollingBot>
) {

    companion object {
        private val log = Logger.getLogger(TelegramBotConfiguration::class.simpleName)
    }

    init {
        log.info("Registering ${longPollingBots.size} bots")
    }

    @Bean
    fun botApi() = TelegramBotsApi(DefaultBotSession::class.java).apply {
        longPollingBots.forEach(this::registerBot)
    }
}
