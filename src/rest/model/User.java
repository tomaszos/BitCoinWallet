package rest.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class User {
	private String login;
	private String password;
	private String restKey;

	// Must have no-argument constructor
	public User() {

	}

	public User(String login, String password, String restKey) {
		this.login = login;
		this.password = password;
		this.restKey = restKey;
	}

	@XmlElement(name ="login")
	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return this.login;
	}

	@XmlElement(name ="password")
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	@XmlElement(name ="restKey")
	public void setRestKey(String restKey) {
		this.restKey = restKey;
	}

	public String getRestKey() {
		return this.restKey;
	}
}
