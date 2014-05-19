/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathfinding;

import java.awt.Color;
import java.awt.Graphics;
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
    private int parentID;
    private int hScore;
    private int gScore;
    private int outlineColor;
    
    public square(int rowId)
    {
        super();
        this.wall = false;
        this.start = false;
        this.end = false;
        this.parentID = -1;
        this.hScore = 0;
        this.rowId = rowId;
        this.gScore = 0;
        this.outlineColor = 0;
        this.setSize(100,100);
        this.setBackground(Color.black);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        switch (outlineColor)
        {
            case 0:
                if (wall)
                    g.setColor(Color.blue);
                if (end)
                    g.setColor(Color.red);
                if (start)
                    g.setColor(Color.green);
                break;
            case 1:
                g.setColor(Color.green);
                break;
            case 2:
                g.setColor(Color.CYAN);
                break;
            case 3:
                g.setColor(Color.gray);
                break;
        }
        g.drawLine(2, 2, 2, 97);
        g.drawLine(2, 2, 97, 2);
        g.drawLine(97, 2, 97, 97);
        g.drawLine(2, 97, 97, 97);
        
        
        g.setColor(Color.white);
        g.drawString(Integer.toString(rowId), 5, 15);
        g.drawString(Integer.toString(hScore), 75, 90);
        g.drawString(Integer.toString(gScore), 5, 90);
        g.drawString(Integer.toString(gScore + hScore), 75, 15);
        
        if (parentID >= 0)
        {
            int xDir = (parentID % 7 - rowId % 7 + 2);
            int yDir = (parentID / 7 - rowId / 7 + 2);
            g.drawOval(45, 45, 10, 10);
            g.drawLine(xDir * 5 + 40, yDir * 5 + 40, xDir * 25, yDir * 25);
        }
        
        
    }
    
    public void toggleWall()
    {
        wall = !wall;
        if (wall) {
            this.setBackground(Color.blue);
        } else {
            this.setBackground(Color.black);
        }
    }
    
    public void setStart(boolean b)
    {
        start = b;
        if (start) {
            this.setBackground(Color.GREEN);
        } else {
            this.setBackground(Color.black);
        }
    }
    
    public void setEnd(boolean b)
    {
        end = b;
        if (end) {
            this.setBackground(Color.red);
        } else {
            this.setBackground(Color.black);
        }
    }
    
    public void setGScore(int s)
    {
        gScore = s;
    }
    
    public void setHScore(int h)
    {
        hScore = h;
    }
    
    public void setOutline(int colorID)
    {
        outlineColor = colorID;
    }
    
    public boolean getStart()
    {
        return start;
    }
    
    public boolean getEnd()
    {
        return end;
    }
    
    public boolean getWall()
    {
        return wall;
    }
    
    public void setParentID(int id)
    {
        parentID = id;
    }
    
    public int getParentID()
    {
        return parentID;
    }
    
    public int getId()
    {
        return rowId;
    }
    
    public int getGScore()
    {
        return gScore;
    }
    
    public int getFScore()
    {
        return hScore + gScore;
    }
}
