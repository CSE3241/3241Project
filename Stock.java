
public class Stock {
    private int modelNum;
    private String status;
    private String description;
    private String arrivalDate;
    private String warrExpDate;
    private String manufacturer;
    private int serialNum;
    private int year;
    private String equipType;
    private String currentLoc;
    private double height;
    private double width;
    private double length;
    private double weight;
    private String warehouseAddr;
    private int orderNum;

    // Constructors, getters, and setters for the attributes
    public Stock(int modelNum, String status, String description,
            String arrivalDate, String warrExpDate, String manufacturer,
            int serialNum, int year, String equipType, String currentLoc,
            double height, double weight, double width, double length,
            String warehouseAddr, int orderNum) {
        this.modelNum = modelNum;
        this.status = status;
        this.description = description;
        this.arrivalDate = arrivalDate;
        this.warrExpDate = warrExpDate;
        this.manufacturer = manufacturer;
        this.serialNum = serialNum;
        this.year = year;
        this.equipType = equipType;
        this.currentLoc = currentLoc;
        this.height = height;
        this.width = width;
        this.length = length;
        this.weight = weight;
        this.warehouseAddr = warehouseAddr;
        this.orderNum = orderNum;
    }
}
