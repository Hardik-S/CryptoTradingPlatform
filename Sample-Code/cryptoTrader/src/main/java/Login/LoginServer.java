package Login;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

import cryptoTrader.gui.MainUI;

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
			File myObj = new File("src/main/java/Login/LoginDB.txt");
			Scanner myReader = new Scanner(myObj);

			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				dataParse = data.split(",");
				dataParse[1] = dataParse[1].replace(" ", "");

				if (user.equals(dataParse[0]) && password.equals(dataParse[1])) {
					return true;
				}
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred");

		}

		return false;
	}
	
	public static LoginServer getInstance() {
		
		if (instance == null)
			instance = new LoginServer();

		return instance;
		
	}
	

}
