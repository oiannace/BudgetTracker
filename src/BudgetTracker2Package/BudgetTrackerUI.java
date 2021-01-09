package BudgetTracker2Package;
/**
 *
 * @author Ornello
 */
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;

public class BudgetTrackerUI extends JPanel
{
    private JTextField MB;
    private JTextField Newexp;
    private JTextField ExpDesc;
    double Expensetotal = 0;
    double MonthlyBudget = 0;
    ArrayList<String> LatestPurchases = new ArrayList<String>();
    ArrayList<Double> PurchasePrices = new ArrayList<Double>();
    
    public BudgetTrackerUI(int size)
    {
        this.setPreferredSize(new Dimension(size, size));
        
        JLabel hyperlinkPurchaseHist = new JLabel("Complete Purchase History");
        hyperlinkPurchaseHist.setForeground(Color.BLUE.darker());
        hyperlinkPurchaseHist.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        hyperlinkPurchaseHist.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                try{
                    File file = PurchaseHistoryFile();         
                    Desktop desktop = Desktop.getDesktop();                   
                    desktop.open(file);
                    }
                catch(IOException ehyp){ehyp.printStackTrace();}
                }
        });
        
        JLabel hyperlinkclearfiles = new JLabel("Clear Files");
        hyperlinkclearfiles.setForeground(Color.BLUE.darker());
        hyperlinkclearfiles.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        hyperlinkclearfiles.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                try{
                    FileWriter writer = new FileWriter("C:\\Users\\Ornello\\Documents\\BudgetTracker2\\MonthlyBudget.txt"); //Replace with user file path
                    FileWriter expensewriter = new FileWriter("C:\\Users\\Ornello\\Documents\\BudgetTracker2\\Expensesfile.txt"); //Replace with user file path
                    FileWriter expensedetaillist = new FileWriter("C:\\Users\\Ornello\\Documents\\BudgetTracker2\\LatestPurchases.txt"); //Replace with user file path
                    FileWriter PurchaseHistoryWriter = new FileWriter("C:\\Users\\Ornello\\Documents\\BudgetTracker2\\PurchaseHistory.txt"); //Replace with user file path
                    
                    writer.write("");
                    expensewriter.write("");
                    expensedetaillist.write("");
                    PurchaseHistoryWriter.write("");
                    
                    PurchasePrices.clear();
                    LatestPurchases.clear();
                    Expensetotal = 0;
                    MonthlyBudget = 0;
                    
                    expensewriter.close();
                    writer.close();
                    expensedetaillist.close();
                    PurchaseHistoryWriter.close();
                    
                    repaint();
                }
                catch(IOException eclear){
                    eclear.printStackTrace();
                }; 
            }   
        });
        
        JLabel l = new JLabel("Change Monthly Budget:");
        JLabel l1 = new JLabel("Enter Expense:");
        JLabel l2 = new JLabel("Purchase Descrption:");
        MB = new JTextField( 2);
        Newexp = new JTextField(3);
        ExpDesc = new JTextField( 10);
        BufferedReader myReader = null;
        BufferedReader expensefile = null;
        BufferedReader purchasetype = null;
        
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(l);p.add(MB);
        p.add(l1);p.add(Newexp);
        p.add(l2);p.add(ExpDesc);
        p.add(hyperlinkPurchaseHist);
        p.add(hyperlinkclearfiles);
        
        this.add(p);
        
        try{
            expensefile = new BufferedReader(new FileReader("C:\\Users\\Ornello\\Documents\\BudgetTracker2\\Expensesfile.txt")); //Replace with user file path
            String tempexp = null;
            while((tempexp = expensefile.readLine()) != null){
                PurchasePrices.add(Double.parseDouble(tempexp));
                Expensetotal = Double.parseDouble(tempexp) + Expensetotal;
                }
            myReader = new BufferedReader(new FileReader("C:\\Users\\Ornello\\Documents\\BudgetTracker2\\MonthlyBudget.txt")); //Replace with user file path
            String temp = null;
            
            while((temp = myReader.readLine()) != null){
                
                MonthlyBudget = Double.parseDouble(temp);    
            }
            purchasetype = new BufferedReader(new FileReader("C:\\Users\\Ornello\\Documents\\BudgetTracker2\\LatestPurchases.txt")); //Replace with user file path
            String temppurchase = null;
            while((temppurchase = purchasetype.readLine())!= null){
                if(!temppurchase.equals("")){
                    LatestPurchases.add(temppurchase);}
            }
        }
        catch(IOException e){System.out.println("IO ex");}
        catch(NullPointerException e1){System.out.println("null ex");}
        catch(NumberFormatException e){System.out.println("num format ex");}
        finally{
            try{
                if(expensefile != null){
                    expensefile.close();}
                if(myReader != null){
                    myReader.close();}
                if(purchasetype != null){
                    purchasetype.close();}
            }
            catch(IOException ex){
                    ex.printStackTrace();                   
            }};
        
        MB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
        {
            repaint();
        }
        });
        Newexp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
        {
            repaint();           
        }
        });
        ExpDesc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
        {
            repaint();            
        }
        });
    }
    
    public File PurchaseHistoryFile(){
        File file = new File("C:\\Users\\Ornello\\Documents\\BudgetTracker2\\PurchaseHistory.txt"); //Replace with user file path
            if(LatestPurchases.size() != 0 && PurchasePrices.size() != 0){
            try{
                FileWriter PurchaseHistoryWriter = new FileWriter("C:\\Users\\Ornello\\Documents\\BudgetTracker2\\PurchaseHistory.txt"); //Replace with user file path
                for(int i = 0; i<LatestPurchases.size(); i++){
                    PurchaseHistoryWriter.write(LatestPurchases.get(i) + " $" + Double.toString(PurchasePrices.get(i)) + "\n");
                    
                }
                PurchaseHistoryWriter.close();
            }
            catch(IOException ePH){ePH.printStackTrace();}
            }
        return file;
    }
    
    @Override@SuppressWarnings("empty-statement")
    public void paintComponent(Graphics g)
    {
        double NewMonthlyBudget = 0, Newexpense = 0;
        String latestpurchase = null;
        try{
            NewMonthlyBudget = Double.parseDouble(MB.getText());
        }
        catch(NumberFormatException e){Thread.currentThread().interrupt();};
        try{
            Newexpense = Double.parseDouble(Newexp.getText());
            PurchasePrices.add(Newexpense);
        }
        catch(NumberFormatException e){Thread.currentThread().interrupt();};
        try{
            latestpurchase = ExpDesc.getText();
           
            if(ExpDesc.getText().length() == 0){
                throw new IllegalArgumentException();
            }
            LatestPurchases.add(latestpurchase);
        }
        catch(IllegalArgumentException e){Thread.currentThread().interrupt();};
        
        try{
            FileWriter writer = new FileWriter("C:\\Users\\Ornello\\Documents\\BudgetTracker2\\MonthlyBudget.txt", true); //Replace with user file path
            FileWriter expensewriter = new FileWriter("C:\\Users\\Ornello\\Documents\\BudgetTracker2\\Expensesfile.txt", true); //Replace with user file path
            FileWriter expensedetaillist = new FileWriter("C:\\Users\\Ornello\\Documents\\BudgetTracker2\\LatestPurchases.txt", true); //Replace with user file path
            if(Newexpense != 0){
                expensewriter.write(Double.toString(Newexpense)+"\n");
                Expensetotal = Expensetotal + Newexpense;               
            }
            if(NewMonthlyBudget != 0){
                writer.write(Double.toString(NewMonthlyBudget)+"\n");
                MonthlyBudget = NewMonthlyBudget;
            }
            if(latestpurchase  != null){
                expensedetaillist.write(latestpurchase + "\n");
            }
            expensewriter.close();
            writer.close();
            expensedetaillist.close();
        }
        catch(IOException e1){
                e1.printStackTrace();
                };
  
        double BudgetRemaining = MonthlyBudget - Expensetotal;
        
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(3.0f)); 
        g2.setPaint(Color.BLACK);
        
        g2.drawString("Monthly Budget:" , 20, 180);
        g2.drawString("$" + Double.toString(MonthlyBudget), 180, 180);
        g2.drawString("Monthly Budget Remaining:" , 20, 190 );
        g2.drawString("$" + Double.toString(BudgetRemaining), 180, 190);
        g2.drawString("Latest Purchases:", 20, 220);
        
        int count = 0;
        for(int i = LatestPurchases.size() -1; i>(LatestPurchases.size()-4); i--){
            try{
            g2.drawString(LatestPurchases.get(i),20, 230 +(10*(count+1)));
            g2.drawString("$" + Double.toString(PurchasePrices.get(i)), 180, 230 +(10*(count+1)));
            count++;
            }
            catch(IndexOutOfBoundsException e1){}
            catch(NullPointerException e2){};
        }
        MB.setText("");
        Newexp.setText("");
        ExpDesc.setText("");
    }
    
}
