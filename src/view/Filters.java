package view;

public enum Filters {
	LOCKED, OUTSTANDING, ALL, ADMIN, SHOPPER, CATEGORY, RESTOCK, SALE;
	
	public String toString()
	{
		switch(this)
		{
		case LOCKED: return "View Locked Accounts";
		case OUTSTANDING: return "View Accounts with Outstanding Balance ";
		case ALL: return "View All";
		case ADMIN: return "View Administrators";
		case SHOPPER: return "View Shoppers";
		case CATEGORY: return "View By Category";
		case RESTOCK: return "View Stocks To Restock";
		case SALE: return "View Products On Sale";
		default: return "Invalid";
		}
	}
}
