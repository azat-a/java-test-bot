package com.github.simohin.testbot.service.telegram

import com.github.simohin.testbot.model.Command
import com.github.simohin.testbot.model.getCommand
import com.github.simohin.testbot.repository.GameRepository
import com.github.simohin.testbot.repository.UserResultRepository
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendGame
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

@Service
class TelegramBot(
    private val gameRepository: GameRepository,
    private val userResultRepository: UserResultRepository
) : TelegramLongPollingBotService() {

    override fun onUpdateReceived(update: Update) {
        val message = update.message
        if (update.hasMessage() && message.isCommand) {
            when (message.getCommand()) {
                Command.GET_GAME -> getGame(message.from.id, message.chat.id.toString())
                else -> {}
            }
        }
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
