
//import Connection interface
import java.sql.Connection; 
//import DriverManager class
import java.sql.DriverManager;
//connection Class

public class connection {
	static Connection con; //Static variable con of type Connection
	public static Connection getConnection() //returns database connection
	{ try {String url = "jdbc:mysql://localhost:3306/?user=root";
		   String user = "root";
		   String pass = "12345678"; 
		   con = DriverManager.getConnection(url,user,pass);
		   con.setCatalog("BANK"); //specify name of the database//establishes connection
		  }
	  catch (Exception e)
		  {System.out.println("Connection Failed!");}
      return con;
	}
}