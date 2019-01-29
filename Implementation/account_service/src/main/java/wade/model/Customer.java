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

	
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Id
	@Column(name = "username")
	private String username;
	
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
	
	public Customer() {
	}
	
	public Customer(String firstName, String lastName,String username,String password,String genre) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.genre = genre;
	}

	public String getUsername() {
		return username;
	}
	
	
	public void setUsername(String username) {
		this.username = username;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
}
