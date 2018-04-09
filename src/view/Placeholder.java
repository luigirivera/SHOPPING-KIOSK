package view;

public enum Placeholder {
	USERNAME, PASSWORD, OLDPASSWORD, NEWPASSWORD, CONFIRMPASSWORD, FIRSTNAME, LASTNAME, ADDRESS, ADMINCODE, VERIFYADMINCODE, ADMINISTRATOR,
	SHOPPER,
	
	CATEGORY, SUPPLIER, PRODUCT, AVAILABLE, PURCHASE, SELLING, DISCOUNT,
	
	CARDNUMBER, CVC, MONTH, YEAR, AMOUNT;
	
	public String toString()
	{
		switch(this)
		{
		case USERNAME : return "Username";
		case PASSWORD : return "Password";
		case OLDPASSWORD : return "Current Password";
		case NEWPASSWORD : return "New Password";
		case CONFIRMPASSWORD : return "Confirm Password";
		case FIRSTNAME : return "First Name";
		case LASTNAME : return "Last Name";
		case ADDRESS : return "Address";
		case ADMINCODE : return "Admin Code";
		case VERIFYADMINCODE : return "DLSU2017";
		case ADMINISTRATOR : return "Administrator";
		case SHOPPER : return "Shopper";
		
		case CATEGORY : return "Category";
		case SUPPLIER : return "Supplier";
		case PRODUCT : return "Product";
		case AVAILABLE : return "Quantity Available";
		case PURCHASE : return "Purchase Price";
		case SELLING : return "Unit Selling Price";
		case DISCOUNT : return "Discount Rate";
		
		case CARDNUMBER : return "Card Number";
		case CVC : return "CVC";
		case MONTH : return "MM";
		case YEAR : return "YY";
		case AMOUNT : return "Amount";
		default : return "Invalid";
		}
	}
}
