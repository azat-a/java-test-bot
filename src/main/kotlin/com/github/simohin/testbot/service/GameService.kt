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
                    import java.util.*;
                    
                    class main {
                    
                        //Напишите алгоритм поиска в массиве nums индексов элементов, 
                        //которые в сумме равны target
                        private static int[] twoSum(int[] nums, int target) {
                            return null;
                        }
                    
                        public static void main(String... args) {
                            assertArrays(new int[]{0, 1}, twoSum(new int[]{2, 7, 11, 15}, 9));
                            assertArrays(new int[]{0, 1}, twoSum(new int[]{3, 2, 4}, 6));
                            assertArrays(new int[]{0, 1}, twoSum(new int[]{3, 3}, 6));
                        }
                    
                        private static void assertArrays(int[] expected, int[] actual) {
                            if (expected.length != actual.length) {
                                throw new RuntimeException("Result array has wrong length");
                            }
                            for (int i = 0; i < expected.length; i++) {
                                if (expected[i] != actual[i]) {
                                    throw new RuntimeException("Result array is wrong: " + i +
                                            " element expected to be " + expected[i] +
                                            " but was " + actual[i]);
                                }
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
