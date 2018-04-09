package view;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.StocksController;

public abstract class StocksList extends JFrame {
	private static final long serialVersionUID = 1L;
	protected JPanel stocksList;
	protected JTable stocksTable;
	protected DefaultTableModel modelStocksTable;
	protected JScrollPane scrollStocksTable;
	
	protected JComboBox <String> listFilter;
	protected JPopupMenu rightClick;
	protected JTextField categoryField;
	protected JButton searchCategory;
	protected StocksController sctrl;
	
	public StocksList(String s, StocksController sctrl)
	{
		super(s);
		this.sctrl = sctrl;
		
		URL iconURL = getClass().getResource("/res/favicon.png");
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());
		
		setSize(1200, 600);
		setLayout(null);	
	}
	
	protected void commonInstantiate()
	{
		stocksList = new JPanel();
		
		
		modelStocksTable = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		stocksTable = new JTable(modelStocksTable);
		scrollStocksTable = new JScrollPane(stocksTable);
		
		listFilter = new JComboBox<String>();
		
		rightClick = new JPopupMenu();
		
		categoryField = new JTextField();
		searchCategory = new JButton("Search");
	}
	
	protected void commonInitialize()
	{
		add(stocksList);
		stocksList.setLayout(null);
		
		stocksList.add(listFilter);
		stocksList.add(categoryField);
		stocksList.add(searchCategory);
		stocksList.add(scrollStocksTable);
		
		listFilter.addItem(Filters.ALL.toString());
		listFilter.addItem(Filters.CATEGORY.toString());
		
		stocksList.setBounds(0,0, this.getWidth(),this.getHeight());
		listFilter.setBounds(10, 10, 150, 30);
		categoryField.setBounds(listFilter.getX() + listFilter.getWidth() + 20, 10, 200, 30);
		searchCategory.setBounds(categoryField.getX() + categoryField.getWidth() + 20, 10, 100, 30);
		scrollStocksTable.setBounds(0, listFilter.getHeight()+30, this.getWidth()-15, this.getHeight()-98);
		
		toggleCategoryField(false);
	}
	
	protected void toggleCategoryField(boolean toggle)
	{
		categoryField.setVisible(toggle);
		searchCategory.setVisible(toggle);
		
		categoryField.setEnabled(toggle);
		categoryField.setEnabled(toggle);
		
		categoryField.setText(Placeholder.CATEGORY.toString());
		categoryField.setForeground(Color.GRAY);
	}
	
	class categoryFieldListener implements FocusListener
	{
		@Override
		public void focusGained(FocusEvent arg0) {
			if(categoryField.getText().equals(Placeholder.CATEGORY.toString()))
			{
				categoryField.setText("");
				categoryField.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(categoryField.getText().equals(""))
			{
				categoryField.setText(Placeholder.CATEGORY.toString());
				categoryField.setForeground(Color.GRAY);
			}
			
		}
	}
}
