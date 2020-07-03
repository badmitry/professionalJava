public class CheckArrOnOneFore {
    public static boolean checkArr(int[] arr) {
        boolean isOne = false;
        boolean isFour = false;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 1) {
                isOne = true;
            }
            if (arr[i] == 4) {
                isFour = true;
            }
        }
        if(isFour && isOne) {
            return true;
        }else{
            return false;
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            if (Math.random() < 0.5) {
                arr[i] = 1;
            } else {
                arr[i] = 4;
            }
        }

    }
}
