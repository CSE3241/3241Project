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

public class FinalCode {
    
	/**
	 *  The database file name.
	 *  
	 *  Make sure the database file is in the root folder of the project if you only provide the name and extension.
	 *  
	 *  Otherwise, you will need to provide an absolute path from your C: drive or a relative path from the folder this class is in.
	 */
	private static String DATABASE = "projectDB3.db";
	
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
    
    /**
     * Queries the database and prints the results.
     * 
     * @param conn a connection object
     * @param sql a SQL statement that returns rows
     * This query is written with the Statement class, typically  
     * used for static SQL SELECT statements
     */    
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
    
    
    
    
    
    /** Here begins the methods we wrote for the checkpoint **/
    
    //Prompts the user to add a member
    public static void addMember(Connection conn) {
    	
    		Scanner in = new Scanner(System.in);    				
    		System.out.println("Enter the userID of the record: ");
            int userID = in.nextInt();
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
            
            insertMember(conn, userID, community, status, fName, lName, warehouseDist, phoneNum, address, email, startDate);
            
            in.close();
            
    	} 
        
    //Inserts a member
    public static void insertMember(Connection conn, int userID, String community, String 	status, String Fname, String Lname, String warehouseDist, String phoneNum, String 	address, String email, String startDate) 
{

    	String sql = "INSERT INTO MEMBER (userID, Community, Status, Fname, Lname, warehouseDist, phoneNum, Address, Email, startDate) VALUES(?,?,?, ?, ?, ?, ?, ?, ?, ?)";

    	try (PreparedStatement ps = conn.prepareStatement(sql)) {
    		ps.setInt(1, userID);
    		ps.setString(2, community);
    		ps.setString(3, status);
    		ps.setString(4, Fname);
    		ps.setString(5, Lname);
    		ps.setString(6, warehouseDist);
    		ps.setString(7, phoneNum);
    		ps.setString(8, address);
    		ps.setString(9, email);
    		ps.setString(10, startDate);
    		ps.executeUpdate();
    		
    	} catch (SQLException e) {
        		    		
        		System.out.println(e.getMessage());    		
        	}  
    	

    	}
        
    //Prompts the user to add a drone
    public static void addDrone(Connection conn) {
    	
		Scanner in = new Scanner(System.in);    				
		System.out.println("Enter the Manufacturer of the record: ");
        String Manufacturer = in.nextLine();
        System.out.println("Enter the CurrentLoc of the record: ");
        String CurrentLoc = in.nextLine();
        System.out.println("Enter the DistAuto of the record: ");
        String DistAuto = in.nextLine();
        System.out.println("Enter the Status of the record: ");
        String Status = in.nextLine();
        System.out.println("Enter the Dname of the record: ");
        String Dname = in.nextLine();
        System.out.println("Enter the VolumeCap of the record: ");
        String VolumeCap = in.nextLine();
        System.out.println("Enter the ModelNum of the record: ");
        String ModelNum = in.nextLine();
        System.out.println("Enter the MaxSpeed of the record: ");
        String MaxSpeed = in.nextLine();
        System.out.println("Enter the Year of the record: ");
        String Year = in.nextLine();
        System.out.println("Enter the WarrExpDate of the record: ");
        String WarrExpDate = in.nextLine(); 
        System.out.println("Enter the SerialNum of the record: ");
        String SerialNum = in.nextLine();
        System.out.println("Enter the WeightCap of the record: ");
        String WeightCap = in.nextLine();
        System.out.println("Enter the WareAddr of the record: ");
        String WareAddr = in.nextLine();  
        System.out.println("Enter the OrderNum of the record: ");
        String OrderNum = in.nextLine();
         
        
        insertDrone(conn, Manufacturer, CurrentLoc, DistAuto, Status, Dname, VolumeCap, ModelNum, MaxSpeed, Year, WarrExpDate, SerialNum, WeightCap, WareAddr, OrderNum);
        
        in.close();
        
	} 
      
