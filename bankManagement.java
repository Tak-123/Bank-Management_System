import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

//creating a class for all methods for the bank
public class bankManagement {
	
	static Connection con = connection.getConnection(); 
	
	static String sql = ""; //to store SQL queries or database operation strings
	
	// create account method
	public static boolean createAccount(String name,int passCode) 
	{
		try {
			
			//create statement object for sending SQL statements to database
			Statement st = con.createStatement();
			sql = "INSERT INTO customer(cname,balance,pass_code) values('"
				+ name + "',1000," + passCode + ")"; //customer is the table name

			if (st.executeUpdate(sql) == 1) {
				System.out.println(name+ ", Now You Login!");
				return true;
			}
		}
		catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("Username Not Available!"); 
			//this catch makes sure user name is unique
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// login method
	public static boolean loginAccount(String name, int passCode) 
	{
		try {//sql statement
			sql = "select * from customer where cname='" + name + "' and pass_code=" + passCode;
			PreparedStatement st= con.prepareStatement(sql); 
			ResultSet rs = st.executeQuery(); //to obtain the result set
			
			BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));

			if (rs.next()) //rs.next()-> cursor moved to next row 
			{	//to show after login
				int ch = 0;
				int amount = 0;
				int senderAc = rs.getInt("ac_no");
				int receiveAc;
				while (ch!=3)
				{try {
					  System.out.println("Hello, "+ rs.getString("cname")+"\n");
					  System.out.println("1)Money Transfer");
					  System.out.println("2)View Balance");
					  System.out.println("3)LogOut");
					  System.out.print("Enter your Choice:");
					  ch = Integer.parseInt(sc.readLine());
					  switch(ch)
					  {
					  case 1:System.out.print("Enter Receiver A/c No:");
							receiveAc = Integer.parseInt(sc.readLine());
							System.out.print("Enter Amount:");
							amount = Integer.parseInt(sc.readLine());
							if (bankManagement.transferMoney(senderAc, receiveAc,amount)) 
							{
								System.out.println("Money Sent Successfully!\n");
							}
							else {
								System.out.println(
									"ERROR : Failed!\n");
							}
							break;
							
					  case 2:bankManagement.getBalance(senderAc);
							 break;
							 
					  case 3: break;
					  
					  default:System.out.println("ERROR : Enter Valid input!\n");
					  }
					}
				catch (Exception e) {e.printStackTrace();
					}
				}
			}
			else {
				return false;
			}
			return true;
		}
		catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("Username Not Available!"); //To ensure unique username
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// view balance method
	public static void getBalance(int acNo) 
	{
	  try { sql = "select * from customer where ac_no="+ acNo;
			PreparedStatement st= con.prepareStatement(sql);
			ResultSet rs = st.executeQuery(sql);
			System.out.println("\n\n");
			System.out.printf("%12s %10s %10s\n","Account No","Name","Balance");

			while (rs.next()) {
				System.out.printf("%12d %10s %10d.00\n",rs.getInt("ac_no"),rs.getString("cname"),
								rs.getInt("balance"));
			}
			System.out.println("\n\n");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Money transfer method
	public static boolean transferMoney(int sender_ac,int reveiver_ac,int amount)
	throws SQLException 
	{try {
			con.setAutoCommit(false);
			sql = "select * from customer where ac_no="+ sender_ac;
			PreparedStatement ps= con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			//debit
			if (rs.next()) {
				if (rs.getInt("balance") < amount) {
					System.out.println("Insufficient Balance!");
					return false;
				}
			}

			Statement st = con.createStatement();

			con.setSavepoint();
			
			sql = "update customer set balance=balance-"+ amount + " where ac_no=" + sender_ac;
			if (st.executeUpdate(sql) == 1) {
				System.out.println("Amount Successfully Debited!");
			}

			//credit
			sql = "update customer set balance=balance+"+ amount + " where ac_no=" + reveiver_ac;
			st.executeUpdate(sql);

			con.commit();
			return true;
		}
		catch (Exception e) {e.printStackTrace();
			con.rollback();
		}
		// return
		return false;
	}
}
