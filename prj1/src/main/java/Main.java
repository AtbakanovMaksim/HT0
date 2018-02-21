import Exceptions.IlluminanceTooMuchException;
import Exceptions.SpaceUsageTooMuchException;

public class Main {
    public static void main(String[] args) {
        Building building = new Building("Здание 1");
        building.addRoom("Комната 1", "100 м2", "3 окна");
        building.addRoom("Комната 2", "5 м2", "2 окна");

        try {
            building.getRoom("Комната 1").add(new Lamp(150));
            building.getRoom("Комната 1").add(new Lamp(250));
        } catch (IlluminanceTooMuchException e) {
            e.printStackTrace();
        }
        try {
            building.getRoom("Комната 1").add(new Furniture("Стол письменный", 3.0));
            building.getRoom("Комната 1").add(new Furniture("Кресло мягкое и пушистое", 1.0, 2.0));
        } catch (SpaceUsageTooMuchException e) {
            e.printStackTrace();
        }

        building.describe();

    }
}
