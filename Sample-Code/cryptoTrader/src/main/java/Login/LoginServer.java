package Login;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Singleton Login Server for verifying the user credentials 
 * @author Hunter Terpstra
 *
 */
public class LoginServer {
	private static final String LOGIN_RESOURCE = "/Login/LoginDB.txt";

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
		if (user == null || password == null) {
			return false;
		}

		try (Scanner myReader = openCredentialScanner()) {
			while (myReader.hasNextLine()) {
				String[] dataParse = myReader.nextLine().split(",", 2);
				if (dataParse.length != 2) {
					continue;
				}

				if (user.equals(dataParse[0].trim()) && password.equals(dataParse[1].trim())) {
					return true;
				}
			}
		} catch (FileNotFoundException e) { //file not found
			return false;
		}

		return false;
	}

	/**
	 * Prefer the packaged resource so the login database works from a built JAR
	 * or any working directory. Keep the file fallback for legacy IDE launches.
	 */
	private Scanner openCredentialScanner() throws FileNotFoundException {
		InputStream resource = LoginServer.class.getResourceAsStream(LOGIN_RESOURCE);
		if (resource != null) {
			return new Scanner(resource, StandardCharsets.UTF_8.name());
		}

		return new Scanner(new File("LoginDB.txt"), StandardCharsets.UTF_8.name());
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
