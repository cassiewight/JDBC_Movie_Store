package movie_store;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class MovieController {

	private List<Movie> movies;
	private MovieDbUtil movieDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());

	
	public List<Movie> getMovies() {
		return movies;
	}

	public MovieController() throws Exception {
		movies = new ArrayList<>();

		movieDbUtil = MovieDbUtil.getInstance();
	}

/*---------------------------------------------------------------------------------------*/
/*************   LOAD MOVIES (LIST)  *********************************************************/
/*---------------------------------------------------------------------------------------*/
	
	public void loadMovies() {

		logger.info("Loading movies");

		try {
			
			movies = movieDbUtil.getMovies();
			

		} catch (Exception exc) {
			// Send this to server logs
			logger.log(Level.SEVERE, "Error loading movies", exc);

			// Add error message for JSF page
			addErrorMessage(exc);
		} 
		
	
	}
	

	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}

