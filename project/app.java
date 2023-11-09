package project;

import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * <h1>CSE3241 Introduction to Database Systems - Sample Java application.</h1>
 * 
 * <p>Sample app to be used as guidance and a foundation for students of 
 * CSE3241 Introduction to Database Systems at 
 * The Ohio State University.</p>
 * 
 * <h2>!!! - Vulnerable to SQL injection - !!!</h2>
 * <p>Correct the code so that it is not 
 * vulnerable to a SQL injection attack. ("Parameter substitution" is the usual way to do this.)</p>
 * 
 * <p>Class is written in Java SE 8 and in a procedural style. Implement a constructor if you build this app out in OOP style.</p>
 * <p>Modify and extend this app as necessary for your project.</p>
 *
 * <h2>Language Documentation:</h2>
 * <ul>
 * <li><a href="https://docs.oracle.com/javase/8/docs/">Java SE 8</a></li>
 * <li><a href="https://docs.oracle.com/javase/8/docs/api/">Java SE 8 API</a></li>
 * <li><a href="https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/">Java JDBC API</a></li>
 * <li><a href="https://www.sqlite.org/docs.html">SQLite</a></li>
 * <li><a href="http://www.sqlitetutorial.net/sqlite-java/">SQLite Java Tutorial</a></li>
 * </ul>
 *
 * <h2>MIT License</h2>
 *
 * <em>Copyright (c) 2019 Leon J. Madrid, Jeff Hachtel</em>
 * 
 * <p>Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.</p>
 *
 * 
 * @author Leon J. Madrid (madrid.1), Jeff Hachtel (hachtel.5)
 * 
 */

public class app {
    
	/**
	 *  The database file name.
	 *  
	 *  Make sure the database file is in the root folder of the project if you only provide the name and extension.
	 *  
	 *  Otherwise, you will need to provide an absolute path from your C: drive or a relative path from the folder this class is in.
	 */
	private static String DATABASE = "projectDB.db";
	
	
	/**
	 *  The query statement to be executed.
	 *  
	 *  Remember to include the semicolon at the end of the statement string.
	 *  (Not all programming languages and/or packages require the semicolon (e.g., Python's SQLite3 library))
	 */
	private static String sqlStatement = "SELECT * FROM Employee;";
	