    //Inserts a member
    public static void insertDrone(Connection conn, String Manufacturer, String CurrentLoc, String DistAuto, String Status, String Dname, String VolumeCap, String ModelNum, String MaxSpeed, String Year, String WarrExpDate, String SerialNum, String WeightCap, String WareAddr, String OrderNum) 
{

    	String sql = "INSERT INTO DRONE (Manufacturer, CurrentLoc, DistAuto, Status, Dname, VolumeCap, ModelNum, MaxSpeed, Year, WarrExpDate, SerialNum, WeightCap, WareAddr, OrderNum) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    	try (PreparedStatement ps = conn.prepareStatement(sql)) {
    		ps.setString(1, Manufacturer);
    		ps.setString(2, CurrentLoc);
    		ps.setString(3, DistAuto);
    		ps.setString(4, Status);
    		ps.setString(5, Dname);
    		ps.setString(6, VolumeCap);
    		ps.setString(7, ModelNum);
    		ps.setString(8, MaxSpeed);
    		ps.setString(9, Year);
    		ps.setString(10, WarrExpDate);
    		ps.setString(11, SerialNum);
    		ps.setString(12, WeightCap);
    		ps.setString(13, WareAddr);
    		ps.setString(14, OrderNum);
    		ps.executeUpdate(); 		
    		
    	} catch (SQLException e) {
        		    		
        		System.out.println(e.getMessage());    		
        	}  
    	

    	}
    
    //Prompts the user to add equipment
    public static void addStock(Connection conn) {
    	
    	Scanner in = new Scanner(System.in);    				
		System.out.println("Enter the modelNum of the record: ");
        String modelNum = in.nextLine();
        System.out.println("Enter the Status of the record: ");
        String Status = in.nextLine();
        System.out.println("Enter the Description of the record: ");
        String Description = in.nextLine();
        System.out.println("Enter the arrivalDate of the record: ");
        String arrivalDate = in.nextLine();
        System.out.println("Enter the warrExpDate of the record: ");
        String warrExpDate = in.nextLine();
        System.out.println("Enter the SerialNum of the record: ");
        String SerialNum = in.nextLine();
        System.out.println("Enter the Year of the record: ");
        String Year = in.nextLine();
        System.out.println("Enter the equipType of the record: ");
        String equipType = in.nextLine();
        System.out.println("Enter the currentLoc of the record: ");
        String currentLoc = in.nextLine();
        System.out.println("Enter the Height of the record: ");
        String Height = in.nextLine(); 
        System.out.println("Enter the Width of the record: ");
        String Width = in.nextLine();
        System.out.println("Enter the Length of the record: ");
        String Length = in.nextLine();
        System.out.println("Enter the Weight of the record: ");
        String Weight = in.nextLine();  
        System.out.println("Enter the Warehouse of the record: ");
        String Warehouse = in.nextLine();
        System.out.println("Enter the Manufacturer of the record: ");
        String Manufacturer = in.nextLine();  
        System.out.println("Enter the orderNum of the record: ");
        String orderNum = in.nextLine();
         
        
        insertStock(conn, modelNum, Status, Description, arrivalDate, warrExpDate, SerialNum, Year, equipType, currentLoc, Height, Width, Length, Weight, Warehouse, Manufacturer, orderNum);
        
        in.close();  
        
    }
        
    //Inserts equipment
    public static void insertStock(Connection conn, String Manufacturer, String CurrentLoc, String DistAuto, String Status, String Dname, String VolumeCap, String ModelNum, String MaxSpeed, String Year, String WarrExpDate, String SerialNum, String WeightCap, String WareAddr, String OrderNum, String extra1, String extra2) 
    {

        	String sql = "INSERT INTO STOCK (modelNum, Status, Description, arrivalDate, warrExpDate, SerialNum, Year, equipType, currentLoc, Height, Width, Length, Weight, Warehouse, Manufacturer, orderNum) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        	try (PreparedStatement ps = conn.prepareStatement(sql)) {
        		ps.setString(1, Manufacturer);
        		ps.setString(2, CurrentLoc);
        		ps.setString(3, DistAuto);
        		ps.setString(4, Status);
        		ps.setString(5, Dname);
        		ps.setString(6, VolumeCap);
        		ps.setString(7, ModelNum);
        		ps.setString(8, MaxSpeed);
        		ps.setString(9, Year);
        		ps.setString(10, WarrExpDate);
        		ps.setString(11, SerialNum);
        		ps.setString(12, WeightCap);
        		ps.setString(13, WareAddr);
        		ps.setString(14, OrderNum);
        		ps.setString(15, extra1);
        		ps.setString(16, extra2);
        		ps.executeUpdate();    		
        		
        	} catch (SQLException e) {
            		    		
            		System.out.println(e.getMessage());    		
            	}  
        	

        	}
    
    //deletes an entry of Member based on userID
    public static void deleteMember(Connection conn, int userID) {
    	String sql = "DELETE FROM MEMBER WHERE userID = ?";
    	try (PreparedStatement ps = conn.prepareStatement(sql)) {
    		ps.setInt(1, userID);
    		ps.executeUpdate();}
    	catch (SQLException e) {
        		    		
        		System.out.println(e.getMessage());    		
        	 }
    	}
        
    //deletes an entry of Stock based on serialNum
    public static void deleteStock(Connection conn, String serialNum) {
    	String sql = "DELETE FROM STOCK WHERE serialNum = ?";
    	try (PreparedStatement ps = conn.prepareStatement(sql)) {
    		ps.setString(1, serialNum);
    		ps.executeUpdate();}
    	catch (SQLException e) {
        		    		
        		System.out.println(e.getMessage());    		
        	 }}
        
    //deletes an entry of Drone based on serialNumber
    public static void deleteDrone(Connection conn, String serialNum) {
    	String sql = "DELETE FROM DRONE WHERE serialNum = ?";
    	try (PreparedStatement ps = conn.prepareStatement(sql)) {
    		ps.setString(1, serialNum);
    		ps.executeUpdate();}
    	catch (SQLException e) {
        		    		
        		System.out.println(e.getMessage());    		
        	 }}
       
    //edits member
    public static void editMember(Connection conn, int userID, String editAttribute, Object newValue) {
    String sql = "UPDATE MEMBER SET "+editAttribute+" = ? WHERE userID = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
    	ps.setObject(1, newValue);
    	ps.setInt(2, userID);
    	ps.executeUpdate();}
    	catch (SQLException e) {
    		    		
    		System.out.println(e.getMessage());    		
    	}  
    
    }
    
    //edits stock
    public static void editStock(Connection conn, String serialNum, String editAttribute, Object newValue) {
    	String sql = "UPDATE STOCK SET "+editAttribute+" = ? WHERE SerialNum = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
    	ps.setObject(1, newValue);
    	ps.setString(2, serialNum);
    	ps.executeUpdate();}
    	catch (SQLException e) {
    		    		
    		System.out.println(e.getMessage());    		
    	}  }
    
    //edits drone
    public static void editDrone(Connection conn, String serialNum, String editAttribute, Object newValue)  {
    	String sql = "UPDATE DRONE SET "+editAttribute+" = ? WHERE SerialNum = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
    	ps.setObject(1, newValue);
    	ps.setString(2, serialNum);
    	ps.executeUpdate();}
    	catch (SQLException e) {
    		    		
    		System.out.println(e.getMessage());    		
    	}  }
          
   //checks out stock
    public static void checkOut(Connection conn, String serialNum) {
    	String sql = "UPDATE STOCK SET Status = \"IN USE\" WHERE SerialNum = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
    	ps.setString(1, serialNum);
    	ps.executeUpdate();}
    	catch (SQLException e) {
    		    		
    		System.out.println(e.getMessage());    		
    	}  }
    
    //Compiles Rental Data
    public static void addRental(Connection conn, String Item) {   	
    	Scanner in = new Scanner(System.in);    				
		//System.out.println("Enter the modelNum of the record: ");
        String rentalNum = "555555";
        //System.out.println("Enter the Status of the record: ");
        String checkOut = "2023-02-02";
        //System.out.println("Enter the Description of the record: ");
        String dueDate = "2023-03-02";
        //System.out.println("Enter the item number: ");
        //String Item = in.nextLine();
        //System.out.println("Enter the warrExpDate of the record: ");
        String Fees = "0";
        //System.out.println("Enter the SerialNum of the record: ");
        String userID = "7";
        //System.out.println("Enter the Year of the record: ");
        String empID = "123421211";
        //System.out.println("Enter the equipType of the record: ");
        String dropDrone = "10000001";
        //System.out.println("Enter the currentLoc of the record: ");
        String pickUpDrone = "10000001";
        
         
        
        insertRental(conn, rentalNum, checkOut, dueDate, Item, Fees, userID, empID, dropDrone, pickUpDrone);
        
        in.close();  
        
    }
    
    //Inserts a rental
    //didn't change variable names
    public static void insertRental(Connection conn, String userID, String community, String 	status, String Fname, String Lname, String warehouseDist, String phoneNum, String 	address, String email) 
{

    	String sql = "INSERT INTO RENTAL (rentalNum, checkOut, dueDate, Item, Fees, userID, empID, dropDrone, pickUpDrone) VALUES(?,?,?, ?, ?, ?, ?, ?, ?)";

    	try (PreparedStatement ps = conn.prepareStatement(sql)) {
    		ps.setString(1, userID);
    		ps.setString(2, community);
    		ps.setString(3, status);
    		ps.setString(4, Fname);
    		ps.setString(5, Lname);
    		ps.setString(6, warehouseDist);
    		ps.setString(7, phoneNum);
    		ps.setString(8, address);
    		ps.setString(9, email);
    		ps.executeUpdate();
    		
    	} catch (SQLException e) {
        		    		
        		System.out.println(e.getMessage());    		
        	}  
    	

    	}
      
   //checks out stock
    public static void checkIn(Connection conn, String serialNum) {
    	String sql = "UPDATE STOCK SET Status = \"WAREHOUSE\" WHERE SerialNum = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
    	ps.setString(1, serialNum);
    	ps.executeUpdate();}
    	catch (SQLException e) {
    		    		
    		System.out.println(e.getMessage());
    	}  }

    
    /**Example from in-class lab**/
    
    public static void main(String[] args) {
    	System.out.println("This is a new run");
    	Connection conn = initializeDB(DATABASE);  	
    	Scanner in = new Scanner(System.in);
    	
    	
        System.out.println("Welcome to the homepage for the database system. Please "
                + "read the commands and enter what you would like to do.");
        System.out.println();

        System.out.println("1 - Add New Records");
        System.out.println("2 - Edit/Delete Records");
        System.out.println("3 - Search");
        System.out.println("4 - Find Useful Reports");
        System.out.println("5 - Check Out/Return Stock");
        System.out.println();

        

        System.out.print("Enter a valid option (1/2/3/4/5): ");
        int whichFunction = in.nextInt();
        System.out.println();
        
        
        //add new record
        if (whichFunction == 1) {
            System.out.println("What relation would you like to add a record to?");
            System.out.println();
            System.out.println("1 - STOCK");
            System.out.println("2 - MEMBER");
            System.out.println("3 - DRONE");
            System.out.println();
            System.out.println("Enter a valid option (1/2/3): ");
            int whichEntity = in.nextInt();
            System.out.println();

            //equipment
            if (whichEntity == 1) {
                addStock(conn);
            } 
            //member
            else if (whichEntity == 2) {
                addMember(conn);
            } 
            //drone
            else if (whichEntity == 3) {
                addDrone(conn);
            } 
            //invalid input
            else {
            	System.out.println("Invalid option entered");
            }
            
        } 
        
        //edit or delete
        else if (whichFunction == 2) {
            System.out.println(
                    "Which relation would you like to edit or delete a record?");
            System.out.println();
            System.out.println("1 - STOCK");
            System.out.println("2 - MEMBER");
            System.out.println("3 - DRONE");
            System.out.println();
            System.out.print("Enter a valid option (1/2/3): ");
            int whichEntity = in.nextInt();
            in.nextLine();

            System.out.println();


            System.out.println("Are you editing or deleting a record?");
            System.out.println("1 - editing");
            System.out.println("2 - deleting");
            System.out.print("Enter a valid option (1/2): ");
            int editOrDelete = in.nextInt();
            in.nextLine();

            //Equipment
            if(whichEntity == 1) {
            	//edit
            	if(editOrDelete == 1) {
            		
            		System.out.println("Enter the Serial Number of the stock you would like to edit ");
            		System.out.println();
                    String stockEditID = in.nextLine();
                    
                    
                    
                    System.out.println("Enter the new description for the item ");
                    String stockEditDescription = in.nextLine();    
                    
                    
                    
                    
            		editStock(conn,stockEditID,"Description",stockEditDescription);
            	} 
            	//delete
            	else if (editOrDelete == 2) {
            		
            		
            		//come back and protect against misinputs
            		System.out.print("Enter the Serial Number of the stock you would like to delete ");            		
                    String stockDeleteID = in.nextLine();               
            		deleteStock(conn, stockDeleteID);
            	}
            	//invalid input
            	else {
            		System.out.println("Invalid option selected for Edit or Delete.");
            	}
            	
            } 
            
            //Member
            else if(whichEntity == 2) {
            	//edit
            	if(editOrDelete == 1) {
            		
            		System.out.println("Enter the USER_ID for the member you would like to edit ");
            		System.out.println();
                    int memberEditID = in.nextInt();
                    in.nextLine();                   
                    System.out.println("Enter the new address for the user ");
                    String memberEditAddress = in.nextLine();    
            		
            		editMember(conn, memberEditID, "Address", memberEditAddress);
            	} 
            	//delete
            	else if (editOrDelete == 2) {
            		
            		System.out.println("Enter the USER_ID for the member you would like to delete ");
            		System.out.println();
                    int memberDeleteID = in.nextInt();
                    in.nextLine();
            		deleteMember(conn, memberDeleteID);
            	}
            	//invalid input
            	else {
            		System.out.println("Invalid option selected for Edit or Delete.");
            	}
            } 
            
            //Drone
            else if (whichEntity == 3){
            	//edit
            	if(editOrDelete == 1) {
            		System.out.println("Enter the Serial Number of the drone you would like to edit ");
            		System.out.println();
                    String droneEditID = in.nextLine();
                    
                    
                    System.out.println("Enter the new DistAuto for the drone ");
                    String droneDistAuto = in.nextLine();
            		editDrone(conn, droneEditID, "DistAuto", droneDistAuto);
            	} 
            	//delete
            	else if (editOrDelete == 2) {
            		
            		System.out.println("Enter the Serial Number of the drone you would like to delete ");
            		System.out.println();
                    String droneDeleteID = in.nextLine();
            		deleteDrone(conn,droneDeleteID);
            	}
            	//invalid input
            	else {
            		System.out.println("Invalid option selected for Edit or Delete.");
            	}
            } 
            
            //Invalid input to entity type
            else {
            	System.out.println("Invalid selction for Entity.");
            }
        }

                
        //search records
        else if (whichFunction == 3) {
            System.out.println("What relation would you like to search records in?");
            System.out.println();
            System.out.println("1 - STOCK");
            System.out.println("2 - MEMBER");
            System.out.println("3 - DRONE");
            System.out.println();
            System.out.print("Enter a valid option (1/2/3): ");
            int whichEntity = in.nextInt();
            System.out.println();

            //Equipment
            if(whichEntity == 1) {
            	sqlQuery(conn, "SELECT * FROM STOCK");
            	
            } 
            //Member
            else if(whichEntity == 2) {
            	sqlQuery(conn, "SELECT * FROM MEMBER");
            } 
            //Drone
            else if (whichEntity == 3){
            	sqlQuery(conn, "SELECT * FROM DRONE");
            }
            //invalid option
            else {
            	System.out.println("Invalid entity selection.");
            }

        }
        
        //useful reports
        else if (whichFunction == 4) {
        	
        	System.out.println("What report would you like to view?");
            System.out.println();
            System.out.println("1 - Items Rented by 1 Member");
            System.out.println("2 - Most Popular Item");
            System.out.println("3 - Most Popular Manufacturer");
            System.out.println("4 - Most Popular Drone");
            System.out.println("5 - Items checked out by the most active customer");
            System.out.println("6 - Equipment by Type of Equipment");              
            System.out.println();
            System.out.print("Enter a valid option (1/2/3): ");
            int whichReport = in.nextInt();
            System.out.println();
        	
            if(whichReport == 1) {
            	sqlQuery(conn, "SELECT	item, checkOut\r\n"
            			+ "FROM 	Rental\r\n"
            			+ "WHERE	userID = 12;");           	
            } 
            //Member
            else if(whichReport == 2) {
            	sqlQuery(conn, "SELECT Item, count(distinct rentalNum) AS RentOutCount, sum(julianday(dueDate) - julianday(checkOut)) AS RunningRentedTime\r\n"
            			+ "FROM RENTAL \r\n"
            			+ "GROUP BY Item\r\n"
            			+ "ORDER BY RunningRentedTime DESC;\r\n"
            			+ "");
            } 
            //Drone
            else if (whichReport == 3){
            	sqlQuery(conn, "SELECT M.name as Manufacturer, count(distinct C.rentalNum) as RentedItems\r\n"
            			+ "FROM MANUFACTURER M, STOCK S, CONTAINS C\r\n"
            			+ "WHERE S.Manufacturer = M.Name AND S.SerialNum = C.serialNum \r\n"
            			+ "GROUP BY M.name\r\n"
            			+ "ORDER BY RentedItems DESC\r\n"
            			+ "LIMIT 1;\r\n"
            			+ "");
            }
            else if(whichReport == 4) {
            	sqlQuery(conn, "SELECT D.Dname, sum(M.warehouseDist) * 4 AS TotalDistTraveled\r\n"
            			+ "FROM MEMBER M, RENTAL R, DRONE D\r\n"
            			+ "WHERE M.userID = R.userID AND D.SerialNum = R.dropDrone\r\n"
            			+ "GROUP BY D.Dname\r\n"
            			+ "ORDER BY TotalDistTraveled DESC;\r\n"
            			+ "");
            } 
            //Drone
            else if (whichReport == 5){
            	sqlQuery(conn, "SELECT 		m.userID, COUNT(r.item)\r\n"
            			+ "FROM 		MEMBER m JOIN RENTAL r ON m.userID=r.userID\r\n"
            			+ "GROUP BY 	m.userID\r\n"
            			+ "HAVING COUNT(r.item) = (\r\n"
            			+ "	SELECT 	MAX(item_count)\r\n"
            			+ "	FROM (\r\n"
            			+ "SELECT	 	r.userID, COUNT(r.item) AS item_count\r\n"
            			+ "FROM 		MEMBER m JOIN RENTAL r ON m.userID=r.userID\r\n"
            			+ "GROUP BY	 m.userID));");
            }
            else if (whichReport == 6){
            	sqlQuery(conn, "SELECT equipType, serialNum\r\n"
            			+ "FROM STOCK\r\n"
            			+ "WHERE Warehouse = 'WAREHOUSE' AND\r\n"
            			+ "   equipType IN ( SELECT equipType\r\n"
            			+ "                   FROM STOCK\r\n"
            			+ "                   WHERE Warehouse = 'WAREHOUSE'\r\n"
            			+ "                   GROUP BY equipType\r\n"
            			+ "                   HAVING COUNT(*) > 3);\r\n"
            			+ "");
            }
                                                            
            //invalid option
            else {
            	System.out.println("Invalid entity selection.");
            }
               	              	        	
        }
        
        //Check out or return
        else if (whichFunction == 5) {
        	System.out.println("What would you like to do?");
            System.out.println();
            System.out.println("1 - Check Out Stock");
            System.out.println("2 - Return Stock");
            System.out.println();
            System.out.print("Enter a valid option (1/2): ");
            int whichChoice = in.nextInt();
            System.out.println();
            
            if(whichChoice == 1) {
            	sqlQuery(conn, "SELECT * FROM STOCK WHERE Status =\"WAREHOUSE\"");
            	System.out.println("*******************************************");
            	System.out.println();
            	System.out.println("Please enter the Serial Number of the item you would like to check out:");
            	System.out.println();
            	String checkOutItem=in.nextLine();
            	checkOut(conn, checkOutItem);
            	addRental(conn, checkOutItem);
            	
            } 
            //Member
            else if(whichChoice == 2) {
            	sqlQuery(conn, "SELECT * FROM STOCK WHERE Status = \"IN USE\"");
            	System.out.println();
            	System.out.println("Please enter the Serial Number of the item you would like to check in:");
            	System.out.println();
            	String checkInItem=in.nextLine();
            	checkIn(conn, checkInItem);
            	
            	
            } 
            //invalid option
            else {
            	System.out.println("Invalid entity selection.");
            }
            
        	
        	
        }
        
        //invalid option for which function
        else {
        	System.out.println("Invalid input for function to be executed.");
        }
    }}

