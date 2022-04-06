package Login;

import java.awt.Window; //THIS IS HUNTERS COMMENT
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import cryptoTrader.gui.MainUI;

public class LoginUI extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private static JLabel userLabel;
	private static JLabel passwordLabel;
	private static JTextField usernameText;
	private static JPasswordField passwordText;
	private static JButton button;
	private static JLabel success;
	private static JPanel panel;
	private static LoginUI instance;
	
	private static LoginServer auth;

	private LoginUI() {
		super("Crypto Trading Login");
		panel = new JPanel();
		//test
		//dimensions for organizing text and scaling
		int labelX = 10;
		int labelY = 15;
		int textFieldX = 100;
		int textFieldY = 20;

		getContentPane().add(panel);
		
		//username label for displaying where to enter username
		userLabel = new JLabel("Username");
		panel.setLayout(null);
		userLabel.setBounds(labelX, labelY, 70, 30);
		panel.add(userLabel);
		
		//password label for displaying where to enter password
		passwordLabel = new JLabel("Password");
		panel.setLayout(null);
		passwordLabel.setBounds(labelX, labelY*4, 70, 30);
		panel.add(passwordLabel);
		
		//area where user enters their username (shown)
		usernameText = new JTextField(20);
		usernameText.setBounds(textFieldX, textFieldY, 165, 25);
		panel.add(usernameText);
		
		//area where user enters password (password protected)
		passwordText = new JPasswordField(20);
		passwordText.setBounds(textFieldX, textFieldY*3+5, 165, 25);
		panel.add(passwordText);
		
		//login button
		button = new JButton("Login");
		button.setBounds(10, 110, 80, 25);
		button.addActionListener(this);
		panel.add(button);
		
		//login successful text
		success = new JLabel();
		success.setBounds(10, 135, 300, 25);
		panel.add(success);
	}
	
	/**
	 * singleton method for login UI
	 * @return
	 */
	private static LoginUI getInstance() {
        if (instance == null)
            instance = new LoginUI();

        return instance; 
    }
	
	/**
	 * Crypto Trading Application start point
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		auth = LoginServer.getInstance();
		JFrame frame = LoginUI.getInstance();
		frame.setSize(500, 300);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * Method for when the user presses the "login" button. Will attempt to log the user in.
	 * If successful, the user will be redirected to the main UI
	 * If unsuccessful, the user will be given an error message and they can retry to login
	 */
	public void actionPerformed(ActionEvent e) {
		
		//user inputed username and password
		String user = usernameText.getText();
		String password = passwordText.getText();
		
		//checks authentication server for correct username and password
		if (auth.checkCredentials(user, password)) { 
			success.setText("Successful Login");
			
			close();
			
			JFrame frame = MainUI.getInstance(); //creates a main UI instance
			frame.setSize(900, 600);
			frame.pack();
			frame.setVisible(true);
		}
		else {
			close();
			
			//displays an error message and closes the application
			JFrame errorFrame = new JFrame("Error");
			JPanel errorPanel = new JPanel();
			errorFrame.add(errorPanel);
			errorFrame.setSize(250, 100);
			
			errorFrame.setLocationRelativeTo(null);
			JLabel errorLabel = new JLabel("Wrong Username or Password");
			errorPanel.setLayout(null);
			errorLabel.setBounds(5, 20, 270, 20);
			errorPanel.add(errorLabel);
			errorFrame.setVisible(true);
			
		}
		
	}
	
	/**
	 * method to close the login UI after successful or unsuccessful login
	 */
	public void close() {
		setVisible(false);
		dispose();
		
	}
	
	
}
