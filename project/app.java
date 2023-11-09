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
    
    /** Here begins the methods we wrote for the checkpoint **/
    
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
    
    public static void addEquipment(Connection conn) {
    	try {
    		
    		Date arrivalDate = new Date(2023-11-9);
    		Date warrExpDate = new Date(2025-1-1);
    		double Height = 1.0;
    		double Width = 1.0;
    		double Length = 10.0;
    		double Weight = 5.0;
    		
    		
    		String sql = "INSERT INTO STOCK (arrivalDate,warrExpDate,SerialNum,Year,";
    		sql+= "equipType,currentLoc,Height,Width,Length,Weight,Warehouse,Manufacturer,orderNum)";
    		sql += "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    		
    		PreparedStatement equipment = conn.prepareStatement(sql);
    		
    		equipment.setDate(1,  arrivalDate);
    		equipment.setDate(2, warrExpDate);
    		
    		Scanner in = new Scanner(System.in);
    		System.out.print("Serial Number (8 characters): ");
    		String SerialNum = in.nextLine();
    		
    		System.out.print("\nYear: ");
    		String Year = in.nextLine();
    		
    		System.out.print("\nEquipment Type (up to 50 characters): ");
    		String equipType = in.nextLine();
    		
    		System.out.print("\nCurrent Location (up to 50 characters): ");
    		String currentLoc = in.nextLine();
    		
    		System.out.print("\nWarehouse (up to 50 characters): ");
    		String Warehouse = in.nextLine();
    		
    		System.out.print("\nManufacturer (up to 50 characters): ");
    		String Manufacturer = in.nextLine();
    		
    		System.out.print("\nOrder Number: ");
    		int orderNum = in.nextInt();
    		
    		equipment.setString(3,  SerialNum);
    		equipment.setString(4, Year);
    		equipment.setString(5, equipType);
    		equipment.setString(6, currentLoc);
    		
    		equipment.setDouble(7, Height);
    		equipment.setDouble(8, Width);
    		equipment.setDouble(9, Length);
    		equipment.setDouble(10, Weight);
    		
    		equipment.setString(11, Warehouse);
    		equipment.setString(12, Manufacturer);
    		
    		equipment.setInt(13, orderNum);
    		
    		in.close();
    		
    	} catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void addMember(Connection conn) {
    	try {
    		Scanner in = new Scanner(System.in);
    		
    		String sql = "";
    		
    		PreparedStatement member = conn.prepareStatement(sql);
    		
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

            //String memberEntry = memberEntryBuild(userID, community, status, fName, lName,
                 //   warehouseDist, phoneNum, address, email, startDate);
           // insertStatement(conn, memberEntry);
            
            in.close();
            
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    }
    
    public static void addDrone(Connection conn) {
    	try {
    		String sql = "";
    		PreparedStatement drone = conn.prepareStatement(sql);
    		
    		Scanner in = new Scanner(System.in);
    		
    		
    		in.close();
    		
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    }
    
    public static void editEquipment(Connection conn) {
    	try {
    		String sql = "";
    		PreparedStatement equipment = conn.prepareStatement(sql);
    		Scanner in = new Scanner(System.in);
    		
    		
    		in.close();
    		
    		
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    }
    
    public static void deleteEquipment(Connection conn) {
    	try {
    		String sql = "";
    		PreparedStatement equipment = conn.prepareStatement(sql);
    		
    		Scanner in = new Scanner(System.in);
    		
    		
    		in.close();
    		
    		
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    }
    
    public static void editMember(Connection conn) {
    	try {
    		String sql = "";
    		PreparedStatement member = conn.prepareStatement(sql);
    		
    		Scanner in = new Scanner(System.in);
    		
    		System.out.println(
                    "Enter the memberID of the record you'd like to edit: ");
            int memberID = in.nextInt();
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
                    
                    
                    System.out.println("The record has been updated.");
            
            		in.close();
    		
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    }
    
    public static void deleteMember(Connection conn) {
    	try {
    		String sql = "";
    		PreparedStatement member = conn.prepareStatement(sql);
    		Scanner in = new Scanner(System.in);
    		
    		
    		in.close();
    		
    		
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    }
    
    public static void editDrone(Connection conn) {
    	try {
    		String sql = "";
    		PreparedStatement drone = conn.prepareStatement(sql);
    		Scanner in = new Scanner(System.in);
    		
    		
    		in.close();
    		
    		
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    }
    
    public static void deleteDrone(Connection conn) {
    	try {
    		String sql = "";
    		PreparedStatement drone = conn.prepareStatement(sql);
    		Scanner in = new Scanner(System.in);
    		
    		
    		in.close();
    		
    		
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    }
    
    /**Example from in-class lab**/
    
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
        System.out.println();

        

        System.out.print("Enter a valid option (1/2/3/4): ");
        int whichFunction = in.nextInt();
        System.out.println();
        
        
        //add new record
        if (whichFunction == 1) {
            System.out.println("What relation would you like to add a record to?");
            System.out.println();
            System.out.println("1 - EQUIPMENT");
            System.out.println("2 - MEMBER");
            System.out.println("3 - DRONE");
            System.out.println();
            System.out.println("Enter a valid option (1/2/3): ");
            int whichEntity = in.nextInt();
            System.out.println();

            //equipment
            if (whichEntity == 1) {
                addEquipment(conn);
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
            System.out.println("1 - WAREHOUSE");
            System.out.println("2 - MEMBER");
            System.out.println("3 - REVIEW");
            System.out.println();
            System.out.print("Enter a valid option (1/2/3): ");
            int whichEntity = in.nextInt();

            System.out.println();


            System.out.println("Are you editing or deleting a record?");
            System.out.println("1 - editing");
            System.out.println("2 - deleting");
            System.out.print("Enter a valid option (1/2): ");
            int editOrDelete = in.nextInt();

            //Equipment
            if(whichEntity == 1) {
            	//edit
            	if(editOrDelete == 1) {
            		editEquipment(conn);
            	} 
            	//delete
            	else if (editOrDelete == 2) {
            		deleteEquipment(conn);
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
            		editMember(conn);
            	} 
            	//delete
            	else if (editOrDelete == 2) {
            		deleteMember(conn);
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
            		editDrone(conn);
            	} 
            	//delete
            	else if (editOrDelete == 2) {
            		deleteDrone(conn);
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
            System.out.println("1 - WAREHOUSE");
            System.out.println("2 - MEMBER");
            System.out.println("3 - REVIEW");
            System.out.println();
            System.out.print("Enter a valid option (1/2/3): ");
            int whichEntity = in.nextInt();
            System.out.println();

            //Equipment
            if(whichEntity == 1) {
            	
            	
            } 
            //Member
            else if(whichEntity == 2) {
            	
            } 
            //Drone
            else if (whichEntity == 3){
            	
            }
            //invalid option
            else {
            	System.out.println("Invalid entity selection.");
            }

        }
        
        //useful reports
        else if (whichFunction == 4) {
        	
        	
        }
        
        //invalid option for which function
        else {
        	System.out.println("Invalid input for function to be executed.");
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

    }
}

    	
	/** from lab example **/
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
