
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class bank {
 public static void main(String[] args) throws IOException{
	BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
	String name = "";
	int password;
	int ch=0;
	while (ch!=3) {
		System.out.println("\n WELCOME TO OUR BANK \n\n");
	    System.out.println("1)New Account Creation");
		System.out.println("2)Account Login");
		System.out.println("3)EXIT");

		try {
			    System.out.print("\nEnter Choice (1,2 or 3): ");
			    ch = Integer.parseInt(sc.readLine());
			    switch (ch) 
			    {	case 1: try {
								System.out.print("Enter User Name:");
								name = sc.readLine();
								System.out.print("Enter New Password:");
								password = Integer.parseInt(sc.readLine());

								if (bankManagement.createAccount(name, password)) {
									System.out.println("Account Created Successfully!\n");
								}
								else {
									System.out.println("ERROR! Account Creation Failed!\n");
								}
							}
							catch (Exception e) {
								System.out.println(" ERROR : Enter Valid Data\n");
							}
							break;

					case 2: try {System.out.print("Enter User Name:");
								name = sc.readLine();
								System.out.print("Enter Password:");
								password = Integer.parseInt(sc.readLine());

								if (bankManagement.loginAccount(name, password)) {
									System.out.println("Logout Successful!\n");
								}
								else {
									System.out.println("ERROR : login Failed!\n");
								}
							}
							catch (Exception e) {
								System.out.println(" ERR : Enter Valid Data\n");
							}
							break;
					case 3: break;		
					default:
							System.out.println("Invalid Choice!\n");
				}

						
					}
	   catch (Exception e) {System.out.println("Enter Valid Entry!");
	    }
	}
	sc.close();
  }
}

