package view;

import controller.AccountsController;
import controller.CartController;
import controller.StocksController;
import model.Person;

public class Mainframe {
	private ShoppingKioskLogin login;
	private AdminMenu admin;
	private ShopperMenu shopper;
	private AccountsController actrl;
	private StocksController sctrl;
	private CartController cctrl;
	
	public Mainframe()
	{
		login = new ShoppingKioskLogin();
	}

	public void initialize(AccountsController ctrl) {
		this.actrl = ctrl;
		login.addComponents(actrl, this);
	}
	
	public void initialize(StocksController sctrl) {
		this.sctrl = sctrl;
	}
	
	public void initialize(CartController cctrl) {
		this.cctrl = cctrl;
	}
	
	public void login(Person account)
	{
		if(account.getAccountType().equals(Placeholder.ADMINISTRATOR.toString()))
		{
			admin = new AdminMenu(account, actrl, sctrl, cctrl, this);
			admin.setVisible(true);
		}
			
		else
		{
			shopper = new ShopperMenu(account, actrl, sctrl, cctrl, this);
			shopper.setVisible(true);
		}
	}
	
	public void logout()
	{
		login.setVisible(true);
		admin = null;
		shopper = null;
	}

	public AdminMenu getAdmin() {
		return admin;
	}

	public void setAdmin(AdminMenu admin) {
		this.admin = admin;
	}

	public ShopperMenu getShopper() {
		return shopper;
	}

	public void setShopper(ShopperMenu shopper) {
		this.shopper = shopper;
	}
	
}
