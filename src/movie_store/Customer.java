package movie_store;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class Customer {

	private String id;
	private String firstName;
	private String lastName;
	private String phone;
	private String address;
	private String email;
	private String username;
	private String password;
	private List<String> emailUpdate;
	private String profileVisibility;
	
	
	public Customer() {
		
		emailUpdate = new ArrayList<String>();

	}
	
	public Customer(String id, String firstName, String lastName, String phone, String address, String email, String username, String password, String profileVisibility) {
		
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.username = username;
		this.password = password;
		this.profileVisibility = profileVisibility;
		
		emailUpdate = new ArrayList<String>();

	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
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


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	
	public List<String> getEmailUpdate() {
		return emailUpdate;
	}


	public void setEmailUpdate(List<String> emailUpdate) {
		this.emailUpdate = emailUpdate;
	}


	public String getProfileVisibility() {
		return profileVisibility;
	}


	public void setProfileVisibility(String profileVisibility) {
		this.profileVisibility = profileVisibility;
	}


	public void validateUsername(FacesContext context, 
								  UIComponent component, 
								  String value)throws ValidatorException {

		if (value == null) { return; }
		String data = value.toString();
		
		// Course code must start with IPD17
		if (!data.startsWith("IPD17")) {
			FacesMessage message = new FacesMessage("Username must start with IPD17");
			throw new ValidatorException(message);
		}
	
	}
	

}
