/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    private JTextField a;
    private JTextField b;
    private JTextField delta;
    int size1;
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
                    FileWriter writer = new FileWriter("C:\\Users\\Ornello\\Documents\\BudgetTracker2\\MonthlyBudget.txt");
                    FileWriter expensewriter = new FileWriter("C:\\Users\\Ornello\\Documents\\BudgetTracker2\\Expensesfile.txt");
                    FileWriter expensedetaillist = new FileWriter("C:\\Users\\Ornello\\Documents\\BudgetTracker2\\LatestPurchases.txt");
                    FileWriter PurchaseHistoryWriter = new FileWriter("C:\\Users\\Ornello\\Documents\\BudgetTracker2\\PurchaseHistory.txt");
                    
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
        a = new JTextField( 2);
        b = new JTextField(3);
        delta = new JTextField( 10);
        BufferedReader myReader = null;
        BufferedReader expensefile = null;
        BufferedReader purchasetype = null;
        
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(l);p.add(a);
        p.add(l1);p.add(b);
        p.add(l2);p.add(delta);
        p.add(hyperlinkPurchaseHist);
        p.add(hyperlinkclearfiles);
        
        size1 = size;
        this.add(p);
        
        try{
            expensefile = new BufferedReader(new FileReader("C:\\Users\\Ornello\\Documents\\BudgetTracker2\\Expensesfile.txt"));
            String tempexp = null;
            while((tempexp = expensefile.readLine()) != null){
                PurchasePrices.add(Double.parseDouble(tempexp));
                Expensetotal = Double.parseDouble(tempexp) + Expensetotal;
                }
            myReader = new BufferedReader(new FileReader("C:\\Users\\Ornello\\Documents\\BudgetTracker2\\MonthlyBudget.txt"));
            String temp = null;
            
            while((temp = myReader.readLine()) != null){
                
                MonthlyBudget = Double.parseDouble(temp);    
            }
            purchasetype = new BufferedReader(new FileReader("C:\\Users\\Ornello\\Documents\\BudgetTracker2\\LatestPurchases.txt"));
            String temppurchase = null;
            while((temppurchase = purchasetype.readLine())!= null){
                if(!temppurchase.equals("")){
                    LatestPurchases.add(temppurchase);}
            }
        }
        catch(IOException e){
            System.out.println("dang");}
        catch(NullPointerException e1){System.out.println("null ex");}
        catch(NumberFormatException e){System.out.println("num ex");}
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
        
        a.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
        {
            repaint();
        }
        });
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
        {
            repaint();           
        }
        });
        delta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
        {
            repaint();            
        }
        });
    }
    public File PurchaseHistoryFile(){
        File file = new File("C:\\Users\\Ornello\\Documents\\BudgetTracker2\\PurchaseHistory.txt");
            if(LatestPurchases.size() != 0 && PurchasePrices.size() != 0){
            try{
                FileWriter PurchaseHistoryWriter = new FileWriter("C:\\Users\\Ornello\\Documents\\BudgetTracker2\\PurchaseHistory.txt");
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
            NewMonthlyBudget = Double.parseDouble(a.getText());
            //System.out.println("goo" + a.getText());
        }
        catch(NumberFormatException e){Thread.currentThread().interrupt();System.out.println("herem");};
        try{
            Newexpense = Double.parseDouble(b.getText());
            PurchasePrices.add(Newexpense);
        }
        catch(NumberFormatException e){Thread.currentThread().interrupt();System.out.println("here");};
        try{
            latestpurchase = delta.getText();
           
            if(delta.getText().length() == 0){
                throw new IllegalArgumentException("you got me");
            }
            LatestPurchases.add(latestpurchase);
        }
        catch(IllegalArgumentException e){Thread.currentThread().interrupt();System.out.println("herep");};
        
        try{
            FileWriter writer = new FileWriter("C:\\Users\\Ornello\\Documents\\BudgetTracker2\\MonthlyBudget.txt", true);
            FileWriter expensewriter = new FileWriter("C:\\Users\\Ornello\\Documents\\BudgetTracker2\\Expensesfile.txt", true);
            FileWriter expensedetaillist = new FileWriter("C:\\Users\\Ornello\\Documents\\BudgetTracker2\\LatestPurchases.txt", true);
            if(Newexpense != 0){
                expensewriter.write(Double.toString(Newexpense)+"\n");
                Expensetotal = Expensetotal + Newexpense;               
            }
            if(NewMonthlyBudget != 0){
                writer.write(Double.toString(NewMonthlyBudget)+"\n");
                MonthlyBudget = NewMonthlyBudget;
                //This one can be overwritten but the latest purchases need to not overwrite eachother
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
        a.setText("");
        b.setText("");
        delta.setText("");
    }
    
}
