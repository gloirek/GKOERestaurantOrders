package restaurant;

/**
 * @author gk, Om
 *
 */

public class Menu {
	private String dishName;
	private String dishType;
	private double price;
	private double time;//cooking time for a meal
	
	public Menu ( String dn, String dt, double p, double t ){
		dishName = dn;
		dishType = dt;
		price = p;
		time = t;
	}
	

	public String getDishName () { return dishName;}
	public String getDishType () { return dishType;}
	public double getPrice() { return price;}
	public double getCookTime() { return time;}
	
	public void setDishName(String dishName){this.dishName = dishName;}
	public void setDishType(String dishType){this.dishType =dishType;}
	public void setPrice( double price){this.price = price;}
	public void setCookTime (double time) { this.time = time;}

}
