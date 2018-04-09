package controller;

import java.util.List;

import model.AccountsService;
import model.Person;
import view.Mainframe;

public class AccountsController {
	private AccountsService service;
	private Mainframe view;
	
	public AccountsController(AccountsService service, Mainframe view) {
		this.service = service;
		this.view = view;
	}
	
	public boolean checkUsername(String name)
	{
		return service.checkUsername(name);
	}
	
	public void addPerson(Person person)
	{
		service.addAccount(person);
	}
	
	public void deletePerson(Person person)
	{
		service.deleteAccount(person.getId());
	}
	
	public void updateAccount(Person account) {
		service.updateAccount(account);
	}
	
	public Person getAccount(String name)
	{
		return service.getAccount(name);
	}
	
	public Person getAccount(int id)
	{
		return service.getAccount(id);
	}
	
	public List<Person> getAllAccounts()
	{
		return service.getAll();
	}
	
	public List<Person> getLockedAccounts()
	{
		return service.getLockedAccounts();
	}
	
	public List<Person> getOutstandingAccounts()
	{
		return service.getOutstandingAccounts();
	}
	
	public void start()
	{
		view.initialize(this);
	}
}
