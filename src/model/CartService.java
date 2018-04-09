package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class CartService {
	private StoreDB database;

	public CartService(StoreDB database) {
		this.database = database;
	}
	
	public List<Cart> getAll(int id)
	{
		List<Cart> cart = new ArrayList<Cart>();
		
		Connection cnt = database.getConnection();
		
		String query = "SELECT * FROM " + Cart.TABLE + " WHERE " + Cart.COL_IDACCOUNTHOLDER + " = ? AND " + Cart.COL_CHECKEDOUT + " = false";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				cart.add(toCart(rs));
			
			ps.close();
			rs.close();
			cnt.close();
			
			System.out.println("[CART] CART GRAB SUCCESS");
		} catch (SQLException e) {
			System.out.println("[CART] CART GRAB FAILED");
			e.printStackTrace();
		}
		
		return cart;
	}
	
	public List<Integer> getAccountIDs()
	{
		List<Integer> ints = new ArrayList<Integer>();
		Connection cnt = database.getConnection();
		
		String query = "SELECT DISTINCT " + Cart.COL_IDACCOUNTHOLDER + " FROM " + Cart.TABLE;
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				ints.add(rs.getInt(Cart.COL_IDACCOUNTHOLDER));
			
			ps.close();
			rs.close();
			cnt.close();
			
			System.out.println("[CART] ACCOUNTS GRAB SUCCESS");
		} catch (SQLException e) {
			System.out.println("[CART] ACCOUNTS GRAB FAILED");
			e.printStackTrace();
		}
		return ints;
	}
	
	public List<Cart> getCheckedOutItems(int id)
	{
		List<Cart> cart = new ArrayList<Cart>();
		Connection cnt = database.getConnection();
		
		String query = "SELECT * FROM " + Cart.TABLE + " WHERE " + Cart.COL_IDACCOUNTHOLDER + " = ? AND " + Cart.COL_CHECKEDOUT + " = true";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				cart.add(toCart(rs));
			
			System.out.println("[CART] CHECKED OUT GRAB SUCCESS");
		} catch (SQLException e) {
			System.out.println("[CART] CHECKED OUT GRAB FAILED");
			e.printStackTrace();
		}
		
		return cart;
	}
	
	private Cart toCart(ResultSet rs) throws SQLException{
		Cart item = new Cart();
		
		item.setIdcart(rs.getInt(Cart.COL_IDCART));
		item.setIdaccountholder(rs.getInt(Cart.COL_IDACCOUNTHOLDER));
		item.setProductcode(rs.getString(Cart.COL_PRODUCTCODE));
		item.setIdproduct(rs.getInt(Cart.COL_IDPRODUCT));
		item.setQuantity(rs.getInt(Cart.COL_QUANTITY));
		item.setCheckedout(rs.getBoolean(Cart.COL_CHECKEDOUT));
		
		return item;
	}
	
	public void addToCart(Cart cart) {
		Connection cnt = database.getConnection();
		
		String query = "INSERT INTO " + Cart.TABLE + " VALUES(?,?,?,?,?,?)";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setInt(1, Types.NULL);
			ps.setInt(2, cart.getIdaccountholder());
			ps.setString(3, cart.getProductcode());
			ps.setInt(4, cart.getIdproduct());
			ps.setInt(5, cart.getQuantity());
			ps.setBoolean(6, cart.isCheckedout());
			
			
			ps.executeUpdate();
			
			ps.close();
			cnt.close();
			System.out.println("[CART] ADDITION SUCCESS");
		} catch (SQLException e) {
			System.out.println("[CART] ADDITION FAILED");
			e.printStackTrace();
		}
	}
	
	public void deleteFromCart(int id) {
		Connection cnt = database.getConnection();
		
		String query = "DELETE FROM " + Cart.TABLE + " WHERE " + Cart.COL_IDCART + " = ?";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setInt(1, id);
			
			ps.executeUpdate();
			
			ps.close();
			cnt.close();
			System.out.println("[CART] DELETE SUCCESS");
		} catch (SQLException e) {
			System.out.println("[CART] DELETE FAILED");
			e.printStackTrace();
		}
	}
	
	public void deliverAllUserItems(int id) {
		Connection cnt = database.getConnection();
		
		String query = "DELETE FROM " + Cart.TABLE + " WHERE " + Cart.COL_IDACCOUNTHOLDER + " = ?";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setInt(1, id);
			
			ps.executeUpdate();
			
			ps.close();
			cnt.close();
			System.out.println("[CART] DELIVER SUCCESS");
		} catch (SQLException e) {
			System.out.println("[CART] DELIVER FAILED");
			e.printStackTrace();
		}
	}
	
	public void updateCart(Cart cart) {
		Connection cnt = database.getConnection();
		
		String query = "UPDATE " + Cart.TABLE  + 
						" SET " 
						+ Cart.COL_IDCART + " = ?,"
						+ Cart.COL_IDACCOUNTHOLDER + " = ?,"
						+ Cart.COL_PRODUCTCODE + " = ?,"
						+ Cart.COL_IDPRODUCT + " = ?,"
						+ Cart.COL_QUANTITY + " = ?,"
						+ Cart.COL_CHECKEDOUT + " = ?"
						+ " WHERE " + Cart.COL_IDCART + " = ?";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setInt(7, cart.getIdcart());
			ps.setInt(1, cart.getIdcart());
			ps.setInt(2, cart.getIdaccountholder());
			ps.setString(3, cart.getProductcode());
			ps.setInt(4, cart.getIdproduct());
			ps.setInt(5, cart.getQuantity());
			ps.setBoolean(6, cart.isCheckedout());
			
			ps.executeUpdate();
			
			ps.close();
			cnt.close();
			System.out.println("[CART] CART UPDATE SUCCESS");
		} catch (SQLException e) {
			System.out.println("[CART] CART UPDATE FAILED");
			e.printStackTrace();
		}
	}
}
