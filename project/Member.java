package project;

public class Member {
    public int userID;
    public String community;
    public String status;
    public String fName;
    public String lName;
    public double warehouseDist;
    public String phoneNum;
    public String address;
    public String email;
    public String startDate;

    // Constructors, getters, and setters for the attributes
    public Member(int userID, String community, String status, String FName,
            String LName, double warehouseDist, String phoneNum, String address,
            String email, String startDate) {
        this.userID = userID;
        this.community = community;
        this.status = status;
        this.fName = FName;
        this.lName = LName;
        this.warehouseDist = warehouseDist;
        this.phoneNum = phoneNum;
        this.address = address;
        this.email = email;
        this.startDate = startDate;
    }

    public void setUserID(String changeValTo) {
        int updateVal = Integer.parseInt(changeValTo);
        this.userID = updateVal;
    }

    public void setComm(String changeValTo) {
        this.community = changeValTo;
    }

    public void setStatus(String changeValTo) {
        this.status = changeValTo;
    }

    public void setFName(String changeValTo) {
        this.fName = changeValTo;
    }

    public void setLName(String changeValTo) {
        this.lName = changeValTo;
    }

    public void setWarehouseDist(String changeValTo) {
        Double doubleVal = Double.parseDouble(changeValTo);
        this.warehouseDist = doubleVal;
    }

    public void setPhoneNum(String changeValTo) {
        this.phoneNum = changeValTo;
    }

    public void setAddress(String changeValTo) {
        this.address = changeValTo;
    }

    public void setEmail(String changeValTo) {
        this.email = changeValTo;
    }

    public void setStartDate(String changeValTo) {
        this.startDate = changeValTo;
    }

    public int getUserID() {
        return this.userID;
    }

    public String getCommunity() {
        return this.community;
    }

    public String getFName() {
        return this.fName;
    }

    public String getLName() {
        return this.lName;
    }

    public double getWarehouseDist() {
        return this.warehouseDist;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public String getEmail() {
        return this.email;
    }

    public String getStartDate() {
        return this.startDate;
    }
}
