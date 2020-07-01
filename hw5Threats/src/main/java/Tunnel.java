import java.util.concurrent.ArrayBlockingQueue;

public class Tunnel extends Stage {

    private ArrayBlockingQueue<Car> arrayBlockingQueue;
    public Tunnel(ArrayBlockingQueue arrayBlockingQueue) {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        this.arrayBlockingQueue = arrayBlockingQueue;
    }
    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                arrayBlockingQueue.put(c);
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                arrayBlockingQueue.take();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
