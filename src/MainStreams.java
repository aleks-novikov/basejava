import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3, 3, 2, 3};
        int[] arr2 = {9, 8};

        System.out.println("Сумма уникальных чисел первого массива равна " + minValue(arr1));
        System.out.println("Сумма уникальных чисел второго массива равна " + minValue(arr2) + "\n");

        List<Integer> list = Arrays.stream(arr1).boxed().collect(Collectors.toList());
        System.out.println("Новый массив чисел: " + oddOrEven(list));
    }

    private static int minValue(int[] values) {
        List<Integer> list = Arrays.stream(values)
                .boxed().distinct().sorted()
                .collect(Collectors.toList());

        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i) * Math.pow(10, list.size() - i - 1);
        }
        return sum;
    }

    private static List<Integer> oddOrEven(List<Integer> list) {
        int sum = list.stream().reduce((v1, v2) -> v1 + v2).get();
        System.out.println("Сумма всех чисел массива равна " + sum);
        return transformList(list, sum % 2 == 0);
    }

    private static List<Integer> transformList(List<Integer> list, boolean sumIsEven) {
        return list.stream().filter(num -> sumIsEven == (num % 2 != 0)).collect(Collectors.toList());
    }
}