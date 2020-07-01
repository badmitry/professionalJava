import java.util.concurrent.*;

public class Go {
    public static final int CARS_COUNT = 4;

    public static void main(String[] args) {
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
//                thread.setDaemon(true);
//                try {
//                    thread.join();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                return thread;
            }
        };
        ArrayBlockingQueue <Car> tunnelQueue = new ArrayBlockingQueue<>(CARS_COUNT/2);
        CountDownLatch startTogether = new CountDownLatch(CARS_COUNT);
        CountDownLatch countDownLatch = new CountDownLatch(CARS_COUNT);
        ExecutorService executorService = Executors.newFixedThreadPool(CARS_COUNT, threadFactory);

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(tunnelQueue), new Road(40, countDownLatch));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            executorService.execute(cars[i] = new Car(race, 20 + (int) (Math.random() * 10), startTogether));
        }
//        for (int i = 0; i < cars.length; i++) {
//
//            new Thread(cars[i]).start();
//        }
        try {
            startTogether.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        executorService.shutdown();
    }
}
