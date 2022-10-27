package com.github.simohin.testbot.service.telegram

import com.github.simohin.testbot.config.TelegramBotProperties
import com.github.simohin.testbot.model.Command
import com.github.simohin.testbot.model.getCommand
import com.github.simohin.testbot.model.toMap
import com.github.simohin.testbot.repository.GameRepository
import com.github.simohin.testbot.repository.UserResultRepository
import com.github.simohin.testbot.service.GameService
import org.springframework.stereotype.Service
import org.springframework.web.util.DefaultUriBuilderFactory
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.methods.send.SendGame
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

@Service
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
            val find = gameService.find(update.callbackQuery.from.id, update.callbackQuery.gameShortName)
            execute(update.toAnswerCallbackQuery(find.toMap()))
        }
    }

    private fun Update.toAnswerCallbackQuery(params: Map<String, Any?>) =
        AnswerCallbackQuery(this.callbackQuery.id).apply {
            url = DefaultUriBuilderFactory()
                .uriString("https://simohin-test-bot.herokuapp.com/game")
                .build(params)
                .toString()
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
