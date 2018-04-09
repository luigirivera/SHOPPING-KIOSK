package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class AccountTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private List<Person> accounts;
	
	public AccountTableModel(List<Person> accounts)
	{
		this.accounts = new ArrayList<Person>(accounts);
	}

	@Override
	public int getRowCount() {
		if(accounts != null)
			return accounts.size();
		else
			return 0;
	}
	
	@Override
	public int getColumnCount() {
		return 10;
	}
	
	public Person getAccountAt(int row)
	{
		return accounts.get(row);
	}
	
	@Override
	public Object getValueAt(int row, int col)
	{
		Person account = accounts.get(row);
		
		switch(row)
		{
		case 0: return account.getId();
		case 1: return account.getUsername();
		case 2: return account.getPassword();
		case 3: return account.getFirstName();
		case 4: return account.getLastName();
		case 5: return account.getAddress();
		case 6: return account.getAccountType();
		case 7: return account.isLocked();
		case 8: return account.getOutstandingBalance();
		case 9: return account.getCreditLimit();
		}
		
		return "??";
	}
	
	public List<Person> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Person> accounts) {
		this.accounts = accounts;
	}

}
