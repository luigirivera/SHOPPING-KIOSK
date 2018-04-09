package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import controller.AccountsController;
import controller.CartController;
import controller.StocksController;
import model.Cart;
import model.CartTableModel;
import model.Item;
import model.Person;

public class ShopperCart extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel cartList;
	private JTable cartTable;
	private DefaultTableModel modelCartTable;
	private JScrollPane scrollCartTable;
	private StocksController sctrl;
	private CartController cctrl;
	private JPopupMenu rightClick;
	private JMenuItem delete, quantity;
	private CartTableModel cartModel;
	private JButton checkOut;
	private Person account;
	private JPanel changeQuantityPanel;
	private JTextField value;
	private String valuePlaceholder = "Enter New Quantity";
	private JLabel information;
	private String infoLabel;
	private ShopperMenu view;
	private AccountsController actrl;
	
	public ShopperCart(AccountsController actrl, StocksController sctrl, CartController cctrl, Person account, ShopperMenu view) {
		
		super("Pick 'N Save : Shopping Cart");
		
		this.sctrl = sctrl;
		this.cctrl = cctrl;
		this.account = account;
		this.view = view;
		this.actrl = actrl;
		
		URL iconURL = getClass().getResource("/res/favicon.png");
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());
		
		setSize(1200, 600);
		
		instantiate();
		setLayout(null);
		initialize();
		addListeners();
		generateTable();
		
		setResizable(false);
		setVisible(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void instantiate()
	{
		List<Cart> cart = cctrl.grabCart(account);
		List<Item> products = new ArrayList<Item>();
		
		cartList = new JPanel();
		
	
		for(Cart c : cart)
			products.add(sctrl.getStock(c.getProductcode()));
		
		cartModel = new CartTableModel(cart, products);
		
		
		modelCartTable = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		cartTable = new JTable(modelCartTable);
		scrollCartTable = new JScrollPane(cartTable);
		
		rightClick = new JPopupMenu();
		delete = new JMenuItem("Delete");
		quantity = new JMenuItem("Update Quantity");
		
		checkOut = new JButton("Check Out");
		
		value = new JTextField();
		changeQuantityPanel = new JPanel();
		
		information = new JLabel(infoLabel, SwingConstants.CENTER);
	}

	private void initialize()
	{
		add(cartList);
		cartList.setLayout(null);
		
		cartList.add(checkOut);
		cartList.add(scrollCartTable);
		cartList.add(information);
		
		rightClick.add(quantity);
		rightClick.add(delete);
		
		cartList.setBounds(0,0, this.getWidth(),this.getHeight());
		checkOut.setBounds(10, 10, 150, 30);
		information.setBounds(checkOut.getX() + 400, 5, checkOut.getWidth(), 50);
		scrollCartTable.setBounds(0, checkOut.getHeight()+30, this.getWidth()-15, this.getHeight()-98);
		
		changeQuantityPanel.setLayout(null);
		
		changeQuantityPanel.add(value);
		value.setText(valuePlaceholder);
		value.setForeground(Color.GRAY);
		
		value.setBounds(0, 0, 175, 30);
	}

	private void generateTable()
	{
		
		modelCartTable.addColumn(CartTableHeader.CODE.toString());
		modelCartTable.addColumn(CartTableHeader.PRODUCT.toString());
		modelCartTable.addColumn(CartTableHeader.QUANTITY.toString());
		modelCartTable.addColumn(CartTableHeader.SOLOPRICE.toString());
		modelCartTable.addColumn(CartTableHeader.TOTALPRICE.toString());
		modelCartTable.addColumn(CartTableHeader.DISCOUNT.toString());
		modelCartTable.addColumn(CartTableHeader.SUBTOTAL.toString());
		
		cartTable.getTableHeader().setResizingAllowed(false);
		cartTable.getTableHeader().setReorderingAllowed(false);
		cartTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		publicUpdate();
	}
	
	private void refreshTable()
	{
		modelCartTable.setRowCount(0);
		
		for(int i = 0; i < cartModel.getRowCount(); i++)
		{
			Cart c = cartModel.getCartAt(i);
			Item item = cartModel.getStockAt(i);
			Object[] row = new Object[] {item.getProductcode(), item.getProduct(), c.getQuantity(),
										 item.getUnitsellingprice(), item.getUnitsellingprice() * c.getQuantity(), item.getDiscountrate(),
										 (item.getUnitsellingprice() - (item.getUnitsellingprice() * (item.getDiscountrate()/100))) * c.getQuantity()};
			modelCartTable.addRow(row);
		}
			
	}
	
	public void labelUpdate()
	{
		List<Cart> cart = cctrl.grabCart(account);
		List<Item> products = new ArrayList<Item>();
		
		for(Cart c : cart)
			products.add(sctrl.getStock(c.getProductcode()));
		
		int num = 0;
		double total = 0;
		double carttotal = 0;
		
		for(int i = 0; i < cart.size(); i++)
		{
			num += cart.get(i).getQuantity();
			total += (products.get(i).getUnitsellingprice() * (products.get(i).getDiscountrate()/100));
			carttotal += (products.get(i).getUnitsellingprice() - (products.get(i).getUnitsellingprice() * (products.get(i).getDiscountrate()/100))) * cart.get(i).getQuantity();
		}
			
			
		infoLabel = String.format("<html><div style='text-align: center;'>Number of Items: %d<br>"
																		+ "Total Discount: $%.2f<br>"
																		+ "Cart Amount: $%.2f", num, total, carttotal);
		
		information.setText(infoLabel);
	}
	
	private void update()
	{
		publicUpdate();
		view.checkButtons();
		view.update();
	}
	
	public void publicUpdate()
	{
		List<Cart> cart = cctrl.grabCart(account);
		List<Item> products = new ArrayList<Item>();
		
		for(Cart c : cart)
			products.add(sctrl.getStock(c.getProductcode()));
		
		cartModel.setCart(cart);
		cartModel.setStocks(products);
		
		if(cart.size()==0)
			checkOut.setEnabled(false);
		else
			checkOut.setEnabled(true);
		
		labelUpdate();
		refreshTable();
	}
	
	private void addListeners()
	{
		cartTable.addMouseListener(new RightClickListener());
		delete.addActionListener(new DeleteListener());
		quantity.addActionListener(new QuantityListener());
		checkOut.addActionListener(new CheckOutListener());
		value.addFocusListener(new ValueListener());
	}
	
	class CheckOutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			List<Cart> cart = cctrl.grabCart(account);
			List<Item> products = new ArrayList<Item>();
			
			for(Cart c : cart)
				products.add(sctrl.getStock(c.getProductcode()));
			
			double balance = 0;
			
			for(int i = 0; i < cart.size(); i++)
			{
				balance += (products.get(i).getUnitsellingprice() - (products.get(i).getUnitsellingprice() * (products.get(i).getDiscountrate()/100))) * cart.get(i).getQuantity();
				products.get(i).setQuantityavailable(products.get(i).getQuantityavailable() - cart.get(i).getQuantity());
				products.get(i).setQuantitysold(products.get(i).getQuantitysold() + cart.get(i).getQuantity());
				cart.get(i).setCheckedout(true);
				
				sctrl.updateStock(products.get(i));
				cctrl.updateCart(cart.get(i));
			}
			
			account.setOutstandingBalance(account.getOutstandingBalance() + balance);
			actrl.updateAccount(account);
			update();
			
		}
		
	}
	
	class QuantityListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Item stock = cartModel.getStockAt(cartTable.getSelectedRow());
			Cart cartItem = cartModel.getCartAt(cartTable.getSelectedRow());
			
			int result = JOptionPane.showConfirmDialog(null, changeQuantityPanel, String.format("Update %s in Cart", stock.getProductcode()), JOptionPane.OK_CANCEL_OPTION);
			
			switch(result)
			{
			case JOptionPane.OK_OPTION : 
				
				if(valid(stock))
				{
					Cart cart = new Cart();
					
					cart.setIdcart(cartItem.getIdcart());
					cart.setIdaccountholder(account.getId());
					cart.setProductcode(stock.getProductcode());
					cart.setIdproduct(stock.getIdstocks());
					cart.setQuantity(Integer.parseInt(value.getText()));
					cart.setCheckedout(cartItem.isCheckedout());
					
					if(Integer.parseInt(value.getText()) == 0)
						cctrl.deleteFromCart(cart);

					else
						cctrl.updateCart(cart);
				}
				break;
			default: break;
			}
			
			value.setText(valuePlaceholder);
			value.setForeground(Color.GRAY);
			update();
		}
		
		private boolean valid(Item stock)
		{
			boolean valid = true;
			if(value.getText().equals(valuePlaceholder))
			{
				JOptionPane.showMessageDialog(null, "Please enter a value", "Edit Quantity Error", JOptionPane.ERROR_MESSAGE);
				valid = false;
			}
				
			else
				try {
					Integer.parseInt(value.getText());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Please enter an integer value", "Edit Quantity Error", JOptionPane.ERROR_MESSAGE);
					valid = false;
				}
			
			if(Integer.parseInt(value.getText()) > stock.getQuantityavailable())
			{
				JOptionPane.showMessageDialog(null, "Please enter a value equal or lower than the available", "Edit Quantity Error", JOptionPane.ERROR_MESSAGE);
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
	
	class DeleteListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Cart item = cartModel.getCartAt(cartTable.getSelectedRow());
			cctrl.deleteFromCart(item);
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
			if(SwingUtilities.isRightMouseButton(e) && !cartTable.getSelectionModel().isSelectionEmpty())
			{
				int x = e.getX();
				int y = e.getY();
				
				rightClick.show(cartTable, x, y-20);
			}
		}
		
	}



}
