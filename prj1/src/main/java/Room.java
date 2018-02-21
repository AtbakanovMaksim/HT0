import Exceptions.IlluminanceTooMuchException;
import Exceptions.SpaceUsageTooMuchException;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private int illumination;
    private String name;
    private Double space;
    private Double minFurnitureSpace;
    private Double maxFurnitureSpace;
    private int windowNumber;
    private List<Furniture> furnitureList = new ArrayList<Furniture>();
    private List<Lamp> lampList = new ArrayList<Lamp>();

    public int getIllumination() {
        return illumination;
    }

    public Double getMinFurnitureSpace() {
        return minFurnitureSpace;
    }

    public void setMinFurnitureSpace(Double minFurnitureSpace) {
        this.minFurnitureSpace = minFurnitureSpace;
    }

    public Double getMaxFurnitureSpace() {
        return maxFurnitureSpace;
    }

    public void setMaxFurnitureSpace(Double maxfurnitureSpace) {
        this.maxFurnitureSpace = maxfurnitureSpace;
    }

    public void setIllumination(int illumination) {
        this.illumination = illumination;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSpace() {
        return space;
    }

    public void setSpace(Double space) {
        this.space = space;
    }

    public int getWindowNumber() {
        return windowNumber;
    }

    public void setWindowNumber(int windowNumber) {
        this.windowNumber = windowNumber;
    }

    public String getFurnitureList() {
        String result = "";
        if (furnitureList.size() != 0) {
            for (int i = 0; i < furnitureList.size(); i++) {
                result = result + "\t\t" + furnitureList.get(i).getName() + " (площадь ";
                if (furnitureList.get(i).getMinSpace() == null) {
                    result = result + furnitureList.get(i).getMaxSpace() + " м2)" + "\n";
                } else {
                    result = result + "от " + furnitureList.get(i).getMinSpace() + " до " + furnitureList.get(i).getMaxSpace() + " м2)" + "\n";
                }
            }
        } else {
            result = "\t\t" + "отсутствует";
        }
        return result;
    }

    public String getLampList() {
        String result = "";
        if (lampList.size() != 0) {
            result = result + " лампочки ";
            for (int i = 0; i < lampList.size(); i++) {
                result = result + lampList.get(i).getIllumination() + "лк ";
            }
        } else {
            result = " лампочек нет";
        }
        return result;
    }

    public Room(String name, Double space, int windowNumber) {
        this.name = name;
        this.space = space;
        this.windowNumber = windowNumber;
        illumination = windowNumber * 700;
        maxFurnitureSpace = 0.0;
        minFurnitureSpace = 0.0;
    }

    public void add(Furniture furniture) throws SpaceUsageTooMuchException {
        double tempFurnitureSpace = maxFurnitureSpace + furniture.getMaxSpace();
        if (tempFurnitureSpace / space > 0.7) {
            throw new SpaceUsageTooMuchException();
        } else {
            furnitureList.add(furniture);
            maxFurnitureSpace = maxFurnitureSpace + furniture.getMaxSpace();
            if (furniture.getMinSpace() != null) {
                minFurnitureSpace = minFurnitureSpace + furniture.getMinSpace();
            }
        }
    }

    public void add(Lamp lamp) throws IlluminanceTooMuchException {
        if (illumination + lamp.getIllumination() >= 4000) {
            throw new IlluminanceTooMuchException();
        } else {
            lampList.add(lamp);
            illumination = illumination + lamp.getIllumination();
        }
    }
}
