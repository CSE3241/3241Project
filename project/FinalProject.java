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
	
	private static String DATABASE = "finalDB.db";
	
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
		
		int selection = 0;
		Scanner scan = new Scanner(System.in);
		while (selection != 6) {
		System.out.println("Please select from the following options: ");
		System.out.println("1 - Enter equipment rental");
		System.out.println("2 - Enter equipment return");
		System.out.println("3 - Deliver rental");
		System.out.println("4 - Pickup return");
		System.out.println("5 - Add/Modify/Remove/Retrieve Equipment");
		System.out.println("6 - Exit program");
		
		selection = scan.nextInt();
		
		
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
		//modify equipment
		else if (selection == 5) {
			System.out.println("Would you like to :");
			System.out.println("1 - ADD EQUIPMENT");
			System.out.println("2 - MODIFY EQUIPMENT");
			System.out.println("3 - REMOVE EQUIPMENT");
			System.out.println("4 - RETRIEVE EQUIPMENT");
			int choice = scan.nextInt();
			
			System.out.println("Would you like to view all equipment?");
			System.out.println("1 - YES");
			System.out.println("2 - NO");
			int viewAll = scan.nextInt();
			if(viewAll == 1) {
				showAll(conn);
			}
			
			if(choice == 1) {
				add(conn,scan);
			} else if (choice == 2) {
				modify(conn,scan);
			} else if (choice == 3) {
				delete(conn,scan);
			} else if (choice == 4) {
				get(conn,scan);
			} else {
				System.out.println("Invalid selection.");
			}
			
		}
		else if(selection == 6) {
			System.out.println("Goodbye!");
		}
		
		//did not select one of the options
		else {
			System.out.println("Invalid selection.");
		}
		
		}
		scan.close();
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void get(Connection conn, Scanner scan) {
		try {
			
			
			System.out.println("Would you like to search with a select (rows) or a project (columns) operation?");
			System.out.println("1 - SELECT");
			System.out.println("2 - PROJECT");
			
			int op = scan.nextInt();
			String eatLine = scan.nextLine();
			
			ResultSet rs;
			if(op == 1) {
				System.out.println("Which item would like to select? Enter serial number: ");
				String select = "SELECT * FROM Stock WHERE SerialNum = ?";
				PreparedStatement prep = conn.prepareStatement(select);
				prep.setString(1, scan.nextLine());
				 rs = prep.executeQuery();
			} else {
				System.out.println("Which attribute would you like to project? Enter column name: ");
				String select = "SELECT ? FROM Stock";
				PreparedStatement prep = conn.prepareStatement(select);
				prep.setString(1, scan.nextLine());
				 rs = prep.executeQuery();
			}
			
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
	
	public static void modify(Connection conn, Scanner scan) {
		try {
			String eatNewLine = scan.nextLine();
			
			String change = "UPDATE Stock SET Description = ? WHERE serialNum = ?;";
			PreparedStatement changeStmt = conn.prepareStatement(change);
			
			System.out.println("Which item would you like to modify? Enter serial number: ");
			String serialNum = scan.nextLine();
			changeStmt.setString(2, serialNum);
			
			
			System.out.println("What would you like to change the description to? Enter here: ");
			
			String eatNewLine2 = scan.nextLine();
			changeStmt.setString(1, scan.nextLine());
			
			
			int result = changeStmt.executeUpdate();
			
			if(result == 1) {
				System.out.println("Item successfully modified.");
			} else {
				System.out.println("Error modifying item");
			}
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void add(Connection conn, Scanner scan) {
		
		try {
			
		
			String addString = "INSERT INTO Stock(modelNum,Status,Description,arrivalDate,warrExpDate,SerialNum,Year,equipType,currentLoc,Height,Width,Length,Weight,Warehouse,Manufacturer,orderNum) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		
			PreparedStatement add = conn.prepareStatement(addString);
			
			String eatNewLine = scan.nextLine();
			
			System.out.println("modelNum (7 characters):");
			add.setString(1, scan.nextLine());
			
			add.setString(2, "WAREHOUSE");
			
			System.out.println("Description: ");
			add.setString(3, scan.nextLine());
			System.out.println("arrivalDate (YYYY-MM-DD): ");
			add.setString(4, scan.nextLine());
			System.out.println("warrExpDate (YYYY-MM-DD): ");
			add.setString(5, scan.nextLine());
			System.out.println("SerialNum (8 characters): ");
			add.setString(6, scan.nextLine());
			System.out.println("Year (YYYY): ");
			add.setString(7, scan.nextLine());
			System.out.println("equipType: ");
			add.setString(8, scan.nextLine());
			
			add.setString(9, "WAREHOUSE");
			
			System.out.println("Warehouse: ");
			add.setString(14, scan.nextLine());
			System.out.println("Manufacturer: ");
			add.setString(15, scan.nextLine());
			
			System.out.println("Height: ");
			add.setDouble(10, scan.nextDouble());
			System.out.println("Width: ");
			add.setDouble(11, scan.nextDouble());
			System.out.println("Length: ");
			add.setDouble(12, scan.nextDouble());
			System.out.println("Weight: ");
			add.setDouble(13, scan.nextDouble());
			
			System.out.println("orderNum: ");
			add.setInt(16, scan.nextInt());
			
			int result = add.executeUpdate();
			
			if(result == 1) {
				System.out.println("Item successfully added.");
			} else {
				System.out.println("Error adding item");
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void delete(Connection conn, Scanner scan) {
		
		try {
			
			String eatNewLine = scan.nextLine();
			
			System.out.println("Enter the serial number of the item you would like to remove: ");
			String serialNum = scan.nextLine();
			
			String removeString = "DELETE FROM Stock WHERE SerialNum = ?;";
			
			PreparedStatement remove = conn.prepareStatement(removeString);
			
			remove.setString(1, serialNum);
			
			int removed = remove.executeUpdate();
			
			if(removed == 1) {
				System.out.println("Item successfully removed.");
			} else {
				System.out.println("Error removing item");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		
		
	}
	
	public static void showAll(Connection conn) {
		try {
			System.out.println("ALL EQUIPMENT: ");
			String display = "SELECT SerialNum, modelNum, equipType, Description, arrivalDate, warrExpDate, Year, currentLoc, Warehouse FROM Stock;";
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
	
	public static void pickup(Connection conn, Scanner scan) {
		try {
			
			System.out.println("RETURNS NOT YET PICKED UP: ");
			//show rentals that have been checked in but not picked up
			String needDrone = "SELECT Rental.rentalNum, Contains.serialNum, Rental.checkOut, Rental.dueDate, Rental.userID, Rental.empID FROM Rental, Contains, Stock " +
					"WHERE Rental.rentalNum = Contains.rentalNum AND Stock.SerialNum = Contains.serialNum" +
					" AND Stock.Status = 'WAREHOUSE' AND Stock.currentLoc != 'WAREHOUSE' AND Rental.pickUpDrone IS NULL;";
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
        	
        	System.out.println("Enter the serial number of the drone to pickup the return: ");
        	String serialNum = scan.nextLine();
        	
        	//update dropDrone in Rental
        	String drone = "UPDATE Rental SET pickUpDrone = '" + serialNum + "' WHERE rentalNum = " + rentalID + ";";
        	Statement setDrone = conn.createStatement();
        	int droneResult = setDrone.executeUpdate(drone);
        	
        	//update drone's current location
        	String location = "UPDATE Drone SET CurrentLoc = 'IN FLIGHT' WHERE serialNum = '" + serialNum + "';";
        	Statement updateDrone = conn.createStatement();
        	int locationResult = updateDrone.executeUpdate(location);
        	
        	String itemsString = "SELECT Stock.SerialNum FROM Stock, Contains WHERE Stock.SerialNum = Contains.serialNum AND Contains.rentalNum = " + rentalID + ";";
			Statement itemsStmt = conn.createStatement();
			ResultSet numItems = itemsStmt.executeQuery(itemsString);
			
			int itemLocResult = 0;
			while(numItems.next()) {
				String currentLoc = "UPDATE Stock SET currentLoc = 'WAREHOUSE' WHERE SerialNum = '" + numItems.getString(1) + "';";
				Statement updateLoc = conn.createStatement();
				itemLocResult += updateLoc.executeUpdate(currentLoc);
			}
			
			if(droneResult == 1 && locationResult == 1 && itemLocResult > 0) {
				System.out.println("Return successfully picked up by drone.");
			} else {
				System.out.println("Possible error picking up return.");
			}
			
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void deliverRental(Connection conn, Scanner scan) {
		
		try {
			//show them the undelivered rentals
			System.out.println("UNDELIVERED RENTALS: ");
			String undelivered = "SELECT rentalNum, checkOut, dueDate, Fees, userID, empID FROM Rental WHERE dropDrone IS NULL";
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
        	int droneResult = setDrone.executeUpdate(drone);
        	
        	//update drone's current location
        	String location = "UPDATE Drone SET CurrentLoc = 'IN FLIGHT' WHERE serialNum = '" + serialNum + "';";
        	Statement updateDroneLoc = conn.createStatement();
        	int droneLocResult = updateDroneLoc.executeUpdate(location);
        	
        	//finally, update currentLoc in stock to user's address
        	String getUser = "SELECT userID FROM Rental WHERE rentalNum = " + rentalID + ";";
        	Statement userStmt = conn.createStatement();
        	ResultSet userResult = userStmt.executeQuery(getUser);
        	String userID = userResult.getString(1);

			String getAddress = "SELECT Address FROM Member WHERE userID = " + userID + ";";
			Statement addressStmt = conn.createStatement();
			ResultSet addressResult = addressStmt.executeQuery(getAddress);
			String address = addressResult.getString(1);
			
			String itemsString = "SELECT Stock.SerialNum FROM Stock, Contains WHERE Stock.SerialNum = Contains.serialNum AND Contains.rentalNum = " + rentalID + ";";
			Statement itemsStmt = conn.createStatement();
			ResultSet numItems = itemsStmt.executeQuery(itemsString);
			
			int itemLocResult = 0;
			while(numItems.next()) {
				String currentLoc = "UPDATE Stock SET currentLoc = '" + address + "' WHERE SerialNum = '" + numItems.getString(1) + "';";
				Statement updateLoc = conn.createStatement();
				itemLocResult += updateLoc.executeUpdate(currentLoc);
			}
			
			
			if(droneResult == 1 && droneLocResult == 1 && itemLocResult >= 1) {
				System.out.println("Rental was delivered to " + address +".");
			} else {
				System.out.println("Possible error delivering rental.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void returnEquipment(Connection conn, Scanner scan) {
		System.out.println("Enter the rental ID of what you are returning: ");
		int rentalID = scan.nextInt();
		
		System.out.println("Would you like to return all items in the rental, or only select items?");
		System.out.println("1 - ALL ITEMS \n2 - SELECT ITEMS");
		int allOrSome = scan.nextInt();
		
		try {
			String selectedItems;
			if(allOrSome == 1) {
				selectedItems = "SELECT serialNum FROM Contains WHERE rentalNum = " + rentalID + ";";
				Statement selectStatement = conn.createStatement();
				ResultSet rs = selectStatement.executeQuery(selectedItems);
				int itemsReturned = 0;
				while (rs.next()) {
					//get each serial num
	        		String item = rs.getString(1);
	        		
	        		//set status of that item back to WAREHOUSE
	        		String returnString = "UPDATE Stock SET Status = 'WAREHOUSE' WHERE SerialNum = '" + item + "';";
	        		Statement returnItem = conn.createStatement();
	        		itemsReturned += returnItem.executeUpdate(returnString);
	        	}
				if(itemsReturned > 0) {
					System.out.println(itemsReturned + " items were marked for return. A drone will soon be assigned to pick them up.");
			
				} else {
					System.out.println("Possible error returning items.");
				}
			} else {
				String getItems = "SELECT Contains.rentalNum, Contains.serialNum, Rental.checkOut, Rental.dueDate, Rental.userID FROM Contains, Rental "
						+ "WHERE Rental.rentalNum = Contains.rentalNum AND Contains.rentalNum = " + rentalID + ";";
				Statement getItemsStmt = conn.createStatement();
				ResultSet rs = getItemsStmt.executeQuery(getItems);
				
				System.out.println("ITEMS IN RENTAL: ");
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
	        		System.out.println("Enter item " + (i+1) + "'s serial number: ");
	        		String serialNum = scan.nextLine();
	        		
	        		String updateString = "UPDATE Stock SET Status = 'WAREHOUSE' WHERE SerialNum = '" + serialNum +"';";
        			Statement updateStmt = conn.createStatement();
        			int result = updateStmt.executeUpdate(updateString);
        			if(result == 1) {
        				System.out.println("Item was marked for return. A drone will soon be assigned to pick it up.");
        			} else {
        				System.out.println("Error adding item to return.");
        			}
	        		
	        	}
	        	System.out.println("Return order complete.");
				
				
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void rentEquipment(Connection conn, Scanner scan) {
		
		try {
		System.out.println("How many items would you like to rent?");
		int numRentals = scan.nextInt();
		
		System.out.println("Enter a new, unique rental ID number: ");
		int rentalNum = scan.nextInt();
		
		System.out.println("Enter userID of member that is renting: ");
		int userID = scan.nextInt();
		
		System.out.println("Enter the ID of the employee to fulfill this rental: ");
		int empID = scan.nextInt();
		
		String eatNewLine = scan.nextLine();
		
		System.out.println("Enter today's date (YYYY-MM-DD): ");
		String date1 = scan.nextLine();
		
		System.out.println("Enter due date (YYYY-MM-DD): ");
		String date2 = scan.nextLine();
		
		System.out.println("Enter fees: ");
		double fees = scan.nextDouble();
		
		//add the item to the rental table
		String addRentalString = "INSERT INTO Rental(rentalNum, checkOut, dueDate, Fees, userID, empID)";
		addRentalString += " VALUES(?,?,?,?,?,?);";
		//NOTE: the drone attributes are left as NULL, to be handled by check out / pick up
		
		PreparedStatement addRental = conn.prepareStatement(addRentalString);
		addRental.setInt(1, rentalNum);
		addRental.setString(2, date1);
		addRental.setString(3, date2);
		addRental.setDouble(4, fees);
		addRental.setInt(5, userID);
		addRental.setInt(6, empID);
		
		String eatNewLine2 = scan.nextLine();
		
		int numItems = 0;
		
		
		//for however many items they want to rent and to contains
		for(int i = 0; i < numRentals; i++) {
			System.out.println("Enter the serial number of Item " + (i+1) + ": ");
			String serialNum = scan.nextLine();
			
			//update status of stock to IN USE
			String status = "UPDATE Stock SET Status = 'IN USE' WHERE SerialNum = '" + serialNum + "';";
			Statement updateStatus = conn.createStatement();
			updateStatus.executeUpdate(status);
			
			String addContainsString = "INSERT INTO Contains(serialNum, rentalNum) VALUES(?, ?);";
			PreparedStatement addContains = conn.prepareStatement(addContainsString);
			
			addContains.setString(1, serialNum);
			addContains.setInt(2, rentalNum);
			
			//now we're ready to add to Contains table
			numItems += addContains.executeUpdate();
			
			
		}
		
		int result = addRental.executeUpdate();
		if(result == 1 && numItems > 0) {
			System.out.println("Successfully created rental! " + numItems + " item(s) are in the order.");
		} else {
			System.out.println("Error creating rental.");
		}
		
		System.out.println("Rental order placed. A drone will soon be assigned to drone it off.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	public static void displayRentals(Connection conn) {
		System.out.println("RENTALS IN USE: ");
		String selectString = "SELECT Rental.rentalNum, Rental.checkOut, Rental.dueDate, Stock.SerialNum, Stock.modelNum FROM Rental, Contains, Stock"
				+ " WHERE Contains.serialNum = Stock.SerialNum AND Contains.rentalNum = Rental.rentalNum AND Stock.Status = 'IN USE' AND Rental.dropDrone IS NOT NULL;";
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