package Farm_And_Shop;

public class Farmer {
    public void fetchMilk(MilkProvider a) {
        System.out.printf("Fetched %.2f liters of milk from %s.%n", a.getMilk(),
                (a instanceof Animal ? ((Animal) a).getType() : "Unknown"));
    }

    public void fetchEgg(EggProvider a) {
        System.out.printf("Fetched %d eggs from %s.%n", a.getEgg(),
                (a instanceof Animal ? ((Animal) a).getType() : "Unknown"));
    }
}