package Farm_And_Shop;

public class Pig implements Animal {
    @Override
    public String getType() {
        return "Pig";
    }

    @Override
    public String getSound() {
        return "oink";
    }
}