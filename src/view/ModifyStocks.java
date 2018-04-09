package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.StocksController;
import model.Item;

public class ModifyStocks extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField category, supplier, product, purchase, selling, discount;
	private JButton modify, cancel;
	private JPanel modifyStockPanel;
	private StocksController sctrl;
	private AdminStocksList view;
	private Item stock;
	
	public ModifyStocks(StocksController sctrl, AdminStocksList view)
	{
		super("Modify Stock");
		
		URL iconURL = getClass().getResource("/res/favicon.png");
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());
		
		this.sctrl = sctrl;
		this.view = view;
		
		instantiate();
		setLayout(null);
		setSize(250, 500);
		initialize();
		addListeners();
		
		setResizable(false);
		setVisible(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void instantiate()
	{
		modifyStockPanel = new JPanel();
		stock = new Item();
		
		category = new JTextField();
		supplier = new JTextField();
		product = new JTextField();
		purchase = new JTextField();
		selling = new JTextField();
		discount = new JTextField();
		
		modify = new JButton("Modfiy");
		cancel = new JButton("Cancel");
	}
	
	private void initialize()
	{
		add(modifyStockPanel);
		
		modifyStockPanel.setLayout(null);
		
		modifyStockPanel.add(category);
		modifyStockPanel.add(supplier);
		modifyStockPanel.add(product);
		modifyStockPanel.add(purchase);
		modifyStockPanel.add(selling);
		modifyStockPanel.add(discount);
		modifyStockPanel.add(modify);
		modifyStockPanel.add(cancel);
		
		modifyStockPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
		category.setBounds(10, 20, 150, 30);
		supplier.setBounds(category.getX(), category.getY() + category.getHeight() + 20, category.getWidth(), category.getHeight());
		product.setBounds(supplier.getX(), supplier.getY() + supplier.getHeight() + 20, supplier.getWidth(), supplier.getHeight());
		purchase.setBounds(product.getX(), product.getY() + product.getHeight() + 40, product.getWidth(), product.getHeight());
		selling.setBounds(purchase.getX(), purchase.getY() + purchase.getHeight() + 20, purchase.getWidth(), purchase.getHeight());
		discount.setBounds(selling.getX(), selling.getY() + selling.getHeight() + 20, selling.getWidth(), selling.getHeight());
		
		int height = 30;
		modify.setBounds(category.getX(), this.getHeight() - height*2 - 30, 100, height);
		cancel.setBounds(modify.getX() + modify.getWidth() + 10, modify.getY(), modify.getWidth(), modify.getHeight());
	}

	private void formatFields() {
		category.setText(stock.getCategory());
		supplier.setText(stock.getSupplier());
		product.setText(stock.getProduct());
		purchase.setText(String.valueOf(stock.getPurchaseprice()));
		selling.setText(String.valueOf(stock.getUnitsellingprice()));
		discount.setText(String.valueOf(stock.getDiscountrate()));
		
	}
	
	private void addListeners() {
		modify.addActionListener(new ModifyListener());
		cancel.addActionListener(new CancelListener());
		category.addFocusListener(new CategoryListener());
		supplier.addFocusListener(new SupplierListener());
		product.addFocusListener(new ProductListener());
		purchase.addFocusListener(new PurchaseListener());
		selling.addFocusListener(new SellingListener());
		discount.addFocusListener(new DiscountListener());
	}

	public Item getStock() {
		return stock;
	}

	public void setStock(Item stock) {
		this.stock = stock;
		formatFields();
	}
	
	class ModifyListener implements ActionListener{
		String errors = "<html>Here are the following errors:<br>";
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(validInputs())
			{
				String catString = category.getText().substring(0, 1).toUpperCase() + category.getText().toLowerCase().substring(1);
				String supString = supplier.getText().substring(0, 1).toUpperCase() + supplier.getText().toLowerCase().substring(1);
				String prodString = product.getText().substring(0, 1).toUpperCase() + product.getText().toLowerCase().substring(1);
				String prodCode = catString.substring(0, 1) + supString.substring(0, 1) + prodString.substring(0, 1) + stock.getIdstocks();
				
				stock.setCategory(catString);
				stock.setSupplier(supString);
				stock.setProduct(product.getText());
				stock.setProductcode(prodCode);
				stock.setQuantitysold(stock.getQuantitysold());
				stock.setQuantityavailable(stock.getQuantityavailable());
				stock.setPurchaseprice(Double.parseDouble(purchase.getText()));
				stock.setUnitsellingprice(Double.parseDouble(selling.getText()));
				stock.setDiscountrate(Double.parseDouble(discount.getText()));
				
				JOptionPane.showMessageDialog(null, "<html>The code of this product is:<br>" + prodCode + "</html>", "Add Stocks", JOptionPane.INFORMATION_MESSAGE);
				sctrl.updateStock(stock);
				
			}
			
			else
				JOptionPane.showMessageDialog(null, errors, "Modify Stocks Error", JOptionPane.ERROR_MESSAGE);
			
			dispose();
			errors = "<html>Here are the following errors:<br>";
			view.update();
		}
		
		private boolean validInputs()
		{
			boolean valid = true;
			
			if(category.getText().equals(Placeholder.CATEGORY.toString()) || category.getText().trim().isEmpty())
			{
				errors += "<br>Please enter a category";
				valid = false;
			}
			
			if(supplier.getText().equals(Placeholder.SUPPLIER.toString()) || supplier.getText().trim().isEmpty())
			{
				errors += "<br>Please enter a supplier";
				valid = false;
			}
			
			if(product.getText().equals(Placeholder.PRODUCT.toString()) || product.getText().trim().isEmpty())
			{
				errors += "<br>Please enter a product name";
				valid = false;
			}
			
			if(valid && sctrl.checkStocks(category.getText(), supplier.getText(), product.getText()))
				if(sctrl.getStockDuplicate(category.getText(), supplier.getText(), product.getText()).getIdstocks() != stock.getIdstocks())
				{
					errors += "<br>Product exists already";
					valid = false;
				}
			
			if(purchase.getText().equals(Placeholder.PURCHASE.toString()) || purchase.getText().trim().isEmpty())
			{
				errors += "<br>Please enter a purchase price value";
				valid = false;
			}
			
			else
			{
				try {
					Double.parseDouble(purchase.getText());
				} catch (NumberFormatException e) {
					errors += "<br>Please enter an integer for the purchase price field";
					valid = false;
				}
			}
			
			if(selling.getText().equals(Placeholder.SELLING.toString()) || selling.getText().trim().isEmpty())
			{
				errors += "<br>Please enter a selling price value";
				valid = false;
			}
			
			else
			{
				try {
					Double.parseDouble(selling.getText());
				} catch (NumberFormatException e) {
					errors += "<br>Please enter an integer for the selling price field";
					valid = false;
				}
			}
			
			if(discount.getText().equals(Placeholder.DISCOUNT.toString()) || discount.getText().trim().isEmpty())
			{
				errors += "<br>Please enter a discount rate value";
				valid = false;
			}
			
			else
			{
				try {
					Double.parseDouble(discount.getText());
				} catch (NumberFormatException e) {
					errors += "<br>Please enter an integer for the discount rate field";
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
			dispose();
			view.update();
		}
	}
	
	class CategoryListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent arg0) {
			if(category.getText().equals(Placeholder.CATEGORY.toString()))
			{
				category.setText("");
				category.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(category.getText().equals(""))
			{
				category.setText(Placeholder.CATEGORY.toString());
				category.setForeground(Color.GRAY);
			}
			
		}
		
	}
	
	class SupplierListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent arg0) {
			if(supplier.getText().equals(Placeholder.SUPPLIER.toString()))
			{
				supplier.setText("");
				supplier.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(supplier.getText().equals(""))
			{
				supplier.setText(Placeholder.SUPPLIER.toString());
				supplier.setForeground(Color.GRAY);
			}
			
		}
		
	}
	
	class ProductListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent arg0) {
			if(product.getText().equals(Placeholder.PRODUCT.toString()))
			{
				product.setText("");
				product.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(product.getText().equals(""))
			{
				product.setText(Placeholder.PRODUCT.toString());
				product.setForeground(Color.GRAY);
			}
			
		}
		
	}
	
	class PurchaseListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent arg0) {
			if(purchase.getText().equals(Placeholder.PURCHASE.toString()))
			{
				purchase.setText("");
				purchase.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(purchase.getText().equals(""))
			{
				purchase.setText(Placeholder.PURCHASE.toString());
				purchase.setForeground(Color.GRAY);
			}
			
		}
		
	}
	
	class SellingListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent arg0) {
			if(selling.getText().equals(Placeholder.SELLING.toString()))
			{
				selling.setText("");
				selling.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(selling.getText().equals(""))
			{
				selling.setText(Placeholder.SELLING.toString());
				selling.setForeground(Color.GRAY);
			}
			
		}
		
	}
	
	class DiscountListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent arg0) {
			if(discount.getText().equals(Placeholder.DISCOUNT.toString()))
			{
				discount.setText("");
				discount.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(discount.getText().equals(""))
			{
				discount.setText(Placeholder.DISCOUNT.toString());
				discount.setForeground(Color.GRAY);
			}
			
		}
		
	}
	
	
}
