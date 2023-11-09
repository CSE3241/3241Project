package project;

public class Review {
    public double rating;
    public String comments;
    public int userID;
    public int rentalNum;

    // Constructors, getters, and setters for the attributes
    public Review(double rating, String comments, int userID, int rentalNum) {
        this.rating = rating;
        this.comments = comments;
        this.userID = userID;
        this.rentalNum = rentalNum;
    }

    public void setRating(String changeValTo) {
        double updateVal = Double.parseDouble(changeValTo);
        this.rating = updateVal;
    }

    public void setComments(String changeValTo) {
        this.comments = changeValTo;
    }

    public void setUserID(String changeValTo) {
        int changeValInt = Integer.parseInt(changeValTo);
        this.userID = changeValInt;
    }

    public void setRentalNum(String changeValTo) {
        int changeValInt = Integer.parseInt(changeValTo);
        this.rentalNum = changeValInt;
    }

    public double getRating() {
        return this.rating;
    }

    public String getComments() {
        return this.comments;
    }

    public int getUserID() {
        return this.userID;
    }

    public int getRentalNum() {
        return this.rentalNum;
    }
}
