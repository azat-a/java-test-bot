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

interface Filter {
    boolean apply(Object o);
}

class Solution {

    //    Реализуйте метод, который возвращает отфильтрованный массив объектов
    //    с применением реализации интерфейса Filter
    public static Object[] filter(Object[] values, Filter filter) {
        int offset = 0;

        for(int i = 0; i< values.length; i++){
            if(!filter.apply(values[i])){
                offset++;
            } else{
                values[i - offset] = values[i];
            }
        }

        // Arrays.copyOf копирует значение из массива values в новый массив
        // с длинной values.length - offset
        return Arrays.copyOf(values, values.length - offset);
    }

    //    Реализуйте метод, который возвращает имплементацию интерфейса Filter
    public static Filter getFilter() {
        return Objects::nonNull;
    }
}
