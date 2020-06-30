import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        //task 1

        ArrayList<Integer> arrList1 = new ArrayList<>();
        Integer[] arr = new Integer[10];
        ArrayList<Integer> arrList2;
        for (int i = 0; i < 10; i++) {
            arrList1.add(i);
            arr[i] = i;
        }
        System.out.println("Начальный список :" + arrList1);
        changeTwoElementArr(arrList1, 0, 4);
        System.out.println("Список после перемещения элементов: " + arrList1);

        //task 2

        arrList2 = convertToArrList(arr);
        System.out.println("Список из массива: " + arrList2);



        //task3
        Box<Apple> box1 = new Box<>();
        Box<Apple> box2 = new Box<>();
        Box<Orange> box3 = new Box<>();
        for (int i = 0; i < 10; i++) {
            box1.addFruit(new Apple());
            box2.addFruit(new Apple());
            box3.addFruit(new Orange());
        }
        System.out.println("Размеры коробок:");
        System.out.println(box1.getWeight());
        System.out.println(box2.getWeight());
        System.out.println(box3.getWeight());
        System.out.println("Равны ли коробки с яблоками (с учетом погрешности):");
        System.out.println(box1.compare(box2));
        System.out.println("Равны ли коробки с яблоками и апельсинами " +
                "(с учетом погрешности):");
        System.out.println(box1.compare(box3));
        box1.moveFruit(box1);
        box1.moveFruit(box2);
        System.out.println("Масса коробок после перемещения:");
        System.out.println(box1.getWeight());
        System.out.println(box2.getWeight());
        // box1.moveFruit(box3); // выдает ошибку

    }







    private static void changeTwoElementArr(ArrayList<Integer> arr, int elem1, int elem2){
        int temp = arr.get(elem2);
        arr.set(elem2, arr.get(elem1));
        arr.set(elem1, temp);
    }

    private static <T> ArrayList<T> convertToArrList(T[] arr) {
        return new ArrayList<>(Arrays.asList(arr));
    }

}
