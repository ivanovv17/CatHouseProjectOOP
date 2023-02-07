package catHouse.core;

import catHouse.entities.cat.Cat;
import catHouse.entities.cat.LongHairCat;
import catHouse.entities.cat.ShortHairCat;
import catHouse.entities.houses.House;
import catHouse.entities.houses.LongHouse;
import catHouse.entities.houses.ShortHouse;
import catHouse.entities.toys.Ball;
import catHouse.entities.toys.Mouse;
import catHouse.entities.toys.Toy;
import catHouse.repositories.ToyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static catHouse.common.ConstantMessages.*;
import static catHouse.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {
    private ToyRepository toys;
    private Collection<House> houses;

    public ControllerImpl() {
        this.toys = new ToyRepository();
        this.houses = new ArrayList<>();
    }

    @Override
    public String addHouse(String type, String name) {
        House house;
        if (!type.equals(LongHouse.class.getSimpleName()) &&
                !type.equals(ShortHouse.class.getSimpleName())) {
            throw new NullPointerException(INVALID_HOUSE_TYPE);
        }

        if (type.equals(LongHouse.class.getSimpleName())) {
            house = new LongHouse(name);
        } else {
            house = new ShortHouse(name);
        }
        houses.add(house);
        return String.format(SUCCESSFULLY_ADDED_HOUSE_TYPE, type);
    }

    @Override
    public String buyToy(String type) {
        Toy toy;
        if (!type.equals(Ball.class.getSimpleName())
                && !type.equals(Mouse.class.getSimpleName())) {
            throw new IllegalArgumentException(INVALID_TOY_TYPE);
        }
        if (type.equals(Ball.class.getSimpleName())) {
            toy = new Ball();
        } else {
            toy = new Mouse();
        }
        toys.buyToy(toy);
        return String.format(SUCCESSFULLY_ADDED_TOY_TYPE, type);
    }

    @Override
    public String toyForHouse(String houseName, String toyType) {
        House house = houses.stream().filter(h -> h.getName().equals(houseName))
                .findFirst().orElse(null);
        Toy toy = toys.findFirst(toyType);
        if (toy == null) {
            return String.format(NO_TOY_FOUND, toyType);
        }

        toys.removeToy(toy);
        house.buyToy(toy);
        return String.format(SUCCESSFULLY_ADDED_TOY_IN_HOUSE, toyType, houseName);
    }

    @Override
    public String addCat(String houseName, String catType, String catName, String catBreed, double price) {
        if (!catType.equals("ShorthairCat")
                && !catType.equals("LonghairCat")) {
            throw new IllegalArgumentException(INVALID_CAT_TYPE);
        }

        House house = houses.stream().filter(h -> h.getName().equals(houseName))
                .findFirst().orElse(null);
        Cat cat = null;
        if (catType.equals("ShorthairCat")) {

            cat = new ShortHairCat(catName, catBreed, price);


        } else if (catType.equals("LonghairCat")) {

            cat = new LongHairCat(catName, catBreed, price);

        }
        String houseNames = house.getClass().getSimpleName();

        if (house.getClass().getSimpleName().equals(LongHouse.class.getSimpleName())
                && catType.equals("LonghairCat")) {
            if (cat != null) {
                house.addCat(cat);
            }
        } else if (house.getClass().getSimpleName().equals(ShortHouse.class.getSimpleName())
                && catType.equals("ShorthairCat")) {
            if (cat != null) {
                house.addCat(cat);
            }
        } else {
            throw new IllegalArgumentException(UNSUITABLE_HOUSE);
        }
        return String.format(SUCCESSFULLY_ADDED_CAT_IN_HOUSE, catType, houseName);
    }

    @Override
    public String feedingCat(String houseName) {
        House house = houses.stream().filter(h -> h.getName().equals(houseName))
                .findFirst().orElse(null);
        house.getCats().stream().forEach(cat -> cat.eating());
        return String.format(FEEDING_CAT, house.getCats().size());
    }

    @Override
    public String sumOfAll(String houseName) {
        House house = houses.stream().filter(h -> h.getName().equals(houseName))
                .findFirst().orElse(null);
        double sumCats = house.getCats().stream().mapToDouble(Cat::getPrice).sum();
        double sumToys = house.getToys().stream().mapToDouble(Toy::getPrice).sum();
        return String.format(VALUE_HOUSE, houseName, sumCats + sumToys);
    }

    @Override
    public String getStatistics() {
        String output = houses.stream().map(House::getStatistics)
                .collect(Collectors.joining(System.lineSeparator()));
        return output;
    }
}
