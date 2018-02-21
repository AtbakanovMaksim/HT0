public class Furniture {

    private String name;
    private Double minSpace;
    private Double maxSpace;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMinSpace() {
        return minSpace;
    }

    public void setMinSpace(Double minSpace) {
        this.minSpace = minSpace;
    }

    public Double getMaxSpace() {
        return maxSpace;
    }

    public void setMaxSpace(Double maxSpace) {
        this.maxSpace = maxSpace;
    }

    public Furniture(String name, Double maxSpace) {
        this.name = name;
        this.maxSpace = maxSpace;
    }

    public Furniture(String name, Double minSpace, Double maxSpace) {
        this.name = name;
        this.minSpace = minSpace;
        this.maxSpace = maxSpace;
    }
}
