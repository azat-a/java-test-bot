package com.github.simohin.testbot.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.methods.send.SendGame
import org.telegram.telegrambots.meta.api.objects.Update

@Service
class TelegramBot(
    @Value("\${telegram.bot.username}")
    private val username: String,
    @Value("\${telegram.bot.token}")
    private val token: String
) : TelegramLongPollingBot() {

    override fun getBotToken() = token

    override fun getBotUsername() = username

    override fun onUpdateReceived(update: Update) {
        val gameShortName = "coding"

        if (update.hasMessage()) {
            execute(update.toSendGame(gameShortName))
        }

        if (update.hasCallbackQuery()) {
            execute(update.toAnswerCallbackQuery(gameShortName))
        }
    }

    fun Update.toSendGame(gameShortName: String) = SendGame(
        this.message.chat.id.toString(),
        gameShortName
    )

    fun Update.toAnswerCallbackQuery(gameShortName: String) = AnswerCallbackQuery(this.callbackQuery.id).apply {
        url = "https://simohin-test-bot.herokuapp.com/game?gameShortName=$gameShortName"
    }
}
