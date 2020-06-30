public class Orange extends Fruit {
    private double weight = Math.random()*0.2 + 1.4;

    @Override
    public double getWeight() {
        return weight;
    }
}
