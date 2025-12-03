package Farm_And_Shop;

public abstract class Item {
    protected final String name;
    protected final double cost;

    public Item(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public abstract double costPerUnit();

    @Override
    public String toString() {
        return String.format("%s: price=%.2f, unitPrice=%.2f", name, getCost(), costPerUnit());
    }
}