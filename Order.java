
public class Order {
    private int orderNum;
    private String type;
    private String description;
    private double value;
    private int numOrdered;
    private String estArrival;
    private int empSSN;

    // Constructors, getters, and setters for the attributes
    public Order(int orderNum, String type, String description, double value,
            int numOrdered, String estArrival, int empSSN) {
        this.orderNum = orderNum;
        this.type = type;
        this.description = description;
        this.value = value;
        this.numOrdered = numOrdered;
        this.estArrival = estArrival;
        this.empSSN = empSSN;
    }
}
