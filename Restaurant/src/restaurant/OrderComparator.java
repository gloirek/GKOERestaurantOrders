package restaurant;

//defines an ordering on Staff objects on the name

import java.util.Comparator;
public class OrderComparator 
               implements Comparator<Order>
{
	public int compare(Order o1, Order o2) {
		return o1.getDish().compareTo(o2.getDish());
	}
}