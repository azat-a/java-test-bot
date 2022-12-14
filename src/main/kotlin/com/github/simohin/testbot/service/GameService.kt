package com.github.simohin.testbot.service

import com.github.simohin.testbot.entity.GameEntity
import com.github.simohin.testbot.entity.UserResultEntity
import com.github.simohin.testbot.entity.UserResultId
import com.github.simohin.testbot.model.Game
import com.github.simohin.testbot.model.Snippet
import com.github.simohin.testbot.model.Task
import com.github.simohin.testbot.repository.GameRepository
import com.github.simohin.testbot.repository.UserResultRepository
import com.github.simohin.testbot.service.result.send.ResultSendService
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.logging.Logger
import javax.annotation.PostConstruct

@Service
class GameService(
    private val gameRepository: GameRepository,
    private val userResultRepository: UserResultRepository,
    private val resultSendService: ResultSendService
) {

    companion object {
        private val log = Logger.getLogger(GameService::class.simpleName)

        private val tasks = listOf(
            Task(
                "twoSum",
                """
                    class Solution {
                    
                        //Напишите алгоритм поиска в массиве nums индексов элементов,
                        //которые в сумме равны target
                        public static int[] twoSum(int[] nums, int target) {
                            return new int[]{};
                        }
                    
                    }
                """.trimIndent(),
                """
                    import java.util.*;

                    class main {

                        public static void main(String... args) {
                            assertArrays(new int[]{0, 1}, Solution.twoSum(new int[]{2, 7, 11, 15}, 9));
                            assertArrays(new int[]{1, 2}, Solution.twoSum(new int[]{3, 2, 4}, 6));
                            assertArrays(new int[]{0, 1}, Solution.twoSum(new int[]{3, 3}, 6));
                        }

                        private static void assertArrays(int[] expected, int[] actual) {
                            Arrays.sort(expected);
                            Arrays.sort(actual);
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
            ),
            Task(
                "frequencyMap",
                """
                    class Solution {

                        //Постройте алгоритм, который возвращает частотный словарь букв, отсортированный по ключам
                        //Например: привет - {в,1;е,1;и,1;п,1;р,1;т,1}
                        public static Map<Character, Integer> frequencyMap(String text) {
                            return Collections.emptyMap();
                        }

                    }
                """.trimIndent(),
                """
                    import java.util.*;
                    import java.util.stream.*;

                    class main {

                        public static void main(String... args) {
                            boolean equals = Map.of('в', 1, 'е', 1, 'и', 1, 'п', 1, 'р', 1, 'т', 1)
                                    .equals(Solution.frequencyMap("привет"));
                            if (!equals) {
                                throw new RuntimeException("Unexpected result");
                            }
                        }

                    }
                """.trimIndent()
            ),
            Task(
                "exceptions",
                """
                    class Solution {
                    
                        //Какая проблема возникнет с этим кодом? Исправьте код и напишите комментарий с коротким объяснением
                        public static void handleExceptions() {
                            try {
                                foo();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    
                        private static void foo() throws IOException,FileNotFoundException {
                    
                        }
                    
                    } 
                """.trimIndent(),
                """
                    import java.io.*;
                    
                    class main {
                    
                        public static void main(String... args) {
                            Solution.handleExceptions();
                        }
                    
                    }
                """.trimIndent()
            ),
            Task(
                "exchanger",
                """
                    //Реализуйте ниже интерфейс Exchanger
                    interface Exchanger {

                        //Задаёт пару валют и отношение между ними
                        void setCurrencyPair(String currentCurrency, String targetCurrency, float quotientCurrentByTarget);

                        //задаёт комиссию в %
                        void setCommissionInPercent(float commissionInPercent);


                        //Производит обмен с учетом отношения пары валют и комиссии. В случае отсутствия пары в памяти или комиссии (выдает RuntimeException)
                        float makeAnExchange(String currentCurrency, String targetCurrency, float amountOfCurrent);
                    }

                    class ExchangerImpl implements Exchanger {

                        @Override
                        public void setCurrencyPair(String currentCurrency, String targetCurrency, float quotientCurrentByTarget) {
                        }

                        @Override
                        public void setCommissionInPercent(float commissionInPercent) {
                        }

                        @Override
                        public float makeAnExchange(String currentCurrency, String targetCurrency, float amountOfCurrent) {
                        }
                    }
                """.trimIndent(),
                """
                    import java.util.*;

                    class main {

                        public static void main(String... args) {
                            var exchanger = (Exchanger) new ExchangerImpl();
                            exchanger.setCurrencyPair("USD", "EUR", 1.1F);
                            exchanger.setCommissionInPercent(2.5F);
                            float result = exchanger.makeAnExchange("USD", "EUR", 300);
                            double expected = 321.75;
                            if (result != expected) {
                                throw new RuntimeException("Result is wrong: expected to be " + expected +
                                        " but was " + result);
                            }
                        }

                    }
                """.trimIndent()
            ),
            Task(
                "filter",
                """
                    interface Filter {
                        boolean apply(Object o);
                    }
                    
                    class Solution {
                    
                        //    Реализуйте метод, который возвращает отфильтрованный массив объектов
                        //    с применением реализации интерфейса Filter
                        public static Object[] filter(Object[] values, Filter filter) {
                            return null;
                        }
                    
                        //    Реализуйте метод, который возвращает имплементацию интерфейса Filter
                        public static Filter getFilter() {
                            return null;
                        }
                    }
                """.trimIndent(),
                """
                    import java.util.*;
                    
                    class main {
                    
                        public static void main(String... args) {
                            String[] array = new String[]{"1rewf ", "feefewf", "a", null, "1"};
                    
                            String[] newArray = (String[]) Solution.filter(array, Solution.getFilter());
                    
                            if (!Arrays.equals(new String[]{"1rewf ", "feefewf", "a", "1"}, newArray)) {
                                throw new RuntimeException("Unexpected result");
                            }
                        }
                    
                    }
                """.trimIndent()
            )
        )
    }

    @PostConstruct
    fun init() = tasks
        .map { task -> GameEntity(task.execution!!, task.template!!, task.name!!) }
        .forEach(gameRepository::save)

    fun find(userId: Long, gameShortName: String): Game {
        val gameOptional = gameRepository.findByTemplateName(gameShortName)
        if (gameOptional.isEmpty) {
            throw NoSuchElementException("Game $gameShortName not found")
        }

        val gameEntity = gameOptional.get()
        val game = Game(
            gameEntity.templateName,
            gameEntity.codeTemplate,
            gameEntity.codeExecution
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
                userName,
                snippet.code!!
            )
        )
    }

    @Scheduled(fixedDelayString = "\${result.send.schedule.delay}")
    fun sendResults() {
        val found = userResultRepository.getBySent(false)

        log.info("Found ${found.size} user results for update")
        val updated = found.filter {
            val gameEntityOptional = gameRepository.findById(it.gameId)
            if (gameEntityOptional.isEmpty) return@filter false

            val gameEntity = gameEntityOptional.get()

            return@filter resultSendService.send(gameEntity.templateName, it.userId)
        }
            .map {
                it.sent = true
                it
            }
        log.info("Updated ${updated.size} user results")
        userResultRepository.saveAll(updated)
    }
}
