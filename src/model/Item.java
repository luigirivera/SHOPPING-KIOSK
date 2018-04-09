package model;

public class Item {
	private int idstocks;
	private String category;
	private String supplier;
	private String product;
	private int quantityavailable;
	private double purchaseprice;
	private double unitsellingprice;
	private double discountrate;
	private int quantitysold;
	private String productcode;
	
	public static final String TABLE = "stocks";
	public static final String COL_ID = "idstocks";
	public static final String COL_CATEGORY = "category";
	public static final String COL_SUPPLIER = "supplier";
	public static final String COL_PRODUCT = "product";
	public static final String COL_QUANTITYAVAILABLE = "quantityavailable";
	public static final String COL_PURCHASEPRICE = "purchaseprice";
	public static final String COL_UNITSELLINGPRICE = "unitsellingprice";
	public static final String COL_DISCOUNTRATE = "discountrate";
	public static final String COL_QUANTITYSOLD = "quantitysold";
	public static final String COL_PRODUCTCODE = "productcode";
	
	public int getIdstocks() {
		return idstocks;
	}
	public void setIdstocks(int idstocks) {
		this.idstocks = idstocks;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public int getQuantityavailable() {
		return quantityavailable;
	}
	public void setQuantityavailable(int quantityavailable) {
		this.quantityavailable = quantityavailable;
	}
	public double getPurchaseprice() {
		return purchaseprice;
	}
	public void setPurchaseprice(double purchaseprice) {
		this.purchaseprice = purchaseprice;
	}
	public double getUnitsellingprice() {
		return unitsellingprice;
	}
	public void setUnitsellingprice(double unitsellingprice) {
		this.unitsellingprice = unitsellingprice;
	}
	public double getDiscountrate() {
		return discountrate;
	}
	public void setDiscountrate(double discountrate) {
		this.discountrate = discountrate;
	}
	public int getQuantitysold() {
		return quantitysold;
	}
	public void setQuantitysold(int quantitysold) {
		this.quantitysold = quantitysold;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	
	
}
