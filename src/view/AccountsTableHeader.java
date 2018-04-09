package view;

public enum AccountsTableHeader {
	ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, ADDRESS, ACCOUNTTYPE, LOCKED, OUTSTANDING, CREDIT;
	
	public String toString()
	{
		switch(this)
		{
		case ID: return "ID";
		case USERNAME: return "Username";
		case PASSWORD: return "Password";
		case FIRSTNAME: return "First Name";
		case LASTNAME: return "Last Name";
		case ADDRESS: return "Address";
		case ACCOUNTTYPE: return "Account Type";
		case LOCKED: return "Is Locked?";
		case OUTSTANDING: return "Outstanding Balance";
		case CREDIT: return "Credit Limit";
		default: return "Invalid";
		}
	}
}
