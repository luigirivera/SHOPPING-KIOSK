package model;

public class Person {
	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String address;
	private String accountType;
	private boolean isLocked;
	private int tries;
	private double creditLimit;
	private double outstandingBalance;
	
	public static final String TABLE = "accounts";
	public static final String COL_ID = "idaccounts";
	public static final String COL_USERNAME = "username";
	public static final String COL_PASSWORD = "password";
	public static final String COL_FIRSTNAME = "firstname";
	public static final String COL_LASTNAME = "lastname";
	public static final String COL_ADDRESS = "address";
	public static final String COL_ACCOUNTTYPE = "accounttype";
	public static final String COL_ISLOCKED = "isLocked";
	public static final String COL_TRIES = "tries";
	public static final String COL_OUTSTANDINGBALANCE = "outstandingbalance";
	public static final String COL_CREDITLIMIT = "creditlimit";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}

	public double getOutstandingBalance() {
		return outstandingBalance;
	}

	public void setOutstandingBalance(double outstandingBalance) {
		this.outstandingBalance = outstandingBalance;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public boolean isLocked() {
		return isLocked;
	}
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	public int getTries() {
		return tries;
	}
	public void setTries(int tries) {
		this.tries = tries;
	}
	
}
