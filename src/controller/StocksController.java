package controller;

import java.util.List;

import model.Item;
import model.StocksService;
import view.Mainframe;

public class StocksController {
	private StocksService service;
	private Mainframe view;

	public StocksController(StocksService stocksService, Mainframe frame) {
		this.service = stocksService;
		this.view = frame;
	}
	
	public List<Item> getStockInCategory(String category, String userType){
		return service.getStockIn(category, userType);
	}

	public List<Item> getAllStocks(String userType) {
		return service.getAll(userType);
	}
	
	public List<Item> getAllRestocks() {
		return service.getAllRestocks();
	}
	
	public List<Item> getAllSale() {
		return service.getAllSale();
	}
	
	public boolean checkStocks(String cat, String sup, String prod)
	{
		return service.checkStocks(cat, sup, prod);
	}
	
	public Item getStock(String code)
	{
		return service.getStock(code);
	}
	
	public void addStock(Item item)
	{
		service.addStock(item);
	}
	
	public void deleteStock(Item item)
	{
		service.deleteStock(item.getProductcode());
	}
	
	public void updateStock(Item item)
	{
		service.updateStock(item);
	}
	
	public int getID()
	{
		return service.getID();
	}
	
	public Item getStockDuplicate(String cat, String sup, String prod)
	{
		return service.getStockDuplicate(cat, sup, prod);
	}

	public void start() {
		view.initialize(this);
		
	}

}
