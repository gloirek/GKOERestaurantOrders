/**
 * @author Gloire
 *
 */
package restaurant;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;



public class OrderTreeMap {
	private MenuList menuList;
	private TreeMap<Integer,Order> orders;
	public OrderTreeMap(){
		orders = new TreeMap<Integer, Order>();
		menuList = new MenuList();
	}
	
	/**a getter method for the treemap of orders
	 * @return orders
	 */
	public TreeMap<Integer,Order> getOrders() {
		return orders;
	}
	/**
	 * 
	 * @param order - an object of the class Order to be added in the order collection
	 */
	public void addOrder(Order order){
		int id = order.getOrderNum();
		orders.put(id, order);
	}
	/**
	 * calculate the total bill of a table
	 * @param table_id
	 * @return table_total
	 */

/**
 * method to find an order 
 * @param o
 * @return
 */
	public boolean findTable(int table) throws NoMatchingTableException{
		boolean found = false;
		for (Map.Entry<Integer, Order> entry : orders.entrySet()){
			found = false;
			if(table == entry.getValue().getTableNum()){
					found = true;
					return found;
				}
		}
		return found;
	}
//public
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
		
		for (Map.Entry<Integer, Order> entry : orders.entrySet()){
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
		discount = calculateDiscount(total,countMeals);
		discountedTotal = total - discount;
		bill += "Total:   "+ String.format("%-50.2f", total)+ "\n";
		bill += "Discount:   "+ String.format("%-55.2f", discount)+ "\n";
		bill += "Discounted Total:   "+ String.format("%-60.2f", discountedTotal);
		bill +="\n\n";


		return (report + bill);
	}
	//a method to calculate the discount

	/**
	 * calculate a discount of 10% for bills over £25.00 and 
	 * 2% on each meal if there are more than 6 orders on a table
	 * @param amount
	 * @return discount
	 */
	public double calculateDiscount(double amount,int meals){
		double discount = 0;
		if(amount>25){
			discount = amount * 0.1;
		}
		//if there are more than 6 orders on a table
		if(meals > 6){
			discount = (amount/meals) * 0.02;
		}
		return discount;
	}

	public void printSummary() throws NoMatchingTableException{
		System.out.println("SUMMARY FOR TODAY\n=================================\n");
		for (Map.Entry<Integer, Order> entry : orders.entrySet()){
			int tableNumber = entry.getValue().getTableNum();
			String summary = printBillByTable(tableNumber);
			System.out.print(summary);
			
		}
	}






	/**
	 * @param key
	 * @return true if the order is found and false if there is no such order
	 */
	public boolean containsKey(Object key) {
		return orders.containsKey(key);
	}


	public void removeOrder(Object key){
		if (orders.containsKey(key) == true){
			orders.remove(key);}
		else{

		}
	}
	public void printOrders(){
		String report = "ORDER NO.   TABLE NO.  DISH                          CATEGORY        QUANTITY\n";
		for (Map.Entry<Integer, Order> entry : orders.entrySet()){
			report += String.format("%-15s",entry.getValue().getOrderNum());
			report += String.format("%-8s",entry.getValue().getTableNum());
			report += String.format("%-30s",entry.getValue().getDish());
			report += String.format("%-20s",entry.getValue().getCategory());
			report += String.format("%-9s",entry.getValue().getQuantity());
			report += "\n";

		}
		System.out.println("\n" + report + "= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =\n");

	}

	public void readFile(String orderFile){
		try {
			orderFile = "orders.csv";
			File f = new File(orderFile);
			Scanner scanner = new Scanner(f);
			while (scanner.hasNextLine()) {
				String inputLine = scanner.nextLine();
				if (inputLine.length() != 0){
					processLine(inputLine);

				}

			}
			scanner.close();
		}
		//if the file is not found, stop with system exit
		catch (FileNotFoundException fnf){
			System.out.println( orderFile + " not found ");
			System.exit(0);
		}		
	}

	private void processLine(String line) {
		try {
			String field [] = line.split(",");//get the comma separated fields in the csv file
			int orderID = Integer.parseInt(field[0]);
			int tableID = Integer.parseInt(field[1]);
			String dish = field[2];
			String category = field[3];
			int quantity = Integer.parseInt(field[4]);
			Order o = new Order(orderID,tableID,dish, category, quantity);
			orders.put(orderID, o);
			//printOrders();

			// System.out.println(o.getDish()+ o.getQuantity());

		}

		//for these two formatting errors, ignore lines in error and try and carry on

		//this catches trying to convert a String to an integer
		catch (NumberFormatException nfe) {
			String error = "Number conversion error in '" + line + "'  - " 
					+ nfe.getMessage();
			System.out.println(error);
		}
		//this catches missing items if only one or two items
		//other omissions will result in other errors
		catch (ArrayIndexOutOfBoundsException notEnough) {
			String error = "Not enough items in  : '" + line
					+ "' index position : " + notEnough.getMessage();
			System.out.println(error);
		} //finally {  this.close();
	}


	/**
	 * @param filename - takes the name of the output file to which the report will be printed
	 * @param report - takes the report(String) generated by the printing methods
	 */
	/**
	 * @param filename
	 * @param orderReport
	 */
	public  void writeToFile(String filename, String orderReport) {

		FileWriter fw;
		try {
			fw = new FileWriter(filename);
			fw.write(orderReport);
			fw.close();
		}
		//print a message and stop if file is not found
		catch (FileNotFoundException fnf){
			System.out.println(filename + " not found ");
			System.exit(0);
		}
		//stack trace here because we don't expect to come here
		catch (IOException ioe){
			ioe.printStackTrace();
			System.exit(1);
		}
	}



}




