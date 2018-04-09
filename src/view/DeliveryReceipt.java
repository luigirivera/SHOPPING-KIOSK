package view;

import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import controller.AccountsController;
import controller.CartController;
import controller.StocksController;
import model.Cart;
import model.Item;
import model.Person;

public class DeliveryReceipt extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel receiptPanel;
	private JButton prev, next, print;
	private JTextPane receiptField;
	private JScrollPane scrollReceiptField;
	private AccountsController actrl;
	private StocksController sctrl;
	private CartController cctrl;
	private String receiptLabel;
	private Person admin, currentViewAccount;
	private List<Integer> accountIDs;
	
	public DeliveryReceipt(AccountsController actrl, StocksController sctrl, CartController cctrl, Person admin)
	{
		super("Pick 'N Save : Delivery Receipts");
		
		this.actrl = actrl;
		this.sctrl = sctrl;
		this.cctrl = cctrl;
		this.admin = admin;
		
		URL iconURL = getClass().getResource("/res/favicon.png");
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());
		
		setSize(600, 900);
		
		instantiate();
		setLayout(null);
		initialize();
		addListeners();
				
		setResizable(false);
		setVisible(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void instantiate()
	{
		receiptPanel = new JPanel();
		
		prev = new JButton("◄");
		print = new JButton("Print");
		next = new JButton("►");
		
		receiptField = new JTextPane();
		scrollReceiptField = new JScrollPane(receiptField);
	}
	
	private void initialize()
	{
		add(receiptPanel);
		
		receiptPanel.setLayout(null);;
		receiptPanel.add(prev);
		receiptPanel.add(print);
		receiptPanel.add(next);
		receiptPanel.add(scrollReceiptField);
		
		receiptField.setContentType("text/html");
		receiptField.setEditable(false);
		
		int width = 80;
		int height = 30;
		receiptPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
		print.setBounds(this.getWidth()/2 - (int)Math.floor(width/2) - 10, 10, width, height);
		prev.setBounds(print.getX() - width - 20, print.getY(), width, height);
		next.setBounds(print.getX() + width + 20, print.getY(), width, height);
		scrollReceiptField.setBounds(0, print.getY() + print.getHeight() + 20, this.getWidth() - 15, this.getHeight() - (print.getY() + print.getHeight() + 20) - 35);
		
		publicUpdate();
	}
	
	public void publicUpdate()
	{
		accountIDs = cctrl.getAccountIDs();
		
		if(accountIDs.size() > 0)
		{
			Person firstAccount = actrl.getAccount(accountIDs.get(0));
			List<Cart> firstCart = cctrl.getCheckedOutItems(firstAccount);
			List<Item> stock = new ArrayList<Item>();
			
			for(Cart c : firstCart)
				stock.add(sctrl.getStock(c.getProductcode()));
			
			updateReceipt(firstAccount, firstCart, stock);
		}
		
		else
		{
			receiptLabel = "<html><div style='text-align: center;'>Pick 'N Save<br>"
					+ "1 Coffee Way, Columbus, Ohio, USA<br>"
					+ "------------------------------------------------------------------------------------------------------------------<br><br>"

					+ "Administrator in Charge: " + admin.getLastName() + ", " + admin.getFirstName() + " (" + admin.getUsername() + ")<br>"
					+ "(date and time here)<br>"
					+ "------------------------------------------------------------------------------------------------------------------<br>"
					+ "NO RECEIPTS TO PRODUCE";
			receiptField.setText(receiptLabel);
		}

	}
	
	private void update()
	{
		Person account = actrl.getAccount(currentViewAccount.getId());
		List<Cart> firstCart = cctrl.getCheckedOutItems(currentViewAccount);
		List<Item> stock = new ArrayList<Item>();
		
		for(Cart c : firstCart)
			stock.add(sctrl.getStock(c.getProductcode()));
		
		updateReceipt(account, firstCart, stock);
	}
	
	private void updateReceipt(Person account, List<Cart> cart, List<Item> items)
	{
		int num = 0;
		double total = 0;
		double carttotal = 0;
		double newOutstanding = 0;
		
		receiptLabel = "<html><div style='text-align: center;'>Pick 'N Save<br>"
															+ "1 Coffee Way, Columbus, Ohio, USA<br>"
															+ "------------------------------------------------------------------------------------------------------------------<br><br>"

															+ "Administrator in Charge: " + admin.getLastName() + ", " + admin.getFirstName() + " (" + admin.getUsername() + ")<br>"
															+ "(date and time here)<br>"
															+ "------------------------------------------------------------------------------------------------------------------<br>";
		receiptLabel += "Products Bought:<br>";
		
		for(int i = 0; i < cart.size(); i++)
		{
			Cart c = cart.get(i);
			Item s = items.get(i);
			
			double itemSubtotal = (s.getUnitsellingprice() - (s.getUnitsellingprice() * (s.getDiscountrate()/100))) * c.getQuantity();
			
			num += c.getQuantity();
			total += (s.getUnitsellingprice() * (s.getDiscountrate()/100));
			carttotal += itemSubtotal;
			
			receiptLabel += String.format("%d - %s's %s (%s) - %s $%.2f<br>$%.2f/pc ($%.2f) w/ %.2f%% discount<br><br>", 
					c.getQuantity(), s.getSupplier(), s.getProduct(), c.getProductcode(), s.getCategory(), itemSubtotal,
					s.getUnitsellingprice(), s.getUnitsellingprice() * c.getQuantity(), s.getDiscountrate());
		}
		
		newOutstanding = account.getOutstandingBalance() + carttotal;
		
		receiptLabel +="<br>Number of Items: " + num + "<br>"
					 + "Total Discount: $" + total + "<br>"
					 + "Bill Amount: $" + carttotal + "<br>"
					 + "Total Outstanding Balance: $" + newOutstanding + "<br>";
		
		receiptLabel += "------------------------------------------------------------------------------------------------------------------<br>"
					  + "Order Details:<br>"
					  + "Customer ID: " + account.getId() + " (" + account.getUsername() + ")<br>"
					  + "Customer Name: " + account.getLastName() + ", " + account.getFirstName() + "<br>"
					  + "Delivery Address: " + account.getAddress() + "<br><br>"
					  + "------------------------------------------------------------------------------------------------------------------<br>"
					  + "THIS SERVES AS YOUR OFFICIAL RECEIPT";
		
		receiptLabel += "</html>";
		receiptField.setText(receiptLabel);
		
		currentViewAccount = account;
	}
	
	private void addListeners()
	{
		next.addActionListener(new NextButton());
		prev.addActionListener(new PrevButton());
		print.addActionListener(new PrintButton());
	}
	
	class PrintButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			boolean done = false;
			BufferedImage img = new BufferedImage(receiptField.getWidth(), receiptField.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = img.createGraphics();
            
            receiptField.printAll(g2d);
            g2d.dispose();

            try {
            	File file = new File(String.format("UserID %d Receipt.png", currentViewAccount.getId()));
                ImageIO.write(img, "png", file);
                Desktop.getDesktop().open(file);
                done = true;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            if(done)
            	cctrl.deliverItems(currentViewAccount.getId());
            
            next.doClick();
		}
		
	}
	
	class NextButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			accountIDs = cctrl.getAccountIDs();
			if(accountIDs.size() > 0)
			{
				int currentIndex = accountIDs.lastIndexOf(currentViewAccount.getId());
				if(currentIndex+1 == accountIDs.size())
					currentViewAccount = actrl.getAccount(accountIDs.get(0));
				else
					currentViewAccount = actrl.getAccount(accountIDs.get(currentIndex+1));
				
				update();
			}
			
			else
			{
				receiptLabel = "<html><div style='text-align: center;'>Pick 'N Save<br>"
						+ "1 Coffee Way, Columbus, Ohio, USA<br>"
						+ "------------------------------------------------------------------------------------------------------------------<br><br>"

						+ "Administrator in Charge: " + admin.getLastName() + ", " + admin.getFirstName() + " (" + admin.getUsername() + ")<br>"
						+ "(date and time here)<br>"
						+ "------------------------------------------------------------------------------------------------------------------<br>"
						+ "NO RECEIPTS TO PRODUCE";
				receiptField.setText(receiptLabel);
			}
			
			
		}
		
	}
	
	class PrevButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			accountIDs = cctrl.getAccountIDs();
			
			if(accountIDs.size() > 0)
			{
				int currentIndex = accountIDs.lastIndexOf(currentViewAccount.getId());
				if(currentIndex-1 < 0)
					currentViewAccount = actrl.getAccount(accountIDs.get(accountIDs.size()-1));
				else
					currentViewAccount = actrl.getAccount(accountIDs.get(currentIndex-1));
				
				update();
			}
			
			else
			{
				receiptLabel = "<html><div style='text-align: center;'>Pick 'N Save<br>"
						+ "1 Coffee Way, Columbus, Ohio, USA<br>"
						+ "------------------------------------------------------------------------------------------------------------------<br><br>"

						+ "Administrator in Charge: " + admin.getLastName() + ", " + admin.getFirstName() + " (" + admin.getUsername() + ")<br>"
						+ "(date and time here)<br>"
						+ "------------------------------------------------------------------------------------------------------------------<br>"
						+ "NO RECEIPTS TO PRODUCE";
				receiptField.setText(receiptLabel);
			}
		}
		
	}
}
