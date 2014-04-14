/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathfinding;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;

/**
 *
 * @author Joseph
 */
public class PathFinding {
    
    private static JFrame mainGui;
    private static currentButton selectedButton;
    private static final int width = 7, height = 5;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        mouseListenerForSquares ml = new mouseListenerForSquares();
        
        mainGui = new JFrame();
        mainGui.setSize(width*101+15,height*101 + 100);
        mainGui.setLayout(null);
        
        Container contentPane = mainGui.getContentPane();
        contentPane.removeAll();
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                square s = new square(j * width + i);
                s.setLocation(i * 101, j * 101);
                s.addMouseListener(ml);
                contentPane.add(s);
            }
        }
        
        JPanel options = new JPanel();
        
        options.setSize(width*101, 60);
        options.setLocation(0,height*101);
        options.setBackground(Color.white);
        
        optionButton selectBegin = new optionButton("Select Begin", currentButton.start);
        optionButton selectEnd = new optionButton("Select End", currentButton.end);
        optionButton selectWall = new optionButton("Select Walls", currentButton.wall);
        
        options.add(selectBegin);
        options.add(selectEnd);
        options.add(selectWall);
        
        for (int i = 0; i < 3; i++)
        {
            JButton b = (JButton)options.getComponent(i);
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    optionButtonPressed(e);
                }
            });
        }
        
        contentPane.add(options);
        contentPane.validate();
        contentPane.repaint();
        
        mainGui.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        mainGui.setVisible(true);
        
        selectedButton = currentButton.none;
    }
    
    private static void optionButtonPressed(ActionEvent e)
    {
        //Change enum based upon current action
        optionButton button = (optionButton)e.getSource();
        
        //Button is already selected
        //Toggle off
        //
        //Otherwise
        //Change the current button selected
        if (button.getTag() == selectedButton)
        {    
            selectedButton = currentButton.none;
            button.setBackground(null);
        }else
        {
            clearButtonBackColor();
            selectedButton = button.getTag();
            button.setBackground(Color.yellow);
        }
    }
    
    private static void clearButtonBackColor()
    {
        //Since the panel that contains the button is at the end of the component list
        //we can just access it directly
        JPanel buttonContainer = (JPanel)mainGui.getContentPane().getComponent(mainGui.getContentPane().getComponentCount() - 1);
        for (Component b : buttonContainer.getComponents())
        {
            //we then access all the option buttons and set their background to the defualt
            ((optionButton)b).setBackground(null);
        }
    }
    
    public static void squareClicked(MouseEvent e)
    {
        square s = (square)e.getSource();
        
        System.out.println("Square clicked");
        
        switch(selectedButton)
        {
            case start:
                clearSquaresOf(currentButton.start);
                s.setStart(true);
                break;
            case end:
                clearSquaresOf(currentButton.end);
                s.setEnd(true);
                break;
            case wall:
                if (!s.getEnd() || !s.getStart())
                    s.toggleWall();
                break;
        }
    }
    
    //clears the board of the start/end squares depending on which is passed in
    private static void clearSquaresOf(currentButton sel)
    {
        Container contentPane = mainGui.getContentPane();
        int id = getStartOrEnd(sel);
        if (id < 0) //there is no start or end
            return;
        
        if (sel == currentButton.start)
            ((square)contentPane.getComponent(id)).setStart(false);
        else
            ((square)contentPane.getComponent(id)).setEnd(false);
    }
    
    private static int getStartOrEnd(currentButton sel)
    {
        Container contentPane = mainGui.getContentPane();
        square s;
        int id = -1;
        //Do not include the last element, the options panel
        for (int i = 0; i < contentPane.getComponentCount() - 2; i++)
        {
            s = (square)contentPane.getComponent(i);
            switch (sel)
            {
                case start:
                    if (s.getStart())
                        id = i;
                    break;
                    
                case end:
                    if (s.getEnd())
                        id = i;
                    break;
            }
        }
        return id;
    }
    
    private static ArrayList getSurroundingSquares(int id)
    {
        int[] dir = {-1*width-1, -1*width, -1*width+1,
                    -1, 1,
                    width-1, width, width+1};
        ArrayList possibleMoveToLoc = new ArrayList();
        for (int direction : dir)
        {
            if (inField(id, direction + id))
            {
                possibleMoveToLoc.add(id + direction);
            }
        }
        return possibleMoveToLoc;
    }
    
    
    private static boolean inField(int id1, int id2)
    {
        if (id2 >= width * height || id2 < 0)
            return false;
        //crossing over the left side
        if (id2 % width > id1 % width && id1 % width == 0)
            return false;
        //over the left side
        if (id1 % width > id2 % width && id2 % width == 0)
            return false;
        return true;
    }
}
