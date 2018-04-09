package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class CartTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private List<Cart> cartItems;
	private List<Item> items;
	
	public CartTableModel(List<Cart> cartItems, List<Item> products)
	{
		this.cartItems = new ArrayList<Cart>(cartItems);
		this.items = new ArrayList<Item>(products);
	}
	
	@Override
	public int getRowCount() {
		if(cartItems != null)
			return cartItems.size();
		else
			return 0;
	}
	
	@Override
	public int getColumnCount() {
		return 7;
	}
	
	@Override
	public Object getValueAt(int row, int col)
	{
		Cart item = cartItems.get(row);
		Item stock = items.get(row);
		
		switch(row)
		{
		case 0: return stock.getProductcode();
		case 1: return stock.getProduct();
		case 2: return item.getQuantity();
		case 3: return stock.getUnitsellingprice();
		case 4: return item.getQuantity() * stock.getUnitsellingprice();
		case 5: return stock.getDiscountrate();
		case 6: return (stock.getUnitsellingprice() - (stock.getUnitsellingprice() * (stock.getDiscountrate()/100))) * item.getQuantity();
		}
		
		return "??";
	}
	
	
	public Cart getCartAt(int row)
	{
		return cartItems.get(row);
	}
	
	public List<Cart> getCart() {
		return cartItems;
	}

	public void setCart(List<Cart> cartItems) {
		this.cartItems = cartItems;
	}
	
	public Item getStockAt(int row)
	{
		return items.get(row);
	}
	
	public List<Item> getStocks() {
		return items;
	}

	public void setStocks(List<Item> items) {
		this.items = items;
	}
}
