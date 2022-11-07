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

class Solution {

    //Постройте алгоритм, который возвращает частотный словарь букв, отсортированный по ключам
    //Например: привет - {в,1;е,1;и,1;п,1;р,1;т,1}
    public static Map<Character, Integer> frequencyMap(String text) {
        text = text.toLowerCase();

        Map<Character, Integer> map = new HashMap<>();
        for(int i = 0; i < text.length(); i++){
            char ch = text.charAt(i);

            // ё идёт отдельно от а-я
            if((ch >= 'а' && ch <= 'я') || ch == 'ё'){
                map.compute(ch, (character, integer)
                        -> integer == null ? 1 : integer + 1);
            }
        }

        ArrayList<Map.Entry<Character, Integer>> entries =
                new ArrayList<>(map.entrySet());
        entries.sort((o1, o2) -> Character.compare(o1.getKey(), o2.getKey()));
        return entries.stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
