package model;

public class Cart {
	private int idcart;
	private int idaccountholder;
	private String productcode;
	private int idproduct;
	private int quantity;
	private boolean checkedout;
	
	public final static String TABLE = "cart";
	public final static String COL_IDCART = "idcart";
	public final static String COL_IDACCOUNTHOLDER = "idaccountholder";
	public final static String COL_PRODUCTCODE = "productcode";
	public final static String COL_IDPRODUCT = "idproduct";
	public final static String COL_QUANTITY = "quantity";
	public final static String COL_CHECKEDOUT = "checkedout";
	
	public int getIdcart() {
		return idcart;
	}
	
	
	public boolean isCheckedout() {
		return checkedout;
	}


	public void setCheckedout(boolean checkedout) {
		this.checkedout = checkedout;
	}


	public void setIdcart(int idcart) {
		this.idcart = idcart;
	}
	public int getIdaccountholder() {
		return idaccountholder;
	}
	public void setIdaccountholder(int idaccountholder) {
		this.idaccountholder = idaccountholder;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public int getIdproduct() {
		return idproduct;
	}
	public void setIdproduct(int idproduct) {
		this.idproduct = idproduct;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
