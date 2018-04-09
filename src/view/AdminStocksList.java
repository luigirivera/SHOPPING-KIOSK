package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import controller.StocksController;
import model.AdminStockTableModel;
import model.Item;

public class AdminStocksList extends StocksList {
	private static final long serialVersionUID = 1L;
	private JMenuItem modifyStock, restock, deleteStock;
	private JButton addStock;
	private AdminStockTableModel stocksModel;
	private AddStocks addStocks;
	private ModifyStocks modifyStocks;
	private JPanel restockPanel;
	private JTextField value;
	private String valuePlaceholder = "Enter Additional Stock Count";
	
	public AdminStocksList(StocksController sctrl) {
		super("Pick 'N Save : Stocks Inventory", sctrl);
		
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
		restockPanel = new JPanel();
		value = new JTextField();
		stocksModel = new AdminStockTableModel(sctrl.getAllStocks(Placeholder.ADMINISTRATOR.toString()));
		
		modifyStock = new JMenuItem("Modify Stock");
		restock = new JMenuItem("Restock");
		deleteStock = new JMenuItem("Delete");
		
		addStock = new JButton("Add Stock");
		addStocks = new AddStocks(sctrl, this);
		modifyStocks = new ModifyStocks(sctrl, this);
	}
	
	private void initialize()
	{
		stocksList.add(addStock);
		
		restockPanel.setLayout(null);
		restockPanel.add(value);
		
		value.setText(valuePlaceholder);
		value.setForeground(Color.GRAY);
		
		rightClick.add(modifyStock);
		rightClick.add(restock);
		rightClick.add(deleteStock);
		
		listFilter.addItem(Filters.RESTOCK.toString());
		
		int width = 100;
		addStock.setBounds(stocksList.getWidth() - width - 30, 10, width, 30);
		
		value.setBounds(0, 0, 175, 30);
		
	}
	
	private void generateTable()
	{
		
		modelStocksTable.addColumn(StocksTableHeader.ID.toString());
		modelStocksTable.addColumn(StocksTableHeader.CATEGORY.toString());
		modelStocksTable.addColumn(StocksTableHeader.SUPPLIER.toString());
		modelStocksTable.addColumn(StocksTableHeader.PRODUCTCODE.toString());
		modelStocksTable.addColumn(StocksTableHeader.PRODUCT.toString());
		modelStocksTable.addColumn(StocksTableHeader.AVAILABLE.toString());
		modelStocksTable.addColumn(StocksTableHeader.SOLD.toString());
		modelStocksTable.addColumn(StocksTableHeader.PURCHASE.toString());
		modelStocksTable.addColumn(StocksTableHeader.SELLING.toString());
		modelStocksTable.addColumn(StocksTableHeader.DISCOUNT.toString());
		
		stocksTable.getTableHeader().setResizingAllowed(false);
		stocksTable.getTableHeader().setReorderingAllowed(false);
		stocksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		update();
	}
	
	private void refreshTable()
	{
		modelStocksTable.setRowCount(0);
		
		for(int i = 0; i < stocksModel.getRowCount(); i++)
		{
			Item p = stocksModel.getStockAt(i);
			Object[] row = new Object[] {p.getIdstocks(), p.getCategory(), p.getSupplier(), p.getProductcode(), p.getProduct(), 
										 p.getQuantityavailable(), p.getQuantitysold(), p.getPurchaseprice(), p.getUnitsellingprice(), 
										 p.getDiscountrate()};
			modelStocksTable.addRow(row);
		}
	}
	
	public void update()
	{
		String filter = (String)listFilter.getSelectedItem();
		
		if(filter.equals(Filters.CATEGORY.toString()))
			toggleCategoryField(true);
		
		else
		{
			toggleCategoryField(false);
			if(filter.equals(Filters.RESTOCK.toString()))
				stocksModel.setStocks(sctrl.getAllRestocks());
			else 
				stocksModel.setStocks(sctrl.getAllStocks(Placeholder.ADMINISTRATOR.toString()));
		}
		refreshTable();
	}
	
	private void addListeners() {
		listFilter.addActionListener(new FilterListener());
		searchCategory.addActionListener(new SubmitFilterListener());
		categoryField.addFocusListener(new categoryFieldListener());
		addStock.addActionListener(new AddStocksListener());
		deleteStock.addActionListener(new DeleteStocksListener());
		restock.addActionListener(new RestockListener());
		stocksTable.addMouseListener(new RightClickListener());
		value.addFocusListener(new ValueListener());
		modifyStock.addActionListener(new ModifyStockListener());
	}
	
	class ModifyStockListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			modifyStocks.setStock(stocksModel.getStockAt(stocksTable.getSelectedRow()));
			modifyStocks.setVisible(true);
			
		}
		
	}
	
	class RestockListener implements ActionListener{

		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Item stock = stocksModel.getStockAt(stocksTable.getSelectedRow());
			
			int result = JOptionPane.showConfirmDialog(null, restockPanel, "Restock", JOptionPane.OK_CANCEL_OPTION);
			
			switch(result)
			{
			case JOptionPane.OK_OPTION : 
				
				if(valid())
				{
					stock.setQuantityavailable(stock.getQuantityavailable() + Integer.parseInt(value.getText()));
					sctrl.updateStock(stock);
				}
				break;
			default: break;
			}
			
			update();
			
		}
		
		private boolean valid()
		{
			boolean valid = true;
			if(value.getText().equals(valuePlaceholder))
			{
				JOptionPane.showMessageDialog(null, "Please enter a value", "Restock Error", JOptionPane.ERROR_MESSAGE);
				valid = false;
			}
				
			else
				try {
					Integer.parseInt(value.getText());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Please enter an integer value", "Restock Error", JOptionPane.ERROR_MESSAGE);
					valid = false;
				}
			
			return valid;
		}
	}
	
	class AddStocksListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			addStocks.setVisible(true);
			
		}
		
	}
	
	class DeleteStocksListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Item stock = stocksModel.getStockAt(stocksTable.getSelectedRow());
			sctrl.deleteStock(stock);
			update();
			
		}
		
	}
	
	class FilterListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			update();
			
		}
		
	}
	
	class SubmitFilterListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(categoryField.getText().equals(Placeholder.CATEGORY.toString()))
				stocksModel.setStocks(sctrl.getAllStocks(Placeholder.ADMINISTRATOR.toString()));
			else
				stocksModel.setStocks(sctrl.getStockInCategory(categoryField.getText(), Placeholder.ADMINISTRATOR.toString()));
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
}
