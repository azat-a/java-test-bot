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
            getParams(user.id, user.userName, update.callbackQuery.gameShortName)
            execute(update.toAnswerCallbackQuery(user.id, user.userName, update.callbackQuery.gameShortName))
        }
    }

    fun buildUrl(userId: Long, userName: String, gameShortName: String): String {
        val params = getParams(userId, userName, gameShortName)
        var result = botProperties.baseUrl
        if (params.isNotEmpty()) {
            params.map { (k, v) -> "$k=$v" }.joinToString("&").let { result = "$result?$it" }
        }
        return result
    }

    private fun getParams(userId: Long, userName: String, gameShortName: String): MutableMap<String, Any?> {
        val game = gameService.find(userId, gameShortName)
        val params = game.toMap()
        params["userId"] = userId
        params["userName"] = userName
        return params
    }

    private fun Update.toAnswerCallbackQuery(userId: Long, userName: String, gameShortName: String) =
        AnswerCallbackQuery(this.callbackQuery.id).apply {
            url = buildUrl(userId, userName, gameShortName)
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
