import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class AccountsList extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel accountsList;
	private JTable accountsTable;
	private DefaultTableModel modelAccountsTable;
	private JScrollPane scrollAccountsTable;
	private JComboBox <String> listFilter;
	private JPopupMenu rightClick;
	private JMenuItem deleteAccount, unlockAccount;
	
	public AccountsList()
	{
		super("Pick 'N Save : Registered Accounts");
		
		URL iconURL = getClass().getResource("/res/favicon.png");
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());
		setSize(800, 600);
		
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
		accountsList = new JPanel();
		
		modelAccountsTable = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		accountsTable = new JTable(modelAccountsTable);
		scrollAccountsTable = new JScrollPane(accountsTable);
		
		listFilter = new JComboBox<String>();
		
		rightClick = new JPopupMenu();
		deleteAccount = new JMenuItem("Delete");
		unlockAccount = new JMenuItem("Unlock");
	}
	
	private void initialize()
	{
		add(accountsList);
		accountsList.setLayout(null);
		
		accountsList.add(listFilter);
		accountsList.add(scrollAccountsTable);
		
		listFilter.addItem("View All");
		listFilter.addItem("Locked Accounts");
		listFilter.addItem("Outstanding Balance");
		
		rightClick.add(unlockAccount);
		rightClick.add(deleteAccount);
		
		accountsList.setBounds(0,0, this.getWidth(),this.getHeight());
		listFilter.setBounds(10, 10, 150, 30);
		scrollAccountsTable.setBounds(0, listFilter.getHeight()+30, this.getWidth()-15, this.getHeight()-98);
	}
	
	private void generateTable()
	{
		modelAccountsTable.addColumn("ID");
		modelAccountsTable.addColumn("Username");
		modelAccountsTable.addColumn("Password");
		modelAccountsTable.addColumn("First Name");
		modelAccountsTable.addColumn("Last Name");
		modelAccountsTable.addColumn("Address");
		modelAccountsTable.addColumn("Account Type");
		modelAccountsTable.addColumn("Is Locked?");
	
		modelAccountsTable.setColumnCount(8);
	}
	
	private void addListeners()
	{
		scrollAccountsTable.addMouseListener(new RightClickListener());
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
			if(SwingUtilities.isRightMouseButton(e))
			{
				int x = e.getX();
				int y = e.getY();
				
				rightClick.show(accountsTable, x, y-20);
			}
		}
		
	}
}
