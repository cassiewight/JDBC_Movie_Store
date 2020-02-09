package movie_store;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MovieDbUtil {

	private static MovieDbUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/movie_store";

	public static MovieDbUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new MovieDbUtil();
		}

		return instance;
	}

	private MovieDbUtil() throws Exception {
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
/*************  GET MOVIES (LIST) ****************************************************/
/*---------------------------------------------------------------------------------------*/

		public List<Movie> getMovies() throws Exception {
			
			System.out.println("\n loading Movies DBUTIL");

			List<Movie> movies = new ArrayList<>();

			Connection myConn = null;
			Statement myStmt = null;
			ResultSet myRs = null;
			
			String projectImagePath = "/JSF_Cassie_Mid/resources/img/";

			try {
				myConn = getConnection();

				String sql = "select * from movies";

				myStmt = myConn.createStatement();

				myRs = myStmt.executeQuery(sql);

				// process result set
				while (myRs.next()) {

					// retrieve data from result set row
					int movieID = myRs.getInt("movieID");
					String title = myRs.getString("title");
					String genre = myRs.getString("genre");
					String imagePath = myRs.getString("imagePath");

					System.out.println(title + " " + genre);
					
					imagePath = projectImagePath + imagePath;
					
					// create new student object
					Movie tempMovie = new Movie(movieID, title, genre, imagePath);
					
					// add it to the list of students
					movies.add(tempMovie);
				}

				return movies;
				
			} finally {
				close(myConn, myStmt, myRs);
			}
		}
	
		
}
