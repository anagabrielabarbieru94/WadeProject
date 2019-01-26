package wade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import wade.model.Customer;
import wade.repository.CustomerRepository;

@RestController
public abstract class AccountController {
	
	@Autowired
	protected CustomerRepository repository;
	
	@RequestMapping("/save")
	public String process(){
		repository.save(new Customer("Jack", "Smith","user","pass","Male"));
		repository.save(new Customer("Jack1", "Smith1","user1","pass1","Male"));
		repository.save(new Customer("Jack2", "Smith2","user2","pass2","Male"));
		repository.save(new Customer("Jack3", "Smith3","user3","pass3","Male"));
		repository.save(new Customer("Jack4", "Smith4","user4","pass4","Male"));
		
		return "Done";
	}
	
	@RequestMapping("/findall")
	public String findAll(){
		String result = "";
		
		for(Customer cust : repository.findAll()){
			result += cust.toString() + "<br>";
		}
		
		return result;
	}
	
	@RequestMapping("/findbyid")
	public String findById(@RequestParam("id") long id){
		String result = "";
		result = repository.findById(id).toString();
		return result;
	}
	
	@RequestMapping("/findbylastname")
	public String fetchDataByLastName(@RequestParam("lastname") String lastName){
		String result = "";
		
		for(Customer cust: repository.findByLastName(lastName)){
			result += cust.toString() + "<br>"; 
		}
		
		return result;
	}
}
