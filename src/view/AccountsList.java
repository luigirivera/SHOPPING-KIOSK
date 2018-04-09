package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import controller.AccountsController;
import model.AccountTableModel;
import model.Person;

public class AccountsList extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel accountsList;
	private JTable accountsTable;
	private DefaultTableModel modelAccountsTable;
	private JScrollPane scrollAccountsTable;
	private JComboBox <String> listFilter;
	private AccountsController ctrl;
	private JPopupMenu rightClick;
	private JMenuItem deleteAccount, unlockAccount;
	private AccountTableModel accountsModel;
	private JButton unlockAll;
	private Person account;
	
	public AccountsList(AccountsController ctrl, Person account)
	{
		super("Pick 'N Save : Registered Accounts");
		
		this.ctrl = ctrl;
		this.account = account;
		
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
		accountsList = new JPanel();
		accountsModel = new AccountTableModel(ctrl.getAllAccounts());
		
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
		
		unlockAll = new JButton("Unlock All Accounts");
	}
	
	private void initialize()
	{
		add(accountsList);
		accountsList.setLayout(null);
		
		accountsList.add(listFilter);
		accountsList.add(unlockAll);
		accountsList.add(scrollAccountsTable);
		
		listFilter.addItem(Filters.ALL.toString());
		listFilter.addItem(Filters.LOCKED.toString());
		listFilter.addItem(Filters.OUTSTANDING.toString());
		
		rightClick.add(unlockAccount);
		rightClick.add(deleteAccount);
		
		accountsList.setBounds(0,0, this.getWidth(),this.getHeight());
		listFilter.setBounds(10, 10, 250, 30);
		unlockAll.setBounds(listFilter.getX() + listFilter.getWidth() + 20, 10, 150, 30);
		scrollAccountsTable.setBounds(0, listFilter.getHeight()+30, this.getWidth()-15, this.getHeight()-98);
	}
	
	private void generateTable()
	{
		
		modelAccountsTable.addColumn(AccountsTableHeader.ID.toString());
		modelAccountsTable.addColumn(AccountsTableHeader.USERNAME.toString());
		modelAccountsTable.addColumn(AccountsTableHeader.PASSWORD.toString());
		modelAccountsTable.addColumn(AccountsTableHeader.FIRSTNAME.toString());
		modelAccountsTable.addColumn(AccountsTableHeader.LASTNAME.toString());
		modelAccountsTable.addColumn(AccountsTableHeader.ADDRESS.toString());
		modelAccountsTable.addColumn(AccountsTableHeader.ACCOUNTTYPE.toString());
		modelAccountsTable.addColumn(AccountsTableHeader.LOCKED.toString());
		modelAccountsTable.addColumn(AccountsTableHeader.OUTSTANDING.toString());
		modelAccountsTable.addColumn(AccountsTableHeader.CREDIT.toString());
		
		accountsTable.getTableHeader().setResizingAllowed(false);
		accountsTable.getTableHeader().setReorderingAllowed(false);
		accountsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		update();
	}
	
	private void refreshTable()
	{
		modelAccountsTable.setRowCount(0);
		
		for(int i = 0; i < accountsModel.getRowCount(); i++)
		{
			Person p = accountsModel.getAccountAt(i);
			Object[] row = new Object[] {p.getId(), p.getUsername(), p.getPassword(), p.getFirstName(), p.getLastName(), p.getAddress(),
										 p.getAccountType(), p.isLocked(), p.getOutstandingBalance(), p.getCreditLimit()};
			modelAccountsTable.addRow(row);
		}
			
	}
	
	public void update()
	{
		String filter = (String)listFilter.getSelectedItem();
		
		if(filter.equals(Filters.LOCKED.toString()))
			accountsModel.setAccounts(ctrl.getLockedAccounts());
		
		else if(filter.equals(Filters.OUTSTANDING.toString()))
			accountsModel.setAccounts(ctrl.getOutstandingAccounts());
		
		else 
			accountsModel.setAccounts(ctrl.getAllAccounts());
		
		refreshTable();
	}
	
	private void addListeners()
	{
		accountsTable.addMouseListener(new RightClickListener());
		listFilter.addActionListener(new FilterListener());
		unlockAccount.addActionListener(new UnlockListener());
		deleteAccount.addActionListener(new DeleteListener());
		unlockAll.addActionListener(new UnlockAllListener());
	}
	
	class UnlockAllListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			List<Person> lockedAccounts = ctrl.getLockedAccounts();
			
			for(Person p : lockedAccounts)
			{
				p.setLocked(false);
				p.setTries(0);
				ctrl.updateAccount(p);
			}
			
			update();
			
			
			
		}
		
	}
	
	class UnlockListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Person account = accountsModel.getAccountAt(accountsTable.getSelectedRow());
			account.setLocked(false);
			account.setTries(0);
			ctrl.updateAccount(account);
			
			update();
		}
		
	}
	
	class DeleteListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Person account = accountsModel.getAccountAt(accountsTable.getSelectedRow());
			ctrl.deletePerson(account);
			update();
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
			if(SwingUtilities.isRightMouseButton(e) && !accountsTable.getSelectionModel().isSelectionEmpty())
			{
						
				if((boolean)accountsTable.getValueAt(accountsTable.getSelectedRow(), accountsTable.getColumnModel().getColumnIndex(AccountsTableHeader.LOCKED.toString())))
					unlockAccount.setEnabled(true);
				else
					unlockAccount.setEnabled(false);
				
				if((int)accountsTable.getValueAt(accountsTable.getSelectedRow(), accountsTable.getColumnModel().getColumnIndex(AccountsTableHeader.ID.toString())) == account.getId())
					deleteAccount.setEnabled(false);
				else
					deleteAccount.setEnabled(true);
				
				int x = e.getX();
				int y = e.getY();
				
				rightClick.show(accountsTable, x, y-20);
			}
		}
		
	}
}
