package Farm_And_Shop;

public class Produce extends Item {
    private double pounds;
    private String category;

    public Produce(String name, double cost, double pounds, String category) {
        super(name, cost);
        this.pounds = pounds;
        this.category = category;
    }

    @Override
    public double costPerUnit() {
        return cost / pounds;
    }

    public double getPounds() {
        return pounds;
    }

    public void setPounds(double pounds) {
        this.pounds = pounds;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return String.format("Produce[name=%s, category=%s, pounds=%.2f, price=%.2f, unitPrice=%.2f]",
                name, category, pounds, cost, costPerUnit());
    }
}