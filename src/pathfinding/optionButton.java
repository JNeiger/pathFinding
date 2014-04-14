/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathfinding;

import javax.swing.JButton;

/**
 *
 * @author Joseph
 */
public class optionButton extends JButton{
    private currentButton tag;
    public optionButton(String s, currentButton tag) {
        super();
        this.setSize(50,150);
        this.setText(s);
        this.tag = tag;
        this.setBackground(null);
    }
    
    public currentButton getTag()
    {
        return tag;
    }
}
