package com.github.simohin.testbot.service

import com.github.simohin.testbot.entity.GameEntity
import com.github.simohin.testbot.entity.UserResultId
import com.github.simohin.testbot.model.Game
import com.github.simohin.testbot.repository.GameRepository
import com.github.simohin.testbot.repository.UserResultRepository
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class GameService(
    val gameRepository: GameRepository,
    val userResultRepository: UserResultRepository
) {

    @PostConstruct
    fun init() {
        gameRepository.findByTemplateName("coding")
            .orElse(gameRepository.save(GameEntity("coding")))
    }

    fun find(userId: Long, gameShortName: String): Game {
        val gameOptional = gameRepository.findByTemplateName(gameShortName)
        if (gameOptional.isEmpty) {
            throw NoSuchElementException("Game $gameShortName not found")
        }

        val gameEntity = gameOptional.get()
        val game = Game(gameEntity.templateName)

        userResultRepository.findById(UserResultId(userId, gameEntity.id!!))
            .ifPresent {
                game.passed = it.success
            }

        return game
    }
}
