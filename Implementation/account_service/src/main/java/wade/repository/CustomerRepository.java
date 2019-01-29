package wade.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import wade.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Long> {
	List<Customer> findByLastName(String lastName);
	
	Customer findByUsername(String username);

}
