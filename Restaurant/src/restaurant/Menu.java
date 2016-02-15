package restaurant;

/**
 * @author gk, Om
 *
 */
public class Menu {
	private String dishType;
	private String dishName;
	private double price;
	private  double time;//cooking time for a meal
	
	public Menu ( String dt, String dn, double p, double t ){
		dishType = dt;
		dishName = dn;
		price = p;
		time = t;
	}
	
	public String getDishType () { return dishType;}
	public String getDishName () { return dishName;}
	public double getPrice() { return price;}
	public double getCookTime() { return time;}
	
	public void setDishType(String dishType){this.dishType = dishType;}
	public void setDishName(String dishName){this.dishName =dishName;}
	public void setPrice( double price){this.price = price;}
	public void setCookTime (double time) { this.time = time;}
	

}
