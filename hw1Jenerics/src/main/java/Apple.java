public class Apple extends Fruit {
    private double weight = Math.random()*0.2 + 0.9;

    @Override
    public double getWeight() {
        return weight;
    }
}
