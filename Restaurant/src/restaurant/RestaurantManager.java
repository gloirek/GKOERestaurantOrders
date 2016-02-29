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
			String bill = printBillByTable(1);
			System.out.print(bill);
		} catch (NoMatchingTableException e) {
			System.out.println(e.getMessage());
		
		}
		
		try {
			printSummary();
		} catch (NoMatchingTableException e) {
			System.out.println(e.getMessage());
		}
		
		//orders.searchOrders();
		//calculateBill(1);
	}
		
	double calculateCost(int table_id){
		double table_total = 0;			
		for (Map.Entry<Integer, Order> entry : orderCollection.getOrders().entrySet()){
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
	

/**
 * method to print bill by table number
 * @param tabl
 * @throws NoMatchingTableException
 */
public String printBillByTable(int table)throws NoMatchingTableException{
	String report = "TABLE #" + table +"\n";
	report += "------------\n";
	String bill = "";
	int countMeals = 0;
	double total = 0;
	double discount = 0;
	double discountedTotal = 0;
	
	for (Map.Entry<Integer, Order> entry : orderCollection.getOrders().entrySet()){
		try{
			
			if(table == entry.getValue().getTableNum()){
				int quant = entry.getValue().getQuantity();
				String dish = entry.getValue().getDish();
				menuList.readFile("menu.csv");
				double price = menuList.findDish(dish).getPrice();
				double cost = quant*price;
				total+=cost;
				//total = calculateCost(table);
				countMeals++;

				bill += String.format("%-30s", entry.getValue().getDish());
				bill += String.format("%15s", entry.getValue().getQuantity());
				bill += "*" + String.format("%-15.2f", menuList.findDish(dish).getPrice());
				bill += String.format("%-15.2f", cost);
				bill += "\n";

			}
			//else if(table != entry.getValue().getTableNum()){
			//	throw new NoMatchingTableException(table);
				//bill = "Table No." +table +" is not found\n";
			//}
			
		}catch (NullPointerException npe) {
			String error = "Sorry there is no table or this order entry is " 
					+ npe.getMessage();
			System.out.println(error);
		}

	}
	discount = orderCollection.calculateDiscount(total,countMeals);
	discountedTotal = total - discount;
	bill += "Total:   "+ String.format("%-50.2f", total)+ "\n";
	bill += "Discount:   "+ String.format("%-55.2f", discount)+ "\n";
	bill += "Discounted Total:   "+ String.format("%-60.2f", discountedTotal);
	bill +="\n\n";


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
		
		
	
	
