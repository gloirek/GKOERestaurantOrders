package restaurant;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;

import GUI.RestaurantGUI;

public class RestaurantManager {
	

	private OrderTreeMap orderCollection;
	private MenuList menuList;
	private RestaurantGUI gui;
	/**
	 * constructor creates the list of orders, menu and GUI
	 */
	public RestaurantManager () {
		orderCollection = new OrderTreeMap();
		menuList = new MenuList();
		gui = new RestaurantGUI(orderCollection);
	}
	
	/**
	 * method to show the GUI
	 */
	public void diplayGui(){ gui.setVisible(true);}
	/**
	 * method to run the manager
	 */
	public void run() {
		//fill the list with some orders
		orderCollection.readFile("orders.csv");
		orderCollection.printOrders();
		menuList.readFile("menu.csv");
		String report = menuList.getAllMenu();
		menuList.writeToFile("menuOut.txt", report);
		
		System.out.println(report);
		try {
			//test for wrong table number
			String bill = orderCollection.printBillByTable(1);
			System.out.print(bill);
		} catch (NoMatchingTableException e) {
			System.out.println(e.getMessage());
		
		}
		
		try {
			orderCollection.printSummary();
		} catch (NoMatchingTableException e) {
			System.out.println(e.getMessage());
		}
		
		//orders.searchOrders();
		//calculateBill(1);
		
	public double calculateCost(int table_id){
		double table_total = 0;
		OrderTreeMap orders = new OrderTreeMap();
				orders = orderCollection.getOrders();
		for (Map.Entry<Integer, Order> entry : orderCollection.entrySet()){
			if(table_id== entry.getValue().getTableNum()){
				int quant = entry.getValue().getQuantity();
				String dish = entry.getValue().getDish();
				menuList.readFile("menu.csv");
				double price = menuList.findDish(dish).getPrice();
				double cost = quant*price;
				table_total+=cost;
			}
		}
		return table_total;
	}
	}


		
		
	
}	
