package restaurant;

public class RestaurantManager {
	

	private OrderTreeMap orders;
	
	/**
	 * constructor creates the list of orders
	 */
	public RestaurantManager () {
		orders = new OrderTreeMap();
	}
	
	/**
	 * method outlines and tests all methods of AKCabinList
	 */
	public void run() {
		//fill the list with some orders
		orders.readFile("orders.csv");
		orders.printOrders();
	}

		
	
}	
