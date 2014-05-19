package pathfinding;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Joseph
 */
public class PathFinding {
    
    private static JFrame mainGui;
    private static SquareType selectedButton;
    private static final int width = 7, height = 5;
    private static ArrayList open, closed;
    
    public static void main(String[] args) 
    {
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
        
        optionButton selectBegin = new optionButton("Select Begin", SquareType.start);
        optionButton selectEnd = new optionButton("Select End", SquareType.end);
        optionButton selectWall = new optionButton("Select Walls", SquareType.wall);
        
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
        
        JButton startPathFinding = new JButton();
        startPathFinding.setSize(50,150);
        startPathFinding.setText("Find Quickest Path");
        startPathFinding.setBackground(null);
        startPathFinding.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                startPathFinding(e);
            }
        });
        
        options.add(startPathFinding);
        contentPane.add(options);
        contentPane.validate();
        contentPane.repaint();
        
        mainGui.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        mainGui.setVisible(true);
        
        selectedButton = SquareType.none;
        open = new ArrayList();
        closed = new ArrayList();
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
            selectedButton = SquareType.none;
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
        for (int i = 0; i < 3; i++)
        {
            Component[] b = buttonContainer.getComponents();
            //we then access all the option buttons and set their background to the defualt
            ((optionButton)b[i]).setBackground(null);
        }
    }
    
    public static void squareClicked(MouseEvent e)
    {
        square s = (square)e.getSource();
        
        switch(selectedButton)
        {
            case start:
                clearSquaresOfType(SquareType.start);
                s.setStart(true);
                break;
                
            case end:
                clearSquaresOfType(SquareType.end);
                s.setEnd(true);
                break;
                
            case wall:
                if (!s.getEnd() || !s.getStart()) {
                    s.toggleWall();
                }
                break;
        }
    }
    
    private static void clearSquaresOfType(SquareType sel)
    {
        Container contentPane = mainGui.getContentPane();
        int id;
        
        if (getSquareOfType(sel) == null) { //there is no start or end
            return;
        }else
        {
            id = getSquareOfType(sel).getId();
        }
        
        if (sel == SquareType.start) {
            getSquare(id).setStart(false);
        } else {
            getSquare(id).setEnd(false);
        }
    }
    
    private static square getSquareOfType(SquareType sel)
    {
        Container contentPane = mainGui.getContentPane();
        square s;
        int id = -1;
        
        for (int i = 0; i < width * height; i++)
        {
            s = (square)contentPane.getComponent(i);
            switch (sel)
            {
                case start:
                    if (s.getStart()) {
                        id = s.getId();
                    }
                    break;
                    
                case end:
                    if (s.getEnd()) {
                        id = s.getId();
                    }
                    break;
            }
        }
        return getSquare(id);
    }
    
    private static square getSquare(int id)
    {
        Container contentPane = mainGui.getContentPane();
        for (int i = 0; i < width * height; i++)
        {
            square s = (square)contentPane.getComponent(i);
            if (s.getId() == id)
                return s;
        }
        return null;
    }
    
    private static boolean inField(int start, int end)
    {
        if (start % width == 0 && end % width == width - 1)
            return false;
        
        if (start % width == width - 1 && end % width == 0)
            return false;
        
        if (end < 0 || end > width * height - 1)
            return false;
        
        return true;
    }
    
    private static ArrayList getSurrounding(int id)
    {
        int[] directions = { -1 * width - 1, -1 * width, -1 * width + 1,
                            -1,  1,
                            width - 1, width, width + 1};
        
        ArrayList squares = new ArrayList();
         
        for (int i = 0; i < directions.length; i++)
        {
            int newId = id + directions[i];
            if (inField(id, newId))
            {
                squares.add(getSquare(newId));
            }
        }
        
        return squares;
    }
    
    private static ArrayList getValidSurroudingSquares(square parent)
    {
        ArrayList squares = getSurrounding(parent.getId());
        
        for (int i = 0; i < squares.size(); i++)
        {
            square s = (square)squares.toArray()[i];
            
            if (s.getWall() || closed.contains(s))
            {
                squares.remove(s);
                i--;
            }else
            {
                if (!s.getEnd())
                    s.setOutline(1);
            }
        }
        
        return setParents(squares, parent);
    }
    
    private static ArrayList setParents(ArrayList collection, square parent)
    {
        for (Object o : collection)
        {
            square s = (square)o;
            
            
            if (s.getId() - parent.getId() % width == 0 ||
                    (s.getId() - parent.getId()) / 2 == 0)
            {
                //Cardinal direction from parent
                if (s.getGScore() >= parent.getGScore() + 10 || s.getGScore() == 0)
                {
                    s.setParentID(parent.getId());
                    s.setGScore(parent.getGScore() + 10);
                }
            }else
            {
                if (s.getGScore() >= parent.getGScore() + 14 || s.getGScore() == 0)
                {
                    s.setParentID(parent.getId());
                    s.setGScore(parent.getGScore() + 14);
                }
            }
            
            s.setHScore(estimateHScore(s.getId()));
        }
        
        return collection;
    }
    
    private static int estimateHScore(int id)
    {
        int end = getSquareOfType(SquareType.end).getId();
        int sum = 0;
        
        //Horizontal and vertical estimate
        sum += Math.abs(id % width - end % width) * 10; 
        sum += Math.abs(id / width - end / width) * 10;
        
        return sum;
    }
    
    private static square getClosest()
    {
        square lowest = (square)open.get(0);
        
        for (int i = 1; i < open.size(); i++)
        {
            square s = (square)open.get(i);
            if (s.getFScore() < lowest.getFScore())
                lowest = s;
        }
        
        return lowest;
    }
    
    private static void startPathFinding(ActionEvent e)
    {        
        open.addAll(getValidSurroudingSquares(getSquareOfType(SquareType.start)));
        closed.add(getSquareOfType(SquareType.start));
        
        while (!closed.contains(getSquareOfType(SquareType.end)) && !open.isEmpty())
        {
            square closest = getClosest();
            closest.repaint();
            
            closed.add(closest);
            open.remove(closest);
            open.addAll(getValidSurroudingSquares(closest));
        }
        
        drawPath(getSquareOfType(SquareType.end).getParentID());
    }
    
    private static void drawPath(int sId)
    {
        if (sId < 0)
            return;
        
        square s = getSquare(sId);
        
        if (s.getParentID() != getSquareOfType(SquareType.start).getId())
            drawPath(s.getParentID());        
        
        s.setOutline(2);
    }
}
