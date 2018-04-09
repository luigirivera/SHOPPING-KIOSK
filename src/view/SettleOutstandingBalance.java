package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.AccountsController;
import model.Person;

public class SettleOutstandingBalance extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel sobPanel;
	private JTextField cardNumber, cvc, month, year, amount;
	private JButton confirm, cancel;
	private JLabel slash;
	private Person account;
	private AccountsController actrl;
	private ShopperMenu view;
	
	public SettleOutstandingBalance(Person account, AccountsController actrl, ShopperMenu view)
	{
		super("Settle Outstanding Balance");
		
		URL iconURL = getClass().getResource("/res/favicon.png");
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());
		
		this.actrl = actrl;
		this.view = view;
		this.account = account;
		
		instantiate();
		setLayout(null);
		setSize(350, 250);
		initialize();
		addListeners();
		
		setResizable(false);
		setVisible(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void instantiate()
	{
		sobPanel = new JPanel();
		
		cardNumber = new JTextField();
		cvc = new JTextField();
		month = new JTextField();
		year = new JTextField();
		amount = new JTextField();
		
		slash = new JLabel("/");
		
		confirm = new JButton("Confirm");
		cancel = new JButton("Cancel");
	}
	
	private void initialize()
	{
		add(sobPanel);
		
		sobPanel.setLayout(null);
		
		slash.setFont(new Font("Arial", Font.BOLD, 25));
		
		sobPanel.add(cardNumber);
		sobPanel.add(cvc);
		sobPanel.add(month);
		sobPanel.add(slash);
		sobPanel.add(year);
		sobPanel.add(amount);
		sobPanel.add(confirm);
		sobPanel.add(cancel);
		
		sobPanel.setBounds(0,0, this.getWidth(), this.getHeight());
		cardNumber.setBounds(10, 20, 200, 30);
		cvc.setBounds(cardNumber.getX() + cardNumber.getWidth() + 20, cardNumber.getY(), 40, cardNumber.getHeight());
		month.setBounds(cardNumber.getX(), cardNumber.getY() + cardNumber.getHeight() +20, cvc.getWidth(), cvc.getHeight());
		slash.setBounds(month.getX() + month.getWidth() + 5, month.getY(), 10, month.getHeight());
		year.setBounds(month.getX() + month.getWidth() + 20, month.getY(), month.getWidth(), month.getHeight());
		amount.setBounds(month.getX(), year.getY() + year.getHeight() + 20, cardNumber.getWidth(), year.getHeight());
		confirm.setBounds(amount.getX(), amount.getY() + amount.getHeight() + 20, 100, 30);
		cancel.setBounds(confirm.getX() + confirm.getWidth() + 20, confirm.getY(), confirm.getWidth(), confirm.getHeight());
		
		resetSOB();
	}
	
	private void resetSOB()
	{
		cardNumber.setText(Placeholder.CARDNUMBER.toString());
		cvc.setText(Placeholder.CVC.toString());
		month.setText(Placeholder.MONTH.toString());
		year.setText(Placeholder.YEAR.toString());
		amount.setText(Placeholder.AMOUNT.toString());
		
		cardNumber.setForeground(Color.GRAY);
		cvc.setForeground(Color.GRAY);
		month.setForeground(Color.GRAY);
		year.setForeground(Color.GRAY);
		amount.setForeground(Color.GRAY);
	}
	
	private void addListeners() {
		confirm.addActionListener(new ConfirmListener());
		cancel.addActionListener(new CancelListener());
		cardNumber.addFocusListener(new CardNumberListener());
		cvc.addFocusListener(new CVCListener());
		month.addFocusListener(new MonthListener());
		year.addFocusListener(new YearListener());
		amount.addFocusListener(new AmountListener());
	}
	
	class ConfirmListener implements ActionListener{
		String errors = "<html>Here are the following errors:<br>";
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(verify())
			{
				account.setOutstandingBalance(account.getOutstandingBalance() - Double.parseDouble(amount.getText()));
				actrl.updateAccount(account);
			}
			
			resetSOB();
			dispose();
			errors = "<html>Here are the following errors:<br>";
			view.checkButtons();
			view.update();
		}
		
		private boolean verify()
		{
			boolean valid = true;
			
			if(cardNumber.getText().equals(Placeholder.CARDNUMBER.toString()) || cardNumber.getText().trim().isEmpty())
			{
				errors += "<br>Please enter a card number";
				valid = false;
			}
			
			else
			{
				try {
					Integer.parseInt(cardNumber.getText());
				} catch (NumberFormatException e) {
					errors += "<br>Please enter an integer for the card number field";
					valid = false;
				}
			}
				
			if(cvc.getText().equals(Placeholder.CARDNUMBER.toString()) || cvc.getText().trim().isEmpty())
			{
				errors += "<br>Please enter a CVC";
				valid = false;
			}
			
			else
			{
				try {
					Integer.parseInt(cvc.getText());
				} catch (NumberFormatException e) {
					errors += "<br>Please enter an integer for the cvc field";
					valid = false;
				}
			}
			
			if(month.getText().equals(Placeholder.CARDNUMBER.toString()) || month.getText().trim().isEmpty())	
			{
				errors += "<br>Please enter a month";
				valid = false;
			}
			
			else
			{
				try {
					Integer.parseInt(month.getText());
				} catch (NumberFormatException e) {
					errors += "<br>Please enter an integer for the month field";
					valid = false;
				}
			}
			
			if(year.getText().equals(Placeholder.CARDNUMBER.toString()) || year.getText().trim().isEmpty())
			{
				errors += "<br>Please enter a year";
				valid = false;
			}
			
			else
			{
				try {
					Integer.parseInt(year.getText());
				} catch (NumberFormatException e) {
					errors += "<br>Please enter an integer for the year field";
					valid = false;
				}
			}
			
			if(amount.getText().equals(Placeholder.CARDNUMBER.toString()) || amount.getText().trim().isEmpty())
			{
				errors += "<br>Please enter an amount";
				valid = false;
			}
			
			else
			{
				try {
					Double.parseDouble(amount.getText());
				} catch (NumberFormatException e) {
					errors += "<br>Please enter a double value for the amount field";
					valid = false;
				}
			}
			
			errors +="</html>";
			return valid;
		}
		
	}
	
	class CancelListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			resetSOB();
			dispose();
			view.update();
		}
	}
	
	class CardNumberListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent arg0) {
			if(cardNumber.getText().equals(Placeholder.CARDNUMBER.toString()))
			{
				cardNumber.setText("");
				cardNumber.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(cardNumber.getText().equals(""))
			{
				cardNumber.setText(Placeholder.CARDNUMBER.toString());
				cardNumber.setForeground(Color.GRAY);
			}
			
		}
		
	}
	
	class CVCListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent arg0) {
			if(cvc.getText().equals(Placeholder.CVC.toString()))
			{
				cvc.setText("");
				cvc.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(cvc.getText().equals(""))
			{
				cvc.setText(Placeholder.CVC.toString());
				cvc.setForeground(Color.GRAY);
			}
			
		}
		
	}
	
	class MonthListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent arg0) {
			if(month.getText().equals(Placeholder.MONTH.toString()))
			{
				month.setText("");
				month.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(month.getText().equals(""))
			{
				month.setText(Placeholder.MONTH.toString());
				month.setForeground(Color.GRAY);
			}
			
		}
		
	}
	
	class YearListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent arg0) {
			if(year.getText().equals(Placeholder.YEAR.toString()))
			{
				year.setText("");
				year.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(year.getText().equals(""))
			{
				year.setText(Placeholder.YEAR.toString());
				year.setForeground(Color.GRAY);
			}
			
		}
		
	}
	
	class AmountListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent arg0) {
			if(amount.getText().equals(Placeholder.AMOUNT.toString()))
			{
				amount.setText("");
				amount.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(amount.getText().equals(""))
			{
				amount.setText(Placeholder.AMOUNT.toString());
				amount.setForeground(Color.GRAY);
			}
			
		}
		
	}

}
