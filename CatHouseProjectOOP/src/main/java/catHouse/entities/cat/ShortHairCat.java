package catHouse.entities.cat;

public class ShortHairCat extends BaseCat{
    private static final int KILOGRAMS = 7;
    public ShortHairCat(String name, String breed, double price) {
        super(name, breed, price);
    }

    @Override
    public void eating() {
        setKilograms(getKilograms() + 1);
    }
}
