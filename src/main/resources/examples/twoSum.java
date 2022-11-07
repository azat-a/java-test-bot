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

class Solution {

    //Напишите алгоритм поиска в массиве nums индексов элементов,
    //которые в сумме равны target
    public static int[] twoSum(int[] nums, int target) {
        var holder = new HashMap<Integer, Integer>();

        for (int i = 0; i < nums.length; i++) {
            var current = nums[i];
            var holded = holder.get(target - current);
            if (holded == null) {
                holder.put(current, i);
                continue;
            }
            return new int[]{holded, i};
        }
        return new int[]{};

    }

}
