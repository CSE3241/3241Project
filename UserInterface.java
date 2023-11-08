import java.util.ArrayList;

import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * User interface for the Equipment Renting Database System for Local
 * Communities.
 *
 * @author Team 6
 *
 */
public final class UserInterface {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private UserInterface() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        /*
         * Put your main program code here
         */

        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();

        // create list of arrays to store data for 3 relations implemented

        ArrayList<Warehouse> warehouses = new ArrayList<>();
        ArrayList<Member> members = new ArrayList<>();
        ArrayList<Review> reviews = new ArrayList<>();

        // add test data to Review

        Review r1 = new Review(3.4, "Subpar conditions", 23, 456);
        Review r2 = new Review(5, "Excellent speed", 1, 34);
        reviews.add(r1);
        reviews.add(r2);

        Member m1 = new Member(1, "Columbus", "Active", "John", "Smith", 12.4,
                "614-111-2222", "413 Sunset Drive", "johnsmith@google.com",
                "1/1/2014");
        Member m2 = new Member(2, "Centerville", "Active", "Taylor", "Swift",
                41.3, "614-222-3434", "567 Turtle ", "leahthompson@google.com",
                "8/4/2015");
        members.add(m1);
        members.add(m2);

        Warehouse w1 = new Warehouse("1 Peach Street", "Columbus",
                "614-614-6141", "Leah Kepp", 41, 56);
        warehouses.add(w1);

        // welcome user and ask what they would like to do
        out.println("Welcome to the homepage for the database system. Please "
                + "read the commands and enter what you would like to do.");
        out.println();

        out.println("1 - Add New Records");
        out.println("2 - Edit/Delete Records");
        out.println("3 - Search");
        out.println("4 - Find Useful Reports");
        out.println();

        // get and read prompt

        out.print("Enter a valid option (1/2/3/4): ");
        String userOption = in.nextLine();
        out.println();

        // check the option and then ask what relation they would like to update,
        // based on first option

