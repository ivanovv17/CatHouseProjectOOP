package catHouse.entities.cat;

public class LongHairCat extends BaseCat {
    private static final int KILOGRAMS = 9;

    public LongHairCat(String name, String breed, double price) {
        super(name, breed, price);
    }

    @Override
    public void eating() {
        setKilograms(getKilograms() + 3);
    }
}
