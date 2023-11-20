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

public class FinalProject{
	
	private static String DATABASE = "projectDB3.db";
	
	 public static Connection initializeDB(String databaseFileName) {
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
	
	
	public static void main(String[] args) {
		
		//initialize the database
		Connection conn = initializeDB(DATABASE);  
		
		System.out.println("Please select from the following options: ");
		System.out.println("1 - Rent equipment");
		System.out.println("2 - Return equipment");
		Scanner scan = new Scanner(System.in);
		int selection = scan.nextInt();
		
		//get user's id
		System.out.println("Enter the User ID of the user renting equipment: ");
		int userID = scan.nextInt();
		
		//rent equipment
		if(selection ==1) {
			displayEquipment(conn);
			rentEquipment(conn, scan);
			
		} 
		//return equipment
		else if (selection == 2) {
			
		}
		
		//did not select one of the options
		else {
			System.out.println("Invalid selection.");
		}
		
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		scan.close();
	}
	
	public static void rentEquipment(Connection conn, Scanner scan) {
		
		
		System.out.println("How many items would you like to rent?");
		int numRentals = scan.nextInt();
		
		System.out.println("Enter a new, unique rental ID number: ");
		int rentalNum = scan.nextInt();
		
		System.out.println("Enter userID of member that is renting: ");
		int userID = scan.nextInt();
		
		//for however many items they want to rent, call process rental
		for(int i = 0; i < numRentals; i++) {
			System.out.println("Enter the serial number of Item " + (i+1) + ": ");
			int currentItem = scan.nextInt();
			
			processRental(conn, scan, userID, rentalNum, currentItem);
			
		}
		
		
		
		
	}
	
	public static void processRental(Connection conn, Scanner scan, int userID, int rentalNum, int serialNum) {
		
		try {
			//update status of stock to IN USE
			String status = "UPDATE Stock SET currentLoc = \"IN USE\" WHERE serialNum =" + serialNum + ";";
			
			//add the item to the rental table
			String addRentalString = "INSERT INTO Rental(rentalNum, checkOut, dueDate, Item, Fees, userID, empID)";
			addRentalString += "VALUES(?,?,?,?,?,?,?)";
			//NOTE: the drone attributes are left as NULL, to be handled by check out / pick up
			
			PreparedStatement addRental = conn.prepareStatement(addRentalString);
			
			//not only do you need to add to Rental, but also Contains
			String addContainsString = "INSERT INTO Contains(serialNum, rentalNum) VALUES(?, ?);";
			PreparedStatement addContains = conn.prepareStatement(addContainsString);
			
			addContains.setInt(1, serialNum);
			addContains.setInt(2, rentalNum);
			
			//now we're ready to add to Contains table
			addContains.executeUpdate();
			
			
			//next, set up the new rental item
			addRental.setInt(1, rentalNum);
			
			System.out.println("Enter today's date: ");
			Date checkOut = Date.valueOf(scan.nextLine());
			addRental.setDate(2, checkOut);
			
			System.out.println("Enter rental due date: ");
			Date dueDate = Date.valueOf(scan.nextLine());
			addRental.setDate(3, dueDate);
			
			//to get item (model number), need to look at Stock table
			String findModel = "SELECT modelNum FROM Stock WHERE serialNum = " + serialNum + ";";
			Statement findModelNum = conn.createStatement();
			ResultSet rs = findModelNum.executeQuery(findModel);
			String modelNum = rs.getString(1);
			addRental.setString(4, modelNum);
			
			System.out.println("Enter rental fees: ");
			double fees = scan.nextDouble();
			addRental.setDouble(5, fees);
			
			//we already have userID from other method
			addRental.setInt(6, userID);
			
			System.out.println("Enter the ID of the employee to fulfill this order: ");
			int empID = scan.nextInt();
			addRental.setInt(7, empID);
			
			//now, we can execute the prepared statement
			addRental.executeUpdate();
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		
		
	}
	
	public static void displayEquipment(Connection conn){
		
		try {
			String display = "SELECT * FROM Stock WHERE Status = \"WAREHOUSE\";";
			Statement sql = conn.createStatement();
			ResultSet rs = sql.executeQuery(display);
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
}