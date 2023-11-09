package project;

public class Manufacturer {
    private String name;
    private String email;
    private String address;
    private String phoneNum;
    private String status;
    private String equipSupp;

    // Constructors, getters, and setters for the attributes
    public Manufacturer(String name, String email, String address,
            String phoneNum, String status, String equipSupp) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNum = phoneNum;
        this.status = status;
        this.equipSupp = equipSupp;
    }
}
