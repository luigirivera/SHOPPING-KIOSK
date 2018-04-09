package view;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.AccountsController;
import controller.CartController;
import controller.StocksController;
import model.Person;

public class AdminMenu extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel adminMenuPanel;
	private JLabel welcome;
	private JButton manageAccounts, manageStocks, prepareReceipt, shutdown, logout;
	private AccountsList accountsListFrame;
	private AdminStocksList stocksListFrame;
	private DeliveryReceipt receiptFrame;
	private Person account;
	private AccountsController actrl;
	private Mainframe frame;
	private StocksController sctrl;
	private CartController cctrl;
	
	public AdminMenu(Person account, AccountsController actrl, StocksController sctrl, CartController cctrl, Mainframe frame)
	{
		super("Pick 'N Save : Administrator Menu");

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
		accountsListFrame = new AccountsList(actrl, account);
		stocksListFrame = new AdminStocksList(sctrl);
		receiptFrame = new DeliveryReceipt(actrl, sctrl, cctrl, account);
		
		adminMenuPanel = new JPanel();
		
		welcome = new JLabel(String.format("Welcome %s!", account.getFirstName()), SwingConstants.CENTER);
		
		manageAccounts = new JButton("Manage Accounts");
		manageStocks = new JButton("Manage Stocks");
		prepareReceipt = new JButton("Prepare Delivery Receipt");
		shutdown = new JButton("Shutdown");
		logout = new JButton("Logout");
	}
	
	private void initialize()
	{
		add(adminMenuPanel);
		
		adminMenuPanel.setLayout(null);
		
		welcome.setFont(new Font("Arial", Font.BOLD, 25));
		
		adminMenuPanel.add(welcome);
		adminMenuPanel.add(manageAccounts);
		adminMenuPanel.add(manageStocks);
		adminMenuPanel.add(prepareReceipt);
		adminMenuPanel.add(shutdown);
		adminMenuPanel.add(logout);
		
		
		adminMenuPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
		welcome.setBounds(10, 30, this.getWidth()-50, 30);
		manageAccounts.setBounds(adminMenuPanel.getWidth()/5, adminMenuPanel.getHeight()/5, 325, 50);
		manageStocks.setBounds(manageAccounts.getX(), manageAccounts.getY()+60, manageAccounts.getWidth(), manageAccounts.getHeight());
		prepareReceipt.setBounds(manageStocks.getX(), manageStocks.getY()+60, manageAccounts.getWidth(), manageAccounts.getHeight());
		shutdown.setBounds(prepareReceipt.getX(), prepareReceipt.getY()+60, manageAccounts.getWidth(), manageAccounts.getHeight());
		logout.setBounds(shutdown.getX(), shutdown.getY()+60, shutdown.getWidth(), shutdown.getHeight());
		
	}
	
	private void addListeners()
	{
		manageAccounts.addActionListener(new ManageAccountsListener());
		manageStocks.addActionListener(new ManageStocksListener());
		prepareReceipt.addActionListener(new PrepareReceiptListener());
		shutdown.addActionListener(new ShutdownListener());
		logout.addActionListener(new LogoutListener());
		this.addWindowListener(new AdminWindowListener());
	}
	
	class ManageAccountsListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			accountsListFrame.setVisible(true);			
		}
		
	}
	
	class ManageStocksListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			stocksListFrame.setVisible(true);			
		}
		
	}
		
	class PrepareReceiptListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			receiptFrame.setVisible(true);
		}
		
	}
	
	class ShutdownListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
		
	}
	
	class LogoutListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			account = null;
			accountsListFrame.dispose();
			setVisible(false);
			frame.logout();
		}
		
	}
	
	class AdminWindowListener implements WindowListener{
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
}
