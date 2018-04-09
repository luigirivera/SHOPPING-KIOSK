import controller.AccountsController;
import controller.CartController;
import controller.StocksController;
import model.AccountsService;
import model.CartService;
import model.StocksService;
import model.StoreDB;
import view.Mainframe;

public class Driver {
	public static void main(String[] args)
	{
		Mainframe frame = new Mainframe();
		
		StoreDB db = new StoreDB();
		
		AccountsService accountsService = new AccountsService(db);
		StocksService stocksService = new StocksService(db);
		CartService cartService = new CartService(db);
		
		AccountsController accountsController = new AccountsController(accountsService, frame);
		StocksController stocksController = new StocksController(stocksService, frame);
		CartController cartController = new CartController(cartService, frame);
		
		accountsController.start();
		stocksController.start();
		cartController.start();
	}
}
