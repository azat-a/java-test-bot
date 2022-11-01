class main {

    public static void main(String... args) {
        String[] array = new String[]{"1rewf ", "feefewf", "a", null, "1"};

        String[] newArray = (String[]) Solution.filter(array, Solution.getFilter());

        if (!array.equals(newArray)) {
            throw new RuntimeException("Unexpected result")
        }
    }

}

interface Filter {
    boolean apply(Object o);
}

class Solution {

//    Реализуйте метод, который возвращает отфильтрованный массив объектов
//    с применением реализации интерфейса Filter
    public static Object[] filter(Object[] values, Filter) {
        return null;
    }

//    Реализуйте метод, который возвращает имплементацию интерфейса Filter
    public static Filter getFilter() {
        return null;
    }
}
