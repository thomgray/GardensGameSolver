package gardens;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class Cell extends JComponent{
    public static int cellWidth = 40;
    
    public final int x, y;
    private State state;
    private Color color;
    
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.state=State.Unknown;
        
        this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        
        setListeners();
    }

    public void setColor(Color color) {
        
        this.color = (color==null? Color.WHITE:color);
    }
    
    public Color getColor(){
        return (color==null? Color.WHITE:color);
    }
    
    public void setState(State s){
        this.state=s;
        if (s==State.Void) this.setColor(Color.BLACK);
        this.repaint();
    }
    
    public State getState(){
        return state;
    }
    
    public boolean isAdjacent(Cell in){
        boolean out = true;
        if (in.x<this.x-1 || in.x>x+1) out = false;
        if (in.y<y-1 || in.y>y+1) out = false;
        if (in.y==y && in.x==x) out = false;
        
        return out;    
    }

    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        
        g2.setColor(this.getColor());
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        g.setColor(ColorWheel.getComplementColor(color));
        
        switch (state) {
            case Empty: case Void:
                g2.fillOval(cellWidth/2-2, cellWidth/2-2, 4, 4);
                break;
            case Tree:
                g2.setFont(new Font("Verdana",Font.BOLD,13));
                g2.drawString("T", cellWidth/2-(g.getFontMetrics().charWidth('T')/2), cellWidth/2+(g.getFontMetrics().getAscent()/2));
                break;
            case Unknown:
                break;
            default:
                throw new AssertionError();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Cell.cellWidth, Cell.cellWidth);
    }

    private void setListeners() {
        this.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (state==State.Void) return;
                
                setState(State.shiftState(state));
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
        });
    }
    
    @Override
    public String toString() {
        return ""+this.x+", "+y+"";
    }
    
    
    
}