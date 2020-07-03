import java.util.ArrayList;
import java.util.List;

public class CutArray {
    public static int numberForCut = 4;

    public static int[] cutArray(int[] arr) {
        boolean isNumber = false;
        ArrayList<Integer> arrayList = new ArrayList<>();
//        arrayList = Arrays.asList(arr);
        for (int i = 0; i < arr.length; i++) {
            if (isNumber) {
                arrayList.add(arr[i]);
            }
            if (arr[i] == numberForCut) {
                isNumber = true;
            }
        }
        if (!isNumber) {
            throw new RuntimeException("exception");
        }
        int[] arr2 = new int[arrayList.size()];
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = arrayList.get(i);
        }
        return arr2;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{
                1,
                2,
                3,
                4,
                5,
                6
        };
        int[] arr2 = cutArray(arr);
        for (int i = 0; i < arr2.length; i++) {
            System.out.println(arr2[i]);
        }
    }
}
