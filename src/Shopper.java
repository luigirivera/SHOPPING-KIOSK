import java.util.ArrayList;
import java.util.List;

public class Shopper extends Person {
	private double creditLimit;
	private double outstandingBalance;
	private List<Cart> cart;
	
	public Shopper(String username, String password, String firstName, String lastName, String address)
	{
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		creditLimit = 5000.00;
		outstandingBalance = 0.00;
		cart = new ArrayList<Cart>();
	}
}
