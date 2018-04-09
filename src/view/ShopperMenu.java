package view;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.AccountsController;
import controller.CartController;
import controller.StocksController;
import model.Person;

public class ShopperMenu extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel shopperMenuPanel, modifyInfoPanel, changeNamePanel, changeAddressPanel, changePasswordPanel;
	private JLabel welcome, modifyInfoLabel, currentName, currentAddress, changePassLabel;
	private JButton modifyInfo, browseProducts, cart, settleBalance, logout;
	private JButton changeName, changeAddress, changePassword, modifyInfoBack;
	private JButton nameChangeSubmit, nameChangeCancel, addressChangeSubmit, addressChangeCancel, passwordChangeSubmit, passwordChangeCancel;
	private JTextField firstName, lastName, address;
	private JPasswordField currentPassword, newPassword, confirmPassword;
	private StocksController sctrl;
	private ShopperStocksList catalog;
	private ShopperCart cartFrame;
	private Mainframe frame;
	private Person account;
	private AccountsController actrl;
	private String label;
	private CartController cctrl;
	private SettleOutstandingBalance sobFrame;
	
	public ShopperMenu(Person account, AccountsController actrl, StocksController sctrl, CartController cctrl, Mainframe frame)
	{
		super("Pick 'N Save : Shopper Menu");
		
		this.account = account;
		this.actrl = actrl;
		this.sctrl = sctrl;
		this.cctrl = cctrl;
		this.frame = frame;
		
		URL iconURL = getClass().getResource("/res/favicon.png");
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());
		setSize(550, 450);
		
		instantiate();
		setLayout(null);
		initialize();
		addListeners();
				
		setResizable(false);
		setVisible(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}
	
	private void instantiate()
	{	
		shopperMenuPanel = new JPanel();
		modifyInfoPanel = new JPanel();
		changeNamePanel = new JPanel();
		changeAddressPanel = new JPanel();
		changePasswordPanel = new JPanel();
		
		
		welcome = new JLabel(label, SwingConstants.CENTER);
		modifyInfoLabel = new JLabel("Modify Information", SwingConstants.CENTER);
		currentName = new JLabel(label, SwingConstants.CENTER);
		currentAddress = new JLabel(label, SwingConstants.CENTER);
		changePassLabel = new JLabel("<html><div style='text-align: center;'>Change Password</html>", SwingConstants.CENTER);
		
		modifyInfo = new JButton("Modify Shopper Information");
		browseProducts = new JButton("Browse Products");
		cart = new JButton("View Cart");
		settleBalance = new JButton("Settle Outstanding Balance");
		logout = new JButton("Logout");
		
		changeName = new JButton("Change Name");
		changeAddress = new JButton("Change Address");
		changePassword = new JButton("Change Password");
		modifyInfoBack = new JButton("◄ Back");
		
		nameChangeSubmit = new JButton("Submit");
		nameChangeCancel = new JButton("Cancel");
		addressChangeSubmit = new JButton("Submit");
		addressChangeCancel = new JButton("Cancel");
		passwordChangeSubmit = new JButton("Submit");
		passwordChangeCancel = new JButton("Cancel");
		
		firstName = new JTextField();
		lastName = new JTextField();
		address = new JTextField();
		
		currentPassword = new JPasswordField();
		newPassword = new JPasswordField();
		confirmPassword = new JPasswordField();
		
		cartFrame = new ShopperCart(actrl, sctrl, cctrl, account, this);
		catalog = new ShopperStocksList(sctrl, cctrl, account, this);
		sobFrame = new SettleOutstandingBalance(account, actrl, this);
	}
	
	private void initialize()
	{
		
		add(shopperMenuPanel);
		add(modifyInfoPanel);
		add(changeNamePanel);
		add(changeAddressPanel);
		add(changePasswordPanel);
		
		shopperMenuPanel.setLayout(null);
		modifyInfoPanel.setLayout(null);
		changeNamePanel.setLayout(null);
		changeAddressPanel.setLayout(null);
		changePasswordPanel.setLayout(null);
		
		shopperMenuPanel.add(welcome);
		shopperMenuPanel.add(modifyInfo);
		shopperMenuPanel.add(browseProducts);
		shopperMenuPanel.add(cart);
		shopperMenuPanel.add(settleBalance);
		shopperMenuPanel.add(logout);
		
		modifyInfoPanel.add(modifyInfoBack);
		modifyInfoPanel.add(modifyInfoLabel);
		modifyInfoPanel.add(changeName);
		modifyInfoPanel.add(changeAddress);
		modifyInfoPanel.add(changePassword);
		
		changeNamePanel.add(currentName);
		changeNamePanel.add(firstName);
		changeNamePanel.add(lastName);
		changeNamePanel.add(nameChangeSubmit);
		changeNamePanel.add(nameChangeCancel);
		
		changeAddressPanel.add(currentAddress);
		changeAddressPanel.add(address);
		changeAddressPanel.add(addressChangeSubmit);
		changeAddressPanel.add(addressChangeCancel);
		
		changePasswordPanel.add(changePassLabel);
		changePasswordPanel.add(currentPassword);
		changePasswordPanel.add(newPassword);
		changePasswordPanel.add(confirmPassword);
		changePasswordPanel.add(passwordChangeSubmit);
		changePasswordPanel.add(passwordChangeCancel);
		
		welcome.setFont(new Font("Arial", Font.BOLD, 23));
		modifyInfoLabel.setFont(new Font("Arial", Font.BOLD, 35));
		currentName.setFont(new Font("Arial", Font.BOLD, 20));
		currentAddress.setFont(currentName.getFont());
		changePassLabel.setFont(modifyInfoLabel.getFont());
		
		shopperMenuPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
		welcome.setBounds(10, shopperMenuPanel.getHeight()/50, 500, 70);
		modifyInfo.setBounds(shopperMenuPanel.getWidth()/5, shopperMenuPanel.getHeight()/5, 325, 50);
		browseProducts.setBounds(modifyInfo.getX(), modifyInfo.getY()+60, modifyInfo.getWidth(), modifyInfo.getHeight());
		cart.setBounds(browseProducts.getX(), browseProducts.getY()+60, browseProducts.getWidth(), browseProducts.getHeight());
		settleBalance.setBounds(cart.getX(), cart.getY()+60, cart.getWidth(), cart.getHeight());
		logout.setBounds(settleBalance.getX(), settleBalance.getY()+60, settleBalance.getWidth(), settleBalance.getHeight());
		
		modifyInfoPanel.setBounds(shopperMenuPanel.getBounds());
		modifyInfoBack.setBounds(10, 10, 80, 40);
		modifyInfoLabel.setBounds(10, 60, modifyInfoPanel.getWidth()-50, 50);
		changeName.setBounds(modifyInfoPanel.getWidth()/5, modifyInfoPanel.getHeight()/3, 325, 50);
		changeAddress.setBounds(changeName.getX(), changeName.getY()+80, changeName.getWidth(), changeName.getHeight());
		changePassword.setBounds(changeAddress.getX(), changeAddress.getY()+80, changeAddress.getWidth(), changeAddress.getHeight());
		
		
		changeNamePanel.setBounds(modifyInfoPanel.getWidth(), 0, modifyInfoPanel.getWidth(), modifyInfoPanel.getHeight());
		currentName.setBounds(10, 20, changeNamePanel.getWidth(), 80);
		firstName.setBounds(20, 150, 500, 30);
		lastName.setBounds(firstName.getX(), firstName.getY()+50, firstName.getWidth(), firstName.getHeight());
		nameChangeSubmit.setBounds(changeNamePanel.getWidth()-300, changeNamePanel.getHeight()-100, 100, 30);
		nameChangeCancel.setBounds(changeNamePanel.getWidth()-175, changeNamePanel.getHeight()-100, 100, 30);
		
		
		changeAddressPanel.setBounds(changeNamePanel.getBounds());
		currentAddress.setBounds(currentName.getBounds());
		address.setBounds(20, 200, 500, 30);
		addressChangeSubmit.setBounds(nameChangeSubmit.getBounds());
		addressChangeCancel.setBounds(nameChangeCancel.getBounds());
		
		changePasswordPanel.setBounds(changeNamePanel.getBounds());
		changePassLabel.setBounds(currentName.getBounds());
		currentPassword.setBounds(20, 150, 500, 30);
		newPassword.setBounds(currentPassword.getX(), currentPassword.getY()+30, currentPassword.getWidth(), currentPassword.getHeight());
		confirmPassword.setBounds(newPassword.getX(), newPassword.getY()+30, newPassword.getWidth(), newPassword.getHeight());
		passwordChangeSubmit.setBounds(nameChangeSubmit.getBounds());
		passwordChangeCancel.setBounds(nameChangeCancel.getBounds());
		
		
		toggleModifyInfo(false);
		toggleChangeName(false);
		toggleChangeAddress(false);
		toggleChangePassword(false);
		update();
		checkButtons();
		resetChangeName();
		resetChangeAddress();
		resetChangePassword();
	}
	
	private void addListeners()
	{
		this.addWindowListener(new ShopperWindowListener());
		modifyInfo.addActionListener(new ModifyInfoListener());
		modifyInfoBack.addActionListener(new ModifyInfoBackListener());
		changeName.addActionListener(new ChangeNameListener());
		changeAddress.addActionListener(new ChangeAddressListener());
		changePassword.addActionListener(new ChangePasswordListener());
		firstName.addFocusListener(new FirstNameListener());
		lastName.addFocusListener(new LastNameListener());
		nameChangeCancel.addActionListener(new NameCancelListener());
		addressChangeCancel.addActionListener(new AddressCancelListener());
		passwordChangeCancel.addActionListener(new PasswordCancelListener());
		nameChangeSubmit.addActionListener(new NameSubmitListener());
		addressChangeSubmit.addActionListener(new AddressSubmitListener());
		passwordChangeSubmit.addActionListener(new PasswordSubmitListener());
		address.addFocusListener(new AddressListener());
		currentPassword.addFocusListener(new CurrentPasswordListener());
		newPassword.addFocusListener(new NewPasswordListener());
		confirmPassword.addFocusListener(new ConfirmPasswordListener());
		logout.addActionListener(new LogoutListener());
		browseProducts.addActionListener(new BrowseProductsListener());
		cart.addActionListener(new CartListener());
		settleBalance.addActionListener(new SOBListener());
	}
	
	public void update()
	{
		label = String.format("<html><div style='text-align: center;'>Welcome %s!<br>You have a balance of $%.2f</html>", account.getFirstName(), account.getOutstandingBalance());
		welcome.setText(label);
		
		label = String.format("<html><div style='text-align: center;'>Change Name<br>First Name:%s<br>Last Name:%s</html>", account.getFirstName(), account.getLastName());
		currentName.setText(label);
		
		label = String.format("<html><div style='text-align: center;'>Change Address<br>Address:%s<br></html>", account.getAddress());
		currentAddress.setText(label);
	
	}
	
	public void checkButtons()
	{
		if(account.getOutstandingBalance() > 0.00)
			settleBalance.setEnabled(true);
		else
			settleBalance.setEnabled(false);
		
		if(cctrl.grabCart(account).size() == 0)
			cart.setEnabled(false);
		else
			cart.setEnabled(true);
		
		cartFrame.publicUpdate();
		catalog.publicUpdate();
	}
	
	class SOBListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			sobFrame.setVisible(true);
			checkButtons();
		}
		
	}
	
	class CartListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			cartFrame.setVisible(true);
			checkButtons();
		}
		
	}
	
	class ShopperWindowListener implements WindowListener{
		@Override
		public void windowActivated(WindowEvent arg0) {}

		@Override
		public void windowClosed(WindowEvent arg0) {
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			setVisible(true);
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

	class ModifyInfoBackListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			toggleModifyInfo(false);
			checkButtons();
		}
	}
	
	class ModifyInfoListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			toggleModifyInfo(true);
			
		}
		
	}
	
	class BrowseProductsListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			catalog.setVisible(true);
			
		}
		
	}
	
	class ChangeNameListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			toggleInfoChange(false);
			toggleChangeName(true);
			toggleChangeAddress(false);
			toggleChangePassword(false);
		}
		
	}

	class ChangeAddressListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			toggleInfoChange(false);
			toggleChangeName(false);
			toggleChangeAddress(true);
			toggleChangePassword(false);
		}
		
	}

	class ChangePasswordListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			toggleInfoChange(false);
			toggleChangeName(false);
			toggleChangeAddress(false);
			toggleChangePassword(true);
		}
		
	}
	
	class FirstNameListener implements FocusListener
	{
		@Override
		public void focusGained(FocusEvent arg0) {
			if(firstName.getText().equals(Placeholder.FIRSTNAME.toString()))
			{
				firstName.setText("");
				firstName.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(firstName.getText().equals(""))
			{
				firstName.setText(Placeholder.FIRSTNAME.toString());
				firstName.setForeground(Color.GRAY);
			}
			
		}
	}
	
	class LastNameListener implements FocusListener
	{
		@Override
		public void focusGained(FocusEvent arg0) {
			if(lastName.getText().equals(Placeholder.LASTNAME.toString()))
			{
				lastName.setText("");
				lastName.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(lastName.getText().equals(""))
			{
				lastName.setText(Placeholder.LASTNAME.toString());
				lastName.setForeground(Color.GRAY);
			}
			
		}
	}
	
	class NameCancelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			toggleInfoChange(true);
			toggleChangeName(false);
			resetChangeName();
		}
		
	}
	
	class AddressCancelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			toggleInfoChange(true);
			toggleChangeAddress(false);
			resetChangeAddress();
		}
		
	}
	
	class PasswordCancelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			toggleInfoChange(true);
			toggleChangePassword(false);
			resetChangePassword();
		}
		
	}
	
	class NameSubmitListener implements ActionListener{
		String errors = "<html>Here are the following errors:<br>";
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(valid())
			{
				if(!firstName.getText().equals(Placeholder.FIRSTNAME.toString()))
				{
					account.setFirstName(firstName.getText());
				}
					
				if(!lastName.getText().equals(Placeholder.LASTNAME.toString()))
					account.setFirstName(lastName.getText());
			}
			actrl.updateAccount(account);
			toggleInfoChange(true);
			toggleChangeName(false);
			resetChangeName();
			update();
			errors = "<html>Here are the following errors:<br>";
		}
		
		private boolean valid()
		{
			boolean valid = true;
			if(firstName.getText().trim().isEmpty())
			{
				errors += "<br>First name is empty";
				valid = false;
			}
			
			if(lastName.getText().trim().isEmpty())
			{
				errors += "<br>Last name is empty";
				valid = false;
			}
			errors +="</html>";
			return valid;
		}
		
	}
	
	class AddressSubmitListener implements ActionListener{
		String errors = "<html>Here are the following errors:<br>";
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(valid())
			{
				account.setAddress(address.getText());
			}
			
			actrl.updateAccount(account);
			toggleInfoChange(true);
			toggleChangeAddress(false);
			resetChangeAddress();
			update();
			errors = "<html>Here are the following errors:<br>";
		}
		
		private boolean valid()
		{
			boolean valid = true;
			if(address.getText().trim().isEmpty())
			{
				errors += "<br>Address is empty";
				valid = false;
			}
			errors +="</html>";
			return valid;
		}
		
	}
	
	class PasswordSubmitListener implements ActionListener{
		String errors = "<html>Here are the following errors:<br>";
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(valid())
				account.setPassword(String.valueOf(newPassword.getPassword()));
			
			actrl.updateAccount(account);
			toggleInfoChange(true);
			toggleChangePassword(false);
			resetChangePassword();
			errors = "<html>Here are the following errors:<br>";
		}
		
		private boolean valid()
		{
			boolean valid = true;
			
			if(String.valueOf(currentPassword.getPassword()).trim().isEmpty())
			{
				errors += "<br>Address is empty";
				valid = false;
			}
			
			else if(!String.valueOf(currentPassword.getPassword()).equals(account.getPassword()))
			{
				errors += "<br>Invalid current password";
				valid = false;
			}
			

			
			else
			{
				if(!String.valueOf(newPassword.getPassword()).equals(String.valueOf(confirmPassword.getPassword())))
				{
					errors += "<br>Passwords do not match";
					valid = false;
				}
				else if(!(String.valueOf(newPassword.getPassword()).length() >= 6) || !String.valueOf(newPassword.getPassword()).matches("^.*[^a-zA-Z ].*$"))
				{
					errors += "<br>Password should have 6 characters or more and contains non-alphabetical character";
					valid = false;
				}
					
			}
			errors +="</html>";
			return valid;
		}
		
	}

	class AddressListener implements FocusListener{
		@Override
		public void focusGained(FocusEvent arg0) {
			if(address.getText().equals(Placeholder.ADDRESS.toString()))
			{
				address.setText("");
				address.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(address.getText().equals(""))
			{
				address.setText(Placeholder.ADDRESS.toString());
				address.setForeground(Color.GRAY);
			}
			
		}
	}
	
	class CurrentPasswordListener implements FocusListener{
		@Override
		public void focusGained(FocusEvent arg0) {
			if(String.valueOf(currentPassword.getPassword()).equals(Placeholder.OLDPASSWORD.toString()))
			{
				currentPassword.setText("");
				currentPassword.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(String.valueOf(currentPassword.getPassword()).equals(""))
			{
				currentPassword.setText(Placeholder.OLDPASSWORD.toString());
				currentPassword.setForeground(Color.GRAY);
			}
			
		}
	}
	
	class NewPasswordListener implements FocusListener{
		@Override
		public void focusGained(FocusEvent arg0) {
			if(String.valueOf(newPassword.getPassword()).equals(Placeholder.NEWPASSWORD.toString()))
			{
				newPassword.setText("");
				newPassword.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(String.valueOf(newPassword.getPassword()).equals(""))
			{
				newPassword.setText(Placeholder.NEWPASSWORD.toString());
				newPassword.setForeground(Color.GRAY);
			}
			
		}
	}
	
	class ConfirmPasswordListener implements FocusListener{
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
	
	class LogoutListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			account = null;
			setVisible(false);
			frame.logout();
		}
		
	}
	
	private void resetChangeName()
	{
		lastName.setText(Placeholder.LASTNAME.toString());
		lastName.setForeground(Color.GRAY);
		
		firstName.setText(Placeholder.FIRSTNAME.toString());
		firstName.setForeground(Color.GRAY);
	}
	
	private void resetChangeAddress()
	{
		address.setText(Placeholder.ADDRESS.toString());
		address.setForeground(Color.GRAY);
	}
	
	private void resetChangePassword()
	{
		currentPassword.setText(Placeholder.OLDPASSWORD.toString());
		currentPassword.setForeground(Color.GRAY);
		
		newPassword.setText(Placeholder.NEWPASSWORD.toString());
		newPassword.setForeground(Color.GRAY);
		
		confirmPassword.setText(Placeholder.CONFIRMPASSWORD.toString());
		confirmPassword.setForeground(Color.GRAY);
	}
	
	private void toggleChangeName(boolean toggle)
	{
		nameChangeSubmit.setVisible(toggle);
		nameChangeCancel.setVisible(toggle);
		firstName.setVisible(toggle);
		lastName.setVisible(toggle);
		currentName.setVisible(toggle);
		changeNamePanel.setVisible(toggle);
		
		nameChangeSubmit.setEnabled(toggle);
		nameChangeCancel.setEnabled(toggle);
		firstName.setEnabled(toggle);
		lastName.setEnabled(toggle);
		currentName.setEnabled(toggle);
		changeNamePanel.setEnabled(toggle);
	}
	
	private void toggleChangeAddress(boolean toggle)
	{
		address.setVisible(toggle);
		addressChangeSubmit.setVisible(toggle);
		addressChangeCancel.setVisible(toggle);
		currentAddress.setVisible(toggle);
		changeAddressPanel.setVisible(toggle);
		
		address.setEnabled(toggle);
		addressChangeSubmit.setEnabled(toggle);
		addressChangeCancel.setEnabled(toggle);
		currentAddress.setEnabled(toggle);
		changeAddressPanel.setEnabled(toggle);
	}
	
	private void toggleChangePassword(boolean toggle)
	{
		currentPassword.setVisible(toggle);
		newPassword.setVisible(toggle);
		confirmPassword.setVisible(toggle);
		changePassLabel.setVisible(toggle);
		changePasswordPanel.setVisible(toggle);
		
		currentPassword.setEnabled(toggle);
		newPassword.setEnabled(toggle);
		confirmPassword.setEnabled(toggle);
		changePassLabel.setEnabled(toggle);
		changePasswordPanel.setEnabled(toggle);
	}
	
	private void toggleModifyInfo(boolean toggle)
	{
		modifyInfoPanel.setVisible(toggle);
		shopperMenuPanel.setVisible(!toggle);
		modifyInfoBack.setVisible(toggle);
		changeName.setVisible(toggle);
		changeAddress.setVisible(toggle);
		changePassword.setVisible(toggle);
		
		modifyInfoPanel.setEnabled(toggle);
		shopperMenuPanel.setEnabled(!toggle);
		modifyInfoBack.setEnabled(toggle);
		changeName.setEnabled(toggle);
		changeAddress.setEnabled(toggle);
		changePassword.setEnabled(toggle);
	
	}

	private void toggleInfoChange(boolean toggle)
	{
		changeName.setEnabled(toggle);
		changeAddress.setEnabled(toggle);
		changePassword.setEnabled(toggle);
		modifyInfoBack.setEnabled(toggle);
		
		if(!toggle) setSize(1100,450);
		else setSize(550,450);
	}
}
