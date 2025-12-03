package Farm_And_Shop;

public class Beverage extends Item {
    private double volume;
    private double containerDeposit;

    public Beverage(String name, double cost, double volume, double containerDeposit) {
        super(name, cost);
        this.volume = volume;
        this.containerDeposit = containerDeposit;
    }

    @Override
    public double costPerUnit() {
        return getCost() / volume;
    }

    @Override
    public double getCost() {
        return cost + containerDeposit;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getContainerDeposit() {
        return containerDeposit;
    }

    public void setContainerDeposit(double containerDeposit) {
        this.containerDeposit = containerDeposit;
    }

    @Override
    public String toString() {
        return String.format("Beverage[name=%s, volume=%.2fL, deposit=%.2f, totalPrice=%.2f, unitPrice=%.2f]",
                name, volume, containerDeposit, getCost(), costPerUnit());
    }
}