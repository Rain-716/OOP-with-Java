package Farm_And_Shop;

public class Main {
    public static void main(String[] args) {
        // ShoppingCart 示例
        ShoppingCart cart = new ShoppingCart(10);
        cart.addItem(new Produce("Apple", 5.0, 2.0, "Fruit"));
        cart.addItem(new Beverage("Cola", 2.0, 0.5, 0.1));
        cart.addItem(new PackageItem("Soap", 3.0, 2.0, 1.0, 0.5));
        cart.display();
        System.out.println("Total cost: " + cart.totalCost());
        System.out.println("Number of Apple: " + cart.numberInCart("Apple"));

        // Farm 示例
        Animal[] animals = { new Cow(), new Hen(), new Pig(), new Hen() };
        Farm farm = new Farm(animals);
        farm.animalSound();
        farm.produce();
    }
}