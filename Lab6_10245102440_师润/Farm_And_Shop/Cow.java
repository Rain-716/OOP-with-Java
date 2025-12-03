package Farm_And_Shop;

public class Cow implements Animal, MilkProvider {
    @Override
    public String getType() {
        return "Cow";
    }

    @Override
    public String getSound() {
        return "moo";
    }

    @Override
    public double getMilk() {
        return 10.0; // liters per fetch
    }
}