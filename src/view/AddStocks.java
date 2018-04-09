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

public class AddStocks extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField category, supplier, product, available, purchase, selling, discount;
	private JButton add, cancel;
	private JPanel addStockPanel;
	private StocksController sctrl;
	private AdminStocksList view;
	
	public AddStocks(StocksController sctrl, AdminStocksList view)
	{
		super("Add Stocks");
		
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
		addStockPanel = new JPanel();
		
		category = new JTextField();
		supplier = new JTextField();
		product = new JTextField();
		available = new JTextField();
		purchase = new JTextField();
		selling = new JTextField();
		discount = new JTextField();
		
		add = new JButton("Add");
		cancel = new JButton("Cancel");
	}
	
	private void initialize()
	{
		add(addStockPanel);
		
		addStockPanel.setLayout(null);
		
		addStockPanel.add(category);
		addStockPanel.add(supplier);
		addStockPanel.add(product);
		addStockPanel.add(available);
		addStockPanel.add(purchase);
		addStockPanel.add(selling);
		addStockPanel.add(discount);
		addStockPanel.add(add);
		addStockPanel.add(cancel);
		
		addStockPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
		category.setBounds(10, 20, 150, 30);
		supplier.setBounds(category.getX(), category.getY() + category.getHeight() + 20, category.getWidth(), category.getHeight());
		product.setBounds(supplier.getX(), supplier.getY() + supplier.getHeight() + 20, supplier.getWidth(), supplier.getHeight());
		available.setBounds(product.getX(), product.getY() + product.getHeight() + 40, product.getWidth(), product.getHeight());
		purchase.setBounds(available.getX(), available.getY() + available.getHeight() + 40, available.getWidth(), available.getHeight());
		selling.setBounds(purchase.getX(), purchase.getY() + purchase.getHeight() + 20, purchase.getWidth(), purchase.getHeight());
		discount.setBounds(selling.getX(), selling.getY() + selling.getHeight() + 20, selling.getWidth(), selling.getHeight());
		
		int height = 30;
		add.setBounds(category.getX(), this.getHeight() - height*2 - 30, 100, height);
		cancel.setBounds(add.getX() + add.getWidth() + 10, add.getY(), add.getWidth(), add.getHeight());
		
		resetAddStocks();
	}
	
	private void resetAddStocks()
	{
		category.setText(Placeholder.CATEGORY.toString());
		supplier.setText(Placeholder.SUPPLIER.toString());
		product.setText(Placeholder.PRODUCT.toString());
		available.setText(Placeholder.AVAILABLE.toString());
		purchase.setText(Placeholder.PURCHASE.toString());
		selling.setText(Placeholder.SELLING.toString());
		discount.setText(Placeholder.DISCOUNT.toString());
		
		category.setForeground(Color.GRAY);
		supplier.setForeground(Color.GRAY);
		product.setForeground(Color.GRAY);
		available.setForeground(Color.GRAY);
		purchase.setForeground(Color.GRAY);
		selling.setForeground(Color.GRAY);
		discount.setForeground(Color.GRAY);
	}
	
	private void addListeners() {
		add.addActionListener(new AddListener());
		cancel.addActionListener(new CancelListener());
		category.addFocusListener(new CategoryListener());
		supplier.addFocusListener(new SupplierListener());
		product.addFocusListener(new ProductListener());
		available.addFocusListener(new AvailableListener());
		purchase.addFocusListener(new PurchaseListener());
		selling.addFocusListener(new SellingListener());
		discount.addFocusListener(new DiscountListener());
	}
	
	class CancelListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			resetAddStocks();
			dispose();
			view.update();
		}
	}
	
	class AddListener implements ActionListener{
		String errors = "<html>Here are the following errors:<br>";
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(validInputs())
			{
				String catString = category.getText().substring(0, 1).toUpperCase() + category.getText().toLowerCase().substring(1);
				String supString = supplier.getText().substring(0, 1).toUpperCase() + supplier.getText().toLowerCase().substring(1);
				String prodString = product.getText().substring(0, 1).toUpperCase() + product.getText().toLowerCase().substring(1);
				String prodCode = catString.substring(0, 1) + supString.substring(0, 1) + prodString.substring(0, 1) + sctrl.getID();
				
				Item item = new Item();
				
				item.setCategory(catString);
				item.setSupplier(supString);
				item.setProduct(product.getText());
				item.setProductcode(prodCode);
				item.setQuantitysold(0);
				item.setQuantityavailable(Integer.parseInt(available.getText()));
				item.setPurchaseprice(Double.parseDouble(purchase.getText()));
				item.setUnitsellingprice(Double.parseDouble(selling.getText()));
				item.setDiscountrate(Double.parseDouble(discount.getText()));
				
				JOptionPane.showMessageDialog(null, "<html>The code of this product is:<br>" + prodCode + "</html>", "Add Stocks", JOptionPane.INFORMATION_MESSAGE);
				sctrl.addStock(item);
			}
			
			else
				JOptionPane.showMessageDialog(null, errors, "Add Stocks Error", JOptionPane.ERROR_MESSAGE);
			
			resetAddStocks();
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
			{
				errors += "<br>Product exists already";
				valid = false;
			}
			
			if(available.getText().equals(Placeholder.AVAILABLE.toString()) || available.getText().trim().isEmpty())
			{
				errors += "<br>Please enter a quantity available value";
				valid = false;
			}
			
			else
			{
				try {
					Integer.parseInt(available.getText());
				} catch (NumberFormatException e) {
					errors += "<br>Please enter an integer for the quantity available field";
					valid = false;
				}
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
					errors += "<br>Please enter a double value for the purchase price field";
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
					errors += "<br>Please enter a double value for the selling price field";
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
					errors += "<br>Please enter a double value for the discount rate field";
					valid = false;
				}
			}
			
			errors +="</html>";
			return valid;
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
	
	class AvailableListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent arg0) {
			if(available.getText().equals(Placeholder.AVAILABLE.toString()))
			{
				available.setText("");
				available.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(available.getText().equals(""))
			{
				available.setText(Placeholder.AVAILABLE.toString());
				available.setForeground(Color.GRAY);
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
