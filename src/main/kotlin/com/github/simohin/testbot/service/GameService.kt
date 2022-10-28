package com.github.simohin.testbot.service

import com.github.simohin.testbot.entity.GameEntity
import com.github.simohin.testbot.entity.UserResultEntity
import com.github.simohin.testbot.entity.UserResultId
import com.github.simohin.testbot.model.Game
import com.github.simohin.testbot.model.Snippet
import com.github.simohin.testbot.model.Task
import com.github.simohin.testbot.repository.GameRepository
import com.github.simohin.testbot.repository.UserResultRepository
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class GameService(
    private val gameRepository: GameRepository,
    private val userResultRepository: UserResultRepository
) {

    companion object {

        private val tasks = listOf(
            Task(
                "twoSum",
                """
                    class Solution {
                    
                        private static int[] twoSum(int[] nums, int target) {
                    //        Напишите алгоритм поиска в массиве nums индексов элементов, 
                    //        которые в сумме равны target
                        }
                    
                        public static void main(String... args) {
                            assertArrays(int[]{0,1}, twoSum(int[]{2,7,11,15}, 9));
                            assertArrays(int[]{0,1}, twoSum(int[]{3,2,4}, 6));
                            assertArrays(int[]{0,1}, twoSum(int[]{3,3}, 6));
                        }
                    
                        private static assertArrays(int[] expected, int[] actual) {
                            for (int i = 0; i < expected.length; i++) {
                                assert expected[i] == actual[i];
                            }
                        }
                    }
                """.trimIndent()
            )
        )
    }

    @PostConstruct
    fun init() = tasks
        .map { task -> GameEntity(task.template!!.trimIndent(), task.name!!) }
        .forEach(gameRepository::save)

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
