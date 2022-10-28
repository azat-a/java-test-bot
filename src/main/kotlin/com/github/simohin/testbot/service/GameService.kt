package com.github.simohin.testbot.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.simohin.testbot.entity.GameEntity
import com.github.simohin.testbot.entity.UserResultEntity
import com.github.simohin.testbot.entity.UserResultId
import com.github.simohin.testbot.model.Game
import com.github.simohin.testbot.model.Snippet
import com.github.simohin.testbot.model.Task
import com.github.simohin.testbot.repository.GameRepository
import com.github.simohin.testbot.repository.UserResultRepository
import org.springframework.stereotype.Service
import java.io.File
import javax.annotation.PostConstruct

@Service
class GameService(
    private val gameRepository: GameRepository,
    private val userResultRepository: UserResultRepository,
    private val mapper: ObjectMapper
) {

    @PostConstruct
    fun init() = javaClass.getResource("/tasks")?.toURI()?.let {
        File(it).listFiles()!!
            .map { file -> mapper.readValue(file, Task::class.java) }
            .map { task -> GameEntity(task.template!!.trimIndent(), task.name!!) }
            .forEach(gameRepository::save)
    }

    fun find(userId: Long, gameShortName: String): Game {
        val gameOptional = gameRepository.findByTemplateName(gameShortName)
        if (gameOptional.isEmpty) {
            throw NoSuchElementException("Game $gameShortName not found")
        }

        val gameEntity = gameOptional.get()
        val game = Game(
            gameEntity.templateName,
            gameEntity.codeTemplate
        )

        userResultRepository.findById(UserResultId(userId, gameEntity.id!!))
            .ifPresent {
                game.passed = it.success
            }

        return game
    }

    fun saveResult(userId: Long, userName: String, gameShortName: String, snippet: Snippet) {
        val gameEntityOptional = gameRepository.findByTemplateName(gameShortName)

        if (gameEntityOptional.isEmpty) {
            throw NoSuchElementException("Game $gameShortName not found")
        }

        userResultRepository.save(
            UserResultEntity(
                userId,
                gameEntityOptional.get().id!!,
                true,
                snippet.code!!,
                userName
            )
        )
    }
}
