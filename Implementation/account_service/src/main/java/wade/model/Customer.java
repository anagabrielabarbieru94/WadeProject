package wade.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Customer")
public class Customer implements Serializable{

	private static final long serialVersionUID = -7723877704121464987L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "FirstName")
	private String firstName;
 
	@Column(name = "LastName")
	private String lastName;
	
	@Column(name = "UserID")
	private String userId;
	
	@Column(name = "Password")
	private String password;
	
	@Column(name="Genre")
	private String genre;
	
	@Override
	public String toString()
	{
		return String.format("User[id=%d,firstName='%s', lastName='%s']",id,firstName,lastName);
	}
	
	protected Customer() {
	}
	
	public Customer(String firstName, String lastName,String userId,String password,String genre) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userId = userId;
		this.password = password;
		this.genre = genre;
	}
}
