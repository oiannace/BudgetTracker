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
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.*;
import java.lang.Math;

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
