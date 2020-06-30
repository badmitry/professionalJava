public class ABC {
    private static String letter = "A";

    public static void main(String[] args) {
        Object mon = new Object();
        Thread tA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (mon){
                    for (int i = 0; i < 5; i++) {
                        while (letter != "A") {
                            try {
                                mon.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.print(letter);
                        letter = "B";
                        mon.notifyAll();
                    }
                }
            }
        });

        Thread tB = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (mon){
                    for (int i = 0; i < 5; i++) {
                        while (letter != "B") {
                            try {
                                mon.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.print(letter);
                        letter = "C";
                        mon.notifyAll();
                    }
                }
            }
        });

        Thread tC = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (mon){
                    for (int i = 0; i < 5; i++) {
                        while (letter != "C") {
                            try {
                                mon.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.print(letter);
                        letter = "A";
                        mon.notifyAll();
                    }

                }
            }
        });
//        for (int i = 0; i < 3; i++) {
        tA.start();
        tB.start();
        tC.start();
        try {
            tA.join();
            tB.join();
            tC.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        }




    }
}
