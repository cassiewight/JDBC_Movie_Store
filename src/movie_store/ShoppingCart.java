package movie_store;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class ShoppingCart {
	
	@ManagedProperty(value="#{customer}")
    private Customer customer;
	private List<Movie> moviesInCart;
	
	public ShoppingCart() {
		
		moviesInCart = new ArrayList<Movie>();

	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Movie> getMoviesInCart() {
		return moviesInCart;
	}

	public void setMoviesInCart(List<Movie> moviesInCart) {
		this.moviesInCart = moviesInCart;
	}
	
	public void addToCart(Movie movie) {
		
		moviesInCart.add(movie);
	}
	
	public String removeFromCart(Movie movie) {
		
		moviesInCart.remove(movie);
		
		return "shopping_cart?faces-redirect=true";
	}
	
	
	

}
