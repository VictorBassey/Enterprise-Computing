package enterprisehwgui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/* Name: Victor Bassey
 Course: CNT 4714 – Fall 2016
 Assignment title: Program 1 – Event-driven Programming
 Date: Sunday September 4, 2016
*/

public class EnterpriseHWGUI extends JFrame {
    int totalItemsCompleted = 0;
    GridLayout topGrid = new GridLayout(5,2,5,5);
    JButton btn1 = new JButton("Process Item #1");
    JButton btn2 = new JButton("Confirm Item #1");
    JButton btn3 = new JButton("View Order");
    JButton btn4 = new JButton("Finish Order");
    JButton btn5 = new JButton("New Order");
    JButton btn6 = new JButton("Exit");
    JTextField text1 = new JTextField();
    JTextField text2 = new JTextField();
    JTextField text3 = new JTextField();
    JTextField text4 = new JTextField();
    JTextField text5 = new JTextField();
    JLabel label1 = new JLabel("Enter number of items in this order:", SwingConstants.RIGHT);
    JLabel label2 = new JLabel("Enter Book ID for Item #" + (totalItemsCompleted + 1) + ":", SwingConstants.RIGHT);
    JLabel label3 = new JLabel("Enter quantity for Item #" + (totalItemsCompleted + 1) + ":", SwingConstants.RIGHT);
    JLabel label4 = new JLabel("Item #" + (totalItemsCompleted + 1) + " info:", SwingConstants.RIGHT);
    JLabel label5 = new JLabel("Order subtotal for " + totalItemsCompleted + " item(s):", SwingConstants.RIGHT);
    int pctDiscount;
    Float subTotalPrice = 0.0f;
    int totalItemsReq = 0; 
    Boolean bTest = false; // Used to check if the bookID was found.
    
    // List to hold the value in text4 for each item.
    List<String> vList = new ArrayList<String>();

    // List for each book ordered.
    List<Book> bookList = new ArrayList<Book>();
    
    public EnterpriseHWGUI(String name) {
        super(name);
        setResizable(true);
    }
    
