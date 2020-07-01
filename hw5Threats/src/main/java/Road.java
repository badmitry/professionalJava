import java.util.concurrent.CountDownLatch;

public class Road extends Stage {
    private boolean isFirstCar;
    private boolean isLastRoad;
    private CountDownLatch countDownLatch;

    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }
    public Road(int length, CountDownLatch countDownLatch) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
        isFirstCar = true;
        isLastRoad = true;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void go(Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            System.out.println(c.getName() + " закончил этап: " + description);
            hoIsWin(c);
            if (isLastRoad) {
                countDownLatch.countDown();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void hoIsWin(Car c) {
        if (isFirstCar) {
            System.out.println(c.getName() + " WIN");
            isFirstCar = false;
        }

    }
}
