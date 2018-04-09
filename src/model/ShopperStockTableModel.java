package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class ShopperStockTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private List<Item> stocks;
	
	public ShopperStockTableModel(List<Item> stocks)
	{
		this.stocks = new ArrayList<Item>(stocks);
	}
	
	@Override
	public int getRowCount() {
		if(stocks != null)
			return stocks.size();
		else
			return 0;
	}
	
	@Override
	public int getColumnCount() {
		return 9;
	}
	
	@Override
	public Object getValueAt(int row, int col)
	{
		Item stock = stocks.get(row);
		
		switch(row)
		{
		case 0: return stock.getIdstocks();
		case 1: return stock.getCategory();
		case 2: return stock.getSupplier();
		case 3: return stock.getProductcode();
		case 4: return stock.getProduct();
		case 5: return stock.getQuantityavailable();
		case 6: return stock.getQuantitysold();
		case 7: return stock.getPurchaseprice();
		case 8: return stock.getUnitsellingprice();
		}
		
		return "??";
	}
	
	
	public Item getStockAt(int row)
	{
		return stocks.get(row);
	}
	
	public List<Item> getStocks() {
		return stocks;
	}

	public void setStocks(List<Item> stocks) {
		this.stocks = stocks;
	}
}
