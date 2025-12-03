package Farm_And_Shop;

public class ShoppingCart {
    private Item[] cart;
    private int maxSize;
    private int currentSize;

    public ShoppingCart(int maxSize) {
        this.maxSize = maxSize;
        this.cart = new Item[maxSize];
        this.currentSize = 0;
    }

    public boolean addItem(Item item) {
        if (currentSize >= maxSize) {
            return false;
        }
        cart[currentSize++] = item;
        return true;
    }

    public void display() {
        for (int i = 0; i < currentSize; i++) {
            System.out.println(cart[i]);
        }
    }

    public double totalCost() {
        double sum = 0;
        for (int i = 0; i < currentSize; i++) {
            sum += cart[i].getCost();
        }
        return sum;
    }

    public int numberInCart(String s) {
        int count = 0;
        for (int i = 0; i < currentSize; i++) {
            if (cart[i].getName().equals(s)) {
                count++;
            }
        }
        return count;
    }
}