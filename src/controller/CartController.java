package controller;

import java.util.List;

import model.Cart;
import model.CartService;
import model.Person;
import view.Mainframe;

public class CartController {
	private CartService service;
	private Mainframe view;
	
	public CartController(CartService service, Mainframe view) {
		this.service = service;
		this.view = view;
	}
	
	public List<Cart> grabCart(Person account)
	{
		return service.getAll(account.getId());
	}
	
	public List<Integer> getAccountIDs()
	{
		return service.getAccountIDs();
	}
	
	public List<Cart> getCheckedOutItems(Person account)
	{
		return service.getCheckedOutItems(account.getId());
	}
	
	public void addToCart(Cart cart)
	{
		service.addToCart(cart);
	}
	
	public void deleteFromCart(Cart cart)
	{
		service.deleteFromCart(cart.getIdcart());
	}
	
	public void deliverItems(int id)
	{
		service.deliverAllUserItems(id);
	}
	
	public void updateCart(Cart cart)
	{
		service.updateCart(cart);
	}
	
	public void start()
	{
		view.initialize(this);
	}
}
