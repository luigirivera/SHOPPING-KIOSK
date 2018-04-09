package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import controller.CartController;
import controller.StocksController;
import model.Cart;
import model.Item;
import model.Person;
import model.ShopperStockTableModel;

public class ShopperStocksList extends StocksList {
	private static final long serialVersionUID = 1L;
	private ShopperStockTableModel stocksModel;
	private JMenuItem addToCart;
	private JPanel addToCartPanel;
	private JTextField value;
	private String valuePlaceholder = "Enter Quantity";
	private CartController cctrl;
	private Person account;
	private ShopperMenu view;
	
	public ShopperStocksList(StocksController sctrl, CartController cctrl, Person account, ShopperMenu view)
	{
		super("Pick 'N Save : Store Catalogue", sctrl);
		
		this.cctrl = cctrl;
		this.account = account;
		this.view = view;
		
		instantiate();
		commonInstantiate();
		
		commonInitialize();
		initialize();
		
		generateTable();
		addListeners();
		
		setResizable(false);
		setVisible(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void instantiate() {
		addToCart = new JMenuItem("Add To Cart");
		stocksModel = new ShopperStockTableModel(sctrl.getAllStocks(Placeholder.SHOPPER.toString()));
		value = new JTextField();
		addToCartPanel = new JPanel();
	}
	
	private void initialize()
	{
		addToCartPanel.setLayout(null);
		
		addToCartPanel.add(value);
		value.setText(valuePlaceholder);
		value.setForeground(Color.GRAY);
		
		rightClick.add(addToCart);
		listFilter.addItem(Filters.SALE.toString());
		
		value.setBounds(0, 0, 175, 30);
		
	}
	
	private void generateTable()
	{
		
		modelStocksTable.addColumn(StocksTableHeader.ID.toString());
		modelStocksTable.addColumn(StocksTableHeader.CATEGORY.toString());
		modelStocksTable.addColumn(StocksTableHeader.BRAND.toString());
		modelStocksTable.addColumn(StocksTableHeader.PRODUCTCODE.toString());
		modelStocksTable.addColumn(StocksTableHeader.PRODUCT.toString());
		modelStocksTable.addColumn(StocksTableHeader.AVAILABLE.toString());
		modelStocksTable.addColumn(StocksTableHeader.SOLD.toString());
		modelStocksTable.addColumn(StocksTableHeader.SELLING.toString());
		modelStocksTable.addColumn(StocksTableHeader.DISCOUNT.toString());
		
		stocksTable.getTableHeader().setResizingAllowed(false);
		stocksTable.getTableHeader().setReorderingAllowed(false);
		stocksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		publicUpdate();
	}
	
	private void refreshTable()
	{
		modelStocksTable.setRowCount(0);
		
		for(int i = 0; i < stocksModel.getRowCount(); i++)
		{
			Item p = stocksModel.getStockAt(i);
			Object[] row = new Object[] {p.getIdstocks(), p.getCategory(), p.getSupplier(), p.getProductcode(), p.getProduct(), 
										 p.getQuantityavailable(), p.getQuantitysold(), p.getUnitsellingprice(), p.getDiscountrate()};
			modelStocksTable.addRow(row);
		}
	}
	
	private void update()
	{
		publicUpdate();
		view.checkButtons();
	}
	
	public void publicUpdate()
	{
		String filter = (String)listFilter.getSelectedItem();
		
		if(filter.equals(Filters.CATEGORY.toString()))
			toggleCategoryField(true);
		
		else
		{
			toggleCategoryField(false);
			if(filter.equals(Filters.SALE.toString()))
				stocksModel.setStocks(sctrl.getAllSale());
			else 
				stocksModel.setStocks(sctrl.getAllStocks(Placeholder.SHOPPER.toString()));
		}
		
		if(account.getOutstandingBalance() >= account.getCreditLimit())
			addToCart.setEnabled(false);
		else
			addToCart.setEnabled(true);
		
		refreshTable();
	}
	
	private void addListeners() {
		listFilter.addActionListener(new FilterListener());
		searchCategory.addActionListener(new SubmitFilterListener());
		categoryField.addFocusListener(new categoryFieldListener());
		stocksTable.addMouseListener(new RightClickListener());
		addToCart.addActionListener(new AddToCart());
		value.addFocusListener(new ValueListener());
	}
	
	class AddToCart implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Item stock = stocksModel.getStockAt(stocksTable.getSelectedRow());
			
			int result = JOptionPane.showConfirmDialog(null, addToCartPanel, String.format("Add %s to Cart", stock.getProductcode()), JOptionPane.OK_CANCEL_OPTION);
			
			switch(result)
			{
			case JOptionPane.OK_OPTION : 
				
				if(valid(stock))
				{
					Cart cart = new Cart();
					
					cart.setIdaccountholder(account.getId());
					cart.setProductcode(stock.getProductcode());
					cart.setIdproduct(stock.getIdstocks());
					cart.setCheckedout(false);
					cart.setQuantity(Integer.parseInt(value.getText()));
					
					cctrl.addToCart(cart);
				}
				break;
			default: break;
			}
			
			update();
		}
		
		private boolean valid(Item stock)
		{
			boolean valid = true;
			if(value.getText().equals(valuePlaceholder))
			{
				JOptionPane.showMessageDialog(null, "Please enter a value", "Add To Cart Error", JOptionPane.ERROR_MESSAGE);
				valid = false;
			}
				
			else
				try {
					Integer.parseInt(value.getText());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Please enter an integer value", "Add To Cart Error", JOptionPane.ERROR_MESSAGE);
					valid = false;
				}
			
			if(Integer.parseInt(value.getText()) > stock.getQuantityavailable())
			{
				JOptionPane.showMessageDialog(null, "Please enter a value equal or lower than the available", "Add To Cart Error", JOptionPane.ERROR_MESSAGE);
				valid = false;
			}
			
			return valid;
		}
	}
	
	class ValueListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent arg0) {
			if(value.getText().equals(valuePlaceholder))
			{
				value.setText("");
				value.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(value.getText().equals(""))
			{
				value.setText(valuePlaceholder);
				value.setForeground(Color.GRAY);
			}
			
		}
		
	}
	
	class FilterListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			update();
			
		}
		
	}
	
	class RightClickListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(SwingUtilities.isRightMouseButton(e) && !stocksTable.getSelectionModel().isSelectionEmpty())
			{
				int x = e.getX();
				int y = e.getY();
			
				rightClick.show(stocksTable, x, y-20);
			}
			
		}
		
	}
	
	class SubmitFilterListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(categoryField.getText().equals(Placeholder.CATEGORY.toString()))
				stocksModel.setStocks(sctrl.getAllStocks(Placeholder.SHOPPER.toString()));
			else
				stocksModel.setStocks(sctrl.getStockInCategory(categoryField.getText(), Placeholder.SHOPPER.toString()));
			update();
			
		}
		
	}
}
