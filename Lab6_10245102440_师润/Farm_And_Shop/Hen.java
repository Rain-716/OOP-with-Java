package Farm_And_Shop;

public class Hen implements Animal, EggProvider {
    @Override
    public String getType() {
        return "Hen";
    }

    @Override
    public String getSound() {
        return "cheep";
    }

    @Override
    public int getEgg() {
        return 5; // eggs per fetch
    }
}