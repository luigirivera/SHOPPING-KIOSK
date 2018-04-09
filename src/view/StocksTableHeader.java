package view;

public enum StocksTableHeader {
	ID, CATEGORY, SUPPLIER, BRAND, PRODUCTCODE, PRODUCT, AVAILABLE, SOLD, PURCHASE, SELLING, PRICE, DISCOUNT;
	
	public String toString()
	{
		switch(this)
		{
		case ID: return "ID";
		case CATEGORY: return "Category";
		case SUPPLIER: return "Supplier";
		case BRAND: return "Brand";
		case PRODUCTCODE: return "Product Code";
		case PRODUCT: return "Product";
		case AVAILABLE: return "Quantity Available";
		case SOLD: return "Quantity Sold";
		case PURCHASE: return "Purchase Price";
		case SELLING: return "Unit Selling Price";
		case PRICE: return "Unit Price";
		case DISCOUNT: return "Discount Rate";
		default: return "Invalid";
		}
	}
}
