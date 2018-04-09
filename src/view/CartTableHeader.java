package view;

public enum CartTableHeader {
	CODE, PRODUCT, QUANTITY, SOLOPRICE, TOTALPRICE, DISCOUNT, SUBTOTAL;
	
	public String toString()
	{
		switch(this)
		{
		case CODE: return "Product Code";
		case PRODUCT: return "Product";
		case QUANTITY: return "Quantity";
		case SOLOPRICE: return "Unit Price";
		case TOTALPRICE: return "Total Price";
		case DISCOUNT: return "Discount Rate";
		case SUBTOTAL: return "Item Subtotal";
		default: return "Invalid";
		}
	}
}
