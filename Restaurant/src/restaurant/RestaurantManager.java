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
		String report = menuList.getAllMenuItems();
		menuList.writeToFile("menuOut.txt", report);

		System.out.println(report);

		//test the methods
		try {
			//printSummary();
			String bill = printBillByTable(1);
			System.out.println(bill);
			double cost = calculateBill(1);
			System.out.println(cost);
			boolean found = orderCollection.findTable(1);
			System.out.println(found);
		} catch (NoMatchingTableException e) {
			System.out.println(e.getMessage());
		}
		Thread first = new Thread((new Runnable(){

			
			public void run() {
				for (Map.Entry<Integer, Order> entry : orderCollection.getOrders().entrySet()){
				System.out.println(entry.getValue().getDish());
				}
			}
			
		}));
		Thread second = new Thread((new Runnable(){

			
			public void run() {
				for (Map.Entry<Integer, Order> entry : orderCollection.getOrders().entrySet()){
					System.out.println(entry.getValue().getCategory());
					System.out.println(entry.getValue().getTableNum());
					}
				
			}
			
		}));
		second.start();
		first.start();
		

	}
	/**
	 * calculate the total bill of a table
	 * @param table_id
	 * @return table_total
	 */
	public double calculateBill(int table_id){
		double table_total = 0;			
		for (Map.Entry<Integer, Order> entry : orderCollection.getOrders().entrySet()){
			if(table_id== entry.getValue().getTableNum()){
				int quant = entry.getValue().getQuantity();
				String dish = entry.getValue().getDish();
				menuList.readFile("menu.csv");
				double price = menuList.findMenuItem(dish).getPrice();
				double cost = quant*price;
				table_total+=cost;
			}
		}
		return table_total;
	}

	/**calculates the cost for an order
	 * @param dish
	 * @param o
	 * @return cost
	 */
	public double calculateCost(String dish,Order o){
		Menu menuItem = menuList.findMenuItem(dish);
		double price = menuItem.getPrice();
		int quantity = o.getQuantity();
		double cost = quantity*price;
		return cost;
	}
	/**
	 * method to print bill by table number
	 * @param table
	 * @throws NoMatchingTableException
	 */
	public String printBillByTable(int table)throws NoMatchingTableException{
		String report = "TABLE #" + table +"\n";
		report += "------------\n";
		String bill = "";
		int countMeals = 0;
		boolean found = orderCollection.findTable(table);
		//if(table >=1 && table <=10){
		if (found==true){
			for (Map.Entry<Integer, Order> entry : orderCollection.getOrders().entrySet()){
				try{

					if(table == entry.getValue().getTableNum()){
						String dish = entry.getValue().getDish();
						double cost = calculateCost(dish,entry.getValue());
						countMeals++;//counts the number of orders on the table
						bill += String.format("%-30s", entry.getValue().getDish());
						bill += String.format("%15s", entry.getValue().getQuantity());
						bill += "*" + String.format("%-15.2f", menuList.findMenuItem(dish).getPrice());
						bill += String.format("%-15.2f", cost);
						bill += "\n";

					}

				}catch (NullPointerException npe) {
					String error = "Sorry there is no table or this order entry is " 
							+ npe.getMessage();
					System.out.println(error);
				}

			}
			double total = calculateBill(table);
			double discount = orderCollection.calculateDiscount(total,countMeals);
			double discountedTotal = total - discount;
			bill += "Total:   "+ String.format("%-50.2f", total)+ "\n";
			bill += "Discount:   "+ String.format("%-55.2f", discount)+ "\n";
			bill += "Discounted Total:   "+ String.format("%-60.2f", discountedTotal);
			bill +="\n\n";

		}
		return (report + bill);

	}

	public void printSummary() throws NoMatchingTableException{
		System.out.println("SUMMARY FOR TODAY\n=================================\n");
		for (Map.Entry<Integer, Order> entry : orderCollection.getOrders().entrySet()){
			int tableNumber = entry.getValue().getTableNum();
			String summary = printBillByTable(tableNumber);
			System.out.print(summary);

		}
	}
}




