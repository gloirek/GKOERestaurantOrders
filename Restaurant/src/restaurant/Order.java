package restaurant;

/**
 * @author gk, Om
 *
 */
public class Order{
	private int orderNum;
	private int tableNum;
	private int quantity;
	private String dish;
	private String category;
	
	public Order(int orderNum,int tableNum,String dish, String category, int quantity){
		this.orderNum = orderNum;
		this.tableNum = tableNum;
		this.quantity = quantity;
		this.dish = dish;//to be removed 
		this.category = category;
	}
	
	public int getOrderNum(){ return orderNum;	}
	public int getTableNum(){ return tableNum;	}
	public int getQuantity(){ return quantity;	}
	public String getDish(){ return dish;	}//to be removed once the menu is up
	public String getCategory(){ return category;	}
	public void setOrderID(int orderID){ orderNum = orderID;	}
	public void setTableNum(int tableID){ tableNum = tableID;	}
	public void setQuantity(int quantity){ this.quantity = quantity;	}
	public void setDish(String dish){ this.dish = dish;	}//to be removed once the menu is up
	public void setCategory(String category){ this.category = category;	}
	
	
}