    private void addComponents(final Container pane) {
        final JPanel tBoxPanel = new JPanel();
        tBoxPanel.setLayout(topGrid);
        JPanel btnControl = new JPanel();
        btnControl.setLayout(new GridLayout (1,0,5,0));
        
        JPanel separator = new JPanel(new FlowLayout());
        
        tBoxPanel.add(label1);
        tBoxPanel.add(text1);
        
        tBoxPanel.add(label2);
        tBoxPanel.add(text2);
        
        tBoxPanel.add(label3);
        tBoxPanel.add(text3);
        
        tBoxPanel.add(label4);
        tBoxPanel.add(text4);
        
        tBoxPanel.add(label5);
        tBoxPanel.add(text5);
        
        // Process item button.
        btn1.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                // Check if all text boxes are filled
                if(!text1.getText().isEmpty() && !text2.getText().isEmpty() 
                        && !text3.getText().isEmpty())
                {
                    totalItemsReq = Integer.parseInt(text1.getText());
                    Long quantity = Long.parseLong(text3.getText());

                    text1.setEditable(false);

                    scanFile(text2.getText(), quantity);
                    if(bTest == true)
                    {
                    label4.setText("Item #" + (totalItemsCompleted + 1) + " info:");   
                    Float sTotal = (bookList.get(totalItemsCompleted)).total(Float.parseFloat(pctDiscount((bookList.get(totalItemsCompleted)).quantity)));
                    sTotal = round(sTotal,2);
                    
                    subTotalPrice += sTotal;
                    subTotalPrice = round(subTotalPrice,2);
                    
                    // Set the text for the fourth text box.
                    text4.setText((bookList.get(totalItemsCompleted)).bookID + " " + (bookList.get(totalItemsCompleted)).title + " " + 
                            "$" + (bookList.get(totalItemsCompleted)).price + " " + (bookList.get(totalItemsCompleted)).quantity + " " +
                            pctDiscount((bookList.get(totalItemsCompleted)).quantity) + "%" + " " + "$" + String.format("%.2f",sTotal));
                    

                    
                    btn1.setEnabled(false);
                    btn2.setEnabled(true);
  
                    bTest = false;
                    }
                    else
                    {
                    JOptionPane.showMessageDialog(pane, "Book ID " + text2.getText() + " not in file." );
                    bTest = false; 
                    }

                }
            }
        });
        
        // Confirm item button.
        btn2.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) 
            {
                totalItemsCompleted++;
                btn3.setEnabled(true);
                
                if(totalItemsReq == 1)
                {
                    
                JOptionPane.showMessageDialog(pane, "Item #" + totalItemsCompleted +
                        " accepted.");
                vList.add(text4.getText());
                text5.setText("$" + String.format("%.2f",subTotalPrice)); 
                    
 
                label5.setText("Order subtotal for " + totalItemsCompleted + " item(s):");
                
                text2.setText("");
                text3.setText("");
                label2.setText("");
                label3.setText("");
                btn1.setText("Process Item");
                btn2.setText("Confirm Item");
                btn1.setEnabled(false);
                btn2.setEnabled(false);
                btn4.setEnabled(true);
                text2.setEditable(false);
                text3.setEditable(false);
                text4.setEditable(false);
                text5.setEditable(false);
                
                }
                
                else if(totalItemsReq != totalItemsCompleted)
                {
                JOptionPane.showMessageDialog(pane, "Item #" + totalItemsCompleted +
                        " accepted.");
                vList.add(text4.getText());
                text5.setText("$" + String.format("%.2f",subTotalPrice));

                label5.setText("Order subtotal for " + totalItemsCompleted + " item(s):");
                
                btn1.setText("Process Item #" + (totalItemsCompleted + 1));
                btn2.setText("Confirm Item #" + (totalItemsCompleted + 1));
                
                text2.setText("");
                text3.setText("");
                
                label2.setText("Enter Book ID for Item #" + (totalItemsCompleted + 1) + ":");
                label3.setText("Enter quantity for Item #" + (totalItemsCompleted + 1) + ":");
                
                btn2.setEnabled(false);
                btn1.setEnabled(true);                    
                }
                else
                {
                    JOptionPane.showMessageDialog(pane, "Item #" + totalItemsCompleted +
                            " accepted.");
                    vList.add(text4.getText());
                    text5.setText("$" + String.format("%.2f",subTotalPrice));
                    label5.setText("Order subtotal for " + totalItemsCompleted + " item(s):");
                    text2.setText("");
                    text3.setText("");
                    label2.setText("");
                    label3.setText("");
                    btn1.setText("Process Item");
                    btn2.setText("Confirm Item");
                    btn1.setEnabled(false);
                    btn2.setEnabled(false);
                    btn4.setEnabled(true);
                    text2.setEditable(false);
                    text3.setEditable(false);
                    text4.setEditable(false);
                    text5.setEditable(false);
                }
                
            }
        });
        
        // View order button.
        btn3.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                String vListPrint = new String();
                int vListCounter = 1;
                for (String s: vList)
                {
                    vListPrint += vListCounter + "." + " " + s + "\n";
                    vListCounter++;
                }
                JOptionPane.showMessageDialog(pane, vListPrint);
            }
        });
        
        // Finish order button.
        btn4.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                String timeStamp = new SimpleDateFormat("MM/dd/yy hh:mm:ss a zzz").format(Calendar.getInstance().getTime());
                String timeID = new SimpleDateFormat("yyMMddHHmmss").format(Calendar.getInstance().getTime());
                String output;
                String vListPrint = new String();
                int vListCounter = 1;
                for (String s2: vList)
                {
                    vListPrint += vListCounter + "." + " " + s2 + "\n";
                    vListCounter++;
                }
                
                // Invoice formatting.
                output = "Date: " + timeStamp + "\n" + "\n" + "Number of line items: "
                        + totalItemsReq + "\n" + "\n" 
                        + "Item# / ID / Title / Price / Qty / Disc % / Subtotal:"
                        + "\n" + "\n" + vListPrint + "\n" + "\n" + "\n"
                        + "Order subtotal: $"  + String.format("%.2f", subTotalPrice)
                        + "\n" + "\n" + "Tax rate: 6%" 
                        + "\n" + "\n" + "Tax amount: $" + String.format("%.2f",calculateTax(subTotalPrice))
                        + "\n" + "\n" + "Order total: $" +  String.format("%.2f", (subTotalPrice + calculateTax(subTotalPrice)))
                        + "\n" + "\n" + "Thanks for shopping at Victor's World of Music!";

                JOptionPane.showMessageDialog(pane, output);
                
                // Creates the transactions.txt if it doesn't exist, and appends to it if it does.
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream("transactions.txt", true), StandardCharsets.UTF_8))) 
                {
                    // Iterate through the vList and print each item to the transaction log.
                    for (String s3: vList)
                    {
                    writer.write(timeID + ", " + s3 + ", " + timeStamp);
                    writer.newLine();
                    }
                } 
                catch (IOException ex) 
                {
                    ex.printStackTrace();
                }
                
                // Used to clear out the previous order since it is complete.
                refresh();
            }
        });
        
        // New order button.
        btn5.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) 
            {
                // Let the user know that the order was cleared.
                JOptionPane.showMessageDialog(pane, "The current order has been cleared.");
                refresh();
            }
        });
        
        // Exit button.
        btn6.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                System.exit(0);
            }
        });
        
        btnControl.add(btn1);
        btnControl.add(btn2);
        btnControl.add(btn3);
        btnControl.add(btn4);
        btnControl.add(btn5);
        btnControl.add(btn6);
        
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);
        
        text4.setEditable(false);
        text5.setEditable(false);
        
        // Acts as a spacer between the north and south parts of the border layout
        separator.add(Box.createHorizontalStrut(10));
        
        pane.add(tBoxPanel, BorderLayout.NORTH);
        pane.add(separator, BorderLayout.CENTER);
        pane.add(btnControl, BorderLayout.SOUTH);
        
    }

    
    private static void createAndShowGUI() 
    {
        // Create frame and give it a title.
        EnterpriseHWGUI frame = new EnterpriseHWGUI("Ye Olde Book Shoppe");
        
        // Gives the user the ability to close the application with the x button.
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Set the GUI to launch in the center of the screen
        frame.setLocationRelativeTo(null);
        frame.addComponents(frame.getContentPane());
        
        // Sizes the frame and sets it visible.
        frame.pack();
        frame.setVisible(true);
        
    }
    
    private void scanFile(String ID, Long quantity)
    {
        try (Scanner s = new Scanner (new File("inventory.txt")))
        {
            String bookID;
            
            while (s.hasNextLine())
            {
                StringTokenizer st = new StringTokenizer(s.nextLine(), ",");
                bookID = st.nextToken();
                if(ID.equals(bookID))
                {
                    Book b = new Book();
                    b.bookID = bookID;
                    b.title = st.nextToken();
                    b.price = Float.parseFloat(st.nextToken());
                    b.quantity = quantity;
                    
                    bookList.add(b);
                    bTest = true;
                    break;
                }
            }
                   s.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
 
    }
    
    public static float round(float d, int decimalPlace) {
         return BigDecimal.valueOf(d).setScale(decimalPlace,BigDecimal.ROUND_HALF_UP).floatValue();
    }
    
    // Set things to their initial states.
    public void refresh()
    {
        vList.clear();
        bookList.clear();
        btn1.setText("Process Item #1");
        btn2.setText("Confirm Item #1");
        totalItemsReq = 0;
        totalItemsCompleted = 0;
        bTest = false;
        pctDiscount = 0;
        subTotalPrice = 0.0f;
        label1.setText("Enter number of items in this order:");
        label2.setText("Enter Book ID for Item #" + (totalItemsCompleted + 1) + ":");
        label3.setText("Enter quantity for Item #" + (totalItemsCompleted + 1) + ":");
        label4.setText("Item #" + (totalItemsCompleted + 1) + " info:");
        label5.setText("Order subtotal for " + totalItemsCompleted + " item(s):");
        text1.setText("");
        text2.setText("");
        text3.setText("");
        text4.setText("");
        text5.setText("");
        text1.setEditable(true);
        text2.setEditable(true);
        text3.setEditable(true);
        text4.setEditable(false);
        text5.setEditable(false);
        btn1.setEnabled(true);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);
    }
    
    public String pctDiscount(Long quantity)
    {
        String discount;
        if(quantity >= 1 && quantity <= 4)
        {
            pctDiscount = 0;
        }
        else if (quantity > 4 && quantity <= 9)
        {
            pctDiscount = 10;
        }
        else if (quantity > 9 && quantity <= 14)
        {
            pctDiscount = 15;
        }
        else if (quantity >= 15)
        {
            pctDiscount = 20;
        }
        
        discount = Integer.toString(pctDiscount);
        return discount;
    }
    
    // Book class to manage orders.
    public static class Book {
        
       public String bookID, title;
        Float price;
        Long quantity;
        
        public float total(float discount)
                {
                    discount = discount / 100;
                    return (price * (float)quantity) - 
                            ((price * (float)quantity) * discount);
                }
    }
    
    public float calculateTax(float sTotal)
    {
        return (sTotal * 1.06f) - sTotal;
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Instantiate GUI
        createAndShowGUI();
    }
    
}
