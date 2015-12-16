package utils;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class Cell extends JComponent{
    public static final int cellWidth = 30;
    
    int x,y;

    public Cell(int x, int y) {
        this.x=x; this.y=y;
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(tatami.Cell.cellWidth, tatami.Cell.cellWidth);
    }
    
    
}