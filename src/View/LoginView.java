package View;

import javax.swing.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
	
	
	// Components
	private	JTextField usernameField = new JTextField(20);
	private JPasswordField passwordField = new JPasswordField(20);
	private JButton loginButton = new JButton("LOGIN");

	public LoginView() {
		
        JPanel pane = new JPanel();
		pane.setLayout(null);
		
		JLabel usernameLabel= new JLabel("Username: ");
		usernameLabel.setBounds(10,20,80,25);
		pane.add(usernameLabel);

		usernameField.setBounds(100,20,165,25);
		pane.add(usernameField);	   
		
		JLabel passwordLabel= new JLabel("Password: ");
		passwordLabel.setBounds(10,50,80,25);
		pane.add(passwordLabel);
		
		passwordField.setBounds(100,50,165,25);
		pane.add(passwordField);
		
		loginButton.setBounds(10,80,80,25);
		pane.add(loginButton);
		
		JLabel success = new JLabel("");
		success.setBounds(10,110,300,25);
		pane.add(success);

		this.setContentPane(pane);
        this.pack();
        this.setTitle("Login Page");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
	}
	
	public void addLoginListener(ActionListener log) {
		loginButton.addActionListener(log);
	}
	public String getUsername() {
		return usernameField.getText();
	}
	public char[] getPassword() {
		return passwordField.getPassword();
	}

    public void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }
}
