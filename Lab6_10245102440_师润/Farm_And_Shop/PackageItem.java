package Farm_And_Shop;

public class PackageItem extends Item {
    private double length;
    private double width;
    private double height;

    public PackageItem(String name, double cost, double length, double width, double height) {
        super(name, cost);
        this.length = length;
        this.width = width;
        this.height = height;
    }

    @Override
    public double costPerUnit() {
        return cost / getSize();
    }

    public double getSize() {
        return length * width * height;
    }

    @Override
    public String toString() {
        return String.format("Package[name=%s, size=%.2f, price=%.2f, unitPrice=%.2f]",
                name, getSize(), cost, costPerUnit());
    }
}