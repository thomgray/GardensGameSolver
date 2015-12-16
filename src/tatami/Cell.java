package tatami;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import thom.swing.MoreColor;

public class Cell extends JComponent{
    public static final int cellWidth = 40;
    static Color borderCol = MoreColor.DarkGray;
    static Color matBorderCol = MoreColor.LightGrey;
    int x,y;
    int state=0;
    int max;
    
    Mat mat;

    public Cell(int x, int y, int max) {
        this.x=x; this.y=y;
        this.max = max;
        
        //this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        this.addMouseListener(ml);
    }
    
    void setMat(Mat in){this.mat = in;}
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(tatami.Cell.cellWidth, tatami.Cell.cellWidth);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        
        
        
        g2.setColor(MoreColor.DarkOliveGreen);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        g2.setColor(borderCol);
        g2.setStroke(new BasicStroke(1));
        g2.drawRect(0, 0, this.getWidth(), this.getHeight());
        
        g2.setColor(Cell.matBorderCol);
        g2.setStroke(new BasicStroke(2));
        if (this.isTopEdge()) g2.drawLine(1, 1, this.getWidth(), 1);
        if (this.isBottomEdge()) g2.drawLine(1, this.getHeight(), this.getWidth(), this.getHeight());
        if (this.isLeftEdge()) g2.drawLine(1, 1, 1, this.getHeight());
        if (this.isRightEdge()) g2.drawLine(this.getWidth(), 1, this.getWidth(), this.getHeight());
        
        if (state==0) return;
        String s= String.valueOf(state);
        Dimension d = this.getPreferredSize();
        g2.setColor(Color.WHITE);
        //g2.setStroke(new BasicStroke(1));
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        FontMetrics fm = g2.getFontMetrics();
        int hoff = fm.stringWidth(s);
        int voff = fm.getAscent();
        g2.drawString(s, d.width/2 - hoff/2 , d.height/2+voff/2);
    }
    
    MouseListener ml = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            state++;
            if (state>max) state = 0;
            repaint();
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
    };
   
    boolean isTopEdge(){
        if (this.mat==null) return false;
        return !mat.containsCell(x, y-1);
    }
    boolean isBottomEdge(){
        if (this.mat==null) return false;
        return !mat.containsCell(x, y+1);
    }
    boolean isLeftEdge(){
        if (this.mat==null) return false;
        return !mat.containsCell(x-1, y);
    }
    boolean isRightEdge(){
        if (this.mat==null) return false;
        return !mat.containsCell(x+1, y);
    }

    @Override
    public String toString() {
        return "("+this.x+", "+y+")";
    }
    
    public void setState(int i){
        if (i>max) throw new java.lang.IllegalArgumentException("Needs a number within the max!");
        this.state=i;
        this.repaint();
    }
    
    
}
