package com.github.simohin.testbot.model

import org.telegram.telegrambots.meta.api.objects.Message

enum class Command(
    val text: String
) {
    GET_GAME("/get_game");
}

fun Message?.getCommand() =
    if (this != null && this.isCommand) Command.values().first { it.text == text }
    else null
