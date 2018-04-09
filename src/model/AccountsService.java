package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class AccountsService {
	private StoreDB database;
	
	public AccountsService(StoreDB database) {
		this.database = database;
	}
	
	public List<Person> getAll(){
		List<Person> accounts = new ArrayList<Person>();
		
		Connection cnt = database.getConnection();
		
		String query = "SELECT * FROM " + Person.TABLE;
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				accounts.add(toPerson(rs));
			
			ps.close();
			rs.close();
			cnt.close();
			
			System.out.println("[ACCOUNTS] SELECT SUCCESS");
		} catch (SQLException e) {
			System.out.println("[ACCOUNTS] SELECT FAILED");
			e.printStackTrace();
		}
		
		return accounts;
	}
	
	public Person getAccount(String name) {
		Person account = null;
		Connection cnt = database.getConnection();
		
		String nameString = String.format("\"%s\"", name);
		
		String query = "SELECT * FROM " + Person.TABLE + " WHERE " + Person.COL_USERNAME + " = " + nameString;
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				account = toPerson(rs);
			
			ps.close();
			rs.close();
			cnt.close();
			
			System.out.println("[ACCOUNTS] ACCOUNT GRAB SUCCESS");
		} catch (SQLException e) {
			System.out.println("[ACCOUNTS] ACCOUNT GRAB FAILED");
			e.printStackTrace();
		}
		
		return account;
	}
	
	public Person getAccount(int id) {
		Person account = null;
		Connection cnt = database.getConnection();
		
		String query = "SELECT * FROM " + Person.TABLE + " WHERE " + Person.COL_ID + " = ?" ;
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				account = toPerson(rs);
			
			ps.close();
			rs.close();
			cnt.close();
			
			System.out.println("[ACCOUNTS] ACCOUNT GRAB SUCCESS");
		} catch (SQLException e) {
			System.out.println("[ACCOUNTS] ACCOUNT GRAB FAILED");
			e.printStackTrace();
		}
		
		return account;
	}
	
	private Person toPerson(ResultSet rs) throws SQLException{
		Person account = new Person();
		
		account.setId(rs.getInt(Person.COL_ID));
		account.setUsername(rs.getString(Person.COL_USERNAME));
		account.setPassword(rs.getString(Person.COL_PASSWORD));
		account.setFirstName(rs.getString(Person.COL_FIRSTNAME));
		account.setLastName(rs.getString(Person.COL_LASTNAME));
		account.setAddress(rs.getString(Person.COL_ADDRESS));
		account.setAccountType(rs.getString(Person.COL_ACCOUNTTYPE));
		account.setOutstandingBalance(rs.getDouble(Person.COL_OUTSTANDINGBALANCE));
		account.setCreditLimit(rs.getDouble(Person.COL_CREDITLIMIT));
		account.setLocked(rs.getBoolean(Person.COL_ISLOCKED));
		account.setTries(rs.getInt(Person.COL_TRIES));
		
		
		return account;
		
	}
	
	public void deleteAccount(int id) {
		Connection cnt = database.getConnection();
		
		String query = "DELETE FROM " + Person.TABLE + " WHERE " + Person.COL_ID + " = ?";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setInt(1, id);
			
			ps.executeUpdate();
			
			ps.close();
			cnt.close();
			System.out.println("[ACCOUNTS] DELETE SUCCESS");
		} catch (SQLException e) {
			System.out.println("[ACCOUNTS] DELETE FAILED");
			e.printStackTrace();
		}
	}
	
	public void addAccount(Person person) {
		Connection cnt = database.getConnection();
		
		String query = "INSERT INTO " + Person.TABLE + " VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setInt(1, Types.NULL);
			ps.setString(2, person.getUsername());
			ps.setString(3, person.getPassword());
			ps.setString(4, person.getFirstName());
			ps.setString(5, person.getLastName());
			ps.setString(6, person.getAddress());
			ps.setString(7, person.getAccountType());
			ps.setBoolean(8, person.isLocked());
			ps.setInt(9, person.getTries());
			ps.setDouble(10, person.getOutstandingBalance());
			ps.setDouble(11, person.getCreditLimit());
			
			ps.executeUpdate();
			
			ps.close();
			cnt.close();
			System.out.println("[ACCOUNTS] ADDITION SUCCESS");
		} catch (SQLException e) {
			System.out.println("[ACCOUNTS] ADDITION FAILED");
			e.printStackTrace();
		}
	}
	
	public void updateAccount(Person account) {
		Connection cnt = database.getConnection();
		
		String query = "UPDATE " + Person.TABLE  + 
						" SET " 
						+ Person.COL_ID + " = ?,"
						+ Person.COL_USERNAME + " = ?,"
						+ Person.COL_PASSWORD + " = ?,"
						+ Person.COL_FIRSTNAME + " = ?,"
						+ Person.COL_LASTNAME + " = ?,"
						+ Person.COL_ADDRESS + " = ?,"
						+ Person.COL_ACCOUNTTYPE + " = ?,"
						+ Person.COL_ISLOCKED + " = ?,"
						+ Person.COL_TRIES + " = ?,"
						+ Person.COL_OUTSTANDINGBALANCE + " = ?,"
						+ Person.COL_CREDITLIMIT + " = ?"
						+ " WHERE " + Person.COL_USERNAME + " = ?";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setString(12, account.getUsername());
			ps.setInt(1, account.getId());
			ps.setString(2, account.getUsername());
			ps.setString(3, account.getPassword());
			ps.setString(4, account.getFirstName());
			ps.setString(5, account.getLastName());
			ps.setString(6, account.getAddress());
			ps.setString(7, account.getAccountType());
			ps.setBoolean(8, account.isLocked());
			ps.setInt(9, account.getTries());
			ps.setDouble(10, account.getOutstandingBalance());
			ps.setDouble(11, account.getCreditLimit());
			
			ps.executeUpdate();
			
			ps.close();
			cnt.close();
			System.out.println("[ACCOUNTS] ACCOUNT UPDATE SUCCESS");
		} catch (SQLException e) {
			System.out.println("[ACCOUNTS] ACCOUNT UPDATE FAILED");
			e.printStackTrace();
		}
	}
	
	public boolean checkUsername(String name)
	{
		Connection cnt = database.getConnection();
		boolean result = false;
		String nameString = String.format("\"%s\"", name);
		
		String query = "SELECT * FROM " + Person.TABLE + " WHERE " + Person.COL_USERNAME + " LIKE " + nameString;
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				result = true;
				
			
			ps.close();
			rs.close();
			cnt.close();
			System.out.println("[ACCOUNTS] USERNAME CHECK DONE");
		} catch (SQLException e) {
			System.out.println("[ACCOUNTS] USERNAME CHECK FAILED");
			e.printStackTrace();
		}
		
		return result;
	}
	
	public List<Person> getLockedAccounts(){
		Connection cnt = database.getConnection();
		List<Person> accounts = new ArrayList<Person>();
		
		String query = "SELECT * FROM " + Person.TABLE + " WHERE " + Person.COL_ISLOCKED + " = true";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				accounts.add(toPerson(rs));
			
			ps.close();
			rs.close();
			cnt.close();
			System.out.println("[ACCOUNTS] SELECT LOCKED SUCCESS");
		} catch (SQLException e) {
			System.out.println("[ACCOUNTS] SELECT LOCKED FAILED");
			e.printStackTrace();
		}
		
		return accounts;
	}
	
	public List<Person> getOutstandingAccounts(){
		Connection cnt = database.getConnection();
		List<Person> accounts = new ArrayList<Person>();
		
		String query = "SELECT * FROM " + Person.TABLE + " WHERE " + Person.COL_OUTSTANDINGBALANCE + " > 0.00";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				accounts.add(toPerson(rs));
			
			ps.close();
			rs.close();
			cnt.close();
			System.out.println("[ACCOUNTS] SELECT OUTSTANDING SUCCESS");
		} catch (SQLException e) {
			System.out.println("[ACCOUNTS] SELECT OUTSTANDING FAILED");
			e.printStackTrace();
		}
		
		return accounts;
	}


}
