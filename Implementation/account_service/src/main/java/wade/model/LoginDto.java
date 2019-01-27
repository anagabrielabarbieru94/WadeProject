package wade.model;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "employee")
public class LoginDto {

	private String userName;

	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
