package com.github.simohin.testbot.service.telegram

import com.github.simohin.testbot.model.toMap
import com.github.simohin.testbot.service.GameService
import org.springframework.stereotype.Service
import org.springframework.web.util.DefaultUriBuilderFactory
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.objects.Update

@Service
class SendGameService(
    private val gameService: GameService
) : TelegramLongPollingBotService() {

    private fun Update.toAnswerCallbackQuery(params: Map<String, Any?>) =
        AnswerCallbackQuery(this.callbackQuery.id).apply {
            url = DefaultUriBuilderFactory()
                .uriString("https://simohin-test-bot.herokuapp.com/game")
                .build(params)
                .toString()
        }

    override fun onUpdateReceived(update: Update) {
        if (update.hasCallbackQuery()) {
            val find = gameService.find(update.callbackQuery.from.id, update.callbackQuery.gameShortName)
            execute(update.toAnswerCallbackQuery(find.toMap()))
        }
    }
}
