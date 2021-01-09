package BudgetTracker2Package;

/**
 *
 * @author Ornello
 */
import java.awt.*;
import javax.swing.*;

public class BTMain
{
    public static void main(String[] args)
    {
        JFrame j = new JFrame();
        BudgetTrackerUI lj = new BudgetTrackerUI(300);
        
        j.setLayout(new FlowLayout());
        j.add(lj);
        j.pack();
        
        j.setVisible(true);
    }
}