        if (userOption.equals("1")) {
            out.println("What relation would you like to add a record to?");
            out.println();
            out.println("1 - WAREHOUSE");
            out.println("2 - MEMBER");
            out.println("3 - REVIEW");
            out.println();
            out.println("Enter a valid option (1/2/3): ");
            String userOption2 = in.nextLine();
            out.println();

            // ask user for record information

            if (userOption2.equals("1")) {
                out.println("Enter the address of the record: ");
                String address = in.nextLine();
                out.println("Enter the city of the record: ");
                String city = in.nextLine();
                out.println("Enter the phone number of the record: ");
                String phoneNum = in.nextLine();
                out.println("Enter the  manager's name of the record: ");
                String managerName = in.nextLine();
                out.println("Enter the storage capacity of the record: ");
                int storageCap = in.nextInteger();
                out.println("Enter the drone capacity of the record: ");
                int droneCap = in.nextInteger();

                // add record

                warehouses.add(new Warehouse(address, city, phoneNum,
                        managerName, storageCap, droneCap));
                out.println("The record has been added.");

            }

            // same process for other relations

            else if (userOption2.equals("2")) {
                out.println("Enter the userID of the record: ");
                int userID = in.nextInteger();
                out.println("Enter the community of the record: ");
                String community = in.nextLine();
                out.println("Enter the status of the record: ");
                String status = in.nextLine();
                out.println("Enter the FName of the record: ");
                String fName = in.nextLine();
                out.println("Enter the LName of the record: ");
                String lName = in.nextLine();
                out.println("Enter the warehouseDist of the record: ");
                double warehouseDist = in.nextDouble();
                out.println("Enter the phoneNum of the record: ");
                String phoneNum = in.nextLine();
                out.println("Enter the address of the record: ");
                String address = in.nextLine();
                out.println("Enter the email of the record: ");
                String email = in.nextLine();
                out.println("Enter the startDate of the record: ");
                String startDate = in.nextLine();

                members.add(new Member(userID, community, status, fName, lName,
                        warehouseDist, phoneNum, address, email, startDate));
                out.println("The record has been added.");

            } else if (userOption2.equals("3")) {
                out.println("Enter the rating of the record: ");
                double rating = in.nextDouble();
                out.println("Enter the comments of the record: ");
                String comments = in.nextLine();
                out.println("Enter the userID of the record: ");
                int userID = in.nextInteger();
                out.println("Enter the rental number of the record: ");
                int rentalNum = in.nextInteger();

                reviews.add(new Review(rating, comments, userID, rentalNum));
                out.println("The record has been added.");
            }
        } else if (userOption.equals("2")) {

            // ask what relation

            out.println(
                    "What relation would you like to edit or delete a record?");
            out.println();
            out.println("1 - WAREHOUSE");
            out.println("2 - MEMBER");
            out.println("3 - REVIEW");
            out.println();
            out.print("Enter a valid option (1/2/3): ");
            String userOption2 = in.nextLine();

            out.println();

            // ask if editing or deleting

            out.println("Are you editing or deleting a record?");
            out.println("1 - editing");
            out.println("2 - deleting");
            out.print("Enter a valid option (1/2): ");
            String editOrDelete = in.nextLine();

            // if delete, delete from proper relation
            // exception will be thrown because removing in iteration

            if (editOrDelete.equals("2")) {
                if (userOption2.equals("1")) {
                    out.println(
                            "Enter the warehouse name of the record you'd like to edit: ");
                    String warehouseName = in.nextLine();
                    for (Warehouse warehouse : warehouses) {
                        if (warehouse.address.equals(warehouseName)) {
                            warehouses.remove(warehouse);
                            out.println("The record has been deleted.");

                        }
                    }
                } else if (userOption2.equals("2")) {
                    out.println(
                            "Enter the memberID of the record you'd like to edit: ");
                    int memberID = in.nextInteger();
                    for (Member member : members) {
                        if (member.userID == memberID) {
                            members.remove(member);
                            out.println("The record has been deleted.");
                        }
                    }
                } else if (userOption2.equals("3")) {
                    out.println(
                            "Enter the rental number of the record you'd like to edit: ");
                    int rentalNum = in.nextInteger();
                    for (Review review : reviews) {
                        if (review.rentalNum == rentalNum) {
                            reviews.remove(review);
                            out.println("The record has been deleted.");
                        }
                    }
                }

            }

            else {

                // edit attribute according to input, do the same for other relations

                if (userOption2.equals("1")) {
                    out.println(
                            "Enter the warehouse name of the record you'd like to edit: ");
                    String warehouseName = in.nextLine();
                    for (Warehouse warehouse : warehouses) {
                        if (warehouse.address.equals(warehouseName)) {
                            out.println(
                                    "Enter the corresponding number for the attribute you"
                                            + " would like to update.");
                            out.println("1 - address");
                            out.println("2 - city");
                            out.println("3 - phoneNum");
                            out.println("4 - MGRName");
                            out.println("5 - storageCap");
                            out.println("6 - droneCap");
                            int attOption = in.nextInteger();
                            out.println(
                                    "What would you like to change the value to?");
                            String valChange = in.nextLine();
                            editWarehouse(warehouse, valChange, attOption);
                            out.println("The record has been updated.");

                        }
                    }
                } else if (userOption2.equals("2")) {
                    out.println(
                            "Enter the memberID of the record you'd like to edit: ");
                    int memberID = in.nextInteger();
                    for (Member member : members) {
                        if (member.userID == memberID) {
                            out.println(
                                    "Enter the corresponding number for the attribute you"
                                            + " would like to update.");
                            out.println("1 - userID");
                            out.println("2 - community");
                            out.println("3 - status");
                            out.println("4 - FName");
                            out.println("5 - LName");
                            out.println("6 - warehouseDist");
                            out.println("7 - phoneNum");
                            out.println("8 - address");
                            out.println("9 - email");
                            out.println("10 - startDate");
                            int attOption = in.nextInteger();
                            out.println(
                                    "What would you like to change the value to?");
                            String valChange = in.nextLine();
                            editMember(member, valChange, attOption);
                            out.println("The record has been updated.");
                        }
                    }
                } else if (userOption2.equals("3")) {
                    out.println(
                            "Enter the rental number of the record you'd like to edit: ");
                    int rentalNum = in.nextInteger();
                    for (Review review : reviews) {
                        if (review.rentalNum == rentalNum) {
                            out.println(
                                    "Enter the corresponding number for the attribute you"
                                            + " would like to update.");
                            out.println("1 - rating");
                            out.println("2 - comments");
                            out.println("3 - userID");
                            out.println("4 - rentalNum");
                            int attOption = in.nextInteger();
                            out.println(
                                    "What would you like to change the value to?");
                            String valChange = in.nextLine();
                            editReview(review, valChange, attOption);
                            out.println("The record has been updated.");
                        }
                    }
                }
            }
        }

