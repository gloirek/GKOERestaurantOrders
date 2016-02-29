package GUI;
//import all the GUI classes
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import restaurant.OrderTreeMap;
import restaurant.MenuList;
import restaurant.RestaurantManager;
import restaurant.NoMatchingTableException;

/**
 * GUI for managing restaurant orders
 */
public class RestaurantGUI extends JFrame implements ActionListener
{
	// orders and menu data structures
	private OrderTreeMap orders;
	private MenuList menu;
	private RestaurantManager manager;

	//GUI components

	JTextField tableNum;
	JButton enterButton;
	JScrollPane scrollList;
	JButton closeButton;
	JTextArea displayBill;
	JLabel message;
	JButton clearButton;

	/**
	 * Create the frame with its panels.
	 * @param list	The staff list to be searched.
	 */
	public RestaurantGUI(OrderTreeMap tree)
	{

		this.orders = tree;

		//set up window title
		setTitle("GKOE Italiano");
		//ensure program ends when window closes
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		//the bottom of the gui containing the close button
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,3));
		message = new JLabel("");
		bottomPanel.add(message);
		closeButton = new JButton("Close"); 
		bottomPanel.add(closeButton);  
		JLabel blank = new JLabel("     ");
		bottomPanel.add(blank);
		//specify action when print button is pressed
		closeButton.addActionListener(this) ;



		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(2,1));
		southPanel.add(bottomPanel);


		//add south panel to the content pane
		Container contentPane = getContentPane();
		contentPane.add(southPanel, BorderLayout.SOUTH);

		//add north panel containing some buttons
		JPanel northPanel = new JPanel();
		northPanel.add(new JLabel("Enter a table number:"));   
		tableNum = new JTextField(2);
		northPanel.add(tableNum);   
		enterButton = new JButton("Enter");  
		northPanel.add(enterButton);   
		clearButton = new JButton("Clear");
		northPanel.add(clearButton);
		clearButton.addActionListener(this);
		//specify action when enter button is pressed
		enterButton.addActionListener(this);
		contentPane.add(northPanel, BorderLayout.NORTH);

		displayBill = new JTextArea(15,20);
		displayBill.setFont(new Font (Font.MONOSPACED, Font.PLAIN,14));
		displayBill.setEditable(false);
		scrollList = new JScrollPane(displayBill);
		contentPane.add(scrollList,BorderLayout.CENTER);

		//pack and set visible
		pack();
		setVisible(true);
	}
	/**
	 * this method will close the gui
	 */
	private void closeGUI() {
		//close the gui
		System.exit(0);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) 
	{ 
		//get the table ID and print the bill
		//if the table ID exists
		String bill = "";
		if (e.getSource() == enterButton) {
			int tableID = Integer.parseInt(tableNum.getText().trim());
			try {
				boolean found = orders.findTable(tableID);
				

			} catch (NoMatchingTableException e2) {
				JOptionPane.showMessageDialog(this,e2.getMessage()+"\n Enter a correct table number");
			}
			
				try {
					bill = manager.printBillByTable(tableID);
				} catch (NoMatchingTableException e1) {
					JOptionPane.showMessageDialog(this,e1.getMessage()+"\n Enter a correct table number");
					//e1.printStackTrace();
				}
				//this catches trying to convert a String to an integer
				catch (NumberFormatException nfe) {
					String error = "Sorry'" + tableID + "is not a valid table number" 
							+ nfe.getMessage();
					System.out.println(error);
				}
				try{
					if(tableID != 0) {
						displayBill.setText(bill);
					}
				}//this catches trying to convert a String to an integer
				catch (NumberFormatException nfe) {
					String error = "Sorry'" + tableID + "is not a valid table number" 
							+ nfe.getMessage();
					System.out.println(error);
				}
			}
			if (e.getSource() == closeButton){
				JOptionPane.showMessageDialog(this,"Do you want to close?");
				closeGUI();
			}
			if (e.getSource() == clearButton){
				JOptionPane.showMessageDialog(this,"Clear display?");
				displayBill.setText("");
			}
		}  

	}
