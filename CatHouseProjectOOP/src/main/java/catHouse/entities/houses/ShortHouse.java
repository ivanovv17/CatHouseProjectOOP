package catHouse.entities.houses;

import catHouse.entities.cat.BaseCat;

public class ShortHouse extends BaseHouse {
    private static final int CAPACITY = 15;

    public ShortHouse(String name) {
        super(name, CAPACITY);
    }
}
