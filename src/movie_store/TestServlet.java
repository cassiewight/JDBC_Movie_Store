package movie_store;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {

	/**
	 * The serialization runtime associates with each serializable class a version number, 
	 * called a serialVersionUID, which is used during deserialization to verify that 
	 * the sender and receiver of a serialized object have loaded classes for 
	 * that object that are compatible with respect to serialization. 
	 * 
	 * If the receiver has loaded a class for the object that has a 
	 * different serialVersionUID than that of the corresponding sender's class, 
	 * then deserialization will result in an  InvalidClassException. 
	 * A serializable class can declare its own serialVersionUID explicitly by 
	 * declaring a field named serialVersionUID that must be static, final, and of type long:
	 * 
	 * ANY-ACCESS-MODIFIER static final long serialVersionUID = 42L;
	 * 
	 * If a serializable class does not explicitly declare a serialVersionUID, 
	 * then the serialization runtime will calculate a default serialVersionUID 
	 * value for that class based on various aspects of the class, 
	 * as described in the Java(TM) Object Serialization Specification. 
	 */
	private static final long serialVersionUID = 1L;
	
	// 1- Inject connection pool
	@Resource(name = "jdbc/movie_store")
	private DataSource dataSource;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			// 2- Use dataSource to get connection to database
			myConn = dataSource.getConnection();

			// 3- Define a sql query
			String sql = "select * from imageTest";

			// 4- Create a sql query statement
			myStmt = myConn.createStatement();

			// 5- execute query
			myRs = myStmt.executeQuery(sql);

			// 6- Iterate on result list and extract string contents by key
			while (myRs.next()) {
				String photo = myRs.getString("path");
				out.println(photo);
				System.out.println(photo);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
			out.println(exc.getMessage());
		}
	}
}