package project;

public class Rental {
    private int rentalNum;
    private String dueDate;
    private String item;
    private double fees;
    private int memberID;
    private int empSSN;
    private int droneNum;

    // Constructors, getters, and setters for the attributes
    public Rental(int rentalNum, String dueDate, double fees, int memberID,
            int empSSN, int droneNum) {
        this.rentalNum = rentalNum;
        this.dueDate = dueDate;
        this.fees = fees;
        this.memberID = memberID;
        this.empSSN = empSSN;
        this.droneNum = droneNum;
    }

}
