package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import view.Placeholder;

public class StocksService {
	private StoreDB database;

	public StocksService(StoreDB database) {
		this.database = database;
	}
	
	public int getID()
	{
		Connection cnt = database.getConnection();
		int ID = 0;
		
		String dbString = String.format("\"%s\"", StoreDB.DATABASE);
		String table = String.format("\"%s\"", Item.TABLE);
		String query = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = " + dbString + 
																			 " AND TABLE_NAME = " + table;
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				ID = rs.getInt("AUTO_INCREMENT");
			
			System.out.println("[STOCKS] GRAB ID SUCCESS");
		} catch (SQLException e) {
			System.out.println("[STOCKS] GRAB ID FAILED");
			e.printStackTrace();
		}
		System.out.println(ID);
		return ID;
 	}
	
	public List<Item> getAll(String userType){
		List<Item> stocks = new ArrayList<Item>();
		
		Connection cnt = database.getConnection();
		String query;
		
		if(userType.equals(Placeholder.ADMINISTRATOR.toString()))
			query = "SELECT * FROM " + Item.TABLE;
		else
			query = "SELECT * FROM " + Item.TABLE + " WHERE " + Item.COL_QUANTITYAVAILABLE + " > 0";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				stocks.add(toItem(rs));
			
			ps.close();
			rs.close();
			cnt.close();
			
			System.out.println("[STOCKS] SELECT SUCCESS");
		} catch (SQLException e) {
			System.out.println("[STOCKS] SELECT FAILED");
			e.printStackTrace();
		}
		
		return stocks;
	}
	
	private Item toItem(ResultSet rs) throws SQLException{
		Item item = new Item();
		
		item.setIdstocks(rs.getInt(Item.COL_ID));
		item.setCategory(rs.getString(Item.COL_CATEGORY));
		item.setSupplier(rs.getString(Item.COL_SUPPLIER));
		item.setProduct(rs.getString(Item.COL_PRODUCT));
		item.setQuantityavailable(rs.getInt(Item.COL_QUANTITYAVAILABLE));
		item.setPurchaseprice(rs.getDouble(Item.COL_PURCHASEPRICE));
		item.setUnitsellingprice(rs.getDouble(Item.COL_UNITSELLINGPRICE));
		item.setDiscountrate(rs.getDouble(Item.COL_DISCOUNTRATE));
		item.setQuantitysold(rs.getInt(Item.COL_QUANTITYSOLD));
		item.setProductcode(rs.getString(Item.COL_PRODUCTCODE));
		
		
		return item;
		
	}
	
	public void addStock(Item item) {
		Connection cnt = database.getConnection();
		
		String query = "INSERT INTO " + Item.TABLE + " VALUES(?,?,?,?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setInt(1, Types.NULL);
			ps.setString(2, item.getCategory());
			ps.setString(3, item.getSupplier());
			ps.setString(4, item.getProduct());
			ps.setInt(5, item.getQuantityavailable());
			ps.setDouble(6, item.getPurchaseprice());
			ps.setDouble(7, item.getUnitsellingprice());
			ps.setDouble(8, item.getDiscountrate());
			ps.setInt(9, item.getQuantitysold());
			ps.setString(10, item.getProductcode());
			
			ps.executeUpdate();
			
			ps.close();
			cnt.close();
			System.out.println("[STOCKS] ADDITION SUCCESS");
		} catch (SQLException e) {
			System.out.println("[STOCKS] ADDITION FAILED");
			e.printStackTrace();
		}
	}
	
	public void deleteStock(String code) {
		Connection cnt = database.getConnection();
		
		String codeString = String.format("\"%s\"", code);
		
		String query = "DELETE FROM " + Item.TABLE + " WHERE " + Item.COL_PRODUCTCODE + " = " + codeString;
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.executeUpdate();
			
			ps.close();
			cnt.close();
			System.out.println("[STOCKS] DELETE SUCCESS");
		} catch (SQLException e) {
			System.out.println("[STOCKS] DELETE FAILED");
			e.printStackTrace();
		}
	}
	
	public Item getStock(String code) {
		Item item = null;
		Connection cnt = database.getConnection();
		
		String codeString = String.format("\"%s\"", code);
		
		String query = "SELECT * FROM " + Item.TABLE + " WHERE " + Item.COL_PRODUCTCODE + " = " + codeString;
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				item = toItem(rs);
			
			ps.close();
			rs.close();
			cnt.close();
			
			System.out.println("[STOCKS] STOCK GRAB SUCCESS");
		} catch (SQLException e) {
			System.out.println("[STOCKS] STOCK GRAB FAILED");
			e.printStackTrace();
		}
		
		return item;
	}
	
	public Item getStockDuplicate(String cat, String sup, String prod)
	{
		Item item = null;
		Connection cnt = database.getConnection();

		String catString = String.format("\"%s\"", cat);
		String supString = String.format("\"%s\"", sup);
		String prodString = String.format("\"%s\"", prod);
		
		String query = "SELECT * FROM " + Item.TABLE + " WHERE " + Item.COL_CATEGORY + " LIKE " + catString + 
																			 " AND " + Item.COL_SUPPLIER + " LIKE " + supString +
																			 " AND " + Item.COL_PRODUCT + " LIKE " + prodString;
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				item = toItem(rs);
				
			
			ps.close();
			rs.close();
			cnt.close();
			System.out.println("[STOCKS] STOCKS DUPLICATE GRAB DONE");
		} catch (SQLException e) {
			System.out.println("[STOCKS] STOCKS DUPLICATE GRAB FAILED");
			e.printStackTrace();
		}
		
		return item;
	}
	
	public List<Item> getStockIn(String category, String userType) {
		List<Item> stocks = new ArrayList<Item>();
		Connection cnt = database.getConnection();
		
		String categoryString = String.format("\"%s\"", category);
		String query;
		
		if(userType.equals(Placeholder.ADMINISTRATOR.toString()))
			query = "SELECT * FROM " + Item.TABLE + " WHERE " + Item.COL_CATEGORY + " LIKE " + categoryString;
		else
			query = "SELECT * FROM " + Item.TABLE + " WHERE " + Item.COL_CATEGORY + " LIKE " + categoryString + " AND " + Item.COL_QUANTITYAVAILABLE + " > 0";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				stocks.add(toItem(rs));
			
			ps.close();
			rs.close();
			cnt.close();
			System.out.println("[STOCKS] SELECT CATEGORY SUCCESS");
		} catch (SQLException e) {
			System.out.println("[STOCKS] SELECT CATEGORY FAILED");
			e.printStackTrace();
		}
		
		
		return stocks;
	}
	
	public List<Item> getAllRestocks() {
		List<Item> stocks = new ArrayList<Item>();
		Connection cnt = database.getConnection();
		
		String query = "SELECT * FROM " + Item.TABLE + " WHERE " + Item.COL_QUANTITYAVAILABLE + " <= 5";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				stocks.add(toItem(rs));
			
			ps.close();
			rs.close();
			cnt.close();
			System.out.println("[STOCKS] SELECT RESTOCK SUCCESS");
		} catch (SQLException e) {
			System.out.println("[STOCKS] SELECT RESTOCK FAILED");
			e.printStackTrace();
		}
		
		
		return stocks;
	}
	
	public List<Item> getAllSale() {
		List<Item> stocks = new ArrayList<Item>();
		Connection cnt = database.getConnection();
		
		String query = "SELECT * FROM " + Item.TABLE + " WHERE " + Item.COL_DISCOUNTRATE + " > 0.00 AND " + Item.COL_QUANTITYAVAILABLE + " > 0";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				stocks.add(toItem(rs));
			
			ps.close();
			rs.close();
			cnt.close();
			System.out.println("[STOCKS] SELECT SALE SUCCESS");
		} catch (SQLException e) {
			System.out.println("[STOCKS] SELECT SALE FAILED");
			e.printStackTrace();
		}
		
		
		return stocks;
	}
	
	public boolean checkStocks(String cat, String sup, String prod)
	{
		Connection cnt = database.getConnection();
		boolean result = false;
		String catString = String.format("\"%s\"", cat);
		String supString = String.format("\"%s\"", sup);
		String prodString = String.format("\"%s\"", prod);
		
		String query = "SELECT * FROM " + Item.TABLE + " WHERE " + Item.COL_CATEGORY + " LIKE " + catString + 
																			 " AND " + Item.COL_SUPPLIER + " LIKE " + supString +
																			 " AND " + Item.COL_PRODUCT + " LIKE " + prodString;
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				result = true;
				
			
			ps.close();
			rs.close();
			cnt.close();
			System.out.println("[STOCKS] STOCKS CHECK DONE");
		} catch (SQLException e) {
			System.out.println("[STOCKS] STOCKS CHECK FAILED");
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void updateStock(Item item) {
		Connection cnt = database.getConnection();
		
		String query = "UPDATE " + Item.TABLE  + 
						" SET " 
						+ Item.COL_CATEGORY + " = ?,"
						+ Item.COL_SUPPLIER + " = ?,"
						+ Item.COL_PRODUCT + " = ?,"
						+ Item.COL_QUANTITYAVAILABLE + " = ?,"
						+ Item.COL_PURCHASEPRICE + " = ?,"
						+ Item.COL_UNITSELLINGPRICE + " = ?,"
						+ Item.COL_DISCOUNTRATE + " = ?,"
						+ Item.COL_QUANTITYSOLD + " = ?,"
						+ Item.COL_PRODUCTCODE + " = ?"
						+ " WHERE " + Item.COL_ID + " = ?";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setInt(10, item.getIdstocks());
			ps.setString(1, item.getCategory());
			ps.setString(2, item.getSupplier());
			ps.setString(3, item.getProduct());
			ps.setInt(4, item.getQuantityavailable());
			ps.setDouble(5, item.getPurchaseprice());
			ps.setDouble(6, item.getUnitsellingprice());
			ps.setDouble(7, item.getDiscountrate());
			ps.setInt(8, item.getQuantitysold());
			ps.setString(9, item.getProductcode());
			
			ps.executeUpdate();
			
			ps.close();
			cnt.close();
			System.out.println("[STOCKS] STOCK UPDATE SUCCESS");
		} catch (SQLException e) {
			System.out.println("[STOCKS] STOCK UPDATE FAILED");
			e.printStackTrace();
		}
	}
}
