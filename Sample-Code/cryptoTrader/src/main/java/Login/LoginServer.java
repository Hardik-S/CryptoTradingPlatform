package Login;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors

import java.util.Scanner; // Import the Scanner class to read text files

/**
 * Singleton Login Server for verifying the user credentials 
 * @author Hunter Terpstra
 *
 */
public class LoginServer {

	private static LoginServer instance;
	
	/**
	 * Default empty constructor  
	 */
	private LoginServer() {
		
	}
	
	/**
	 * method for checking if the username and password that the user entered are in
	 * the login database.
	 * 
	 * @param user
	 * @param Password
	 * @return true if username and password are in database, false otherwise
	 */
	public boolean checkCredentials(String user, String password) {
		try {
			
			String[] dataParse;
			File myObj = new File("LoginDB.txt");
			Scanner myReader = new Scanner(myObj);

			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				dataParse = data.split(","); //Separates the username and password
				dataParse[1] = dataParse[1].replace(" ", ""); //gets rid of empty space

				if (user.equals(dataParse[0]) && password.equals(dataParse[1])) { //username and password match
					return true;
				}
			}
			myReader.close();
		} catch (FileNotFoundException e) { //file not found
			System.out.println("An error occurred");

		}

		return false;
	}
	
	/**
	 * Singleton method to only have one login server session
	 * @return login server instance
	 */
	public static LoginServer getInstance() {
		
		if (instance == null)
			instance = new LoginServer();

		return instance;
		
	}
	

}
