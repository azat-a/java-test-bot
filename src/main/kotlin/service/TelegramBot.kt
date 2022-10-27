package service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import java.util.logging.Logger

@Service
class TelegramBot(
    @Value("\${telegram.bot.username}")
    private val username: String,
    @Value("\${telegram.bot.token}")
    private val token: String
) : TelegramLongPollingBot() {

    companion object {
        private val log: Logger = Logger.getLogger(TelegramLongPollingBot::class.simpleName)
    }

    override fun getBotToken() = token

    override fun getBotUsername() = username

    override fun onUpdateReceived(update: Update) = log.info(update.toString())
}
