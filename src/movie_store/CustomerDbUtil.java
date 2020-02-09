package movie_store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CustomerDbUtil {

	private static CustomerDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/movie_store";

	public static CustomerDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new CustomerDbUtil();
		}

		return instance;
	}

	private CustomerDbUtil() throws Exception {
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}

	private Connection getConnection() throws Exception {

		Connection theConn = dataSource.getConnection();

		return theConn;
	}

	private void close(Connection theConn, Statement theStmt) {
		close(theConn, theStmt, null);
	}

	private void close(Connection theConn, Statement theStmt, ResultSet theRs) {

		try {
			if (theRs != null) {
				theRs.close();
			}

			if (theStmt != null) {
				theStmt.close();
			}

			if (theConn != null) {
				theConn.close();
			}

		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
/*---------------------------------------------------------------------------------------*/
/*************  GET CUSTOMERS (LIST) ****************************************************/
/*---------------------------------------------------------------------------------------*/

	public List<Customer> getCustomers() throws Exception {
		
		System.out.println("\n loading customers DBUTIL");

		List<Customer> customers = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = getConnection();

			String sql = "select * from customer";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {

				// retrieve data from result set row
				String id = myRs.getString("custID");
				String firstName = myRs.getString("firstName");
				String lastName = myRs.getString("lastName");
				String phone = myRs.getString("phone");
				String address = myRs.getString("address");
				String email = myRs.getString("email");
				String username= myRs.getString("username");
				String password = myRs.getString("password");
				String profileVisibility = myRs.getString("profileVisibility");
				
				System.out.println(id + " " + firstName + " " + lastName + " " + phone + " " + address + " " + email + " " +
				 username + " " + password + " " + profileVisibility);
				
				// create new student object
				Customer tempCustomer = new Customer(id, firstName, lastName, phone, address, email, username, password, profileVisibility);
				System.out.println(tempCustomer.getFirstName());
				
				// add it to the list of students
				customers.add(tempCustomer);
			}

			return customers;
			
		} finally {
			close(myConn, myStmt, myRs);
		}
	}

/*---------------------------------------------------------------------------------------*/
/*********** GET CUSTOMER  *************************************************************/
/*---------------------------------------------------------------------------------------*/
	
	public Customer getCustomer(String custId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = getConnection();

			String sql = "select * from customer where custID=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, custId);

			myRs = myStmt.executeQuery();

			Customer theCustomer = null;

			// retrieve data from result set row
			if (myRs.next()) {
				
				String id = myRs.getString("custID");
				String firstName = myRs.getString("firstName");
				String lastName = myRs.getString("lastName");
				String phone = myRs.getString("phone");
				String address = myRs.getString("address");
				String email = myRs.getString("email");
				String username= myRs.getString("username");
				String password = myRs.getString("password");
				String profileVisibility = myRs.getString("profileVisibility");

				theCustomer = new Customer(id, firstName, lastName, phone, address, email, username, password, profileVisibility);

			} else {
				throw new Exception("Could not find customer id: " + custId);
			}

			return theCustomer;
			
		} finally {
			close(myConn, myStmt, myRs);
		}
	}
	
/*---------------------------------------------------------------------------------------*/
/************* ADD CUSTOMER  *********************************************************/
/*---------------------------------------------------------------------------------------*/

	public void addCustomer(Customer theCustomer) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "insert into customer (custID, firstName, lastName, phone, address, email, username, password, profileVisibility) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theCustomer.getId());
			myStmt.setString(2, theCustomer.getFirstName());
			myStmt.setString(3, theCustomer.getLastName());
			myStmt.setString(4, theCustomer.getPhone());
			myStmt.setString(5, theCustomer.getAddress());
			myStmt.setString(6, theCustomer.getEmail());
			myStmt.setString(7, theCustomer.getUsername());
			myStmt.setString(8, theCustomer.getPassword());
			myStmt.setString(9, theCustomer.getProfileVisibility());

			myStmt.execute();
			
		} finally {
			
			close(myConn, myStmt);
		}

	}

/*---------------------------------------------------------------------------------------*/
/************* DELETE CUSTOMER  *********************************************************/
/*---------------------------------------------------------------------------------------*/


	public void deleteCustomer(String custId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "delete from customer where custID=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, custId);

			myStmt.execute();
			
		} finally {
			close(myConn, myStmt);
		}
	}

/*---------------------------------------------------------------------------------------*/
/************* UPDATE CUSTOMER  *********************************************************/
/*---------------------------------------------------------------------------------------*/
	
	
	public void updateCustomer(Customer theCustomer) throws Exception {
		
		System.out.println(theCustomer.getId());
		System.out.println(theCustomer.getFirstName());
		System.out.println(theCustomer.getLastName());
		System.out.println(theCustomer.getEmail());
		System.out.println(theCustomer.getPhone());
		System.out.println(theCustomer.getUsername());
		
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "update customer set "
					+ "firstName=?, "
					+ "LastName=?, " 
					+ "phone=?,"
					+ "address=?,"
					+ "email=?, "
					+ "username=?, "
					+ "password=?, "
					+ "profileVisibility=? "
					+ "where custId=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theCustomer.getFirstName());
			myStmt.setString(2, theCustomer.getLastName());
			myStmt.setString(3, theCustomer.getPhone());
			myStmt.setString(4, theCustomer.getAddress());
			myStmt.setString(5, theCustomer.getEmail());
			myStmt.setString(6, theCustomer.getUsername());
			myStmt.setString(7, theCustomer.getPassword());
			myStmt.setString(8, theCustomer.getProfileVisibility());
			myStmt.setString(9, theCustomer.getId());

			myStmt.execute();
			
			System.out.println("CUSTOMER UPDATED");
	
		} finally {
			close(myConn, myStmt);
		}

	}
	
	
/*---------------------------------------------------------------------------------------*/
/*************  SEARCH CUSTOMER  *********************************************************/
/*---------------------------------------------------------------------------------------*/		
		
		
	public List<Customer> searchCustomers(String theSearchName) throws Exception {

		List<Customer> customers = new ArrayList<>();


		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {

			myConn = dataSource.getConnection();

			if (theSearchName != null && theSearchName.trim().length() > 0) {

				
				String sql = "select * from customer where lower(firstName) like ? or lower(lastName) like ?";

				myStmt = myConn.prepareStatement(sql);

				String theSearchNameLike = "%" + theSearchName.toLowerCase() + "%";
				myStmt.setString(1, theSearchNameLike);
				myStmt.setString(2, theSearchNameLike);

			} else {
				String sql = "select * from customer";

				myStmt = myConn.prepareStatement(sql);
			}

			myRs = myStmt.executeQuery();

			while (myRs.next()) {

				String id = myRs.getString("custID");
				String firstName = myRs.getString("firstName");
				String lastName = myRs.getString("lastName");
				String phone = myRs.getString("phone");
				String address = myRs.getString("address");
				String email = myRs.getString("email");
				String username= myRs.getString("username");
				String password = myRs.getString("password");
				String profileVisibility = myRs.getString("profileVisibility");

				Customer tempCustomer = new Customer(id, firstName, lastName, phone, address, email, username, password, profileVisibility);

					// 14- Add it to the list of students
				customers.add(tempCustomer);
			}

			return customers;
			
		} finally {

			close(myConn, myStmt, myRs);
		}
	}


}