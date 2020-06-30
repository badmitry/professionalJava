import java.util.ArrayList;

public class Box<T extends Fruit> {
    private ArrayList<T> arrayList = new ArrayList<>();

    public void addFruit(T fruit) {
        arrayList.add(fruit);
    }

    public double getWeight(){
        double weight=0;
        for (T t: arrayList) {
            weight += t.getWeight();
        }
        return weight;
    }

    public boolean compare (Box box) {
        if (Math.abs(this.getWeight()-box.getWeight())<1){
            return true;
        }
        else return false;
    }

    public void moveFruit(Box<T> box) {
        ArrayList<T> arrayList = new ArrayList();
        arrayList.addAll(this.arrayList);
        this.arrayList.clear();
        box.takeFruit(arrayList);
    }

    public void takeFruit(ArrayList<T> arrayList) {
        for (T o: arrayList
             ) {
            this.addFruit(o);
        }
    }
}
