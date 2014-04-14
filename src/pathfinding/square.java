/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathfinding;

import java.awt.Color;
import javax.swing.JPanel;


/**
 *
 * @author Joseph
 */
public class square extends JPanel{
    private final int rowId;
    private boolean wall;
    private boolean start;
    private boolean end;
    private int directionToParent;
    
    public square(int rowId)
    {
        super();
        this.rowId = rowId;
        this.setSize(100,100);
        this.setBackground(Color.black);
    }
    
    public void toggleWall()
    {
        wall = !wall;
        if (wall)
            this.setBackground(Color.blue);
        else
            this.setBackground(Color.black);
    }
    
    public void setStart(boolean b)
    {
        start = b;
        if (start)
            this.setBackground(Color.green);
        else
            this.setBackground(Color.black);
    }
    
    public void setEnd(boolean b)
    {
        end = b;
        if (end)
            this.setBackground(Color.red);
        else
            this.setBackground(Color.black);
    }
    
    public boolean getStart()
    {
        return start;
    }
    
    public boolean getEnd()
    {
        return end;
    }
    
    public void setDirectionToParent(int dir)
    {
        directionToParent = dir;
    }
    
    public int getId()
    {
        return rowId;
    }
}
