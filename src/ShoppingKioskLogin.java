import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ShoppingKioskLogin extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel startScreen, signup;
	private JTextField signupUsername, signupFirstName, signupLastName, signupAddress, loginUsername;
	private JPasswordField signupPassword, confirmPassword, signupAdminCode, loginPassword;
	private JRadioButton signupShopper, signupAdmin;
	private JButton startSignup, signupSubmit, signupCancel, loginSubmit;
	private JLabel accountCredentials, personalInfo, accountType, welcome;
	
	public ShoppingKioskLogin()
	{
		super("Pick 'N Save : Shopping Kiosk");
		
		setSize(380,210);
		instantiate();
		setLayout(null);
		initialize();
		addListeners();
		
		setBackground(Color.BLACK);
		
		URL iconURL = getClass().getResource("/res/favicon.png");
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());
		
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}
	
	private void instantiate()
	{
		startScreen = new JPanel();
		signup = new JPanel();
		
		signupUsername = new JTextField();
		signupFirstName = new JTextField();
		signupLastName = new JTextField();
		signupAddress = new JTextField();
		loginUsername = new JTextField();
		
		signupPassword = new JPasswordField();
		signupAdminCode = new JPasswordField();
		loginPassword = new JPasswordField();
		confirmPassword = new JPasswordField();
		
		signupShopper = new JRadioButton("Shopper");
		signupAdmin = new JRadioButton("Administrator");
		
		startSignup = new JButton("Signup");
		signupSubmit = new JButton("Register");
		signupCancel = new JButton("Cancel");
		loginSubmit = new JButton("Login");
		
		accountCredentials = new JLabel("Account Credentials");
		personalInfo = new JLabel("Personal Information");
		accountType = new JLabel("Account Type");
		welcome = new JLabel("Welcome to Pick 'N Save!", SwingConstants.CENTER);
	}
	
	private void initialize()
	{
		accountCredentials.setFont(new Font("Arial", Font.BOLD, 15));
		personalInfo.setFont(new Font("Arial", Font.BOLD, 15));
		accountType.setFont(new Font("Arial", Font.BOLD, 15));
		welcome.setFont(new Font("Arial", Font.BOLD, 20));
		
		add(startScreen);
		add(signup);
		
		startScreen.setOpaque(false);
		signup.setOpaque(false);
		
		startScreen.add(loginUsername);
		startScreen.add(loginPassword);
		startScreen.add(loginSubmit);
		startScreen.add(startSignup);
		startScreen.add(welcome);
		
		signup.add(accountCredentials);
		signup.add(signupUsername);
		signup.add(signupPassword);
		signup.add(confirmPassword);
		signup.add(personalInfo);
		signup.add(signupFirstName);
		signup.add(signupLastName);
		signup.add(signupAddress);
		signup.add(accountType);
		signup.add(signupShopper);
		signup.add(signupAdmin);
		signup.add(signupAdminCode);
		signup.add(signupSubmit);
		signup.add(signupCancel);
		
		startScreen.setLayout(null);
		signup.setLayout(null);
		
		int windowWidth = this.getWidth();
		int windowHeight = this.getHeight();
		
		startScreen.setBounds(0, 0, windowWidth, windowHeight);
		signup.setBounds(0, 175, this.getWidth(), 650);
		
		welcome.setBounds(10, 10, windowWidth-50, 50);
		loginUsername.setBounds(30, 70, 150, 30);
		loginPassword.setBounds(30, 110, 150, 30);
		loginSubmit.setBounds(220, 70, 100, 30);
		startSignup.setBounds(220, 110, 100, 30);
		
		accountCredentials.setBounds(30, 30, 150, 30);
		signupUsername.setBounds(30, 60, 150, 30);
		signupPassword.setBounds(30, 100, 150, 30);
		confirmPassword.setBounds(190, 100, 150, 30);
		personalInfo.setBounds(30, 140, 150, 30);
		signupFirstName.setBounds(30, 170, 150, 30);
		signupLastName.setBounds(30, 200, 150, 30);
		signupAddress.setBounds(30, 230, 150, 30);
		accountType.setBounds(30, 270, 150, 30);
		signupShopper.setBounds(30, 290, 80, 30);
		signupAdmin.setBounds(130, 290, 110, 30);
		signupAdminCode.setBounds(130, 320, 150, 30);
		signupSubmit.setBounds(30, 380, 100, 30);
		signupCancel.setBounds(160, 380, 100, 30);
		
		resetLogin();
		resetSignup();
	}
	
	private void addListeners()
	{
		startSignup.addActionListener(new startSignupListener());
		
		signupSubmit.addActionListener(new signupRegisterListener());
		signupCancel.addActionListener(new signupCancelListener());
		
		signupShopper.addActionListener(new signupShopperRBListener());
		signupAdmin.addActionListener(new signupAdminRBListener());
		
		signupUsername.addFocusListener(new signupUsernameListener());
		signupPassword.addFocusListener(new signupPasswordListener());
		confirmPassword.addFocusListener(new confirmPasswordListener());
		
		signupFirstName.addFocusListener(new signupFirstNameListener());
		signupLastName.addFocusListener(new signupLastNameListener());
		signupAddress.addFocusListener(new signupAddressListener());
		
		signupAdminCode.addFocusListener(new signupAdminCodeListener());
		
		loginUsername.addFocusListener(new loginUsernameListener());
		loginPassword.addFocusListener(new loginPasswordListener());
		this.addWindowListener(new loginWindowListener());
		
		loginSubmit.addActionListener(new loginSubmitListener());
	}
	
	class startSignupListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			setSize(380, 650);
			setLoginEnabled(false);
		}
		
	}
	
	class signupRegisterListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//TODO: Validate requirements
			//TODO: Send Data
			resetSignup();
		}
		
	}
	
	class signupCancelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			resetSignup();
		}
		
	}
	
	class signupShopperRBListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			toggleSignupRB(true);
		}
		
	}

	class signupAdminRBListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			toggleSignupRB(false);
		}
		
	}
	
	class signupUsernameListener implements FocusListener
	{
		@Override
		public void focusGained(FocusEvent arg0) {
			if(signupUsername.getText().equals(Placeholder.USERNAME.toString()))
			{
				signupUsername.setText("");
				signupUsername.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(signupUsername.getText().equals(""))
			{
				signupUsername.setText(Placeholder.USERNAME.toString());
				signupUsername.setForeground(Color.GRAY);
			}
			
		}
	}
	
	class signupPasswordListener implements FocusListener
	{
		@Override
		public void focusGained(FocusEvent arg0) {
			if(String.valueOf(signupPassword.getPassword()).equals(Placeholder.PASSWORD.toString()))
			{
				signupPassword.setText("");
				signupPassword.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(String.valueOf(signupPassword.getPassword()).equals(""))
			{
				signupPassword.setText(Placeholder.PASSWORD.toString());
				signupPassword.setForeground(Color.GRAY);
			}
			
		}
	}

	class signupFirstNameListener implements FocusListener
	{
		@Override
		public void focusGained(FocusEvent arg0) {
			if(signupFirstName.getText().equals(Placeholder.FIRSTNAME.toString()))
			{
				signupFirstName.setText("");
				signupFirstName.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(signupFirstName.getText().equals(""))
			{
				signupFirstName.setText(Placeholder.FIRSTNAME.toString());
				signupFirstName.setForeground(Color.GRAY);
			}
			
		}
	}
	
	class signupLastNameListener implements FocusListener
	{
		@Override
		public void focusGained(FocusEvent arg0) {
			if(signupLastName.getText().equals(Placeholder.LASTNAME.toString()))
			{
				signupLastName.setText("");
				signupLastName.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(signupLastName.getText().equals(""))
			{
				signupLastName.setText(Placeholder.LASTNAME.toString());
				signupLastName.setForeground(Color.GRAY);
			}
			
		}
	}
	
	class signupAddressListener implements FocusListener
	{
		@Override
		public void focusGained(FocusEvent arg0) {
			if(signupAddress.getText().equals(Placeholder.ADDRESS.toString()))
			{
				signupAddress.setText("");
				signupAddress.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(signupAddress.getText().equals(""))
			{
				signupAddress.setText(Placeholder.ADDRESS.toString());
				signupAddress.setForeground(Color.GRAY);
			}
			
		}
	}

	class signupAdminCodeListener implements FocusListener
	{
		@Override
		public void focusGained(FocusEvent arg0) {
			if(String.valueOf(signupAdminCode.getPassword()).equals(Placeholder.ADMINCODE.toString()))
			{
				signupAdminCode.setText("");
				signupAdminCode.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(String.valueOf(signupAdminCode.getPassword()).equals(""))
			{
				signupAdminCode.setText(Placeholder.ADMINCODE.toString());
				signupAdminCode.setForeground(Color.GRAY);
			}
			
		}
	}

	class loginUsernameListener implements FocusListener
	{
		@Override
		public void focusGained(FocusEvent arg0) {
			if(loginUsername.getText().equals(Placeholder.USERNAME.toString()))
			{
				loginUsername.setText("");
				loginUsername.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(loginUsername.getText().equals(""))
			{
				loginUsername.setText(Placeholder.USERNAME.toString());
				loginUsername.setForeground(Color.GRAY);
			}
			
		}
	}
	
	class loginPasswordListener implements FocusListener
	{
		@Override
		public void focusGained(FocusEvent arg0) {
			if(String.valueOf(loginPassword.getPassword()).equals(Placeholder.PASSWORD.toString()))
			{
				loginPassword.setText("");
				loginPassword.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(String.valueOf(loginPassword.getPassword()).equals(""))
			{
				loginPassword.setText(Placeholder.PASSWORD.toString());
				loginPassword.setForeground(Color.GRAY);
			}
			
		}
	}
	
	class confirmPasswordListener implements FocusListener
	{
		@Override
		public void focusGained(FocusEvent arg0) {
			if(String.valueOf(confirmPassword.getPassword()).equals(Placeholder.CONFIRMPASSWORD.toString()))
			{
				confirmPassword.setText("");
				confirmPassword.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(String.valueOf(confirmPassword.getPassword()).equals(""))
			{
				confirmPassword.setText(Placeholder.CONFIRMPASSWORD.toString());
				confirmPassword.setForeground(Color.GRAY);
			}
			
		}
	}
	
	class loginWindowListener implements WindowListener{

		@Override
		public void windowActivated(WindowEvent arg0) {}

		@Override
		public void windowClosed(WindowEvent arg0) {}
		@Override
		public void windowClosing(WindowEvent arg0) {
			setVisible(false);
			setVisible(true);
			resetSignup();
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {}

		@Override
		public void windowIconified(WindowEvent arg0) {}

		@Override
		public void windowOpened(WindowEvent arg0) {}
		
	}

	class loginSubmitListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//TODO: check validity of info
			
			setVisible(false);
			
		}
		
	}
	
	private void toggleSignupRB(boolean toggle)
	{
		signupShopper.setSelected(toggle);
		signupAdmin.setSelected(!toggle);
		
		if(!toggle)
		{
			signupAdminCode.setVisible(true);
			signupAdminCode.setText(Placeholder.ADMINCODE.toString());
			signupAdminCode.setForeground(Color.GRAY);
		}
		
		else signupAdminCode.setVisible(false);
	}
	
	private void setLoginEnabled(boolean toggle)
	{
		loginUsername.setEnabled(toggle);
		loginPassword.setEnabled(toggle);
		loginSubmit.setEnabled(toggle);
		startSignup.setEnabled(toggle);
		
		if(toggle) setSize(380,210);
		else setSize(380, 650);
		
	}
	
	private void resetLogin()
	{
		loginUsername.setText(Placeholder.USERNAME.toString());
		loginPassword.setText(Placeholder.PASSWORD.toString());
		
		loginUsername.setForeground(Color.GRAY);
		loginPassword.setForeground(Color.GRAY);
		
	}
	
	private void resetSignup()
	{
		signupUsername.setText(Placeholder.USERNAME.toString());
		signupPassword.setText(Placeholder.PASSWORD.toString());
		signupFirstName.setText(Placeholder.FIRSTNAME.toString());
		signupLastName.setText(Placeholder.LASTNAME.toString());
		signupAddress.setText(Placeholder.ADDRESS.toString());
		signupAdminCode.setText(Placeholder.ADMINCODE.toString());
		confirmPassword.setText(Placeholder.CONFIRMPASSWORD.toString());
		
		signupUsername.setForeground(Color.GRAY);
		signupPassword.setForeground(Color.GRAY);
		signupFirstName.setForeground(Color.GRAY);
		signupLastName.setForeground(Color.GRAY);
		signupAddress.setForeground(Color.GRAY);
		signupAdminCode.setForeground(Color.GRAY);
		confirmPassword.setForeground(Color.GRAY);
		
		signupAdminCode.setVisible(false);
		
		setLoginEnabled(true);
		resetLogin();
	}
}
