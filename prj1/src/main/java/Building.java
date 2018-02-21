import java.util.ArrayList;

import static org.apache.commons.lang3.math.NumberUtils.*;


public class Building {

    private String name;
    private ArrayList<Room> roomList = new ArrayList<Room>();

    public Building(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addRoom(String name, String space, String window) {
        Integer windowNumber = null;
        Double roomSpace = null;
        if (isParsable(space.split(" ")[0])) {
            roomSpace = Double.parseDouble(space.split(" ")[0]);
        } else {
            System.out.println("Parameter Space was entered incorrect");
        }
        if (isParsable(window.split(" ")[0])) {
            windowNumber = Integer.parseInt(window.split(" ")[0]);
        } else {
            System.out.println("Parameter windowNumber was entered incorrect");
        }

        if ((windowNumber != null) && (roomSpace != null)) {
            Room room = new Room(name, roomSpace, windowNumber);
            roomList.add(room);
        }
    }

    public Room getRoom(String name) {
        int temp = 0;
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).getName().equalsIgnoreCase(name)) {
                temp = i;
                break;
            }
        }
        if (roomList.get(temp) != null) {
            return roomList.get(temp);
        } else {
            System.out.println("No such room were found");
            return null;
        }
    }

    public void describe() {
        System.out.println(getName());
        for (int i = 0; i < roomList.size(); i++) {
            System.out.println("\t" + roomList.get(i).getName());
            System.out.println("\t\t" + "Освещенность - " + roomList.get(i).getIllumination() +
                    "лк : окон - " + roomList.get(i).getWindowNumber() + " по 700 лк," +
                    roomList.get(i).getLampList());
            System.out.print("\t\t" + "Площадь - " + roomList.get(i).getSpace() + "м2 (занято ");
            if (roomList.get(i).getMinFurnitureSpace() == 0) {
                System.out.print(roomList.get(i).getMaxFurnitureSpace());
            } else {
                System.out.print("от " + roomList.get(i).getMinFurnitureSpace() + " до " + roomList.get(i).getMaxFurnitureSpace());
            }
            System.out.println(" м2, гарантировано свободно " + (roomList.get(i).getSpace() - roomList.get(i).getMaxFurnitureSpace()) +
                    " м2 или " + ((roomList.get(i).getSpace() - roomList.get(i).getMaxFurnitureSpace()) * 100 / roomList.get(i).getSpace()) + "% площади)");
            System.out.println("\t\t" + "Мебель:");
            System.out.println(roomList.get(i).getFurnitureList());
        }
    }
}
