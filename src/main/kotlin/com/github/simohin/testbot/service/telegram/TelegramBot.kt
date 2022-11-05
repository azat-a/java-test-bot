package com.github.simohin.testbot.service.telegram

import com.github.simohin.testbot.config.TelegramBotConfiguration
import com.github.simohin.testbot.config.TelegramBotProperties
import com.github.simohin.testbot.model.Command
import com.github.simohin.testbot.model.getCommand
import com.github.simohin.testbot.model.toMap
import com.github.simohin.testbot.repository.GameRepository
import com.github.simohin.testbot.repository.UserResultRepository
import com.github.simohin.testbot.service.GameService
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.methods.send.SendGame
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

@Service
@ConditionalOnBean(TelegramBotConfiguration::class)
class TelegramBot(
    private val botProperties: TelegramBotProperties,
    private val gameService: GameService,
    private val gameRepository: GameRepository,
    private val userResultRepository: UserResultRepository
) : TelegramLongPollingBot() {

    override fun getBotToken() = botProperties.token

    override fun getBotUsername() = botProperties.userName

    override fun onUpdateReceived(update: Update) {
        val message = update.message
        if (update.hasMessage() && message.isCommand) {
            when (message.getCommand()) {
                Command.GET_GAME -> getGame(message.from.id, message.chat.id.toString())
                else -> {}
            }
        }
        if (update.hasCallbackQuery()) {
            val user = update.callbackQuery.from
            val game = gameService.find(user.id, update.callbackQuery.gameShortName)
            val params = game.toMap()
            params["userId"] = user.id
            params["userName"] = user.userName
            execute(update.toAnswerCallbackQuery(params))
        }
    }

    private fun Update.toAnswerCallbackQuery(params: Map<String, Any?>) =
        AnswerCallbackQuery(this.callbackQuery.id).apply {
            var base = botProperties.baseUrl
            if (params.isNotEmpty()) {
                params.map { (k, v) -> "$k=$v" }.joinToString("&").let { base = "$base?$it" }
            }

            url = base
        }

    private fun getGame(userId: Long, chatId: String) {
        val results = userResultRepository.getByUserId(userId)
            .filter { it.success }
            .map { it.gameId }
            .toSet()
        val first = gameRepository.findAll().firstOrNull { !results.contains(it.id!!) }

        if (first == null) {
            execute(SendMessage(chatId, "Вы выполнили все задания!!!"))
            return
        }

        execute(SendGame(chatId, first.templateName))
    }
}
