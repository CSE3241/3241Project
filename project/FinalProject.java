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
	
	private static String DATABASE = "finalProjectDB.db";
	
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
		System.out.println("1 - Enter equipment rental");
		System.out.println("2 - Enter equipment return");
		System.out.println("3 - Deliver rental");
		System.out.println("4 - Pickup return");
		Scanner scan = new Scanner(System.in);
		int selection = scan.nextInt();
		
		
		//rent equipment
		if(selection ==1) {
			displayEquipment(conn);
			rentEquipment(conn, scan);
			
		} 
		//return equipment
		else if (selection == 2) {
			System.out.println("Would you like to see a list of current rentals?");
			System.out.println("1 - YES \n2 - NO");
			int showRentals = scan.nextInt();
			if(showRentals == 1) {
				displayRentals(conn);
			}
			returnEquipment(conn, scan);
		} 
		//deliver
		else if (selection == 3) {
			deliverRental(conn, scan);
		} 
		//pickup
		else if (selection == 4) {
			pickup(conn,scan);
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
	
	public static void pickup(Connection conn, Scanner scan) {
		try {
			
			System.out.println("RETURNS NOT YET PICKED UP: ");
			//show rentals that have been checked in but not picked up
			String needDrone = "SELECT Rental.rentalNum, Rental.modelNum FROM Rental, Contains, Stock " +
					"WHERE Rental.rentalNum = Contains.rentalNum AND Stock.serialNum = Contains.serialNum" +
					"AND Stock.CurrentLoc = 'WAREHOUSE';";
			Statement drones = conn.createStatement();
			ResultSet rs = drones.executeQuery(needDrone);
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
        	
        	System.out.println("Enter the rental number of the return: ");
        	int rentalID = scan.nextInt();
        	
        	System.out.println("AVAILABLE DRONES: ");
        	
        	//show available drones
        	String selectDrones = "SELECT SerialNum, DistAuto, Dname, VolumeCap, MaxSpeed, Year, WeightCap, WareAddr " +
        			"FROM Drone WHERE CurrentLoc = 'WAREHOUSE' AND Status = 'A';";
        	Statement getDrones = conn.createStatement();
        	ResultSet rs1 = getDrones.executeQuery(selectDrones);
        	ResultSetMetaData rsmd1 = rs1.getMetaData();
        	int columnCoun1t = rsmd1.getColumnCount();
        	for (int i = 1; i <= columnCoun1t; i++) {
        		String value = rsmd1.getColumnName(i);
        		System.out.print(value);
        		if (i < columnCoun1t) System.out.print(",  ");
        	}
			System.out.print("\n");
        	while (rs1.next()) {
        		for (int i = 1; i <= columnCoun1t; i++) {
        			String columnValue = rs1.getString(i);
            		System.out.print(columnValue);
            		if (i < columnCoun1t) System.out.print(",  ");
        		}
    			System.out.print("\n");
        	}
        	
        	String eatNewLine = scan.nextLine();
        	
        	System.out.println("Enter the serial number of the drone to pickup the return");
        	String serialNum = scan.nextLine();
        	
        	//update dropDrone in Rental
        	String drone = "UPDATE Rental SET pickUpDrone = '" + serialNum + "' WHERE rentalNum = " + rentalID + ";";
        	Statement setDrone = conn.createStatement();
        	setDrone.executeUpdate(drone);
        	
        	//update drone's current location
        	String location = "UPDATE Drone SET CurrentLoc = 'IN FLIGHT' WHERE serialNum = '" + serialNum + "';";
        	Statement updateLoc = conn.createStatement();
        	updateLoc.executeUpdate(location);
			
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void deliverRental(Connection conn, Scanner scan) {
		
		try {
			//show them the undelivered rentals
			System.out.println("UNDELIVERED RENTALS: ");
			String undelivered = "SELECT * FROM Rental WHERE dropDrone IS NULL";
			Statement findUndelivered = conn.createStatement();
			ResultSet rs = findUndelivered.executeQuery(undelivered);
			
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
        	rs.close();
        	
        	System.out.println("Enter the rentalID of the rental you'd like to deliver: ");
        	int rentalID = scan.nextInt();
        	
        	System.out.println("AVAILABLE DRONES: ");
        	
        	//show available drones
        	String drones = "SELECT SerialNum, DistAuto, Dname, VolumeCap, MaxSpeed, Year, WeightCap, WareAddr " +
        			"FROM Drone WHERE CurrentLoc = 'WAREHOUSE' AND Status = 'A';";
        	Statement getDrones = conn.createStatement();
        	ResultSet rs1 = getDrones.executeQuery(drones);
        	ResultSetMetaData rsmd1 = rs1.getMetaData();
        	int columnCoun1t = rsmd1.getColumnCount();
        	for (int i = 1; i <= columnCoun1t; i++) {
        		String value = rsmd1.getColumnName(i);
        		System.out.print(value);
        		if (i < columnCoun1t) System.out.print(",  ");
        	}
			System.out.print("\n");
        	while (rs1.next()) {
        		for (int i = 1; i <= columnCoun1t; i++) {
        			String columnValue = rs1.getString(i);
            		System.out.print(columnValue);
            		if (i < columnCoun1t) System.out.print(",  ");
        		}
    			System.out.print("\n");
        	}
        	
        	String eatNewLine = scan.nextLine();
        	
        	System.out.println("Enter the serial number of the drone to deliver the rental");
        	String serialNum = scan.nextLine();
        	
        	//update dropDrone in Rental
        	String drone = "UPDATE Rental SET dropDrone = '" + serialNum + "' WHERE rentalNum = " + rentalID + ";";
        	Statement setDrone = conn.createStatement();
        	setDrone.executeUpdate(drone);
        	
        	//update drone's current location
        	String location = "UPDATE Drone SET CurrentLoc = 'IN FLIGHT' WHERE serialNum = '" + serialNum + "';";
        	Statement updateLoc = conn.createStatement();
        	updateLoc.executeUpdate(location);
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void returnEquipment(Connection conn, Scanner scan) {
		System.out.println("Enter the rental ID of what you are returning: ");
		int rentalID = scan.nextInt();
		
		System.out.println("Would you like to return all items in the rental, or only select items?");
		System.out.println("1 - ALL ITEMS \n 2 - SELECT ITEMS");
		int allOrSome = scan.nextInt();
		
		try {
			String selectedItems;
			if(allOrSome == 1) {
				selectedItems = "SELECT serialNum FROM Contains WHERE rentalNum = " + rentalID + ";";
				Statement selectStatement = conn.createStatement();
				ResultSet rs = selectStatement.executeQuery(selectedItems);
				while (rs.next()) {
					//get each serial num
	        		String item = rs.getString(1);
	        		
	        		//set status of that item back to WAREHOUSE
	        		String returnString = "UPDATE Stock SET Status = 'WAREHOUSE' WHERE serialNum = '" + item + "';";
	        		Statement returnItem = conn.createStatement();
	        		returnItem.executeUpdate(returnString);
	        	}
			
			} else {
				String getItems = "SELECT Item FROM Rental WHERE rentalNum = " + rentalID + ";";
				Statement getItemsStmt = conn.createStatement();
				ResultSet rs = getItemsStmt.executeQuery(getItems);
				
				//show the items in the rental
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
	        	
	        	System.out.println("How many items would you like to return?");
	        	int numItems = scan.nextInt();
	        	
	        	String eatNewLine = scan.nextLine();
	        	
	        	
	        	for(int i = 0; i < numItems; i++) {
	        		System.out.println("Enter item " + (i+1) + "'s model number: ");
	        		String modelNum = scan.nextLine();
	        		
	        		String findItem = "SELECT Stock.serialNum FROM Stock, Contains WHERE Contains.serialNum = Stock.serialNum "
	        				+ "AND Stock.modelNum = " + modelNum + " AND Contains.rentalNum = " + rentalID + ";";
	        		Statement returnItem = conn.createStatement();
	        		ResultSet rs1 = returnItem.executeQuery(findItem);
	        		while(rs1.next()) {
	        			String serialNum = rs1.getString(6);
	        			String updateString = "UPDATE Stock SET Status = 'WAREHOUSE' WHERE serialNum = '" + serialNum +"';";
	        			Statement updateStmt = conn.createStatement();
	        			updateStmt.executeUpdate(updateString);
	        		}
	        	}
				
				
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void rentEquipment(Connection conn, Scanner scan) {
		
		
		System.out.println("How many items would you like to rent?");
		int numRentals = scan.nextInt();
		
		System.out.println("Enter a new, unique rental ID number: ");
		int rentalNum = scan.nextInt();
		
		System.out.println("Enter userID of member that is renting: ");
		int userID = scan.nextInt();
		
		String eatNewLine = scan.nextLine();
		
		//for however many items they want to rent, call process rental
		for(int i = 0; i < numRentals; i++) {
			System.out.println("Enter the serial number of Item " + (i+1) + ": ");
			String currentItem = scan.nextLine();
			
			processRental(conn, scan, userID, rentalNum, currentItem);
			
		}
		
	}
	
	public static void processRental(Connection conn, Scanner scan, int userID, int rentalNum, String serialNum) {
		
		try {
			//update status of stock to IN USE
			String status = "UPDATE Stock SET currentLoc = 'IN USE' WHERE serialNum = '" + serialNum + "';";
			Statement updateStatus = conn.createStatement();
			updateStatus.executeUpdate(status);
			
			//add the item to the rental table
			String addRentalString = "INSERT INTO Rental(rentalNum, checkOut, dueDate, Item, Fees, userID, empID)";
			addRentalString += " VALUES(?,?,?,?,?,?,?);";
			//NOTE: the drone attributes are left as NULL, to be handled by check out / pick up
			
			PreparedStatement addRental = conn.prepareStatement(addRentalString);
			
			//not only do you need to add to Rental, but also Contains
			String addContainsString = "INSERT INTO Contains(serialNum, rentalNum) VALUES(?, ?);";
			PreparedStatement addContains = conn.prepareStatement(addContainsString);
			
			addContains.setString(1, serialNum);
			addContains.setInt(2, rentalNum);
			
			//now we're ready to add to Contains table
			addContains.executeUpdate();
			
			
			//next, set up the new rental item
			addRental.setInt(1, rentalNum);
			
			
			System.out.println("Enter today's date (YYYY-MM-DD): ");
			addRental.setString(2, scan.nextLine());
			
			System.out.println("Enter rental due date (YYYY-MM-DD): ");
			addRental.setString(3, scan.nextLine());
			
			//to get item (model number), need to look at Stock table
			String findModel = "SELECT modelNum FROM Stock WHERE serialNum = '" + serialNum + "';";
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
			
			String eatNewLine = scan.nextLine();
			
			//now, we can execute the prepared statement
			addRental.executeUpdate();
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
	public static void displayRentals(Connection conn) {
		String selectString = "SELECT rentalNum, checkOut, dueDate, Item FROM Rental;";
		try {
			Statement selectStmt = conn.createStatement();
			
			ResultSet rs = selectStmt.executeQuery(selectString);
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
	
	public static void displayEquipment(Connection conn){
		
		try {
			System.out.println("EQUIPMENT IN STOCK: ");
			String display = "SELECT SerialNum, modelNum, equipType, Description, arrivalDate, warrExpDate, Year, currentLoc, Warehouse FROM Stock WHERE Status = 'WAREHOUSE';";
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