        // if user picked to search, determine what relation and then display all
        // records in specified relation

        else if (userOption.equals("3")) {
            out.println("What relation would you like to search records in?");
            out.println();
            out.println("1 - WAREHOUSE");
            out.println("2 - MEMBER");
            out.println("3 - REVIEW");
            out.println();
            out.print("Enter a valid option (1/2/3): ");
            userOption = in.nextLine();
            out.println();

            if (userOption.equals("1")) {
                out.println(
                        "Address, city, phoneNum, MGRName, storageCap, droneCap");
                for (Warehouse w : warehouses) {
                    displayWarehouse(w);

                }
            }
            if (userOption.equals("2")) {
                out.println(
                        "UserID, community, status, FName, LName, warehouseDist, phoneNum, address, email, startDate");
                for (Member m : members) {
                    displayMember(m);

                }
            }
            if (userOption.equals("3")) {
                out.println("Rating, Comments, UserID, RentalID");
                for (Review r : reviews) {
                    displayReview(r);

                }
            }

        }
    }

    // edit warehouse based on desired attribute to change

    public static void editWarehouse(Warehouse w, String updateTo,
            int selection) {
        if (selection == 1) {
            w.setAddress(updateTo);
        } else if (selection == 2) {
            w.setCity(updateTo);
        } else if (selection == 3) {
            w.setPhoneNum(updateTo);
        } else if (selection == 4) {
            w.setMGRName(updateTo);
        } else if (selection == 5) {
            int updateToValInt = Integer.parseInt(updateTo);
            w.setStorageCap(updateToValInt);
        } else if (selection == 6) {
            int updateToValInt = Integer.parseInt(updateTo);
            w.setDroneCap(updateToValInt);
        }

    }

    // edit member based on desired attribute to change

    public static void editMember(Member m, String updateTo, int selection) {
        if (selection == 1) {
            m.setUserID(updateTo);
        } else if (selection == 2) {
            m.setComm(updateTo);
        } else if (selection == 3) {
            m.setStatus(updateTo);

        } else if (selection == 4) {
            m.setFName(updateTo);
        } else if (selection == 5) {
            m.setLName(updateTo);
        } else if (selection == 6) {
            m.setWarehouseDist(updateTo);

        } else if (selection == 7) {
            m.setPhoneNum(updateTo);
        } else if (selection == 8) {
            m.setAddress(updateTo);
        } else if (selection == 9) {
            m.setEmail(updateTo);
        } else if (selection == 10) {
            m.setStartDate(updateTo);
        }

    }

    // edit review based on desired attribute to change

    public static void editReview(Review r, String updateTo, int selection) {
        if (selection == 1) {
            r.setRating(updateTo);
        } else if (selection == 2) {
            r.setComments(updateTo);
        } else if (selection == 3) {
            r.setUserID(updateTo);
        } else if (selection == 4) {
            r.setRentalNum(updateTo);
        }

    }

    // display entries in Review

    public static void displayReview(Review r) {
        SimpleWriter out = new SimpleWriter1L();
        out.print(r.getRating() + " ");
        out.print(r.getComments() + " ");
        out.print(r.getUserID() + " ");
        out.print(r.getRentalNum());
        out.println();

    }

    // display entries in Member

    public static void displayMember(Member m) {
        SimpleWriter out = new SimpleWriter1L();
        out.print(m.getAddress() + " ");
        out.print(m.getCommunity() + " ");
        out.print(m.getEmail() + " ");
        out.print(m.getFName() + " ");
        out.print(m.getLName() + " ");
        out.print(m.getPhoneNum() + " ");
        out.print(m.getStartDate() + " ");
        out.print(m.getUserID() + " ");
        out.print(m.getWarehouseDist() + " ");
        out.println();

    }

    // display entries in Warehouse

    public static void displayWarehouse(Warehouse w) {
        SimpleWriter out = new SimpleWriter1L();
        out.print(w.getAddress() + " ");
        out.print(w.getCity() + " ");
        out.print(w.getMGRName() + " ");
        out.print(w.getPhoneNum() + " ");
        out.print(w.getStorageCap() + " ");
        out.print(w.getDroneCap() + " ");
        out.println();

    }
}
