
public class Drone {
    private String manufacturer;
    private String currentLoc;
    private double distAuto;
    private String status;
    private String dName;
    private double volumeCap;
    private int modelNum;
    private double maxSpeed;
    private String warrantyExpDate;
    private int serialNum;
    private double weightCap;
    private String warehouseAddr;
    private int orderNum;

    // Constructors, getters, and setters for the attributes
    public Drone(String manufacturer, String currentLoc, double distAuto,
            String status, String dName, double volumeCap, int modelNum,
            double maxSpeed, String warrantyExpDate, int serialNum,
            double weightCap, String warehouseAddr, int orderNum) {
        this.manufacturer = manufacturer;
        this.currentLoc = currentLoc;
        this.distAuto = distAuto;
        this.status = status;
        this.dName = dName;
        this.volumeCap = volumeCap;
        this.modelNum = modelNum;
        this.maxSpeed = maxSpeed;
        this.warrantyExpDate = warrantyExpDate;
        this.serialNum = serialNum;
        this.weightCap = weightCap;
        this.warehouseAddr = warehouseAddr;
        this.orderNum = orderNum;
    }
}
