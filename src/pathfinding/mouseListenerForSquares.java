/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathfinding;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Joseph
 */
public class mouseListenerForSquares implements MouseListener{

    @Override
    public void mouseClicked(MouseEvent e) {
        PathFinding.squareClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.