package Farm_And_Shop;

import java.util.ArrayList;
import java.util.List;

public class Farm {
    private Animal[] animals;
    private Farmer farmer;
    private MilkProvider[] mp;
    private EggProvider[] ep;

    public Farm(Animal[] animals) {
        this.animals = animals;
        this.farmer = new Farmer();
        List<MilkProvider> milks = new ArrayList<>();
        List<EggProvider> eggs = new ArrayList<>();
        for (Animal a : animals) {
            if (a instanceof MilkProvider) {
                milks.add((MilkProvider) a);
            }
            if (a instanceof EggProvider) {
                eggs.add((EggProvider) a);
            }
        }
        this.mp = milks.toArray(new MilkProvider[0]);
        this.ep = eggs.toArray(new EggProvider[0]);
    }

    public void animalSound() {
        for (Animal a : animals) {
            System.out.println(a.getType() + " goes " + a.getSound());
        }
    }

    public void produce() {
        for (MilkProvider m : mp) {
            farmer.fetchMilk(m);
        }
        for (EggProvider e : ep) {
            farmer.fetchEgg(e);
        }
    }
}