    /**
     * Connects to the database if it exists, creates it if it does not, and returns the connection object.
     * 
     * @param databaseFileName the database file name
     * @return a connection object to the designated database
     */
    public static Connection initializeDB(String databaseFileName) {
    	/**
    	 * The "Connection String" or "Connection URL".
    	 * 
    	 * "jdbc:sqlite:" is the "subprotocol".
    	 * (If this were a SQL Server database it would be "jdbc:sqlserver:".)
    	 */
        String url = "jdbc:sqlite:" + databaseFileName;
        Connection conn = null; // If you create this variable inside the Try block it will be out of scope
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
            	// Provides some positive assurance the connection and/or creation was successful.
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("The connection to the database was successful.");
            } else {
            	// Provides some feedback in case the connection failed but did not throw an exception.
            	System.out.println("Null Connection");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("There was a problem connecting to the database.");
        }
        return conn;
    }
    
    public static void addRental(Connection conn) {
    	try {
    		String sql = "INSERT INTO RENTAL (rentalNum,checkOut,dueDate,item,fees,userID, ";
    		sql += "empID,dropDrone,pickUpDrone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    		PreparedStatement rental = conn.prepareStatement(sql);
    		
    		
    		
    		Scanner intScanner = new Scanner(System.in);
    		Scanner wordScanner = new Scanner(System.in);
    		System.out.println("Rental Number: ");
    		int rentalNum = intScanner.nextInt();
    		
    		System.out.println("Date of check out: ");
    		Date checkOut = Date.valueOf(wordScanner.nextLine());
    		
    		System.out.println("Due date: ");
    		Date dueDate = Date.valueOf(wordScanner.nextLine());
    		
    		System.out.println("Item: ");
    		String item = wordScanner.nextLine();
    		
    		System.out.println("Fees: ");
    		double fees = intScanner.nextDouble();
    		
    		System.out.println("userID: ");
    		int userID = intScanner.nextInt();
    		
    		System.out.println("empID: ");
    		String empID = wordScanner.nextLine();
    		
    		System.out.println("Delivery Drone: ");
    		String dropDrone = wordScanner.nextLine();
    		
    		System.out.println("Pick Up Drone: ");
    		String pickUpDrone = wordScanner.nextLine();
    		
    		rental.setInt(1, rentalNum);
    		rental.setDate(2,  checkOut);
    		rental.setDate(3,  dueDate);
    		rental.setString(4, item);
    		rental.setDouble(5, fees);
    		rental.setInt(6, userID);
    		rental.setString(7, empID);
    		rental.setString(8, dropDrone);
    		rental.setString(9, pickUpDrone);
    		
    		
    		ResultSet rs = rental.executeQuery();
        	ResultSetMetaData rsmd = rs.getMetaData();
        	int columnCount = rsmd.getColumnCount();
        	for (int i = 1; i <= columnCount; i++) {
        		String value = rsmd.getColumnName(i);
        		System.out.print(value);
        		if (i < columnCount) System.out.print(",  ");
        	}
        	while (rs.next()) {
        		for (int i = 1; i <= columnCount; i++) {
        			String columnValue = rs.getString(i);
            		System.out.print(columnValue);
            		if (i < columnCount) System.out.print(",  ");
        		}
    			System.out.print("\n");
        	}
    		
        	wordScanner.close();
        	intScanner.close();
    		
    	} catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Queries the database and prints the results.
     * 
     * @param conn a connection object
     * @param sql a SQL statement that returns rows
     * This query is written with the Statement class, tipically 
     * used for static SQL SELECT statements
     */
    public static void lindseyQuery(Connection conn, String sql){
        try {
        	PreparedStatement state = conn.prepareStatement(sql);
        	state.setString(1, "Packer");
        	state.setString(2,  "123 Main St");
        			
        	ResultSet rs = state.executeQuery();
        	ResultSetMetaData rsmd = rs.getMetaData();
        	int columnCount = rsmd.getColumnCount();
        	for (int i = 1; i <= columnCount; i++) {
        		String value = rsmd.getColumnName(i);
        		System.out.print(value);
        		if (i < columnCount) System.out.print(",  ");
        	}
			System.out.print("\n");
        	while (rs.next()) {
        		for (int i = 1; i <= columnCount; i++) {
        			String columnValue = rs.getString(i);
            		System.out.print(columnValue);
            		if (i < columnCount) System.out.print(",  ");
        		}
    			System.out.print("\n");
        	}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void sqlQuery(Connection conn, String sql){
        try {
        	Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	ResultSetMetaData rsmd = rs.getMetaData();
        	int columnCount = rsmd.getColumnCount();
        	for (int i = 1; i <= columnCount; i++) {
        		String value = rsmd.getColumnName(i);
        		System.out.print(value);
        		if (i < columnCount) System.out.print(",  ");
        	}
			System.out.print("\n");
        	while (rs.next()) {
        		for (int i = 1; i <= columnCount; i++) {
        			String columnValue = rs.getString(i);
            		System.out.print(columnValue);
            		if (i < columnCount) System.out.print(",  ");
        		}
    			System.out.print("\n");
        	}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    
    //runs insertion query
    public static void insertStatement (Connection conn, String sql) {
    	try {
        	Statement stmt = conn.createStatement();
        	stmt.executeUpdate(sql);       	
			System.out.print("\n");
			System.out.print("Update Successfull");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    
    
    
    //Builds insert query string for warehouse
    public static String warehouseEntryBuild(String address, String city, String phoneNo, String mgrName, String storageCap, String droneCap) 
    {
    	String values = address+","+city+","+phoneNo+","+mgrName+","+storageCap+","+droneCap;
    	String insertStatement = "INSERT INTO WAREHOUSE (Address, City, PhoneNum, MGRName, StorageCap, DroneCap) Values ("+values+");";
    	return insertStatement;
    }
    
  //Builds insert query string for review
    public static String reviewEntryBuild(String rating, String comments, String userID, String rentalNum, String date) 
    {
    	String values = userID+","+rating+","+comments+","+userID+","+rentalNum+","+date;
    	String insertStatement = "INSERT INTO review (Rating, Comments, userID, RentalNum, Date) Values ("+values+");";
    	return insertStatement;
    }
    
  //Builds insert query string for member
    public static String memberEntryBuild(String userID, String Community, String Status, String Fname, String Lname, String warehouseDist, String phoneNum, String Address, String Email, String startDate) 
    {
    	String values = userID+","+Community+","+Status+","+Fname+","+Lname+","+warehouseDist+","+phoneNum+","+Address+","+Email+","+startDate;
    	String insertStatement = "INSERT INTO MEMBER (userID, Community, Status, Fname, Lname, warehouseDist, pphoneNum, Address, Email, startDate) Values ("+values+");";
    	return insertStatement;
    }
    
    public static void main(String[] args) {
    	System.out.println("This is a new run");
    	Connection conn = initializeDB(DATABASE);  	
    	Scanner in = new Scanner(System.in);
    	
    	
    	    	
    	// welcome user and ask what they would like to do
        System.out.println("Welcome to the homepage for the database system. Please "
                + "read the commands and enter what you would like to do.");
        System.out.println();

        System.out.println("1 - Add New Records");
        System.out.println("2 - Edit/Delete Records");
        System.out.println("3 - Search");
        System.out.println("4 - Find Useful Reports");
        System.out.println();

        // get and read prompt

        System.out.print("Enter a valid option (1/2/3/4): ");
        String userOption = in.nextLine();
        System.out.println();
        
     // check the option and then ask what relation they would like to update,
        // based on first option

        if (userOption.equals("1")) {
            System.out.println("What relation would you like to add a record to?");
            System.out.println();
            System.out.println("1 - WAREHOUSE");
            System.out.println("2 - MEMBER");
            System.out.println("3 - REVIEW");
            System.out.println();
            System.out.println("Enter a valid option (1/2/3): ");
            String userOption2 = in.nextLine();
            System.out.println();

            // ask user for record information

            if (userOption2.equals("1")) {
                System.out.println("Enter the address of the record: ");
                String address = in.nextLine();
                System.out.println("Enter the city of the record: ");
                String city = in.nextLine();
                System.out.println("Enter the phone number of the record: ");
                String phoneNum = in.nextLine();
                System.out.println("Enter the  manager's name of the record: ");
                String managerName = in.nextLine();
                System.out.println("Enter the storage capacity of the record: ");
                String storageCap = in.nextLine();
                System.out.println("Enter the drone capacity of the record: ");
                String droneCap = in.nextLine();

                //Create the string for the insert query
                String warehouseEntry=warehouseEntryBuild(address, city, phoneNum, managerName, storageCap, droneCap);
                
                
                //Run insertion
                insertStatement(conn,warehouseEntry);
                
                
                
                //warehouses.add(new Warehouse(address, city, phoneNum,
                        //managerName, storageCap, droneCap));
                //System.out.println("The record has been added.");

            }

            // same process for other relations

            else if (userOption2.equals("2")) {
                System.out.println("Enter the userID of the record: ");
                String userID = in.nextLine();
                System.out.println("Enter the community of the record: ");
                String community = in.nextLine();
                System.out.println("Enter the status of the record: ");
                String status = in.nextLine();
                System.out.println("Enter the FName of the record: ");
                String fName = in.nextLine();
                System.out.println("Enter the LName of the record: ");
                String lName = in.nextLine();
                System.out.println("Enter the warehouseDist of the record: ");
                String warehouseDist = in.nextLine();
                System.out.println("Enter the phoneNum of the record: ");
                String phoneNum = in.nextLine();
                System.out.println("Enter the address of the record: ");
                String address = in.nextLine();
                System.out.println("Enter the email of the record: ");
                String email = in.nextLine();
                System.out.println("Enter the startDate of the record: ");
                String startDate = in.nextLine();

                String memberEntry = memberEntryBuild(userID, community, status, fName, lName,
                        warehouseDist, phoneNum, address, email, startDate);
                insertStatement(conn, memberEntry);
                //System.out.println("The record has been added.");

            } else if (userOption2.equals("3")) {
                System.out.println("Enter the rating of the record: ");
                String rating = in.nextLine();
                System.out.println("Enter the comments of the record: ");
                String comments = in.nextLine();
                System.out.println("Enter the userID of the record: ");
                String userID = in.nextLine();
                System.out.println("Enter the rental number of the record: ");
                String rentalNum = in.nextLine();
                System.out.println("Enter the rental number of the record: ");
                String rentalDate = in.nextLine();

                String reviewEntry = reviewEntryBuild(rating, comments, userID, rentalNum, rentalDate);
                insertStatement(conn, reviewEntry);
                
                
                
            }
        } else if (userOption.equals("2")) {

            // ask what relation

            System.out.println(
                    "What relation would you like to edit or delete a record?");
            System.out.println();
            System.out.println("1 - WAREHOUSE");
            System.out.println("2 - MEMBER");
            System.out.println("3 - REVIEW");
            System.out.println();
            System.out.print("Enter a valid option (1/2/3): ");
            String userOption2 = in.nextLine();

            System.out.println();

            // ask if editing or deleting

            System.out.println("Are you editing or deleting a record?");
            System.out.println("1 - editing");
            System.out.println("2 - deleting");
            System.out.print("Enter a valid option (1/2): ");
            String editOrDelete = in.nextLine();

            // if delete, delete from proper relation
            // exception will be thrown because removing in iteration

            if (editOrDelete.equals("2")) {
                if (userOption2.equals("1")) {
                    System.out.println(
                            "Enter the warehouse name of the record you'd like to edit: ");
                    String warehouseName = in.nextLine();
                    for (Warehouse warehouse : warehouses) {
                        if (warehouse.address.equals(warehouseName)) {
                            warehouses.remove(warehouse);
                            System.out.println("The record has been deleted.");

                        }
                    }
                } else if (userOption2.equals("2")) {
                    System.out.println(
                            "Enter the memberID of the record you'd like to edit: ");
                    int memberID = in.nextInt();
                    for (Member member : members) {
                        if (member.userID == memberID) {
                            members.remove(member);
                            System.out.println("The record has been deleted.");
                        }
                    }
                } else if (userOption2.equals("3")) {
                    System.out.println(
                            "Enter the rental number of the record you'd like to edit: ");
                    int rentalNum = in.nextInt();
                    for (Review review : reviews) {
                        if (review.rentalNum == rentalNum) {
                            reviews.remove(review);
                            System.out.println("The record has been deleted.");
                        }
                    }
                }

            }

            else {

                // edit attribute according to input, do the same for other relations

                if (userOption2.equals("1")) {
                    System.out.println(
                            "Enter the warehouse name of the record you'd like to edit: ");
                    String warehouseName = in.nextLine();
                    for (Warehouse warehouse : warehouses) {
                        if (warehouse.address.equals(warehouseName)) {
                            System.out.println(
                                    "Enter the corresponding number for the attribute you"
                                            + " would like to update.");
                            System.out.println("1 - address");
                            System.out.println("2 - city");
                            System.out.println("3 - phoneNum");
                            System.out.println("4 - MGRName");
                            System.out.println("5 - storageCap");
                            System.out.println("6 - droneCap");
                            int attOption = in.nextInt();
                            System.out.println(
                                    "What would you like to change the value to?");
                            String valChange = in.nextLine();
                            editWarehouse(warehouse, valChange, attOption);
                            System.out.println("The record has been updated.");

                        }
                    }
                } else if (userOption2.equals("2")) {
                    System.out.println(
                            "Enter the memberID of the record you'd like to edit: ");
                    int memberID = in.nextInt();
                    for (Member member : members) {
                        if (member.userID == memberID) {
                            System.out.println(
                                    "Enter the corresponding number for the attribute you"
                                            + " would like to update.");
                            System.out.println("1 - userID");
                            System.out.println("2 - community");
                            System.out.println("3 - status");
                            System.out.println("4 - FName");
                            System.out.println("5 - LName");
                            System.out.println("6 - warehouseDist");
                            System.out.println("7 - phoneNum");
                            System.out.println("8 - address");
                            System.out.println("9 - email");
                            System.out.println("10 - startDate");
                            int attOption = in.nextInt();
                            System.out.println(
                                    "What would you like to change the value to?");
                            String valChange = in.nextLine();
                            editMember(member, valChange, attOption);
                            System.out.println("The record has been updated.");
                        }
                    }
                } else if (userOption2.equals("3")) {
                    System.out.println(
                            "Enter the rental number of the record you'd like to edit: ");
                    int rentalNum = in.nextInt();
                    for (Review review : reviews) {
                        if (review.rentalNum == rentalNum) {
                            System.out.println(
                                    "Enter the corresponding number for the attribute you"
                                            + " would like to update.");
                            System.out.println("1 - rating");
                            System.out.println("2 - comments");
                            System.out.println("3 - userID");
                            System.out.println("4 - rentalNum");
                            int attOption = in.nextInt();
                            System.out.println(
                                    "What would you like to change the value to?");
                            String valChange = in.nextLine();
                            editReview(review, valChange, attOption);
                            System.out.println("The record has been updated.");
                        }
                    }
                }
            }
        }

        // if user picked to search, determine what relation and then display all
        // records in specified relation

        else if (userOption.equals("3")) {
            System.out.println("What relation would you like to search records in?");
            System.out.println();
            System.out.println("1 - WAREHOUSE");
            System.out.println("2 - MEMBER");
            System.out.println("3 - REVIEW");
            System.out.println();
            System.out.print("Enter a valid option (1/2/3): ");
            userOption = in.nextLine();
            System.out.println();

            if (userOption.equals("1")) {
                System.out.println(
                        "Address, city, phoneNum, MGRName, storageCap, droneCap");
                for (Warehouse w : warehouses) {
                    displayWarehouse(w);

                }
            }
            if (userOption.equals("2")) {
                System.out.println(
                        "UserID, community, status, FName, LName, warehouseDist, phoneNum, address, email, startDate");
                for (Member m : members) {
                    displayMember(m);

                }
            }
            if (userOption.equals("3")) {
                System.out.println("Rating, Comments, UserID, RentalID");
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
        System.out.print(r.getRating() + " ");
        System.out.print(r.getComments() + " ");
        System.out.print(r.getUserID() + " ");
        System.out.print(r.getRentalNum());
        System.out.println();

    }

    // display entries in Member

    public static void displayMember(Member m) {
        System.out.print(m.getAddress() + " ");
        System.out.print(m.getCommunity() + " ");
        System.out.print(m.getEmail() + " ");
        System.out.print(m.getFName() + " ");
        System.out.print(m.getLName() + " ");
        System.out.print(m.getPhoneNum() + " ");
        System.out.print(m.getStartDate() + " ");
        System.out.print(m.getUserID() + " ");
        System.out.print(m.getWarehouseDist() + " ");
        System.out.println();

    }

    // display entries in Warehouse

    public static void displayWarehouse(Warehouse w) {
        System.out.print(w.getAddress() + " ");
        System.out.print(w.getCity() + " ");
        System.out.print(w.getMGRName() + " ");
        System.out.print(w.getPhoneNum() + " ");
        System.out.print(w.getStorageCap() + " ");
        System.out.print(w.getDroneCap() + " ");
        System.out.println();

    }}


        
        
        
        
        

        // check the option and then ask what relation they would like to update,
        // based on first option
         
         
         
         
    	
    	/*String select = "SELECT Name FROM Employee WHERE jobTitle = ? AND Warehouse = ?";
    	
    	
    	System.out.println("\nPick a query");
    	System.out.println("1. Print the entire Employee table.");
    	System.out.println("2. Select the name of an employee based on jobTitle = packer and Warehouse = 123 Main St.");
    	System.out.println("3. Add Rental to Database");
    	Scanner scan = new Scanner(System.in);
    	int choice = scan.nextInt();
    	if(choice == 1) {
    		sqlQuery(conn, sqlStatement);
    	} else if (choice == 2){
    		lindseyQuery(conn, select);
    	} else if (choice == 3) {
    		addRental(conn);
    	}*/
    	
    	
    	//System.out.println("*********************************************************************");
    	//System.out.println("Part 6 - Add other queries - Use PreparedStatements");
    	
    	
    	
    	
    	/* finally best approach
		finally{
		   
			/* From JSE7 onwards the try-with-resources statement is introduced. 
			 * The resources in the try block will be closed automatically after the use,
			 * at the end of the try block
			 *  close JDBC objects
			 * If not, use the following block:
		   try {
		      if(rs!=null) rs.close();
		   } catch (SQLException se) {
		      se.printStackTrace();
		   }
		   try {
		      if(stmt !=null) st.close();
		   } catch (SQLException se) {
		      se.printStackTrace();
		   }
		   try {
		      if(conn !=null) con.close();
		   } catch (SQLException se) {
		      se.printStackTrace();
		   }
		}*/
