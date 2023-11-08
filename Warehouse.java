public class Warehouse {
    public String address;
    public String city;
    public String phoneNum;
    public String MGRName;
    public int storageCap;
    public int droneCap;

    // Constructors, getters, and setters for the attributes
    public Warehouse(String address, String city, String phoneNum,
            String MGRName, int storageCap, int droneCap) {
        this.address = address;
        this.city = city;
        this.phoneNum = phoneNum;
        this.MGRName = MGRName;
        this.storageCap = storageCap;
        this.droneCap = droneCap;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setMGRName(String mgrName) {
        this.MGRName = mgrName;
    }

    public void setStorageCap(int storageCap) {
        this.storageCap = storageCap;
    }

    public void setDroneCap(int droneCap) {
        this.droneCap = droneCap;
    }

    public String getAddress() {
        return this.address;
    }

    public String getCity() {
        return this.city;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public String getMGRName() {
        return this.MGRName;
    }

    public int getStorageCap() {
        return this.storageCap;
    }

    public int getDroneCap() {
        return this.droneCap;
    }

}
