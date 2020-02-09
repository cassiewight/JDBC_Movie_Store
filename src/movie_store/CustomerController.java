package movie_store;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class CustomerController {

	private List<Customer> customers;
	private CustomerDbUtil customerDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	private String theSearchName;
	private String confirmation;
	
	public String getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}

	public String getTheSearchName() {
		return theSearchName;
	}

	public void setTheSearchName(String theSearchName) {
		this.theSearchName = theSearchName;
	}
	
	public List<Customer> getCustomers() {
		return customers;
	}

	public CustomerController() throws Exception {
		customers = new ArrayList<>();
		customerDbUtil = CustomerDbUtil.getInstance();
	}

/*---------------------------------------------------------------------------------------*/
/*************   LOAD CUSTOMERS (LIST)  *********************************************************/
/*---------------------------------------------------------------------------------------*/
	
	public void loadCustomers() {

		logger.info("Loading customers");
		logger.info("theSearchName = " + theSearchName);

		try {
			
			if (theSearchName != null && theSearchName.trim().length() > 0) {
				
				customers = customerDbUtil.searchCustomers(theSearchName);
			}
			
			else {
				
				customers = customerDbUtil.getCustomers();
			}

		} catch (Exception exc) {
			
			logger.log(Level.SEVERE, "Error loading students", exc);
			addErrorMessage(exc);
			
		} finally {
			
			theSearchName = null;
		}
	}
	
	
	
/*---------------------------------------------------------------------------------------*/
/************* LOAD CUSTOMER FOR UPDATE *********************************************************/
/*---------------------------------------------------------------------------------------*/	
	
	public String loadCustomer(String custId) {

		logger.info("loading customer: " + custId);
		logger.info("CUSTOMER CONTROLLER LOAD CUSTOMER CALLED");

		try {
			// get student from database
			Customer theCustomer = customerDbUtil.getCustomer(custId);

			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("customer", theCustomer);

		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading student id:" + custId, exc);

			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "update_customer_form";

	}
	
	
	
/*---------------------------------------------------------------------------------------*/
/*************   CONFIRM CUSTOMER FOR REGISRTATION  *************************************/
/*---------------------------------------------------------------------------------------*/
	
	public String confirmCustomer(Customer customer) {
		
		
		System.out.println("navigate() called");
		System.out.println("value of confirmation is " + confirmation);
		
		System.out.print("customerID is " + customer.getId());
		System.out.print(customer.getFirstName());
		System.out.print(customer.getLastName());
		System.out.print(customer.getEmail());
		
		
		if (confirmation != null && confirmation.contentEquals("edit")) {
			
			return "registration";
		}
		
		else {
			
			try {

			// add student to the database
			customerDbUtil.addCustomer(customer);

		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error adding Customer", exc);

			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}

			return "shopping";
		}
		
	}
	

/*---------------------------------------------------------------------------------------*/
/*************  UPDATE CUSTOMER  *********************************************************/
/*---------------------------------------------------------------------------------------*/	
	
	public String updateCustomer(Customer theCustomer) {

		logger.info("updating customer: " + theCustomer.getId());

		try {

			// update student in the database
			customerDbUtil.updateCustomer(theCustomer);

		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error updating student: " + theCustomer, exc);

			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}

		return "user_management?faces-redirect=true";
	}

	
/*---------------------------------------------------------------------------------------*/
/*************  DELETE CUSTOMER  *********************************************************/
/*---------------------------------------------------------------------------------------*/	
		
	
	public String deleteCustomer(String customerId) {

		logger.info("Deleting student id: " + customerId);

		try {

			// delete the student from the database
			customerDbUtil.deleteCustomer(customerId);

		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error deleting student id: " + customerId, exc);

			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}

		return "user_management";
	}
	
	

	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
}

