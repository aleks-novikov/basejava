import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        int[] arr1 = {1, 9, 8, 5, 3, 2, 3};
        int[] arr2 = {9, 8};

        System.out.println("Сумма уникальных чисел первого массива равна " + minValue(arr1));
        System.out.println("Сумма уникальных чисел второго массива равна " + minValue(arr2) + "\n");

        List<Integer> list = Arrays.stream(arr1).boxed().collect(Collectors.toList());
        System.out.println("Новый массив чисел: " + oddOrEven(list));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (v1, v2) -> Integer.parseInt(v1 + "" + v2));
    }

    private static List<Integer> oddOrEven(List<Integer> list){
        int sum = list.stream().reduce(0, (v1, v2) -> v1 + v2);
        System.out.println("Сумма всех чисел массива равна " + sum);
        return list.stream().filter(num -> (sum % 2 != num % 2)).collect(Collectors.toList());
    }
}